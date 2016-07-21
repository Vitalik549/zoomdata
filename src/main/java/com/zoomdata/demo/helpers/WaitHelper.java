package com.zoomdata.demo.helpers;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static com.zoomdata.demo.helpers.EnvironmentPropertiesHandler.*;


@SuppressWarnings("all")
public class WaitHelper {
    private static final Logger LOGGER = Logger.getLogger(WaitHelper.class);
    private static final long DEFAULT_WAIT_MILISECONDS = 200;
    private static EnvironmentPropertiesHandler properties = getInstance();

    public static void setImplicitWaitDefault(WebDriver driver) {
        int implicit = Integer.valueOf(properties.getProperty("default." + IMPLICITLY_WAIT_TIMEOUT_IN_SECONDS));
        driver.manage().timeouts().implicitlyWait(implicit, TimeUnit.SECONDS);
    }

    public static void setImplicitWait(WebDriver driver, double seconds) {
        driver.manage().timeouts().implicitlyWait((long)seconds*1000, TimeUnit.MILLISECONDS);
    }

    public static void waitUntilElementIsLoaded(WebDriver driver, final String xPath, long timeOutInSeconds) {
        WebDriverWait driverWait = new WebDriverWait(driver, timeOutInSeconds);
        LOGGER.debug("Waiting for Element to be loaded from xPath" + xPath);
        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
    }

    private static void defaultWait() {
        waitAdditional(DEFAULT_WAIT_MILISECONDS);
    }

    public static void waitAdditional(double seconds) {
        if (seconds <= 0) {
            return;
        }
        long milliseconds = (long) (seconds * 1000);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebDriverException(e);
        }
    }

    public static void waitUntilElementDisplayed(WebDriver driver, final By by, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        LOGGER.debug("Waiting for element to be displayed");
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void waitUntilElementClickable(WebDriver driver, final WebElement webElement, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        LOGGER.debug("Waiting for element to be displayed");
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public static void waitUntilElementNotVisibleNow(WebDriver driver, final WebElement webElement, int timeOutInSeconds) {
        int count = 0;
        if (timeOutInSeconds > 10) {
            System.err.println("Wait is longer than 10 sec, was disabled");
        } else {
            setImplicitWait(driver, 0);
            while (count < timeOutInSeconds*2) {
                try {
                    if (!webElement.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    break;
                }
                WaitHelper.waitAdditional(0.5);
                count++;
            }
            setImplicitWaitDefault(driver);
        }
    }

    public static void waitForAjax(WebDriver driver, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        LOGGER.debug("Waiting for AJAX requests to finish");
        try {
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    Boolean cond = (Boolean) js.executeScript("return jQuery.active == 0");
                    return cond;
                }
            });
        } catch (Exception e) {
            LOGGER.debug("AJAX is not present");
        }
    }

    public static void waitForAlert(WebDriver driver, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        LOGGER.debug("Waiting for alert to be displayed");
        wait.until(ExpectedConditions.alertIsPresent());
    }
}
