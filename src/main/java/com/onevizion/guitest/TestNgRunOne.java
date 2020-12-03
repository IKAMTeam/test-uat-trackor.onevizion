package com.onevizion.guitest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import com.onevizion.uitest.api.SeleniumAnnotationTransformer;
import com.onevizion.uitest.api.annotation.SeleniumTest;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;

public class TestNgRunOne extends TestNgRun {

    public static void main(String[] args) throws ClassNotFoundException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:com/onevizion/guitest/beans.xml");
        String screenshotsPath = (String) ctx.getBean("screenshotsPath");
        String ciAddr = (String) ctx.getBean("ciAddr");
        String testUser = (String) ctx.getBean("testUser");
        Boolean remoteWebDriver = (Boolean) ctx.getBean("remoteWebDriver");
        String fullClassName = (String) ctx.getBean("testName");
        ((AbstractApplicationContext) ctx).close();

        Map<String, String> suiteParams = new HashMap<String, String>();
        suiteParams.put("test.selenium.screenshotsPath", screenshotsPath);
        suiteParams.put("test.selenium.ciAddr", ciAddr);
        suiteParams.put("test.selenium.remoteWebDriver", remoteWebDriver.toString());

        XmlSuite suite = new XmlSuite();
        suite.setName("Selenium tests suite");
        suite.setParameters(suiteParams);

        Class<?> testClass = Class.forName(fullClassName);
        if (!testClass.isAnnotationPresent(SeleniumTest.class)) {
            throw new SeleniumUnexpectedException("Annotation @SeleniumTest not exist in file " + fullClassName);
        }

        createXmlTest(suite, fullClassName, testUser);

        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(suite);
        TestNG testNg = new TestNG();
        testNg.setXmlSuites(suites);
        testNg.addListener(new SeleniumAnnotationTransformer());
        testNg.run();
    }

}