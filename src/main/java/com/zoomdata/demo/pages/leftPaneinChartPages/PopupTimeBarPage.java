package com.zoomdata.demo.pages.leftPaneinChartPages;

import com.codeborne.selenide.Selectors;
import com.zoomdata.demo.pages.ChartPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class PopupTimeBarPage extends BasePopupPage {

    public static final String THIS = TIME_BAR;

    public PopupTimeBarPage(WebDriver driver) {
        super(driver);
    }

    public ChartPage closePopup() {
        return super.closePopup(THIS);
    }

    public PopupTimeBarPage selectTimeAttribute(String timeAttribute) {
        $(getPopup(THIS)).find(By.className("time-attributes-list")).find(By.xpath(".//li[.//*[text()='" + timeAttribute + "']]")).shouldBe(visible).click();
        return this;
    }

    public ChartPage clickApplyButton(){
        $(Selectors.byValue("applyTime")).shouldBe(visible).click();
        return new ChartPage(driver);
    }



}