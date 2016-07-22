package com.zoomdata.demo.pages;

import com.codeborne.selenide.Condition;
import com.zoomdata.demo.pages.basePages.MenuPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@SuppressWarnings("unchecked")
public class HomePage extends MenuPage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    By sourceItems = By.className("zd-carousel-area-sourceItem");

    private By getDataSource(String dataSource) {
        return By.xpath(".//*[contains(@class, 'zd-carousel-area-sourceItem')][.//header//div[@class='title'][text()='" + dataSource + "']]"); //get DS with text = 'dataSource; text in header
    }

    private By getChart(String dataSource) {
        return By.xpath(".//div[contains(@class,'carousel-area-snapshot')][.//*[contains(@class,'carousel-area-snapshot-title')][text() ='" + dataSource + "']]");
    }

    @Override
    public HomePage checkUserLoggedIn(boolean isToBeLoggedIn) {
        super.checkUserLoggedIn(isToBeLoggedIn);
        return this;
    }

    public HomePage checkDataSourcesAmount(int expected) {
        $$(sourceItems).shouldHave(size(expected + 1)); //+1, as Favorites group has same class
        return this;
    }

    public ChartPage openChart(String dataSource, String chartName) {
        $(getDataSource(dataSource)).find(getChart(chartName)).shouldBe(visible).click();
        return new ChartPage(driver);
    }



}
