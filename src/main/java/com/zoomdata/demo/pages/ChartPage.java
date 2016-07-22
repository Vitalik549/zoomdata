package com.zoomdata.demo.pages;

import com.zoomdata.demo.pages.basePages.MenuPage;
import com.zoomdata.demo.pages.leftPaneinChartPages.LeftPaneChart;
import com.zoomdata.demo.pages.leftPaneinChartPages.SwitchPopupPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.zoomdata.demo.helpers.StatusWebElem.NOT_VISIBLE;
import static com.zoomdata.demo.helpers.StatusWebElem.VISIBLE;

public class ChartPage extends MenuPage {

    By chart = By.xpath(".//canvas[not(contains(@class,'zdView-Background'))]");
    By bottomLabel = By.xpath(".//*[contains(@class,'widgetLabel')][contains(@class,'bottom')]");
    By leftLabel = By.xpath(".//*[contains(@class,'widgetLabel')][contains(@class,'left')]");

    public ChartPage(WebDriver driver) {
        super(driver);
    }

    public ChartPage checkChartLoaded() {
        checkElementStatus(chart, VISIBLE);
        return this;
    }

    public ChartPage changeGroupAttributeTo(String newGroupName) {
        $(bottomLabel).find(byText("Group:")).shouldBe(visible).click();
        $(By.className("zd-palettepopup")).shouldBe(visible).find(By.xpath(".//*[text() ='" + newGroupName + "']"))
                .shouldBe(visible.because("Group " + newGroupName + " expected to be in groupList")).click();
        checkGroupAttrubute(newGroupName);
        return this;
    }

    public ChartPage checkSelectedAttributeInPopup(String expected) {
        $(By.className("zd-palettepopup")).shouldBe(visible).find(By.xpath(".//*[text() ='" + expected + "']")).find(By.xpath(".//ancestor::li[1]")).shouldHave(cssClass("selected"));
        return this;
    }

    public LeftPaneChart onLeftChartPanel() {
        return new LeftPaneChart(driver);
    }

    public SwitchPopupPage switchToPopup() {
        return new SwitchPopupPage(driver);
    }

    public ChartPage checkChartRebuilded() {
        checkElementStatus(chart, NOT_VISIBLE);
        checkChartLoaded();
        return this;
    }

    public ChartPage checkGroupAttrubute(String expectedGroupName) {
        $(bottomLabel).find(byText("Group:")).find(By.xpath(".//following-sibling::b[1]")).shouldHave(text(expectedGroupName));
        return this;
    }

    public ChartPage clickPivotFirstColumn() {
        Actions actions = new Actions(driver);
        actions.moveToElement($(byText("mysql_automation (not delete)")).shouldBe(visible)).moveByOffset(-70,40).click().perform();
        return this;
    }
}