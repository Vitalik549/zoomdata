package com.zoomdata.demo.pages.leftPaneinChartPages;

import com.codeborne.selenide.SelenideElement;
import com.zoomdata.demo.pages.ChartPage;
import com.zoomdata.demo.pages.basePages.MenuPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byTitle;
import static com.codeborne.selenide.Selenide.$;

public class BasePopupPage extends MenuPage {

    //todo: report bug: when several popups opened - only last opened one could be managed. when any other popup clicked - last opened popup will be closed

    public  static final String UNDO = "Undo";
    public  static final String FILTERS = "Filters";
    public  static final String SORT_AND_LIMIT = "Sort & Limit";
    public  static final String COLOR = "Color";
    public  static final String RULERS = "Rulers";
    public  static final String EXPORT = "Export";
    public  static final String INFO = "Info";
    public  static final String CHART_STYLE = "Chart Style";
    public  static final String TIME_BAR = "Time Bar";

    protected By getPopup(String popupName) {
        return By.xpath(".//*[contains(@class,'rail-control')][.//*[contains(@class,'zd-palette-header')][text() = '" + popupName + "']]");
    }

    public BasePopupPage(WebDriver driver) {
        super(driver);
    }

    protected ChartPage closePopup(String popupName) {
        $(getPopup(popupName)).find(byTitle("Close")).shouldBe(visible).click();
        return new ChartPage(driver);
    }



    public SwitchPopupPage switchToPopup() {
        return new SwitchPopupPage(driver);
    }

    public void changPopupSize() {
    }

    public void dragAndDropPopup() {
    }


}
