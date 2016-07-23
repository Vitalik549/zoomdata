package com.zoomdata.demo.pages;

import com.zoomdata.demo.pages.basePages.BasePage;
import com.zoomdata.demo.zoomComponents.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage{

    public LoginPage(WebDriver driver){
        super(driver);
    }

    public HomePage user(User user){
        $(By.id("username")).shouldBe(visible).setValue(user.getName());
        $(By.id("password")).shouldBe(visible).setValue(user.getPassword());
        $(By.id("login-box")).find(By.className("btn-success")).shouldBe(visible).click();
        return new HomePage(driver);
    }
}
