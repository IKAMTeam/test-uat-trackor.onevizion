package com.onevizion.guitest.test;

import com.onevizion.guitest.AbstractSeleniumLoginPage;
import com.onevizion.uitest.api.annotation.SeleniumTest;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import com.onevizion.uitest.api.vo.ConfigFieldType;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.onevizion.uitest.api.helper.filter.Filter.BUTTON_OPEN;

@Component
@SeleniumTest
public class WorkWithIssueAndIssueTask extends AbstractSeleniumLoginPage {

    /**
     * <p><b>Default Page: Issue</b></p>
     * <p><b>View: General Info</b></p>
     * <p><b>Filter: checked My Issues,
     * Status not Closed,
     * Issue Type = Bug</b></p>
     * <p>1. In the grid, find the line corresponding to the issue.</p>
     * <p>2. Click on the hyperlink in the "I: Issue ID" field.</p>
     * <p>3. In the window that opens, in the "Ver: Version" field, select "NA".</p>
     * <p>  ! it is important to remove all other values, leave only NA</p>
     * <p>4. In the field "I: Status" set the value to Closed.</p>
     * <p>5. Click "OK".</p>
     */
    @Test
    public void changeIssueStatus() {
        Map<String, String> vals = new HashMap<>();
        login.login(seleniumSettings.getTestUser(), seleniumSettings.getTestPassword());

//      Default Page: Issue
        mainMenu.selectMenuItem("Issue");

//      View: G:General Info
        if (!view.getCurrentViewName(getGridIdx()).equals("G:General Info"))
            view.selectByVisibleText(getGridIdx(), "G:General Info");

//      set filter
        window.openModal(By.id(BUTTON_OPEN.concat(getGridIdx().toString())));
        wait.waitWebElement(By.id(BUTTON_OK_ID_BASE));
        wait.waitFormLoad();
        //filter.openFilterForm(getGridIdx()); //for 19.3

        seleniumSettings.getWebDriver().findElement(By.name("btnClear")).click();

        window.openModal(By.id("btnWPAttrib1"));
        qs.searchValue(getGridIdx(), "Field Name", "I:Status");
        window.closeModal(By.id(BUTTON_OK_ID_BASE));

        seleniumSettings.getWebDriver().findElement(By.id("WPOperator1")).sendKeys("<>");

        window.openModal(By.id("btnSelWPAttribValue1"));
        checkbox.clickById("cb100097139");
        window.closeModal(By.id("btnOK0"));

        window.openModal(By.id("btnWPAttrib2"));
        qs.searchValue(getGridIdx(), "Field Name", "I:Issue Type");
        window.closeModal(By.id(BUTTON_OK_ID_BASE));

        seleniumSettings.getWebDriver().findElement(By.id("WPOperator2")).sendKeys("=");

        window.openModal(By.id("btnSelWPAttribValue2"));
        checkbox.clickById("cb10009260");
        window.closeModal(By.id("btnOK0"));

        window.closeModal(By.id(BUTTON_OK_ID_BASE));
        //filter.closeFilterFormOk(getGridIdx()); //for 19.3

        /* 1. In the grid, find the line corresponding to the issue. */
        qs.searchValue(getGridIdx(), "I:Issue ID", "BPL-136490");

        if (grid.getGridRowsCount(getGridIdx()) == 0)
            throw new SeleniumUnexpectedException("Issue not found!");

        //WebElement issue_id = seleniumSettings.getWebDriver().findElement(By.linkText("BPL-123504"));
        /* 2. Click on the hyperlink in the "I: Issue ID" field. */
        //window.openModal(issue_id);
        window.openModal(By.linkText("BPL-136490"));

        /* 3. In the window that opens, in the "Ver: Version" field, select "NA". */
        window.openModal(By.id("idx1_but"));
        wait.waitWebElement(By.id("cbSelected0"));

        checkbox.clickById("cbSelected0");
        wait.waitGridLoad(getGridIdx(), getGridIdx());
        element.clickById("SelectCheckboxes0");
        element.clickById("SelectCheckboxes0");
        checkbox.clickById("cbSelected0");
        wait.waitGridLoad(getGridIdx(), getGridIdx());

        //seleniumSettings.getWebDriver().findElement(By.id("navNext0")).click();
        qs.searchValue(getGridIdx(), "Ver:Version", "NA");
        checkbox.clickById("cb10009696502");

        window.closeModal(By.id(BUTTON_OK_ID_BASE + getGridIdx()));

        /* 4. In the field "I: Status" set the value to Closed. */
        //tb.editField(ConfigFieldType.DROP_DOWN, "Closed", null, "fe100092444_1_0", vals, null, 1);

        /* 5. Click "OK". */
        window.closeModal(By.id(BUTTON_OK_ID_BASE));

        wait.waitGridLoad(getGridIdx(), getGridIdx());

        logoff.logoff();
    }

