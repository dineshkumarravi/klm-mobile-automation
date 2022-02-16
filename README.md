

# *KLM - Appium Mobile Test Automation Framework with Page Object model using Appium with Java, TestNG and Maven** 

**(1) First we have to copy git repository on our local system and below command from command line:**

```
git clone https://github.com/dineshkumarravi/klm-mobile-automation.git
cd klm-mobile-automation
mvn clean test

```
**(2) Necessary Libraries and Software on Windows machine:** 

```
(1) Install Java - openjdk11 and Maven
(2) Install Intelij IDE
(3) Install Android studio and set SDK path details in environment variables
(4) Install node.js and appium
     npm install -g appium@1.22.2
(5) Connect Real android device
(6) Install KLM app from google playstore

```
**(3) Project Structure:**
```
  * src/main/java
    - pages.BookAFlight
    - utils
    - BaseTest
  * src/main/resources
    - config.properties 
  * src/test/java
    - BookAFlightTests
  * src/test/resources
       - data
  * pom.xml - maven dependencies    
```
**(4) Run test from different area:**
```
* Once we copy this project on local system either we can run the test cases from command line 
  or from any IDE of your preference. In Eclipse IDE we can right click on textng.xml inside 
  /src/main/resources folder and run as a TestNG suites.
* We can also run from BookAFlightTests.java file right click on it and run as a TestNG suites.

* #Report will be generated inside **test-output folder. For test report I am using extent report.
  **Extent.html file will be generated after every run.
  
* If you want to see any failure report then you have to change id or xpath in BookAFlightTests.java. It will
  capture screenshot with exception.
