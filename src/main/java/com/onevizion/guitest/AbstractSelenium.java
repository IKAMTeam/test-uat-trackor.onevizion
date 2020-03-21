package com.onevizion.guitest;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumListener;
import com.onevizion.uitest.api.UserProperties;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@ContextConfiguration(locations = { "classpath:com/onevizion/guitest/beans.xml" })
@Transactional(transactionManager = "transactionManager")
@Rollback(false)
@Listeners(SeleniumListener.class)
public abstract class AbstractSelenium extends AbstractSeleniumCore {

    public AbstractSelenium() {
        super();
    }

    @Override
    protected final void fillGlobalSettings() {
        
    }

    @Override
    protected final void fillUserSettings(String userName) {
        seleniumSettings.setUserProperties(new UserProperties());
        seleniumSettings.getUserProperties().setDateFormat("MM/DD/YYYY");
        seleniumSettings.getUserProperties().setJavaTimeFormat("hh:mm:ss aa");
    }

    @Override
    protected final String getErrorReport() {
        return "";
    }

    @BeforeClass(alwaysRun = true)
    public void openBrowserAndLogin(ITestContext context) throws Exception {
        WebDriverManager.chromedriver().setup();
        seleniumOpenBrowserAndLogin(context);
    }

    @AfterClass(alwaysRun = true)
    public void closeBrowser(ITestContext context) throws Exception {
        seleniumCloseBrowser(context);
    }

}