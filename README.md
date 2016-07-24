# zoomdata

###Project description:
1. Java + Maven + TestNG + Selenium + Selenide project.
2. TestNG suite location: \src\test\resources\testng\testng.xml
3. By a default suite would be executed in 5 parallel threads > could be changed in testng.xml, property <thread-count>
4. Tests could be launched on Firefox, Chrome or IE browsers
- Warning: firefox browser version 47.0.0 is not stackable with last webdriver version!
Please ensure than last firefox v.47.0.1 is installed on computer, where tests should be executed.
5. Remote git location : https://github.com/Vitalik549/zoomdata


Allure reporting tool may be used, but not required.


###How to start test execution:
#Please ensure than Maven is installed on computer, where tests should be executed. (in command line execute "mvn --version")
#Info how to install Maven: https://maven.apache.org/install.html

In IDE:
1. To run full test suite - rightClick on testng.xml file > Run with testNG
2. TO run separate package/class - rightClick on package/class file > Run with testNG

In cmd:
0. Open cmd =)
1. go to local root project directory
2. execute next script:
mvn clean test -Dtest=%classname -Dbrowser=$browsername

where:
%classname - name of the test class
$browsername - indicate browser in which tests should be executed, possible values: chrome, firefox, internetexplorer.
If no browser property set - tests will be executed in chrome by a default.
