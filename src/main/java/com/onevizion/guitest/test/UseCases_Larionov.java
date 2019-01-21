package com.onevizion.guitest.test;
import java.util.HashMap;
import java.util.Map;

import com.onevizion.guitest.AbstractSeleniumLoginPage;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

import com.onevizion.uitest.api.annotation.SeleniumTest;
import com.onevizion.uitest.api.vo.ConfigFieldType;

@Component
@SeleniumTest

public class UseCases_Larionov extends AbstractSeleniumLoginPage {

    protected final static String USER_NAME = "";
    protected final static String USER_PWD = "";

    /**
     * <p><b>Default Page: Issue</b></p>
     * <p><b>View: G:Issues</b></p>
     * <p><b>Filter: L:Actual (I:Status = Ready for Test, Testing in Progress; Ver:Version = 8.94.0)</b></p>
     * <p>1. Click on the filter icon</p>
     * <p>3. In the opened window Check that the current filter contains previously set fields (I:Status, Ver:Version)</p>
     * <p>4. Click on the trackor selector icon for Ver:Version field</p>
     * <p>5. In the opened window, uncheck from the last version and check on the new version</p>
     * <p>6. Click on the "OK" button</p>
     * <p>7. Make sure the grid is reloaded and contains an issue with the new version</p>
     * <p>8. Open filter drop-down and click to the icon "Save current filter as..."</p>
     * <p>9. Change "Existing" filters and "L:Actual"</p>
     * <p>10. Click on the "Ok" button</p>
     */
    @Test
    public void ChangeLocalFilter() {
        login.login(USER_NAME, USER_PWD);

//        Default Page: Issue
//        View: G:Issues
//        Filter: L:Actual (I:Status = Ready for Test, Testing in Progress; Ver:Version = 8.94.0)

//        1. Click on the filter icon
        wait.waitWebElement(By.id("btnFilter0"));
        window.openModal(By.name("btnFilter0"));
        wait.waitWebElement(By.id(BUTTON_OK_ID_BASE));

//        3. In the opened window Check that the current filter contains previously set fields (I:Status, Ver:Version)
//        4. Click on the trackor selector icon for Ver:Version field
//        5. In the opened window, uncheck from the last version and check on the new version
//        6. Click on the "OK" button

        String oper = seleniumSettings.getWebDriver().findElement(By.id("txtWPAttrib1")).getAttribute("value");
        if(oper.equals("Ver:Version")) {
            window.openModal(By.id("btnTrSelWPAttribValue2"));
            checkbox.clickById("cbSelected0");
            element.click(seleniumSettings.getWebDriver().findElement(By.className("hdrcell")));
            element.click(seleniumSettings.getWebDriver().findElement(By.className("hdrcell")));
            qs.searchValue(getGridIdx(), "Ver:Version", "8.94.0");
            element.click(seleniumSettings.getWebDriver().findElement(By.className("hdrcell")));
            window.closeModal(By.id(BUTTON_OK_ID_BASE+"0"));
        }
        else if(!oper.equals("I:Status"))
            throw new SeleniumUnexpectedException("Filter not found");

        oper = seleniumSettings.getWebDriver().findElement(By.id("txtWPAttrib2")).getAttribute("value");
        if(oper.equals("Ver:Version")) {
            window.openModal(By.id("btnTrSelWPAttribValue2"));
            checkbox.clickById("cbSelected0");
            element.click(seleniumSettings.getWebDriver().findElement(By.className("hdrcell")));
            element.click(seleniumSettings.getWebDriver().findElement(By.className("hdrcell")));
            qs.searchValue(getGridIdx(), "Ver:Version", "8.94.0");
            element.click(seleniumSettings.getWebDriver().findElement(By.className("hdrcell")));
            window.closeModal(By.id(BUTTON_OK_ID_BASE+"0"));
        }
        else if(!oper.equals("I:Status"))
            throw new SeleniumUnexpectedException("Filter not found");

        wait.waitWebElement(By.id(BUTTON_OK_ID_BASE));
        window.closeModal(By.id(BUTTON_OK_ID_BASE));

//        7. Make sure the grid is reloaded and contains an issue with the new version ???
//        8. Open filter drop-down and click to the icon "Save current filter as..."
        element.clickById("ddFilter0");
        element.clickById("unsavedFilterIcon0");

//        9. Change "Existing" filters and "L:Actual"
        Select select = new Select(seleniumSettings.getWebDriver().findElement(By.id("lbFilterType0")));
        select.selectByIndex(1);

//        10. Click on the "Ok" button
        wait.waitWebElement(By.id("filterDialogOk0"));
        element.clickById("filterDialogOk0");

        wait.waitGridLoad(getGridIdx(), getGridIdx());

        logoff.logoff();
    }