    /**
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
    public void createIssueTask() {
        Map<String, String> vals = new HashMap<>();
        login.login(seleniumSettings.getTestUser(), seleniumSettings.getTestPassword());
        wait.waitWebElement(By.id(GRID_ID_BASE + getGridIdx()));

        mainMenu.selectMenuItem("Issue");
//      View: G:Issues
        if (!view.getCurrentViewName(getGridIdx()).equals("G:Issues"))
            view.selectByVisibleText(getGridIdx(), "G:Issues");
//      Filter: L:Actual (I:Status = Ready for Test, Testing in Progress; Ver:Version = 8.94.0)
        if (!filter.getCurrentFilterName(getGridIdx()).equals("L:Actual"))
            filter.selectByVisibleText("L:Actual", getGridIdx());

//      1.Find the desired Issue with status - "Ready for Test"
        qs.searchValue(getGridIdx(), "I:Issue ID", "Other-136480");

        if (grid.getGridRowsCount(getGridIdx()) == 0)
            throw new SeleniumUnexpectedException("Issue not found!");
//      2. Click on the hyperlink in the field "I:Issue ID"
        window.openModal(By.linkText("Other-136480"));
        wait.waitWebElement(By.id(BUTTON_OK_ID_BASE));

//      3. In the opened window (applet) "Edit Issue" go to the tab "IT:Issue Task"
        tab.goToTab(4L);
        wait.waitTabLoad(4L);

        long rows_count_before = grid.getGridRowsCount(4L);

//      4. Click on the "Add" button
        window.openModal(By.id(BUTTON_ADD_ID_BASE + "4"));
        wait.waitWebElement(By.id(BUTTON_OK_ID_BASE));

//      5. In the opened window (applet) "Add Issue Task" fill the field "IT:Description"
        //fe100099175_1_0
        tb.editField(ConfigFieldType.TEXT, "Issue Task for testing", null, "fe100099175_1_0", vals, null, 1);

//      6. Fill the field "IT:Comments"
//      seleniumSettings.getWebDriver().findElement(By.name("fe1000913062_1_0")).sendKeys("Comment for testing");

//      7. In the field "IT: Attachment" select a file from the explorer window
/*
        WebElement we_iframe = seleniumSettings.getWebDriver().findElement(By.id("ifrmHideForm"));
        seleniumSettings.getWebDriver().switchTo().frame(we_iframe);
        seleniumSettings.getWebDriver().findElement(By.name("fe100099178_1_0")).sendKeys("C:\\Users\\User\\Desktop\\attachment_for_issue_task.jpg");
        seleniumSettings.getWebDriver().switchTo().defaultContent();
*/
//      8. Click on the "OK" button
        window.closeModal(By.id(BUTTON_OK_ID_BASE));

        wait.waitWebElement(By.className("gridStat"));
        try {
            Thread.sleep(5000);                 //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        long rows_count_after = grid.getGridRowsCount(4L);

//      9. Check that the Issue Task is created and has status - "Opened"
        if (rows_count_before >= rows_count_after)
            throw new SeleniumUnexpectedException("Issue Task not added!");

        window.openModal(By.name("btnEdit4"));
        wait.waitWebElement(By.id(BUTTON_OK_ID_BASE));

        String status = seleniumSettings.getWebDriver().findElement(By.name("fe100099176_1_0")).getAttribute("value");
        if (!status.equals("100097135")) //("Opened"))
            throw new SeleniumUnexpectedException("Status not Opened!");

        window.closeModal(By.name(BUTTON_CANCEL_ID_BASE));

//      10. Click on the "OK" button
        window.closeModal(By.id(BUTTON_OK_ID_BASE));

        wait.waitGridLoad(getGridIdx(), getGridIdx());
        logoff.logoff();
    }

