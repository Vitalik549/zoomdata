package com.zoomdata.demo.pages.basePages;

import com.zoomdata.demo.pages.LeftPaneMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.zoomdata.demo.helpers.StatusWebElem.NOT_VISIBLE;
import static com.zoomdata.demo.helpers.StatusWebElem.VISIBLE;

public class MenuPage extends BasePage {

    public MenuPage(WebDriver driver) {
        super(driver);
    }

    public static By menuTopPane = By.className("zd-main-header");
    protected static By menuButton = By.xpath(".//*[contains(@value,'showSidePane')]");

    protected static By menuLeftPane = By.xpath(".//div[contains(@class,'zd-side-pane')][.//ancestor::div[1][contains(@style,'display: block')][contains(@class,'sidepane')]]");

    public LeftPaneMenu openLeftMenu() {
        if(!$(menuLeftPane).is(visible)){
            $(menuTopPane).find(menuButton).shouldBe(visible).click();
        }
        checkElementStatus(menuLeftPane, VISIBLE);
        $(menuLeftPane).shouldBe(visible);
        return new LeftPaneMenu(driver);
    }

    public void closeLeftMenu() {
        if($(menuLeftPane).is(visible)){
            $(menuTopPane).find(menuButton).shouldBe(visible).click();
        }
        checkElementStatus(menuLeftPane, NOT_VISIBLE);
    }

}
