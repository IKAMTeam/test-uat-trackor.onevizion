package com.onevizion.guitest.test;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

import com.onevizion.guitest.AbstractSeleniumLoginPage;
import com.onevizion.uitest.api.vo.ConfigFieldType;

@Component
//@SeleniumTest
//@SeleniumUser(name="")
public class AddNewComment2Test extends AbstractSeleniumLoginPage {

    protected final static String USER_NAME = "";
    protected final static String USER_PWD = "";

    protected final static String CFID = "1000912037";

    @Test
    public void addCommentToCase() {
        Map<String, String> vals = new HashMap<String, String>();

        loginHelper.login(USER_NAME, USER_PWD);

        mainMenu.selectMenuItem("Cases");
        qsHelper.searchValue(getGridIdx(), "C:Case ID", "102906");

        window.openModal(By.id(BUTTON_EDIT_ID_BASE + getGridIdx()));
        wait.waitWebElement(By.id(BUTTON_OK_ID_BASE));
        wait.waitConfigTabLoad(1L);

        tabHelper.goToTab(2L);
        wait.waitConfigTabLoad(2L);

        tbHelper.editField(ConfigFieldType.MEMO, "AddNewComment2Test", null, "fe" + CFID + "_1_0", vals, null, 2);

        window.closeModal(By.id(BUTTON_OK_ID_BASE));
        wait.waitGridLoad(getGridIdx(), getGridIdx());

        logoffHelper.logoff();
    }

}