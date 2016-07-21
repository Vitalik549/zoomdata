package com.zoomdata.demo.pages.basePages;

import com.codeborne.selenide.SelenideElement;
import com.zoomdata.demo.helpers.StatusWebElem;
import com.zoomdata.demo.pages.ChartPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MenuPage extends BasePage {

    public static By menuTopPane = By.className("zd-main-header");
    private static By menuButton = By.xpath(".//*[contains(@value,'showSidePane')]");

    private static By favoritesButton = By.xpath(".//button[contains(.,'Favorites')]");
    private static By dataSoucesButton = By.xpath(".//button[contains(.,'Data Sources')]");

    //todo - probably bug should be reported (check with developers), replace "menuLeftPane" xpath with shorter one when problem fixed.
    // when menu opened/closed several times on different pages - amount of menu panes increases in html, though all panes except one have "style: display: none". So selector.click() may fail, as invisible elements could be selected.
    // so this huge xpath was created to find visible SidePane:  it is located in visible (style has 'display: block') div with class "sidepane".
    private static By menuLeftPane = By.xpath(".//div[contains(@class,'zd-side-pane')][.//ancestor::div[1][contains(@style,'display: block')][contains(@class,'sidepane')]]");
    // todo same issue with dataSources

    private static String itemsPath = ".//div[contains(@class,'nav-tabs')][contains(@style,'display: block')]//ul[contains(@class,'nav-tabs')]//li";
    private static By dataSources = By.xpath(itemsPath + "[contains(@class,'sourceItem')]");
    private static By charts = By.xpath(itemsPath + "[contains(@class,'d-list-item')]");


    public MenuPage clickDataSource(String dataSourceName) {
        $(menuLeftPane).findAll(dataSources).filter(exactText(dataSourceName)).first().shouldBe(visible).click();
        return this;
    }

    private SelenideElement getChart(String chartName){
        return  $(menuLeftPane).findAll(charts).filter(exactText(chartName)).first();
    }

    public ChartPage clickChart(String chartName) {
        getChart(chartName).shouldBe(visible).click();
        return new ChartPage(driver);
    }

    public MenuPage checkChartsVisibility(StatusWebElem expectedStatus, List<String> chartNamesList) {
        checkChartsVisibility(expectedStatus, chartNamesList.toArray(new String[chartNamesList.size()]));
        return this;
    }

    public MenuPage checkChartsVisibility(StatusWebElem expectedStatus, String... chartNames) {
        for (String chartName : chartNames) {
            checkElementStatus(getChart(chartName), expectedStatus);
        }
        return this;
    }

    public MenuPage(WebDriver driver) {
        super(driver);
    }

    public MenuPage clickMenu() {
        $(menuTopPane).find(menuButton).shouldBe(visible).click();
        return this;
    }

    public MenuPage clickFavorites() {
        $(menuLeftPane).find(favoritesButton).shouldBe(visible).click();
        return this;
    }

    public MenuPage clickDataSources() {
        $(menuLeftPane).find(dataSoucesButton).shouldBe(visible).click();
        return this;
    }

    public MenuPage checkDataSourcesAmountInMenu(int expected) {
        $$(dataSources).shouldHave(size(expected));
        return this;
    }

    public MenuPage selectDataSource(int expected) {
        $$(dataSources).shouldHave(size(expected));
        return this;
    }

}
