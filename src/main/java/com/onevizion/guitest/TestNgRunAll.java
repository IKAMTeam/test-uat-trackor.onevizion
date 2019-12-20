package com.onevizion.guitest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import com.onevizion.uitest.api.SeleniumAnnotationTransformer;
import com.onevizion.uitest.api.annotation.SeleniumTest;
import com.onevizion.uitest.api.restapi.CreateProcess;

public class TestNgRunAll extends TestNgRun {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:com/onevizion/guitest/beans.xml");
        String screenshotsPath = (String) ctx.getBean("screenshotsPath");
        String ciAddr = (String) ctx.getBean("ciAddr");
        String testUser = (String) ctx.getBean("testUser");
        Boolean remoteWebDriver = (Boolean) ctx.getBean("remoteWebDriver");
        String restApiUrl = (String) ctx.getBean("restApiUrl");
        String restApiCredential = (String) ctx.getBean("restApiCredential");
        String restApiVersion = (String) ctx.getBean("restApiVersion");
        String browser = (String) ctx.getBean("browser");
        ((AbstractApplicationContext) ctx).close();

        String processTrackorKey = createProcess(restApiUrl, restApiCredential, restApiVersion, browser);

        checkAnnotationInTests(new File(mainFolderWithTests));

        Map<String, String> suiteParams = new HashMap<String, String>();
        suiteParams.put("test.selenium.screenshotsPath", screenshotsPath);
        suiteParams.put("test.selenium.ciAddr", ciAddr);
        suiteParams.put("test.selenium.remoteWebDriver", remoteWebDriver.toString());
        suiteParams.put("test.selenium.processTrackorKey", processTrackorKey);

        XmlSuite suiteParallel = new XmlSuite();
        suiteParallel.setName("Selenium tests suite parallel");
        suiteParallel.setParallel("tests");
        suiteParallel.setThreadCount(1);
        suiteParallel.setParameters(suiteParams);

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(SeleniumTest.class));

        for (BeanDefinition bd : scanner.findCandidateComponents(mainPackageWithTests)) {
            String fullClassName = bd.getBeanClassName();
            createXmlTest(suiteParallel, fullClassName, testUser);
        }

        int testsCount = suiteParallel.getTests().size();
        logger.info("Number of tests " + testsCount);
        updateProcessTestsCount(restApiUrl, restApiCredential, processTrackorKey, testsCount);

        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(suiteParallel);
        TestNG testNg = new TestNG();
        testNg.setXmlSuites(suites);
        testNg.setAnnotationTransformer(new SeleniumAnnotationTransformer());
        testNg.run();

        updateProcess(restApiUrl, restApiCredential, processTrackorKey);
    }

    private static String createProcess(String restApiUrl, String restApiCredential, String restApiVersion, String browser) {
        if (restApiUrl.isEmpty() || restApiCredential.isEmpty()) {
            return null;
        }

        String processTrackorKey = null;

        try {
            processTrackorKey = CreateProcess.create(restApiUrl, restApiCredential, restApiVersion, browser);
        } catch (Exception e) {
            logger.error("TestNgRunAll.createProcess call REST API Unexpected exception: " + e.getMessage());
        }

        return processTrackorKey;
    }

    private static void updateProcessTestsCount(String restApiUrl, String restApiCredential, String processTrackorKey, int testsCount) {
        if (restApiUrl.isEmpty() || restApiCredential.isEmpty()) {
            return;
        }

        try {
            CreateProcess.updateTestsCount(restApiUrl, restApiCredential, processTrackorKey, testsCount);
        } catch (Exception e) {
            logger.error("TestNgRunAll.updateProcessTestsCount call REST API Unexpected exception: " + e.getMessage());
        }
    }

    private static void updateProcess(String restApiUrl, String restApiCredential, String processTrackorKey) {
        if (restApiUrl.isEmpty() || restApiCredential.isEmpty()) {
            return;
        }

        try {
            CreateProcess.update(restApiUrl, restApiCredential, processTrackorKey);
        } catch (Exception e) {
            logger.error("TestNgRunAll.updateProcess call REST API Unexpected exception: " + e.getMessage());
        }
    }

}