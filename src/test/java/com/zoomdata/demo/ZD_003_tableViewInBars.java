package com.zoomdata.demo;

import com.zoomdata.demo.dataProviders.TestDataProvider;
import com.zoomdata.demo.pages.basePages.ZoomBaseTest;
import com.zoomdata.demo.zoomComponents.Chart;
import org.testng.annotations.Test;

public class ZD_003_tableViewInBars extends ZoomBaseTest {

    @Test(dataProviderClass = TestDataProvider.class, dataProvider = TestDataProvider.DP_003_TABLE_VIEW_IN_BARS)
    public void zd_003_tableViewInBars(Chart chart) {
        fromHome().openChart(chart)
                .checkChartLoaded()
                .changeGroupAttributeTo(chart.getGroupAttribute())
                .onLeftChartPanel().clickChartStyle()
                .selectChartStyleType(chart.getChartStyle())
                .selectChartStyle(chart.getChartStyle())
                .checkChartRebuilded()
                .clickPivotFirstColumn()
                .checkSelectedAttributeInPopup(chart.getGroupAttribute());


    }


}
