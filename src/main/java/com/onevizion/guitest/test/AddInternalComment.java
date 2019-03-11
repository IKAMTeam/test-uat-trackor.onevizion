package com.onevizion.guitest.test;

import java.util.HashMap;
import java.util.Map;

import com.onevizion.guitest.AbstractSeleniumLoginPage;
import com.onevizion.uitest.api.exception.SeleniumUnexpectedException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

import com.onevizion.uitest.api.annotation.SeleniumTest;
import com.onevizion.uitest.api.vo.ConfigFieldType;


@Component
@SeleniumTest
public class AddInternalComment extends AbstractSeleniumLoginPage {

    /**
     * <p><b>Default Page: Cases</b></p>
     * <p><b>View: G:Case Info</b></p>
     * <p><b>Filter: G:All</b></p>
     * <p>1. Find the desired Case through a quick search on "C:Case ID"</p>
     * <p>2. Click on the hyperlink in the field "C:Case ID"</p>
     * <p>3. In the opened window (applet) "Edit Case" go to the tab "C:Case Info"</p>
     * <p>4. Write a comment in the field "C:New Comment"</p>
     * <p>5. Change the value of the field "C:Status" to "Awaiting Info" (Internal)</p>
     * <p>6. Click on the "OK" button</p>
     * <p>7. Click on the hyperlink in the "C:Case ID" field of the edited Case</p>
     * <p>8. Check that the comment that was written in the "C:New Comment" field has been added to the "C:Internal Comment Log" field</p>
     * <p>9. Check that the value in the "C:New Comment" field is cleared</p>
     * <p>10. Verify that the value of the "C:Status" field is "Awaiting Info" (Internal)</p>
     * <p>11. Click on the "Cancel" button</p>
     */

    @Test
    public void addInternalCommentTest(){
        login.login(seleniumSettings.getTestUser(), seleniumSettings.getTestPassword());
        mainMenu.selectMenuItem("Case");

        /** <b>1. Find the desired Case through a quick search on "C:Case ID"</b> */
        qs.searchValue(getGridIdx(), "C:Case ID", "104741");

        /** <b>2. Click on the hyperlink in the field "C:Case ID"</b> */
        WebElement myCase = seleniumSettings.getWebDriver().findElement(By.linkText("104741"));
        window.openModal(myCase);
        wait.waitTabLoad(1L);
//        try { Thread.sleep(3000);                 //1000 milliseconds is one second.
//        } catch(InterruptedException ex) { Thread.currentThread().interrupt();}

        /** <b>3. In the opened window (applet) "Edit Case" go to the tab "C:Case Info"</b> */
        tab.goToTab(2L);

        element.moveToElement(seleniumSettings.getWebDriver().findElement(By.name("fe100099547_1_0")));

        /** <b>4. Write a comment in the field "C:New Comment"</b> */
        String comments_before = seleniumSettings.getWebDriver().findElement(By.name("fe1000912036_1_0")).getAttribute("value");
        int comments_length_before = comments_before.length();

        Map<String, String> vals = new HashMap<String, String>();

        String content_comment = "This is new comment, created for testing";
        tb.editField(ConfigFieldType.MEMO, content_comment, null, "fe1000912037_1_0", vals, null, 2);

        /** <b>5. Change the value of the field "C:Status" to "Awaiting Info(Internal)"</b> */
        tb.editField(ConfigFieldType.DROP_DOWN, "Awaiting Info(Internal)", null, "fe100099547_1_0", vals, null, 2);

        /** <b>6. Click on the "OK" button</b> */
        window.closeModal(By.id(BUTTON_OK_ID_BASE));
        wait.waitGridLoad(getGridIdx(), getGridIdx());

        /** <b>7. Click on the hyperlink in the "C:Case ID" field of the edited Case.</b> */
        myCase = seleniumSettings.getWebDriver().findElement(By.linkText("104741"));

        window.openModal(myCase);
        wait.waitTabLoad(1L);
        tab.goToTab(2L);
        wait.waitTabLoad(2L);

        element.moveToElement(seleniumSettings.getWebDriver().findElement(By.name("fe100099547_1_0")));

        /** <b>8. Check that the comment that was written in the "C:New Comment" field has been added to the "C:Internal Comment Log" field</b> */
        String comment_log_after = seleniumSettings.getWebDriver().findElement(By.name("fe1000912036_1_0")).getAttribute("value");
        if(comments_length_before >= comment_log_after.length())
            throw new SeleniumUnexpectedException("No comment added");

        /**<b>9. Check that the value in the "C:New Comment" field is cleared</b> */
        if(!seleniumSettings.getWebDriver().findElement(By.name("fe1000912037_1_0")).getAttribute("value").toString().equals(""))
            throw new SeleniumUnexpectedException("Field C:New Comment not cleared");

        vals.put("fe100099547_1_0", "Awaiting Info(Internal)");
        /**<b>10. Verify that the value of the "C:Status" field is "Awaiting Info" (Internal)</b> */
        tb.checkField(ConfigFieldType.DROP_DOWN, "fe100099547_1_0", vals, 2, true, false );

        /**<b>11. Click on the "Cancel" button</b> */
        window.closeModal(By.id(BUTTON_CANCEL_ID_BASE));

        wait.waitGridLoad(getGridIdx(), getGridIdx());

        logoff.logoff();
    }

}
