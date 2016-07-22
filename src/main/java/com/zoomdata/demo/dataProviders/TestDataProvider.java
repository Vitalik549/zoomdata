package com.zoomdata.demo.dataProviders;

import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class TestDataProvider {


    public static final String DP_002_SOURCES_LOAD = "dp_002_sources_load";
    public static final String DP_003_TABLE_VIEW_IN_BARS = "dp_003_tableViewInBars";

    @DataProvider (name = DP_002_SOURCES_LOAD)
    private static Object[][] dp_002_sources_load(){
        String dataSource = "impala_automation (not delete)";
        String chartToBeLoaded = "Donut";
        List<String> expectedCharts = new ArrayList<>();
        expectedCharts.add(chartToBeLoaded);
        expectedCharts.add("Scatter Plot");
        return new Object[][]{{dataSource, chartToBeLoaded, expectedCharts}};
    }

    @DataProvider (name = DP_003_TABLE_VIEW_IN_BARS)
    private static Object[][] dp_003_tableViewInBars(){
        String dataSource = "mysql_automation (not delete)";
        String chart = "Bars";
        return new Object[][]{{dataSource, chart}};
    }


}
