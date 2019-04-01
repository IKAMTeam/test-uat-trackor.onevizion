package com.onevizion.guitest.test;

import com.onevizion.guitest.AbstractSeleniumLoginPage;
import com.onevizion.uitest.api.annotation.SeleniumTest;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;


@Component
@SeleniumTest
public class WorkWithFilter extends AbstractSeleniumLoginPage {

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
    public void changeLocalFilter() {
        login.login(seleniumSettings.getTestUser(), seleniumSettings.getTestPassword());
        mainMenu.selectMenuItem("Issue");
//        View: G:Issues
        if (!view.getCurrentViewName(getGridIdx()).equals("G:Issues"))
            view.selectByVisibleText(getGridIdx(), "G:Issues");

//        Filter: L:Actual (I:Status = Ready for Test, Testing in Progress; Ver:Version = 19.3.0)
        if (!filter.getCurrentFilterName(getGridIdx()).equals("L:Actual"))
            filter.selectByVisibleText("L:Actual", getGridIdx());

//        1. Click on the filter icon
        wait.waitWebElement(By.id("btnFilter0"));
        window.openModal(By.name("btnFilter0"));
        wait.waitWebElement(By.id(BUTTON_OK_ID_BASE));

//        3. In the opened window Check that the current filter contains previously set fields (I:Status, Ver:Version)
//        4. Click on the trackor selector icon for Ver:Version field
//        5. In the opened window, uncheck from the last version and check on the new version
//        6. Click on the "OK" button

        String oper = seleniumSettings.getWebDriver().findElement(By.id("txtWPAttrib1")).getAttribute("value");
        switch (oper) {
            case "Ver:Version":
                window.openModal(By.id("btnSelWPAttribValue1"));
                //wait.waitWebElement(By.id("cbSelected0"));
                checkbox.clickById("cbSelected0");
                try {
                    Thread.sleep(3000);                 //1000 milliseconds is one second.
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                element.click(seleniumSettings.getWebDriver().findElement(By.id("SelectCheckboxes0")));
                element.click(seleniumSettings.getWebDriver().findElement(By.id("SelectCheckboxes0")));
                qs.searchValue(getGridIdx(), "Ver:Version", "19.3.0");
                element.click(seleniumSettings.getWebDriver().findElement(By.id("SelectCheckboxes0")));
                window.closeModal(By.id(BUTTON_OK_ID_BASE + "0"));
                break;
            case "I:Status":
                window.openModal(By.id("btnSelWPAttribValue1"));
                //wait.waitWebElement(By.id("cbSelected0"));
                checkbox.clickById("cbSelected0");
                try {
                    Thread.sleep(3000);                 //1000 milliseconds is one second.
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                element.click(seleniumSettings.getWebDriver().findElement(By.id("SelectCheckboxes0")));
                element.click(seleniumSettings.getWebDriver().findElement(By.id("SelectCheckboxes0")));
                qs.searchValue(getGridIdx(), "Value", "Ready for Test, Testing in Progress");
                element.click(seleniumSettings.getWebDriver().findElement(By.id("SelectCheckboxes0")));
                window.closeModal(By.id(BUTTON_OK_ID_BASE + "0"));
                break;
            default:
                throw new SeleniumUnexpectedException("Filter not found");
        }

        oper = seleniumSettings.getWebDriver().findElement(By.id("txtWPAttrib2")).getAttribute("value");
        switch (oper) {
            case "Ver:Version":
                window.openModal(By.id("btnTrSelWPAttribValue2"));
                //wait.waitWebElement(By.id("cbSelected0"));
                checkbox.clickById("cbSelected0");
                try {
                    Thread.sleep(3000);                 //1000 milliseconds is one second.
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                element.click(seleniumSettings.getWebDriver().findElement(By.id("SelectCheckboxes0")));
                element.click(seleniumSettings.getWebDriver().findElement(By.id("SelectCheckboxes0")));
                qs.searchValue(getGridIdx(), "Ver:Version", "19.3.0");
                element.click(seleniumSettings.getWebDriver().findElement(By.id("SelectCheckboxes0")));
                window.closeModal(By.id(BUTTON_OK_ID_BASE + "0"));
                break;
            case "I:Status":
                window.openModal(By.id("btnSelWPAttribValue2"));
                //grid2.waitLoad(getGridIdx(), getGridIdx());
                wait.waitWebElement(By.id("cbSelected0"));
                try {
                    Thread.sleep(3000);                 //1000 milliseconds is one second.
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                element.click(seleniumSettings.getWebDriver().findElement(By.id("SelectCheckboxes0")));
                element.click(seleniumSettings.getWebDriver().findElement(By.id("SelectCheckboxes0")));
                qs.searchValue(getGridIdx(), "Value", "Ready for Test, Testing in Progress");
                element.click(seleniumSettings.getWebDriver().findElement(By.id("SelectCheckboxes0")));
                window.closeModal(By.id(BUTTON_OK_ID_BASE + "0"));
                break;
            default:
                throw new SeleniumUnexpectedException("Filter not found");
        }

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

        grid2.waitLoad(getGridIdx());

        logoff.logoff();
    }


}
