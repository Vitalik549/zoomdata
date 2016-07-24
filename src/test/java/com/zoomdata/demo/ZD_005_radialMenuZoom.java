package com.zoomdata.demo;

import com.zoomdata.demo.dataProviders.TestDataProvider;
import com.zoomdata.demo.pages.basePages.ZoomBaseTest;
import com.zoomdata.demo.zoomComponents.Chart;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ZD_005_radialMenuZoom extends ZoomBaseTest {

    @Test(dataProviderClass = TestDataProvider.class, dataProvider = TestDataProvider.DP_005_RADIAL_MENU_ZOOM)
    public void zd_005_radialMenuZoom(Chart chart) {
        chartPage = fromHome()
                .openChart(chart).checkChartLoaded().checkGroup2ValueEquals("Gender")
                .moveToChartPointInPercent(25, 25);

        String oldValue = chartPage.getCurrentPointXValue();
        String newXValue = chartPage.openRadialMenu(25, 25)
                .clickZoom()
                .changeAttributeInZoomPopup(chart.getTimeAttribute()).checkChartLoaded().checkGroup2ValueEquals(chart.getTimeAttribute())
                .moveToChartPointInPercent(10, 25).getCurrentPointXValue();

        Assert.assertEquals(newXValue, oldValue, "X value was changed after zoom");
    }
}