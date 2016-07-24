# zoomdata  

###Project description:  
**Automation test project for regression testing of zoomdata application.**  
1. Java 8 + Maven + TestNG + Selenium + Selenide + Allure project.  
2. TestNG suite location: \src\test\resources\testng\testng.xml  
3. By a default suite will be executed in 5 parallel threads > could be changed in testng.xml, property <thread-count>  
4. Tests could be launched on Firefox, Chrome or IE browsers    
 :warning: Warning: firefox browser version 47.0.0 is not stackable with last webdriver version!  
 Please ensure than last firefox v.47.0.1 is installed on computer, where tests should be executed.  
5. Remote git location is [here](https://github.com/Vitalik549/zoomdata/)  

###Maven installation:  
 :warning: *Please ensure than Maven is installed by execution of the next command in cmd:* ```  mvn --version  ```  
*Info how to install Maven is [here](https://github.com/Vitalik549/zoomdata/)*  


###How to start test execution in IDE:  
1. To run full test suite: make right click on testng.xml file > Run with testNG  
2. To run separate package/class: make right click on package/class file > Run with testNG  


###How to start test execution in cmd:  
0. Open cmd :wink:  
1. go to local root project directory   
2. execute next command:  
```  mvn clean test -Dtest=%classname -Dbrowser=%browsername site ```  


Where:   
```%classname```  - name of the test class  
```site```  - maven goal to build allure reports  
```%browsername```  - indicate browser in which tests should be executed, possible values:  
- ```chrome```  
- ```firefox```  
- ```internetexplorer```  
If no browser property set - tests will be executed in **chrome** by a default.  
If no class name property provided - full test suite will be executed by a default.  


###Bug reports:  
Possible :beetle: bugs found in application reported in package   [bugReport](https://github.com/Vitalik549/zoomdata/tree/master/bugReport)  

###Allure reporting:  
Please ensure that test run was executed with 'site' goal in order to generate allure reports. File location is:   
> \target\site\allure-maven-plugin\index.hml  

:warning: Warning: Please open **index.html** file with firefox browser, as it's won't be shown correctly in chrome.    

If test run was started without ```site``` goal or  **index.html** file was not created after test run finished - it's possible to   create reports by executing ```mvn site``` in cmd from project root directory.   
