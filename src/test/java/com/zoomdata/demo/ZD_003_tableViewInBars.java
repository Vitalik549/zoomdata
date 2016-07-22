package com.zoomdata.demo;

import com.zoomdata.demo.dataProviders.TestDataProvider;
import com.zoomdata.demo.pages.basePages.ZoomBaseTest;
import org.testng.annotations.Test;

public class ZD_003_tableViewInBars extends ZoomBaseTest {

    String groupAttribute = "City";

    @Test(dataProviderClass = TestDataProvider.class, dataProvider = TestDataProvider.DP_003_TABLE_VIEW_IN_BARS)
    public void sourcesLoad(String dataSource, String chartName) {
        fromHome().openChart(dataSource, chartName)
                .checkChartLoaded()
                .changeGroupAttributeTo(groupAttribute)

                .onLeftChartPanel().clickChartStyle()
                .selectChartStyleType("Table")
                .selectChartStyle("Table")
                .checkChartRebuilded()
                .clickPivotFirstColumn() //todo
                .checkSelectedAttributeInPopup(groupAttribute);


    }


}
