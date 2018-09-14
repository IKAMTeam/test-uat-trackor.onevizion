package com.onevizion.guitest;

import com.onevizion.uitest.api.UserProperties;

public abstract class AbstractSeleniumDefaultPage extends AbstractSelenium {

    public AbstractSeleniumDefaultPage() {
        super();
    }

    @Override
    protected final void login(String userName, String password) {
        seleniumSettings.setUserProperties(new UserProperties());
        seleniumSettings.getUserProperties().setDateFormat("MM/DD/YYYY");
        seleniumSettings.getUserProperties().setJavaTimeFormat("hh:mm:ss aa");

        login.login(userName, password);
    }

    @Override
    protected void dataPreparation() {
        
    }

    @Override
    protected final void openInternalPage() {
        
    }

}