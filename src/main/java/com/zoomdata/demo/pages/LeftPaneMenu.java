package com.zoomdata.demo.pages;

import com.codeborne.selenide.SelenideElement;
import com.zoomdata.demo.helpers.StatusWebElem;
import com.zoomdata.demo.pages.basePages.MenuPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class LeftPaneMenu extends MenuPage {

    public LeftPaneMenu(WebDriver driver) {
        super(driver);
    }

    public static By menuTopPane = By.className("zd-main-header");
    private static By menuButton = By.xpath(".//*[contains(@value,'showSidePane')]");

    private static By favoritesButton = By.xpath(".//button[contains(.,'Favorites')]");
    private static By dataSousesButton = By.xpath(".//button[contains(.,'Data Sources')]");

    private static By menuLeftPane = By.xpath(".//div[contains(@class,'zd-side-pane')][.//ancestor::div[1][contains(@style,'display: block')][contains(@class,'sidepane')]]");
    private static String itemsPath = ".//div[contains(@class,'nav-tabs')][contains(@style,'display: block')]//ul[contains(@class,'nav-tabs')]//li";
    private static By dataSources = By.xpath(itemsPath + "[contains(@class,'sourceItem')]");
    private static By charts = By.xpath(itemsPath + "[contains(@class,'d-list-item')]");

    private SelenideElement getChart(String chartName){
        return  $(menuLeftPane).findAll(charts).filter(exactText(chartName)).first();
    }

    public ChartPage openChart(String chartName) {
        getChart(chartName).shouldBe(visible).click();
        return new ChartPage(driver);
    }

    public LeftPaneMenu clickDataSource(String dataSourceName) {
        $(menuLeftPane).findAll(dataSources).filter(exactText(dataSourceName)).first().shouldBe(visible).click();
        return this;
    }


    public LeftPaneMenu clickFavorites() {
        $(menuLeftPane).find(favoritesButton).shouldBe(visible).click();
        return this;
    }

    public LeftPaneMenu clickDataSources() {
        $(menuLeftPane).find(dataSousesButton).shouldBe(visible).click();
        return this;
    }

    public LeftPaneMenu checkChartsVisibility(StatusWebElem expectedStatus, List<String> chartNamesList) {
        checkChartsVisibility(expectedStatus, chartNamesList.toArray(new String[chartNamesList.size()]));
        return this;
    }

    public LeftPaneMenu checkChartsVisibility(StatusWebElem expectedStatus, String... chartNames) {
        for (String chartName : chartNames) {
            checkElementStatus(getChart(chartName), expectedStatus);
        }
        return this;
    }

    public LeftPaneMenu checkDataSourcesAmountInMenu(int expected) {
        $$(dataSources).shouldHave(size(expected));
        return this;
    }

}
