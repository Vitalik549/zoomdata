package com.zoomdata.demo.pages;

import com.zoomdata.demo.pages.basePages.MenuPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.zoomdata.demo.helpers.StatusWebElem.VISIBLE;

public class ChartPage extends MenuPage{

    By chart =  By.xpath(".//canvas[contains(@class,'zr-element')]");

    public ChartPage(WebDriver driver){
        super(driver);
    }

    public ChartPage checkChartLoaded(){
        checkElementStatus(chart, VISIBLE);
        return this;
    }
}
