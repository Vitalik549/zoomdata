package com.zoomdata.demo.pages.basePages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.zoomdata.demo.helpers.StatusWebElem;
import com.zoomdata.demo.helpers.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.zoomdata.demo.helpers.StatusWebElem.*;

public class BasePage {

    protected WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void isTextNotVisibleNow(String... text) {
        WaitHelper.setImplicitWait(driver, 0.5);
        for (String elem : text) {
            $(byText(elem)).shouldNotBe(visible);
        }
        WaitHelper.setImplicitWaitDefault(driver);
    }

    public void isTextDisplayedOnPage(String... text) {
        SoftAssert softAssert = new SoftAssert();
        for (String elem : text) {
            softAssert.assertTrue($(By.xpath("//*[contains(text(), \"" + elem + "\")]")).is(visible), "Text is not displayed on page: " + elem);
        }
        softAssert.assertAll();
    }

    public void isElementsNotVisibleNow(SelenideElement... selenideElements) {
        WaitHelper.setImplicitWait(driver, 0.5);
        for (SelenideElement elem : selenideElements) {
            $(elem).shouldNotBe(visible);
        }
        WaitHelper.setImplicitWaitDefault(driver);

    }

    public boolean isElementsVisibleNow(SelenideElement... selenideElements) {
        WaitHelper.setImplicitWait(driver, 1);
        for (SelenideElement elem : selenideElements) {
            if (!$(elem).is(visible)) {
                return false;
            }
        }
        WaitHelper.setImplicitWaitDefault(driver);
        return true;
    }


    public void scrollAndClick(SelenideElement elem) {
        if (elem.exists()) {
            try {
                elem.shouldBe(visible).click();
            } catch (ElementNotFound a) {
                elem.scrollTo().should(exist).click();
            }
        }
    }

    public SelenideElement scrollAndClickJS(SelenideElement elem) {
        try {
            elem.shouldBe(visible).click();
        } catch (Throwable e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);
            elem.shouldBe(visible).click();
        }
        return $(elem);
    }

    public SelenideElement scrollToElemJS(SelenideElement elem) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);
        return $(elem).shouldBe(visible);
    }

    public void checkElementStatus(SelenideElement selenideElement, StatusWebElem expectedStatus) {
        if (expectedStatus.equals(VISIBLE)) {
            $(selenideElement).shouldBe(visible);
        } else if (expectedStatus.equals(ENABLED)) {
            $(selenideElement).shouldBe(visible, enabled);
        } else if (expectedStatus.equals(DISABLED)) {
            $(selenideElement).shouldBe(visible, disabled);
        } else if (expectedStatus.equals(NOT_VISIBLE)) {
            isElementsNotVisibleNow($(selenideElement));
        } else {
            Assert.assertTrue(false, "Incorrect expected status. Possible values: ENABLED / DISABLED / NOT_AVAILABLE");
        }
    }

    public void checkElementStatus(By by, StatusWebElem expectedStatus) {
        checkElementStatus($(by), expectedStatus);
    }

    public void checkElementStatus(StatusWebElem expectedStatus, By... by_s) {
        for (By by : by_s) {
            checkElementStatus($(by), expectedStatus);
        }
    }

    public void scrollToTheTop() {
        $(By.id("page-content")).scrollTo();
    }

    public BasePage checkUserLoggedIn(boolean isToBeLoggedIn) {
        StatusWebElem statusWebElem = isToBeLoggedIn ? VISIBLE : NOT_VISIBLE;
        checkElementStatus($(MenuPage.menuTopPane), statusWebElem);
        return null;
    }
}
