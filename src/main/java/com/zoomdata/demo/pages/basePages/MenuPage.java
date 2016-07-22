package com.zoomdata.demo.pages.basePages;

import com.codeborne.selenide.SelenideElement;
import com.zoomdata.demo.helpers.StatusWebElem;
import com.zoomdata.demo.pages.ChartPage;
import com.zoomdata.demo.pages.LeftPaneMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.zoomdata.demo.helpers.StatusWebElem.*;

public class MenuPage extends BasePage {

    public MenuPage(WebDriver driver) {
        super(driver);
    }

    public static By menuTopPane = By.className("zd-main-header");
    private static By menuButton = By.xpath(".//*[contains(@value,'showSidePane')]");

    //todo - probably bug should be reported (check with developers), replace "menuLeftPane" xpath with shorter one when problem fixed.
    // when menu opened/closed several times on different pages - amount of menu panes increases in html, though all panes except one have "style: display: none". So selector.click() may fail, as invisible elements could be selected.
    // so this huge xpath was created to find visible SidePane:  it is located in visible (style has 'display: block') div with class "sidepane".
    private static By menuLeftPane = By.xpath(".//div[contains(@class,'zd-side-pane')][.//ancestor::div[1][contains(@style,'display: block')][contains(@class,'sidepane')]]");

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
