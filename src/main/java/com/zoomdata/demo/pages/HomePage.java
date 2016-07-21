package com.zoomdata.demo.pages;

import com.zoomdata.demo.pages.basePages.MenuPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.hasClass;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$$;

@SuppressWarnings("unchecked")
public class HomePage extends MenuPage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    By sourceItems = By.className("zd-carousel-area-sourceItem");


    @Override
    public HomePage checkUserLoggedIn(boolean isToBeLoggedIn) {
        super.checkUserLoggedIn(isToBeLoggedIn);
        return this;
    }

    public HomePage checkDataSourcesAmount(int expected) {
        $$(sourceItems).filter(not(hasClass("small-0"))).shouldHave(size(expected));
        return this;
    }

}
