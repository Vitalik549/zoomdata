package com.zoomdata.demo.dataProviders;

import com.zoomdata.demo.zoomComponents.Chart;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class TestDataProvider {

    public static final String sqlSource = "mysql_automation (not delete)";
    public static final String impalaSource = "impala_automation (not delete)";


    public static final String DP_002_SOURCES_LOAD = "dp_002_sources_load";
    public static final String DP_003_TABLE_VIEW_IN_BARS = "dp_003_tableViewInBars";
    public static final String DP_004_TIME_LINE = "dp_004_timeLine";
    public static final String DP_005_RADIAL_MENU_ZOOM = "dp_005_radialMenuZoom";

    @DataProvider(name = DP_002_SOURCES_LOAD)
    private static Object[][] dp_002_sources_load() {
        String chartToBeLoaded = "Donut";
        List<String> expectedCharts = new ArrayList<>();
        expectedCharts.add(chartToBeLoaded);
        expectedCharts.add("Scatter Plot");
        return new Object[][]{{impalaSource, chartToBeLoaded, expectedCharts}};
    }

    @DataProvider(name = DP_003_TABLE_VIEW_IN_BARS)
    private static Object[][] dp_003_tableViewInBars() {
        return new Object[][]{{new Chart(sqlSource, "Bars", "Table")}};
    }

    @DataProvider(name = DP_004_TIME_LINE)
    private static Object[][] dp_004_timeLine() {
        return new Object[][]{{new Chart(impalaSource, "Scatter Plot"){{setTimeAttribute("Dateid");}}}};
    }

    @DataProvider(name = DP_005_RADIAL_MENU_ZOOM)
    private static Object[][] dp_005_radialMenuZoom() {
        return new Object[][]{{new Chart(sqlSource, "Heat Map"){{setTimeAttribute("Product Group");}}}};
    }


}
