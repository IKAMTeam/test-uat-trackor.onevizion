package com.onevizion.guitest;

public abstract class AbstractSeleniumLoginPage extends AbstractSelenium {

    public AbstractSeleniumLoginPage() {
        super();
    }

    @Override
    protected String getModuleName() {
        return "";
    }

    @Override
    protected final void loginIntoSystem(String userName, String password) {
        
    }

    @Override
    protected void dataPreparation() {
        
    }

    @Override
    protected final void openInternalPage() {
        
    }

}