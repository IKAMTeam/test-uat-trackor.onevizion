package com.onevizion.guitest;

public abstract class AbstractSeleniumDefaultPage extends AbstractSelenium {

    public AbstractSeleniumDefaultPage() {
        super();
    }

    @Override
    protected final void login(String userName, String password) {
        seleniumSettings.setUserProperties(new UserProperties());
        seleniumSettings.getUserProperties().setDateFormat("MM/DD/YYYY");
        seleniumSettings.getUserProperties().setJavaTimeFormat("hh:mm:ss aa");

        loginHelper.login(userName, password);
    }

    @Override
    protected void dataPreparation() {
        
    }

    @Override
    protected final void openInternalPage() {
        
    }

}