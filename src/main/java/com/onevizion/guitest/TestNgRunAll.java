package com.onevizion.guitest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import com.onevizion.guitest.annotation.SeleniumTest;
import com.onevizion.guitest.restapi.CreateProcess;

public class TestNgRunAll extends TestNgRun {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Date startDate = Calendar.getInstance().getTime();

        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:com/onevizion/guitest/beans.xml");
        String screenshotsPath = (String) ctx.getBean("screenshotsPath");
        String ciAddr = (String) ctx.getBean("ciAddr");
        String testUser = (String) ctx.getBean("testUser");
        Boolean remoteWebDriver = (Boolean) ctx.getBean("remoteWebDriver");
        String restApiUrl = (String) ctx.getBean("restApiUrl");
        String restApiCredential = (String) ctx.getBean("restApiCredential");
        String browser = (String) ctx.getBean("browser");
        ((AbstractApplicationContext) ctx).close();

        checkAnnotationInTests(new File(mainFolderWithTests));

        Map<String, String> suiteParams = new HashMap<String, String>();
        suiteParams.put("test.selenium.screenshotsPath", screenshotsPath);
        suiteParams.put("test.selenium.ciAddr", ciAddr);
        suiteParams.put("test.selenium.remoteWebDriver", remoteWebDriver.toString());

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

        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(suiteParallel);
        TestNG testNg = new TestNG();
        testNg.setXmlSuites(suites);
        testNg.setAnnotationTransformer(new SeleniumAnnotationTransformer());
        testNg.run();

        Date finishDate = Calendar.getInstance().getTime();
        long duration = finishDate.getTime() - startDate.getTime();
        long durationMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        String durationMinutesStr = Long.toString(durationMinutes);

        saveResult(restApiUrl, restApiCredential, browser, durationMinutesStr, testsCount);
    }

    private static void saveResult(String restApiUrl, String restApiCredential, String browser, String durationMinutesStr, int testsCount) {
        if (restApiUrl.isEmpty() || restApiCredential.isEmpty()) {
            return;
        }

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(cal.getTime());

        try {
            CreateProcess.create(restApiUrl, restApiCredential, browser, date, durationMinutesStr, testsCount);
        } catch (Exception e) {
            logger.error("TestNgRunAll call REST API Unexpected exception: " + e.getMessage());
        }
    }

}