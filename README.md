# zoomdata

###Project description:
Automation test project for regression testing of zoomdata application.
1. Java + Maven + TestNG + Selenium + Selenide project.
2. TestNG suite location: \src\test\resources\testng\testng.xml
3. By a default suite would be executed in 5 parallel threads > could be changed in testng.xml, property <thread-count>
4. Tests could be launched on Firefox, Chrome or IE browsers  
 :warning: Warning: firefox browser version 47.0.0 is not stackable with last webdriver version!  
 Please ensure than last firefox v.47.0.1 is installed on computer, where tests should be executed.  
5. Remote git location is [here](https://github.com/Vitalik549/zoomdata/)


Allure reporting tool may be used, but not required.


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
```  mvn clean test -Dtest=%classname -Dbrowser=%browsername  ``` 

Where:   
``` %classname```  - name of the test class   
``` %browsername```  - indicate browser in which tests should be executed, possible values: 
- ```chrome```  
- ```firefox```  
- ```internetexplorer```  
If no browser property set - tests will be executed in **chrome** by a default.  


###Bug reports:
Possible :beetle: bugs found in application reported in package  [bugReport](https://github.com/Vitalik549/zoomdata/tree/master/bugReport)
