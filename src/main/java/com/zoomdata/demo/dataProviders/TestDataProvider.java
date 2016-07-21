package com.zoomdata.demo.dataProviders;

import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class TestDataProvider {


    public static final String DP_002_SOURCES_LOAD = "dp_002_sources_load";

    @DataProvider (name = DP_002_SOURCES_LOAD)
    private static Object[][] dp_002_sources_load(){
        String dataSource = "impala_automation (not delete)";
        String chartToBeLoaded = "Donut";
        List<String> expectedCharts = new ArrayList<>();
        expectedCharts.add(chartToBeLoaded);
        expectedCharts.add("Scatter Plot");
        return new Object[][]{{dataSource, chartToBeLoaded, expectedCharts}};
    }


}
