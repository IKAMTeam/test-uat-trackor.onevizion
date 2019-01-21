package com.onevizion.guitest.test;
import java.util.HashMap;
import java.util.Map;

import com.onevizion.guitest.AbstractSeleniumLoginPage;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

import com.onevizion.uitest.api.annotation.SeleniumTest;
import com.onevizion.uitest.api.vo.ConfigFieldType;

@Component
@SeleniumTest

public class UseCases_Stepanyuk extends AbstractSeleniumLoginPage {

    protected final static String USER_NAME = "";
    protected final static String USER_PWD = "";

    /**
     * <p><b>Default Page: Issue</b></p>
     * <p><b>View: G:Issues</b></p>
     * <p><b>Filter: G:Test Android</b></p>
     * <p>1. Go to page Invoices by main menu</p>
     * <p>2. Change filter of the Current Month</p>
     * <p>3. Change view G:General Info</p>
     * <p>4. Find the desired Invoices through a quick search on "INV:Invoice ID"</p>
     * <p>5. Click on the hyperlink in the field "INV:Invoice ID"</p>
     * <p>6. In the opened window "Edit Employee Invoice" go to the tab "INV:General Info"</p>
     * <p>5. Download file of the field "INV:Invoice for Bank</p>
     * <p>7. Click on the "Cancel" button</p>
     */
    @Test
    public void Invoice() {
        login.login(USER_NAME, USER_PWD);

        //Default Page: Issue
        //View: G:Issues
        //Filter: G:Test Android

        //1. Go to page Invoices by main menu
        mainMenu.selectMenuItem("Invoices");

        //2. Change filter of the Current Month
        //seleniumSettings.getWebDriver().findElement(By.className("newGenericDropDown")).click();
        //seleniumSettings.getWebDriver().findElement(By.id("ddFilterSearch0")).sendKeys("G:This Month Invoices");
        //seleniumSettings.getWebDriver().findElement(By.className("newGenericDropDownSearchBlock")).sendKeys("G:This Month Invoices");
        //seleniumSettings.getWebDriver().findElement(By.tagName("span")).click();

        //3. Change view G:General Info
        view.selectByVisibleText(getGridIdx(),"G:General Info");

        //4. Find the desired Invoices through a quick search on "INV:Invoice ID"
        qs.searchValue(getGridIdx(), "INV:Invoice ID", "2018_OCTOBER_JULIA_TOKMAGASHEVA");

        //5. Click on the hyperlink in the field "INV:Invoice ID"
        window.openModal(seleniumSettings.getWebDriver().findElement(By.linkText("2018_OCTOBER_JULIA_TOKMAGASHEVA")));
        wait.waitWebElement(By.id(BUTTON_OK_ID_BASE));

        //6. In the opened window "Edit Employee Invoice" go to the tab "INV:General Info"
        tab.goToTab(1L);
        wait.waitTabLoad(1L);

        // 5. Download file of the field "INV:Invoice for Bank
        // !!! THIS ITEM HASN`T BEEN VERIFIED -
        //seleniumSettings.getWebDriver().findElement(By.id("idx9_disp")).click();

        //7. Click on the "Cancel" button
        window.closeModal(By.id(BUTTON_CANCEL_ID_BASE));

        wait.waitGridLoad(getGridIdx(), getGridIdx());

        logoff.logoff();
    }


