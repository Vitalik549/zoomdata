package com.zoomdata.demo.pages.leftPaneinChartPages;

import com.codeborne.selenide.Condition;
import com.zoomdata.demo.pages.ChartPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class PopupChartStylePage extends BasePopupPage {

    public static final String THIS = CHART_STYLE;

    public PopupChartStylePage(WebDriver driver) {
        super(driver);
    }

    public ChartPage closePopup() {
        return super.closePopup(THIS);
    }

    public PopupChartStylePage selectChartStyleType(String typeName) {
        $(getPopup(THIS)).find(By.xpath(".//*[contains(@class,'type-container')]//*[text()='" + typeName + "']")).shouldBe(visible).click();
        return this;
    }

    public ChartPage selectChartStyle(String typeName) {
        $(getPopup(THIS)).find(By.xpath(".//*[contains(@class,'zd-list-item')][.//*[text()='" + typeName + "']]")).shouldBe(visible).click();
        return new ChartPage(driver);
    }


}
