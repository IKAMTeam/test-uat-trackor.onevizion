package com.onevizion.guitest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.onevizion.uitest.api.annotation.SeleniumUser;

public class TestNgRun {

    protected final static Logger logger = LoggerFactory.getLogger(TestNgRun.class);
 
    protected final static String sep = File.separator;

    protected final static String mainFolderWithTests = "src" + sep + "main" + sep + "java" + sep + "com" + sep + "onevizion" + sep + "guitest" + sep + "test";

    protected final static String mainPackageWithTests = "com.onevizion.guitest.test";

    protected static void createXmlTest(XmlSuite xmlSuite, String fullClassName, String testUser) throws ClassNotFoundException {
        XmlTest test = new XmlTest(xmlSuite);

        String testName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
        test.setName(testName);
        test.setXmlClasses(Arrays.asList(new XmlClass(fullClassName)));
        Map<String, String> classParams = new HashMap<String, String>();
        classParams.put("test.selenium.user", testUser + getSeleniumUser(fullClassName));
        test.setParameters(classParams);
    }

    private static String getSeleniumUser(String fullClassName) throws ClassNotFoundException {
        String testUserEnd = "";

        Class<?> testClass = Class.forName(fullClassName);
        if (testClass.isAnnotationPresent(SeleniumUser.class)) {
            SeleniumUser seleniumUser = (SeleniumUser) testClass.getAnnotation(SeleniumUser.class);
            testUserEnd = seleniumUser.name();
        }

        return testUserEnd;
    }

    protected static void checkAnnotationInTests(File folder) throws IOException {
        File[] filesList = folder.listFiles();
        for (File f : filesList) {
            if (f.isDirectory()) {
                checkAnnotationInTests(f);
            } else if (f.isFile()) {
                String stringContents = fileToString(f);
                if (f.getName().endsWith("Test.java")) {
                    if (!stringContents.contains("@SeleniumTest")) {
                        logger.error("Annotation @SeleniumTest not exist in file " + f.getName());
                    }
                } else {
                    if (stringContents.contains("@SeleniumTest")) {
                        logger.error("Annotation @SeleniumTest exist in file " + f.getName());
                    }
                    if (stringContents.contains("@SeleniumUser")) {
                        logger.error("Annotation @SeleniumUser exist in file " + f.getName());
                    }
                }
            }
        }
    }

    private static String fileToString(File f) throws IOException {
        final BufferedReader reader = new BufferedReader(new FileReader(f));
        final StringBuilder contents = new StringBuilder();
        while (reader.ready()) {
            contents.append(reader.readLine());
        }
        reader.close();
        return contents.toString();
    }

}