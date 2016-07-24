package com.zoomdata.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class RadialMenuPage extends ChartPage {

    By radialMenu = By.className("zd-radial-menu");

    public RadialMenuPage(WebDriver driver) {
        super(driver);
    }

    public ChartPage clickZoom() {
        clickRadiamMenuFilterByText("Zoom");
        return new ChartPage(driver);
    }

    public ChartPage clickFilter() {
        clickRadiamMenuFilterByText("filter");
        return new ChartPage(driver);
    }

    public ChartPage clickDetails() {
        clickRadiamMenuFilterByText("Details");
        return new ChartPage(driver);
    }

    public ChartPage clickTrend() {
        clickRadiamMenuFilterByText("Trend");
        return new ChartPage(driver);
    }

    private ChartPage clickRadiamMenuFilterByText(String buttonText) {
        $(radialMenu).shouldBe(visible).find(By.xpath(".//*[contains(@class,'zd-radial-item-filter')][.//*[contains(.,'" + buttonText + "')]]")).shouldBe(visible).click();
        return new ChartPage(driver);
    }


}
