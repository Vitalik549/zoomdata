package com.zoomdata.demo;

import com.zoomdata.demo.pages.basePages.ZoomBaseTest;
import org.testng.annotations.Test;

public class ZD_001_login extends ZoomBaseTest{

    @Test(groups = GENERAL)
    public void zd_001_login() {
        login().user(admin).checkUserLoggedIn(true).checkDataSourcesAmount(2);
    }
}
