package com.onevizion.guitest.test;

import com.onevizion.guitest.AbstractSeleniumLoginPage;
import com.onevizion.uitest.api.annotation.SeleniumTest;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

@SeleniumTest
public class WorkWithTimeRecords extends AbstractSeleniumLoginPage {
    /**
     * <p><b>View: Default (Unsaved View)</b></p>
     * <p><b>Filter: Default (Unsaved Filter)</b></p>
     * <p>1. Find a desired Issue through a quick search on "I:Summary"</p>
     * <p>2. Click on a hyperlink in the field "I:Issue ID"</p>
     * <p>3. In the opened window (applet) "Edit Issue", go to the tab "T:Time Record"</p>
     * <p>4. Click on the "Add" button </p>
     * <p>5. In the opened window (applet) "Add Time Record", write hours into the "T:Spent Hours" field</p>
     * <p>6. Click on the "OK" button</p>
     * <p>7. Click on the "OK" button</p>
     */

    @Test
    public void addTimeRecord_FillOnlySpentHours() {
        login.login(seleniumSettings.getTestUser(), seleniumSettings.getTestPassword());
        wait.waitWebElement(By.id(GRID_ID_BASE + getGridIdx()));
        mainMenu.openMenuItemAndWaitGridLoad("Issue");
        filter.clearFilter(getGridIdx());

//    View: Default (Unsaved View)
//    Filter: Default (Unsaved Filter)
        if (!filter.getCurrentFilterName(getGridIdx()).equals("G:All"))
            filter.selectByVisibleText("G:All", getGridIdx());

        if (!view.getCurrentViewName(getGridIdx()).equals("G:General Info"))
            view.selectByVisibleText(getGridIdx(), "G:General Info");

//      1. Find a desired Issue through a quick search on "I:Summary"
        qs.searchValue(getGridIdx(), "I:Summary", "Issue for testing");

        if (grid.getGridRowsCount(getGridIdx()) == 0)
            throw new SeleniumUnexpectedException("Issue not found!");

//      2. Click on a hyperlink in the field "I:Issue ID"
        window.openModal(By.linkText("Other-136480"));
        wait.waitFormLoad();

//      3. In the opened window (applet) "Edit Issue", go to the tab "T:Time Record"
        tab.goToTab(5);

//      4. Click on the "Add" button
        window.openModal(By.id(BUTTON_ADD_ID_BASE + "5"));
        wait.waitFormLoad();

//      5. In the opened window (applet) "Add Time Record", write hours into the "T:Spent Hours" field
        tb.editField(ConfigFieldType.TEXT, "0.01", "fe100092729_1_0", 1);

//      6. Click on the "OK" button
        window.closeModal(By.id(BUTTON_OK_ID_BASE));

//      7. Click on the "OK" button
        window.closeModal(By.id(BUTTON_OK_ID_BASE));

        grid2.waitLoad();

        logoff.logoff();

    }

    /**
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
    public void addTimeRecord_with_filter_TestsAndroid() {
        login.login(seleniumSettings.getTestUser(), seleniumSettings.getTestPassword());
        mainMenu.openMenuItemAndWaitGridLoad("Issue");

        //    View: G:Issues
        if (!view.getCurrentViewName(getGridIdx()).equals("G:Issues"))
            view.selectByVisibleText(getGridIdx(), "G:Issues");

        //    Filter: G:Test Android
        if (!filter.getCurrentFilterName(getGridIdx()).equals("G:Tests Android"))
            filter.selectByVisibleText("G:Tests Android", getGridIdx());

        //1. Find the desired Issue through a quick search on "I:Summary"
        qs.searchValue(getGridIdx(), "I:Summary", "Android: add check pdf icon for pgf files");

        if (grid.getGridRowsCount(getGridIdx()) == 0)
            throw new SeleniumUnexpectedException("Issue not found!");

        //2. Click on the hyperlink in the field "I:Issue ID"
        window.openModal(seleniumSettings.getWebDriver().findElement(By.linkText("Test-120919")));
        wait.waitWebElement(By.id(BUTTON_OK_ID_BASE));

        //3. In the opened window (applet) "Edit Issue" go to the tab "T:Time Record"
        tab.goToTab(4);

        //4. Click on the Add button
        window.openModal(By.id("btnAdd4"));
        wait.waitWebElement(By.id(BUTTON_OK_ID_BASE));

        //5. Write the value in the field "T:Comments"
        tb.editField(ConfigFieldType.MEMO, "Time record was added for testing", "fe100092745_1_0", 1);

        //6. Write the value in the field "T:Spent Hours"
        tb.editField(ConfigFieldType.NUMBER, "0.01", "fe100092729_1_0", 1);

        //7. Change the value of the field "T:Work Date"
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("MM/dd/yyyy");

        tb.editField(ConfigFieldType.DATE, formatForDateNow.format(dateNow), "fe100092765_1_0", 1);

        //8. Click on the "OK" button
        window.closeModal(By.id(BUTTON_OK_ID_BASE));
        //window.closeModalWithAlert(By.id(BUTTON_CANCEL_ID_BASE), "");

        //9. Click on the "OK" button
        window.closeModal(By.id(BUTTON_OK_ID_BASE));
        //window.closeModal(By.id(BUTTON_CANCEL_ID_BASE));

        grid2.waitLoad();

        logoff.logoff();
    }

    /**
     * <p>1. Go to page Time Record by main menu</p>
     * <p>2. Change filter G:My Time Records for the Current Month</p>
     * <p>3. Change view G:General Info</p>
     * <p>4. Click on the hyperlink in Time Records count</p>
     * <p>6. Change the value of the field "Columns" to "T:Spent Hours" </p>
     * <p>7. Check Total value</p>
     * <p>8. Click on the "Close" button</p>
     */
    @Test
    public void viewTotalSpendHoursInCurrentMonth() {
        login.login(seleniumSettings.getTestUser(), seleniumSettings.getTestPassword());

        //1. Go to page Time Record by main menu
        mainMenu.openMenuItemAndWaitGridLoad("Time Record");

        //2. Change filter G:My Time Records for the Current Month
        if (!filter.getCurrentFilterName(getGridIdx()).equals("G:My Time Records for the Current Month"))
            filter.selectByVisibleText("G:My Time Records for the Current Month", getGridIdx());

        //3. Change view G:General Info
        if (!view.getCurrentViewName(getGridIdx()).equals("G:General Info"))
            view.selectByVisibleText(getGridIdx(), "G:General Info");

        //4. Click on the hyperlink in Time Records count
        window.openModal(By.className("gridStat"));
        wait.waitWebElement(By.id(BUTTON_CANCEL_ID_BASE));

        //6. Change the value of the field "Columns" to "T:Spent Hours"
        tb.editField(ConfigFieldType.DROP_DOWN, "T:Spent Hours", "fields", 1);

        //7. Check Total value
        sleep(5000L);

        //8. Click on the "Close" button
        window.closeModal(By.id(BUTTON_CANCEL_ID_BASE));

        grid2.waitLoad();

        logoff.logoff();
    }
}
