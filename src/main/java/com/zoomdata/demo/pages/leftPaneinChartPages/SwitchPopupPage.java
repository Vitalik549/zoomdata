package com.zoomdata.demo.pages.leftPaneinChartPages;

import com.zoomdata.demo.pages.ChartPage;
import com.zoomdata.demo.pages.basePages.MenuPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.zoomdata.demo.pages.leftPaneinChartPages.BasePopupPage.*;
import static com.zoomdata.demo.pages.leftPaneinChartPages.BasePopupPage.CHART_STYLE;
import static com.zoomdata.demo.pages.leftPaneinChartPages.BasePopupPage.TIME_BAR;

public class SwitchPopupPage extends MenuPage {

    public SwitchPopupPage(WebDriver driver) {
        super(driver);
    }

    public ChartPage undoPopup() {
        checkPopupVisible(UNDO);
        return new ChartPage(driver);
    }

    public ChartPage filtersPopup() {
        checkPopupVisible(FILTERS);
        return new ChartPage(driver);
    }

    public ChartPage sortAndLimitPopup() {
        checkPopupVisible(SORT_AND_LIMIT);
        return new ChartPage(driver);
    }

    public ChartPage colorPopup() {
        checkPopupVisible(COLOR);
        return new ChartPage(driver);
    }

    public ChartPage rulersPopup() {
        checkPopupVisible(RULERS);
        return new ChartPage(driver);
    }

    public ChartPage exportPopup() {
        checkPopupVisible(EXPORT);
        return new ChartPage(driver);
    }

    public ChartPage infoPopup() {
        checkPopupVisible(INFO);
        return new ChartPage(driver);
    }

    public PopupChartStylePage chartStylePopup() {
        checkPopupVisible(CHART_STYLE);
        return new PopupChartStylePage(driver);
    }

    public ChartPage timeBarPopup() {
        checkPopupVisible(TIME_BAR);
        return new ChartPage(driver);
    }

    private void checkPopupVisible(String popupName) {
        $(By.xpath(".//*[contains(@class,'rail-control')]//*[contains(@class,'zd-palette-header')][text() = '" + popupName + "']"))
                .shouldBe(visible.because("Popup with name <" + popupName + "> expected to be opened in order to switch to it."));
    }

}