    /**
     * <p><b>Default Page: Issue</b></p>
     * <p><b>View: G:Issues</b></p>
     * <p><b>Filter: G:Test Android</b></p>
     * <p>1. Find the desired Issue through a quick search on "I:Summary"</p>
     * <p>2. Click on the hyperlink in the field "I:Issue ID"</p>
     * <p>3. In the opened window (applet) "Edit Issue" go to the tab "T:Time Record"</p>
     * <p>4. Click on the Add button</p>
     * <p>5. Write the value in the field "T:Comments"</p>
     * <p>6. Write the value in the field "T:Spent Hours"</p>
     * <p>7. Change the value of the field "T:Work Date"</p>
     * <p>8. Click on the "OK" button</p>
     * <p>9. Click on the "OK" button</p>
     */
    @Test
    public void Issue() {
        Map<String, String> vals = new HashMap<String, String>();

        login.login(USER_NAME, USER_PWD);

        //    Default Page: Issue
        //    View: G:Issues
        //    Filter: G:Test Android

        //1. Find the desired Issue through a quick search on "I:Summary"
        qs.searchValue(getGridIdx(), "I:Summary", "Julia Tokmagasheva Training (2018)");

        //2. Click on the hyperlink in the field "I:Issue ID"
        window.openModal(seleniumSettings.getWebDriver().findElement(By.linkText("Other-123946")));
        wait.waitWebElement(By.id(BUTTON_OK_ID_BASE));

        //3. In the opened window (applet) "Edit Issue" go to the tab "T:Time Record"
        tab.goToTab(3L);
        wait.waitTabLoad(3L);

        //4. Click on the Add button
        window.openModal(By.id("btnAdd3"));
        wait.waitWebElement(By.id(BUTTON_OK_ID_BASE));

        //5. Write the value in the field "T:Comments"
        tb.editField(ConfigFieldType.MEMO, "Time record was added for testing", null, "fe100092745_1_0", vals, null,1);

        //6. Write the value in the field "T:Spent Hours"
        tb.editField(ConfigFieldType.NUMBER, "1", null, "fe100092729_1_0", vals, null,1);

        //7. Change the value of the field "T:Work Date"
        tb.editField(ConfigFieldType.DATE, "10/29/2018", null, "fe100092765_1_0", vals, null,1);

        //8. Click on the "OK" button
        window.closeModal(By.id(BUTTON_OK_ID_BASE));
        //window.closeModalWithAlert(By.id(BUTTON_CANCEL_ID_BASE), "");

        //9. Click on the "OK" button
        window.closeModal(By.id(BUTTON_OK_ID_BASE));
        //window.closeModal(By.id(BUTTON_CANCEL_ID_BASE));

        wait.waitGridLoad(getGridIdx(), getGridIdx());

        logoff.logoff();
    }

    /**
     * <p><b>Default Page: Issue</b></p>
     * <p><b>View: G:Issues</b></p>
     * <p><b>Filter: G:Test Android</b></p>
     * <p>1. Go to page Time Record by main menu</p>
     * <p>2. Change filter G:My Time Records for the Current Month</p>
     * <p>3. Change view G:General Info</p>
     * <p>4. Click on the hyperlink in Time Records count</p>
     * <p>5. In the opened window (grid stat page) "Edit Case" go to the tab "General"</p>
     * <p>6. Change the value of the field "Columns" to "T:Spent Hours" </p>
     * <p>7. Check Total value</p>
     * <p>8. Click on the "Close" button</p>
     */
    @Test
    public void TimeRecord() {
        Map<String, String> vals = new HashMap<String, String>();

        login.login(USER_NAME, USER_PWD);

        //    Default Page: Issue
        //    View: G:Issues
        //    Filter: G:Test Android

        //1. Go to page Time Record by main menu
        mainMenu.selectMenuItem("Time Record");

        //2. Change filter G:My Time Records for the Current Month
        if(!filter.getCurrentFilterName(getGridIdx()).equals("G:My Time Records for the Current Month"))
            filter.selectByVisibleText("G:My Time Records for the Current Month", getGridIdx());

        //3. Change view G:General Info
        if(!view.getCurrentViewName(getGridIdx()).equals("G:General Info"))
            view.selectByVisibleText(getGridIdx(),"G:General Info");

        //4. Click on the hyperlink in Time Records count
        window.openModal(By.className("gridStat"));
        wait.waitWebElement(By.id(BUTTON_CANCEL_ID_BASE));

        //5. In the opened window (grid stat page) "Edit Case" go to the tab "General"
        tab.goToTab(1L);

        //6. Change the value of the field "Columns" to "T:Spent Hours"
        tb.editField(ConfigFieldType.TEXT,"0.05",null, "fe100092729_1_0", vals,  null,1);

        //7. Check Total value
        //8. Click on the "Close" button
        window.closeModal(By.id(BUTTON_CANCEL_ID_BASE));

        wait.waitGridLoad(getGridIdx(), getGridIdx());

        logoff.logoff();
    }

}
