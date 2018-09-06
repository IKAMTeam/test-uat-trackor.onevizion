package com.onevizion.guitest.test;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

import com.onevizion.guitest.AbstractSeleniumDefaultPage;
import com.onevizion.guitest.vo.ConfigFieldType;

@Component
//@SeleniumTest
//@SeleniumUser(name="")
public class AddNewComment1Test extends AbstractSeleniumDefaultPage {

    protected final static String CFID = "1000912037";

    @Test
    public void addCommentToCase() {
        Map<String, String> vals = new HashMap<String, String>();

        mainMenuHelper.selectMenuItem("Cases");
        qsHelper.searchValue(getGridIdx(), "C:Case ID", "102906");

        windowHelper.openModal(By.id(BUTTON_EDIT_ID_BASE + getGridIdx()));
        waitHelper.waitWebElement(By.id(BUTTON_OK_ID_BASE));
        waitHelper.waitConfigTabLoad(1L);

        tabHelper.goToTab(2L);
        waitHelper.waitConfigTabLoad(2L);

        tbHelper.editField(ConfigFieldType.MEMO, "AddNewComment1Test", null, "fe" + CFID + "_1_0", vals, null, 2);

        windowHelper.closeModal(By.id(BUTTON_OK_ID_BASE));
        waitHelper.waitGridLoad(getGridIdx(), getGridIdx());
    }

}