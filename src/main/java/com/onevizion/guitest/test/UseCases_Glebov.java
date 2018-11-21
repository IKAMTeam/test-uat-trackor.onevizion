package com.onevizion.guitest.test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onevizion.guitest.AbstractSeleniumLoginPage;
import com.onevizion.uitest.api.AbstractSeleniumCore;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.helper.grid.Grid2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

import com.onevizion.uitest.api.annotation.SeleniumTest;
import com.onevizion.uitest.api.vo.ConfigFieldType;

import javax.annotation.Resource;

@Component
@SeleniumTest
public class UseCases_Glebov extends AbstractSeleniumLoginPage {

    protected final static String USER_NAME = "";
    protected final static String USER_PWD = "";

    /**
     * <p><b>Default Page: Issue</b></p>
     * <p><b>View: Default (Unsaved View)</b></p>
     * <p><b>Filter: Default (Unsaved Filter)</b></p>
     * <p>1. Find a desired Issue through a quick search on "I:Summary"</p>
     * <p>2. Click on a hyperlink in the field "I:Issue ID"</p>
     * <p>3. In the opened window (applet) "Edit Issue", go to the tab "T:Time Record"</p>
     * <p>4. Click on the "Add" button </p>
     * <p>5. In the opened window (applet) "Add Time Record", write hours into the "T:Spent Hours" field</p>
     * <p>6. Click on the "OK" button</p>
     * <p>7. Go to the tab "I:General Info"</p>
     * <p>8. Change a value of the field "I:Status" to "Ready for Test" </p>
     * <p>9. Write a comment into the "I:Comment" field</p>
     * <p>10. Attach a pdf file to "I:File" field</p>
     * <p>11. Click on the "OK" button</p>
     */

    @Test
    public void AddTimeRecordAndChangeIssueStatus() {
        Map<String, String> vals = new HashMap<String, String>();

        login.login(USER_NAME, USER_PWD);
        wait.waitWebElement(By.id(GRID_ID_BASE+getGridIdx()));
        filter.clearFilter(getGridIdx());

//    Default Page: Issue
//    View: Default (Unsaved View)
//    Filter: Default (Unsaved Filter)

//            1. Find a desired Issue through a quick search on "I:Summary"
        qs.searchValue(getGridIdx(), "I:Summary", "Issue for testing");

//            2. Click on a hyperlink in the field "I:Issue ID"
        window.openModal(By.linkText("Other-136480"));
        wait.waitFormLoad();

//            3. In the opened window (applet) "Edit Issue", go to the tab "T:Time Record"
        tab.goToTab(4L);
        wait.waitTabLoad(4L);

//            4. Click on the "Add" button
        window.openModal(By.id(BUTTON_ADD_ID_BASE+"4"));
        wait.waitFormLoad();

//            5. In the opened window (applet) "Add Time Record", write hours into the "T:Spent Hours" field
        tb.editField(ConfigFieldType.TEXT,"0.05",null, "fe100092729_1_0", vals,  null,1);

//            6. Click on the "OK" button
        window.closeModal(By.id(BUTTON_OK_ID_BASE));

//            7. Go to the tab "I:General Info"
        tab.goToTab(1L);
        wait.waitTabLoad(1L);

//            8. Change a value of the field "I:Status" to "Ready for Test"

        Select select = new Select(seleniumSettings.getWebDriver().findElement(By.name("fe100092444_1_0")));
        select.selectByValue("Ready for test");

//            9. Write a comment into the "I:Comment" field
        tb.editField(ConfigFieldType.TEXT, "Issue for testing", null, "fe100092605_1_0", vals, null,1);

//            10. Attach a pdf file to "I:File" field
        WebElement we_iframe = seleniumSettings.getWebDriver().findElement(By.id("ifrmHideForm"));
        seleniumSettings.getWebDriver().switchTo().frame(we_iframe);
        seleniumSettings.getWebDriver().findElement(By.name("fe100097048_1_0_100093613405")).sendKeys("C:\\Users\\User\\Desktop\\инф.pdf");

        seleniumSettings.getWebDriver().switchTo().defaultContent();

//            11. Click on the "OK" button
        window.closeModal(By.id(BUTTON_OK_ID_BASE));

        wait.waitGridLoad(getGridIdx(), getGridIdx());

        logoff.logoff();

    }
}