package com.onevizion.guitest.test;

import com.onevizion.guitest.AbstractSeleniumLoginPage;
import com.onevizion.uitest.api.annotation.SeleniumTest;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Locale;


@Component
@SeleniumTest
public class WorkWithInvoices extends AbstractSeleniumLoginPage {

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
    public void loadInvoice() {
        login.login(seleniumSettings.getTestUser(), seleniumSettings.getTestPassword());

        //1. Go to page Invoices by main menu
        mainMenu.selectMenuItem("Employee Invoices");
        //mainMenu.selectMenuItem("Invoices");

        //2. Change filter of the Current Month
        if (!filter.getCurrentFilterName(getGridIdx()).equals("G:This Month Invoices"))
            filter.selectByVisibleText("G:This Month Invoices", getGridIdx());

        //3. Change view G:General Info
        if (!view.getCurrentViewName(getGridIdx()).equals("G:General Info"))
            view.selectByVisibleText(getGridIdx(), "G:General Info");

        //4. Find the desired Invoices through a quick search on "INV:Invoice ID"
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int year = now.get(Calendar.YEAR);       // The current year1900
        String month = now.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH).toUpperCase();

        //seleniumSettings.getTestUserFullName();
        String inv_id = year + "_" + month + "_" + "JULIA_TOKMAGASHEVA".toUpperCase();

        qs.searchValue(getGridIdx(), "INV:Invoice ID", inv_id);

        if (grid.getGridRowsCount(getGridIdx()) == 0)
            throw new SeleniumUnexpectedException("Invoice: " + inv_id + " not found!");

        //5. Click on the hyperlink in the field "INV:Invoice ID"
        window.openModal(seleniumSettings.getWebDriver().findElement(By.linkText(inv_id)));
        wait.waitWebElement(By.id(BUTTON_OK_ID_BASE));

        //6. In the opened window "Edit Employee Invoice" go to the tab "INV:General Info"
        tab.goToTab(1L);
        wait.waitTabLoad(1L);

        // 5. Download file of the field "INV:Invoice for Bank
        // !!! THIS ITEM HASN`T BEEN VERIFIED
        //seleniumSettings.getWebDriver().findElement(By.id("idx9_disp")).click();

        //7. Click on the "Cancel" button
        window.closeModal(By.id(BUTTON_CANCEL_ID_BASE));

        wait.waitGridLoad(getGridIdx(), getGridIdx());

        logoff.logoff();
    }


}
