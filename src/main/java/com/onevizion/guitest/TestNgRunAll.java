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

import com.onevizion.uitest.api.SeleniumAnnotationTransformer;
import com.onevizion.uitest.api.annotation.SeleniumTest;
import com.onevizion.uitest.api.restapi.CreateProcess;

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

        Date finishDate = Calendar.getInstance().getTime();
        long duration = finishDate.getTime() - startDate.getTime();
        long durationMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        String durationMinutesStr = Long.toString(durationMinutes);

        updateProcessDuration(restApiUrl, restApiCredential, processTrackorKey, durationMinutesStr);
    }

    private static String createProcess(String restApiUrl, String restApiCredential, String restApiVersion, String browser) {
        if (restApiUrl.isEmpty() || restApiCredential.isEmpty()) {
            return null;
        }

        String processTrackorKey = null;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(cal.getTime());

        try {
            processTrackorKey = CreateProcess.create(restApiUrl, restApiCredential, restApiVersion, browser, date);
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

    private static void updateProcessDuration(String restApiUrl, String restApiCredential, String processTrackorKey, String durationMinutesStr) {
        if (restApiUrl.isEmpty() || restApiCredential.isEmpty()) {
            return;
        }

        try {
            CreateProcess.updateDuration(restApiUrl, restApiCredential, processTrackorKey, durationMinutesStr);
        } catch (Exception e) {
            logger.error("TestNgRunAll.updateProcessDuration call REST API Unexpected exception: " + e.getMessage());
        }
    }

}