    /**
     * <p><b>Default Page: Issue </b></p>
     * <p><b>View: Unsaved View </b></p>
     * <p><b>Filter: L:My Issue (no closed, no tested) </b></p>
     * <p>1. Find the desired Issue in the grid</p>
     * <p>2. Click on the hyperlink in the field "I:Issue ID"</p>
     * <p>3. In the opened window (applet) "Edit Issue" go to the tab "IT:Issue Task" (View: Unsaved View, Filter: Unsaved Filter)</p>
     * <p>4. On the IT:Issue Task tab click the "Add" button</p>
     * <p>5. In the opened window (applet) “Add Issue Task” fill in the fields IT:Backport Version</p>
     * <p>6. Click on the "OK" button (Add Issue Task)</p>
     * <p>7. Click on the "OK" button (Edit Issue)</p>
     */
    @Test
    public void createBackport() {
        login.login(seleniumSettings.getTestUser(), seleniumSettings.getTestPassword());
        wait.waitWebElement(By.id(GRID_ID_BASE + getGridIdx()));

        mainMenu.selectMenuItem("Issue");

        if (!view.getCurrentViewName(getGridIdx()).equals("G:General Info"))
            view.selectByVisibleText(getGridIdx(), "G:General Info");
//      Filter: L:My issue (I:Status = Ready for Test, Testing in Progress; Ver:Version = 8.94.0)
        if (!filter.getCurrentFilterName(getGridIdx()).equals("L:My Issue"))
            filter.selectByVisibleText("L:My Issue", getGridIdx());
//      1. Find the desired Issue in the grid
        qs.searchValue(getGridIdx(), "I:Summary", "Issue for testing create backport");
        if (grid.getGridRowsCount(getGridIdx()) == 0)
            throw new SeleniumUnexpectedException("Issue not found!");
//      2. Click on the hyperlink in the field "I:Issue ID"
        window.openModal(By.linkText("Rule-136500"));
        wait.waitWebElement(By.id(BUTTON_OK_ID_BASE));

//      3. In the opened window (applet) "Edit Issue" go to the tab "IT:Issue Task"
        tab.goToTab(4L);
        wait.waitTabLoad(4L);
//      5. In the opened window (applet) “Add Issue Task” fill in the fields IT:Backport Version
        window.openModal(By.id(BUTTON_ADD_ID_BASE + "4")); // getGridIdx()));
        wait.waitWebElement(By.name("fe1000913084_1_0_but"));

        window.openModal(By.name("fe1000913084_1_0_but"));
        wait.waitGridLoad(getGridIdx(), getGridIdx());

        qs.searchValue(getGridIdx(), "Ver:Version", "8.93.1");
        if (grid.getGridRowsCount(getGridIdx()) == 0)
            throw new SeleniumUnexpectedException("Version for Backport not found!");
        window.closeModal(By.id(BUTTON_OK_ID_BASE + getGridIdx()));

//      6. Click on the "OK" button (Add Issue Task)
        window.closeModal(By.id(BUTTON_OK_ID_BASE));

//      7. Click on the "OK" button (Edit Issue)
        window.closeModal(By.id(BUTTON_OK_ID_BASE));

        wait.waitGridLoad(getGridIdx(), getGridIdx());
        logoff.logoff();
    }

    /**
     * <p>1. Open the main menu and select "Issue Task" (View: G:Default, Filter: G:Next Release)</p>
     * <p>2. Find the desired Issue Task in the grid</p>
     * <p>3. Update the value of the IT: Status field in the grid</p>
     * <p>4. Save changes to the grid</p>
     */
    @Test
    public void editIssueTaskStatusInGrid() {
        Map<String, String> vals = new HashMap<>();
        login.login(seleniumSettings.getTestUser(), seleniumSettings.getTestPassword());
        wait.waitWebElement(By.id(GRID_ID_BASE + getGridIdx()));
//      1. Open the main menu and select "Issue Task" (View: G:Default, Filter: G:Next Release)
        mainMenu.selectMenuItem("Issue Task");
        if (!view.getCurrentViewName(getGridIdx()).equals("G:Default"))
            view.selectByVisibleText(getGridIdx(), "G:Default");

        if (!filter.getCurrentFilterName(getGridIdx()).equals("G:Next Release"))
            filter.selectByVisibleText("G:Next Release", getGridIdx());

        if (grid.getGridRowsCount(getGridIdx()) == 0)
            throw new SeleniumUnexpectedException("Issue Tasks not found!");

        qs.searchValue(getGridIdx(), "IT:Assigned To", seleniumSettings.getTestUser());

//      2. Find the desired Issue Task in the grid
        //element.setFocusOnElement();
//      3. Update the value of the IT: Status field in the grid
        //tb.editField(ConfigFieldType.DROP_DOWN, "In Progress", null, "epmDd1", vals, vals, 1);
        tb.editCell(getGridIdx(), 0L, 4L, ConfigFieldType.DROP_DOWN, "In Progress", "", null, vals, vals, null, null);
        seleniumSettings.getWebDriver().findElement(By.id("navPowered" + getGridIdx())).click();

//      4. Save changes to the grid
        element.clickByName("btnSaveGrid0");

        try {
            Thread.sleep(1500);                 //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        wait.waitGridLoad(getGridIdx(), getGridIdx());

        logoff.logoff();
    }
}
