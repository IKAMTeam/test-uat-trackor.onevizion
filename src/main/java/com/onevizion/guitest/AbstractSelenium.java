package com.onevizion.guitest;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.SeleniumListener;

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

    @BeforeSuite(alwaysRun = true)
    public void prepare(ITestContext context) throws Exception {
        
    }

    @BeforeClass(alwaysRun = true)
    public void openBrowserAndLogin(ITestContext context) throws Exception {
        seleniumOpenBrowserAndLogin(context);
    }

    @AfterClass(alwaysRun = true)
    public void closeBrowser(ITestContext context) throws Exception {
        seleniumCloseBrowser(context);
    }

}