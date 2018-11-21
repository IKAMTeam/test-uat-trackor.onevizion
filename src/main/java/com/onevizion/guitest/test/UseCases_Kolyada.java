package com.onevizion.guitest.test;

import java.util.HashMap;
import java.util.Map;

import com.onevizion.guitest.AbstractSeleniumLoginPage;
import com.onevizion.uitest.api.AbstractSeleniumCore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

import com.onevizion.guitest.AbstractSeleniumDefaultPage;
import com.onevizion.uitest.api.annotation.SeleniumTest;
import com.onevizion.uitest.api.vo.ConfigFieldType;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;

import static com.onevizion.uitest.api.AbstractSeleniumCore.BUTTON_EDIT_ID_BASE;
import static com.onevizion.uitest.api.AbstractSeleniumCore.BUTTON_OK_ID_BASE;
import static com.onevizion.uitest.api.AbstractSeleniumCore.getGridIdx;

@Component
@SeleniumTest
//@SeleniumUser(name="")

public class UseCases_Kolyada extends AbstractSeleniumLoginPage {

    protected final static String USER_NAME = "";
    protected final static String USER_PWD = "";

    /**
     * <p><b>Default Page: Issue</b></p>
     * <p><b>View: General Info</b></p>
     * <p><b>Filter: checked My Issues,
     * 		 Status not Closed,
     * 		 Issue Type = Bug</b></p>
     * <p>1. In the grid, find the line corresponding to the issue.</p>
     * <p>2. Click on the hyperlink in the "I: Issue ID" field.</p>
     * <p>3. In the window that opens, in the "Ver: Version" field, select "NA".</p>
     * <p>  ! it is important to remove all other values, leave only NA</p>
     * <p>4. In the field "I: Status" set the value to Closed.</p>
     * <p>5. Click the add comment button.</p>
     * <p>6. Enter the text of the comment "Could not reproduce the bug".</p>
     * <p>7. Click "OK".</p>
     */


    @Test
    public void work_with_Filter(){
        Map<String, String> vals = new HashMap<String, String>();

        login.login(USER_NAME, USER_PWD);

//        mainMenu.selectMenuItem("Issue");

        window.openModal(By.id(filter.BUTTON_OPEN + getGridIdx()));
        wait.waitWebElement(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
        wait.waitFormLoad();

// set filter
//        seleniumSettings.getWebDriver().findElement(By.name("btnClear")).click();
//
//        window.openModal(By.id("btnWPAttrib1"));
//        qs.searchValue(getGridIdx(), "Field Name", "I:Status");
//        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
//
//        seleniumSettings.getWebDriver().findElement(By.id("WPOperator1")).sendKeys("<>");
//
//        window.openModal(By.id("btnSelWPAttribValue1"));
//        checkbox.clickById("cb100097139");
//        window.closeModal(By.id("btnOK0"));
//
//        window.openModal(By.id("btnWPAttrib2"));
//        qs.searchValue(getGridIdx(), "Field Name", "I:Issue Type");
//        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));
//
//        seleniumSettings.getWebDriver().findElement(By.id("WPOperator2")).sendKeys("=");
//
//        window.openModal(By.id("btnSelWPAttribValue2"));
//        checkbox.clickById("cb10009260");
//        window.closeModal(By.id("btnOK0"));
//
//        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));

        /** 1. In the grid, find the line corresponding to the issue. */
        WebElement issue_id = seleniumSettings.getWebDriver().findElement(By.linkText("BPL-123504"));
        /** 2. Click on the hyperlink in the "I: Issue ID" field. */
        window.openModal(issue_id);

        /** 3. In the window that opens, in the "Ver: Version" field, select "NA". */
        window.openModal(By.id("idx1_but"));
        wait.waitWebElement(By.id("navNext0"));

        //seleniumSettings.getWebDriver().findElement(By.id("navNext0")).click();
        qs.searchValue(getGridIdx(), "Ver:Version", "NA");

        checkbox.clickById("cb10009696502");

        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE + getGridIdx()));

        /** 4. In the field "I: Status" set the value to Closed. */
        Select select = new Select(seleniumSettings.getWebDriver().findElement(By.name("fe100092444_1_0")));
        select.selectByValue("Closed");

        /** 5. Click the add comment button. */
        seleniumSettings.getWebDriver().findElement(By.id("btnAdd1")).click();

        /** 6. Enter the text of the comment "Could not reproduce the bug". */
        tb.editField(ConfigFieldType.MEMO, "Could not reproduce the bug", null, "fe100092605_5_0", vals, null, 1);

        /** 7. Click "OK". */
        window.closeModal(By.id(AbstractSeleniumCore.BUTTON_OK_ID_BASE));

        wait.waitGridLoad(getGridIdx(), getGridIdx());

        logoff.logoff();
    }

}
