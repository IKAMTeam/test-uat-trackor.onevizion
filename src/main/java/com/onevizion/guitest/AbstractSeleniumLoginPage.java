package com.onevizion.guitest;

import com.onevizion.uitest.api.UserProperties;

public abstract class AbstractSeleniumLoginPage extends AbstractSelenium {

    public AbstractSeleniumLoginPage() {
        super();
    }

    @Override
    protected String getModuleName() {
        return "";
    }

    @Override
    protected final void login(String userName, String password) {
        //protected final void loginIntoSystem(String userName, String password) { //for 19.3
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