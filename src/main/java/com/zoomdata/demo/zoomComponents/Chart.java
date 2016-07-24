package com.zoomdata.demo.zoomComponents;

public class Chart {

    private String name;
    private String dataSource;
    private String chartStyle;

    private String timeAttribute;
    private String groupAttribute;

    public Chart(String dataSource, String chartName) {
        this.name = chartName;
        this.dataSource = dataSource;
    }

    public Chart(String dataSource, String chartName, String chartStyle) {
        this.name = chartName;
        this.dataSource = dataSource;
        this.chartStyle = chartStyle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getChartStyle() {
        return chartStyle;
    }

    public void setChartStyle(String chartStyle) {
        this.chartStyle = chartStyle;
    }

    public String getTimeAttribute() {
        return timeAttribute;
    }

    public void setTimeAttribute(String timeAttribute) {
        this.timeAttribute = timeAttribute;
    }

    public String getGroupAttribute() {
        return groupAttribute;
    }

    public void setGroupAttribute(String groupAttribute) {
        this.groupAttribute = groupAttribute;
    }



}
