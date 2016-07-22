package com.zoomdata.demo.pages.leftPaneinChartPages;

import com.zoomdata.demo.helpers.WaitHelper;
import com.zoomdata.demo.pages.ChartPage;
import com.zoomdata.demo.pages.basePages.MenuPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.zoomdata.demo.pages.leftPaneinChartPages.BasePopupPage.*;

public class LeftPaneChart extends MenuPage {

    public LeftPaneChart(WebDriver driver) {
        super(driver);
    }

    //todo: methods should return proper pages. to be finished on demand

    By leftPaneInChart = By.className("leftPane");

    public ChartPage clickUndo() {
        clickControlButton(UNDO);
        return new ChartPage(driver);
    }

    public ChartPage clickFilters() {
        clickControlButton(FILTERS);
        return new ChartPage(driver);
    }

    public ChartPage clickSortAndLimit() {
        clickControlButton(SORT_AND_LIMIT);
        return new ChartPage(driver);
    }

    public ChartPage clickColor() {
        clickControlButton(COLOR);
        return new ChartPage(driver);
    }

    public ChartPage clickRulers() {
        clickControlButton(RULERS);
        return new ChartPage(driver);
    }

    public ChartPage clickExport() {
        clickControlButton(EXPORT);
        return new ChartPage(driver);
    }

    public ChartPage clickInfo() {
        clickControlButton(INFO);
        return new ChartPage(driver);
    }

    public PopupChartStylePage clickChartStyle() {
        $(By.id("controlStyle")).shouldBe(visible).click();
        return new PopupChartStylePage(driver);
    }

    public ChartPage clickTimeBar() {
        clickControlButton(TIME_BAR);
        return new ChartPage(driver);
    }

    private void clickControlButton(String button) {

        $(leftPaneInChart).find(By.xpath(".//*[contains(@class,'control-button')][.//*[text()='" + button + "']]")).shouldBe(visible).click();
    }
}
