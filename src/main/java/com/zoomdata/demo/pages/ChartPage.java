package com.zoomdata.demo.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.zoomdata.demo.helpers.WaitHelper;
import com.zoomdata.demo.pages.basePages.MenuPage;
import com.zoomdata.demo.pages.leftPaneinChartPages.LeftPaneChart;
import com.zoomdata.demo.pages.leftPaneinChartPages.SwitchPopupPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.zoomdata.demo.helpers.StatusWebElem.NOT_VISIBLE;
import static com.zoomdata.demo.helpers.StatusWebElem.VISIBLE;

public class ChartPage extends MenuPage {

    By chart = By.xpath(".//canvas[not(contains(@class,'zdView-Background'))]");
    By bottomChartLabel = By.xpath(".//*[contains(@class,'widgetLabel')][contains(@class,'bottom')]");
    By bottomPane = By.className("bottomPane");
    By leftLabel = By.xpath(".//*[contains(@class,'widgetLabel')][contains(@class,'left')]");

    By fromCalendar = By.xpath(".//*[contains(@class,'zdView-DateIndicator')][1]");
    By toCalendar = By.xpath(".//*[contains(@class,'zdView-DateIndicator')][2]");
    By fromCurrentTime = By.xpath(".//*[contains(@class,'tooltipDatePicker-content')][contains(@class,'left')]");
    By toCurrentTime = By.xpath(".//*[contains(@class,'tooltipDatePicker-content')][contains(@class,'right')]");
    By fromTimeScroller = By.xpath(".//*[contains(@class,'zdView-DragElement')][contains(@class,'left')]");
    By toTimeScroller = By.xpath(".//*[contains(@class,'zdView-DragElement')][contains(@class,'right')]");

    public ChartPage(WebDriver driver) {
        super(driver);
    }

    public ChartPage checkChartLoaded() {
        checkElementStatus(chart, VISIBLE);
        return this;
    }

    public ChartPage changeGroupAttributeTo(String newGroupName) {
        $(bottomChartLabel).find(byText("Group:")).shouldBe(visible).click();
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
        WaitHelper.setImplicitWait(driver, 1);
        $(By.id("progress")).should(exist).should(disappear); //todo: optimize, disappear wait too long
        WaitHelper.setImplicitWaitDefault(driver);
        return this;
    }

    public ChartPage checkGroupAttrubute(String expectedGroupName) {
        $(bottomChartLabel).find(byText("Group:")).find(By.xpath(".//following-sibling::b[1]")).shouldHave(text(expectedGroupName));
        return this;
    }

    public ChartPage clickPivotFirstColumn() {
        clickByOffset(-70, 40);
        return this;
    }

    private void clickByOffset(int x, int y) {
        Actions actions = new Actions(driver);
        actions.moveToElement($(By.className("widget-label-wrapper")).shouldBe(visible)).moveByOffset(x, y).click().build().perform();
    }

    public ChartPage checkTimeAttribute(String expectedTimeAttribute) {
        $(By.className("attr-indicator")).shouldHave(text(expectedTimeAttribute));
        return new ChartPage(driver);
    }

    public String getMaxFromTime() {
        return $(bottomPane).find(fromCalendar).shouldBe(visible).getText();
    }

    public String getMaxToTime() {
        return $(bottomPane).find(toCalendar).shouldBe(visible).getText();
    }

    public String getCurrentFromTime() {
        return $(fromCurrentTime).shouldBe(visible).getText();
    }

    public String getCurrentToTime() {
        return $(toCurrentTime).shouldBe(visible).getText();
    }

    private void scroll(SelenideElement scrollElement, int x) {
        Actions actions = new Actions(driver);
        actions.dragAndDropBy(scrollElement.shouldBe(visible), x, 0).build().perform();
    }

    public ChartPage changeFromTimeWithScroller() {
        scroll($(fromTimeScroller), 200);
        return this;
    }


    public ChartPage changeToTimeWithScroller() {
        scroll($(toTimeScroller), -200);
        return this;
    }


    // current time tooltipDatePicker-content
}