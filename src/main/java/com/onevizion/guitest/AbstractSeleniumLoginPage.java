package com.onevizion.guitest;

public abstract class AbstractSeleniumLoginPage extends AbstractSelenium {

    public AbstractSeleniumLoginPage() {
        super();
    }

    @Override
    protected final void login(String userName, String password) {
        seleniumSettings.setUserProperties(new UserProperties());
        seleniumSettings.getUserProperties().setDateFormat("MM/DD/YYYY");
        seleniumSettings.getUserProperties().setJavaTimeFormat("hh:mm:ss aa");
    }

    @Override
    protected void dataPreparation() {
        
    }

    @Override
    protected final void openInternalPage() {
        
    }

}