    /**
     * <p><b>Default Page: Issue</b></p>
     * <p><b>View: G:Issues</b></p>
     * <p><b>Filter: L:Actual (I:Status = Ready for Test, Testing in Progress; Ver:Version = 8.94.0)</b></p>
     * <p>1. Find the desired Issue with status - "Ready for Test"</p>
     * <p>2. Click on the hyperlink in the field "I:Issue ID"</p>
     * <p>3. In the opened window (applet) "Edit Issue" go to the tab "IT:Issue Task"</p>
     * <p>4. Click on the "Add" button</p>
     * <p>5. In the opened window (applet) "Add Issue Task" fill the field "IT:Description"</p>
     * <p>6. Fill the field "IT:Comments"</p>
     * <p>7. In the field "IT: Attachment" select a file from the explorer window</p>
     * <p>8. Click on the "OK" button</p>
     * <p>9. Check that the Issue Task is created and has status - "Opened"</p>
     * <p>10. Click on the "OK" button</p>
     */
    @Test
    public void CreateIssueTask() {
        Map<String, String> vals = new HashMap<String, String>();

        login.login(USER_NAME, USER_PWD);
        wait.waitWebElement(By.id(GRID_ID_BASE+getGridIdx()));

//    Default Page: Issue
//    View: G:Issues
//    Filter: L:Actual (I:Status = Ready for Test, Testing in Progress; Ver:Version = 8.94.0)

//            1.Find the desired Issue with status - "Ready for Test"
        qs.searchValue(getGridIdx(), "I:Issue ID", "Other-136480");

//            2. Click on the hyperlink in the field "I:Issue ID"
        window.openModal(By.linkText("Other-136480"));
        wait.waitWebElement(By.id(BUTTON_OK_ID_BASE));

//            3. In the opened window (applet) "Edit Issue" go to the tab "IT:Issue Task"
        tab.goToTab(3L);
        wait.waitTabLoad(3L);

        long rows_count_before = grid.getGridRowsCount(getGridIdx());
//            4. Click on the "Add" button
        window.openModal(By.id(BUTTON_ADD_ID_BASE+"3"));
        wait.waitWebElement(By.id(BUTTON_OK_ID_BASE));

//            5. In the opened window (applet) "Add Issue Task" fill the field "IT:Description"
        //fe100099175_1_0
        tb.editField(ConfigFieldType.TEXT, "Issue Task for testing", null, "fe100099175_1_0", vals, null,1);

//            6. Fill the field "IT:Comments"
        seleniumSettings.getWebDriver().findElement(By.name("fe1000913062_1_0")).sendKeys("Comment for testing");

//            7. In the field "IT: Attachment" select a file from the explorer window

        WebElement we_iframe = seleniumSettings.getWebDriver().findElement(By.id("ifrmHideForm"));
        seleniumSettings.getWebDriver().switchTo().frame(we_iframe);
        seleniumSettings.getWebDriver().findElement(By.name("fe100099178_1_0")).sendKeys("C:\\Users\\User\\Desktop\\инф.docx");

        seleniumSettings.getWebDriver().switchTo().defaultContent();

//            8. Click on the "OK" button
        window.closeModal(By.id(BUTTON_OK_ID_BASE));

        long rows_count_after = grid.getGridRowsCount(getGridIdx());
//            9. Check that the Issue Task is created and has status - "Opened"
        if(rows_count_before<=rows_count_after)
            throw new SeleniumUnexpectedException("Issue Task not added!");

        window.openModal(By.name("btnEdit3"));

        String status = seleniumSettings.getWebDriver().findElement(By.name("fe100099176_1_0")).getAttribute("value");
        if(!status.equals("Opened"))
            throw new SeleniumUnexpectedException("Status not Opened!");

        window.closeModal(By.name(BUTTON_CANCEL_ID_BASE));

//            10. Click on the "OK" button
        window.closeModal(By.id(BUTTON_OK_ID_BASE));

        wait.waitGridLoad(getGridIdx(), getGridIdx());
        logoff.logoff();
    }
}
