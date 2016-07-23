package com.zoomdata.demo;

import com.zoomdata.demo.dataProviders.TestDataProvider;
import com.zoomdata.demo.pages.basePages.ZoomBaseTest;
import com.zoomdata.demo.zoomComponents.Chart;
import org.testng.Assert;
import org.testng.annotations.Test;

import static java.lang.Integer.valueOf;

public class ZD_004_timeLine extends ZoomBaseTest {


    @Test(dataProviderClass = TestDataProvider.class, dataProvider = TestDataProvider.DP_004_TIME_LINE)
    public void timeLine(Chart chart) {
        chartPage = fromHome().openChart(chart)
                .checkChartLoaded()
                .onLeftChartPanel()
                .clickTimeBar()
                .selectTimeAttribute(chart.getTimeAttribute())
                .clickApplyButton()
                .checkTimeAttribute(chart.getTimeAttribute());

        String oldFromTime = chartPage.getCurrentFromTime();
        String oldToTime = chartPage.getCurrentToTime();

        Assert.assertEquals(oldFromTime, chartPage.getMaxFromTime(), "Current 'FROM' time is not set to the lowest possible value");
        Assert.assertEquals(oldToTime, chartPage.getMaxToTime(), "Current 'TO' time is not set to the highest possible value");

        chartPage.changeFromTimeWithScroller().changeToTimeWithScroller().checkChartLoaded();

        String newFromTime = chartPage.getCurrentFromTime();
        String newToTime = chartPage.getCurrentToTime();

        Assert.assertTrue(valueOf(oldFromTime) < valueOf(newFromTime), "From time was not changed with scroller");
        Assert.assertTrue(valueOf(oldToTime) > valueOf(newToTime), "From time was not changed with scroller");
    }


}
