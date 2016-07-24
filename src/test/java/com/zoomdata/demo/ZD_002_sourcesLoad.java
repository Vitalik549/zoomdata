package com.zoomdata.demo;

import com.zoomdata.demo.dataProviders.TestDataProvider;
import com.zoomdata.demo.pages.basePages.ZoomBaseTest;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static com.zoomdata.demo.helpers.StatusWebElem.VISIBLE;

public class ZD_002_sourcesLoad extends ZoomBaseTest {

    @Test(dataProviderClass = TestDataProvider.class, dataProvider = TestDataProvider.DP_002_SOURCES_LOAD)
    public void zd_002_sourcesLoad(String dataSource, String chartToBeLoaded, ArrayList<String>expectedCharts) {
        menu().clickDataSources().checkDataSourcesAmountInMenu(2)
                .clickDataSource(dataSource)
                .checkChartsVisibility(VISIBLE, expectedCharts)
                .clickChart(chartToBeLoaded).checkChartLoaded();
    }


}
