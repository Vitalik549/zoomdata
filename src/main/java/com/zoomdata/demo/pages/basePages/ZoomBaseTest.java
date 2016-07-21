package com.zoomdata.demo.pages.basePages;

import com.zoomdata.demo.helpers.CurrentEnvironment;
import com.zoomdata.demo.helpers.WaitHelper;
import com.zoomdata.demo.pages.HomePage;
import com.zoomdata.demo.pages.LoginPage;
import com.zoomdata.demo.zoomComponents.User;
import org.apache.log4j.Logger;
import org.openqa.selenium.Point;
import org.openqa.selenium.io.TemporaryFilesystem;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.screenshot;

public class ZoomBaseTest extends BaseTest {

    protected User admin = new User("zoomdata_test", "password");

    //all tests started from HomePage (user logged in), exceptions in beforeMethod
    public static final String GENERAL = "general";
    public static final String SUPPLIER = "supplier";

    //pages used in tests
    protected LoginPage loginPage;
    protected HomePage homePage;

    private static final Logger LOGGER = Logger.getLogger(ZoomBaseTest.class);

    @BeforeMethod
    public void loginAdmin(Method method) {
        setUp();
        LOGGER.info(Calendar.getInstance().getTime() + ": Test \"" + method.getDeclaringClass().getName() + "\" was started with user " + admin.getName());
        startTestDependingOnGroups(method);
    }

    public void setUp() {
        super.setUp();
        WaitHelper.setImplicitWaitDefault(driver);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        open(CurrentEnvironment.BASE_URL);
        driver.manage().window().setPosition(new Point(1920, 24));
        driver.manage().window().maximize();
    }

    public void startTestDependingOnGroups(Method method) {
        ArrayList<String> groupsList = new ArrayList<>(Arrays.asList(method.getDeclaredAnnotation(Test.class).groups()));
        if (groupsList.contains(GENERAL)) {
            loginPage = new LoginPage(driver);
        }  else {
            homePage = login().user(admin);
            LOGGER.info("Test " + method.getDeclaringClass().getName() + " started with user: " + admin.getName());
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult testResult) {
        makeScreenShotIfTestFailed(testResult);
        TemporaryFilesystem.getDefaultTmpFS().deleteTemporaryFiles();
        try {
            driver.quit();
        } catch (Exception e) {
            LOGGER.info("Failed to close browser. Error message:" + e.getMessage());
        }
    }

    public void makeScreenShotIfTestFailed(ITestResult testResult) {
        try {
            if (testResult.getStatus() == ITestResult.FAILURE) {
                String timeFormat = new SimpleDateFormat("hh.mm.ss").format(new Date());
                String testName = testResult.getMethod().getTestClass().getRealClass().getSimpleName();
                screenshot(testName + "_" + timeFormat);
            }
        } catch (Throwable exception) {
            LOGGER.info("Failed to make screenshot for " + testResult.getTestClass().getName());
            LOGGER.info("Exception message: " + exception.getMessage());
        }
    }

    public LoginPage login(){
        return new LoginPage(driver);
    }


    protected MenuPage menu() {
        return new MenuPage(driver).clickMenu();
    }



}
