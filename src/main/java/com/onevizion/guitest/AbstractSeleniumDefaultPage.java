package com.onevizion.guitest;

public abstract class AbstractSeleniumDefaultPage extends AbstractSelenium {

    public AbstractSeleniumDefaultPage() {
        super();
    }

    @Override
    protected String getModuleName() {
        return "";
    }

    @Override
    protected final void loginIntoSystem(String userName, String password) {
        login.login(userName, password);
    }

    @Override
    protected void dataPreparation() {
        
    }

    @Override
    protected final void openInternalPage() {
        
    }

}