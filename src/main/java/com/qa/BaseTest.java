package com.qa;

import com.qa.utils.TestUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
/**
 * Common driver and mobile elements methods implementation in Base Test class.
 * @author Dineshkumar.
 *
 */

public class BaseTest {
    protected static AppiumDriver driver;
    private static AppiumDriverLocalService server;
    protected static ExtentReports extent;
    protected static ExtentTest extentTest;

    /**
     * Initialize the page class elements.
     */
    public BaseTest() {
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    /**
     * Get appium driver instance.
     * @return driver
     */
    public AppiumDriver getDriver() {
        return driver;
    }

    /**
     * For Mac - comment the below methods and start appium server manually.
     * For Windows - Below appium server starts programmatically.
     *
     */
    @BeforeSuite
    public void beforeSuite() throws Exception {
        server = getAppiumServerDefault();
        if (!checkIfAppiumServerIsRunning(4723)) {
            server.start();
        }
    }

    public boolean checkIfAppiumServerIsRunning(int port) throws Exception {
        boolean isAppiumServerRunning = false;
        ServerSocket socket;
        try {
            socket = new ServerSocket(port);
            socket.close();
        } catch (IOException e) {
            isAppiumServerRunning = true;
        } finally {
            socket = null;
        }
        return isAppiumServerRunning;
    }

    public AppiumDriverLocalService getAppiumServerDefault() {
        return AppiumDriverLocalService.buildDefaultService();
    }

    /**
     * Stop the Appium server if it's running.
     */
    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        if (server.isRunning()) {
            server.stop();
        }
    }

    /**
     * Configure device details via desired capabilities
     * Initialize the android driver instance.
     */
    @BeforeTest
    public void beforeTest() throws Exception {
        URL url;
        InputStream inputStream = null;
        Properties props;
        try {
            props = new Properties();
            String propFileName = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);
            //Real Android device details
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("deviceName", props.getProperty("deviceName"));
            desiredCapabilities.setCapability("platformName", props.getProperty("platformName"));
            desiredCapabilities.setCapability("version", props.getProperty("version"));
            url = new URL(props.getProperty("appiumURL"));

            switch (props.getProperty("platformName")) {
                case "Android":
                    //apk package details
                    desiredCapabilities.setCapability("automationName", props.getProperty("androidAutomationName"));
                    desiredCapabilities.setCapability("appPackage", props.getProperty("androidAppPackage"));
                    desiredCapabilities.setCapability("appActivity", props.getProperty("androidAppActivity"));
                    //For Emulator
                    /*desiredCapabilities.setCapability("avd", props.getProperty("emulatorName"));
                    desiredCapabilities.setCapability("avdLaunchTimeout", 120000);
                    String androidAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                    			+ File.separator + "resources" + File.separator + "app" + File.separator + "Android.SauceLabs.Mobile.Sample.app.2.2.1.apk";
                    utils.log().info("appUrl is" + androidAppUrl);
                    desiredCapabilities.setCapability("app", androidAppUrl);*/
                    //Initiate Android driver
                    driver = new AndroidDriver(url, desiredCapabilities);
                    System.out.println("Session ID: " + driver.getSessionId());
                    break;
                case "iOS":
                    driver = new IOSDriver(url, desiredCapabilities);
                    break;
                default:
                    throw new Exception("Invalid platform! - " + props.getProperty("platformName"));
            }
            //Configure extent report
            setExtentReport(props);
        } catch (Exception e) {
            throw e;
        }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * Click on Mobile element.
     * @param element
     */
    public void click(MobileElement element) {
        waitForVisibility(element);
        element.click();
    }

    /**
     * Send values in mobile element.
     * @param element
     * @param txt
     */
    public void sendKeys(MobileElement element, String txt) {
        waitForVisibility(element);
        element.sendKeys(txt);
    }

    /**
     * Get the attribute of mobile element.
     * @param element
     * @param attribute
     * @return
     */
    public String getAttribute(MobileElement element, String attribute) {
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }

    /**
     * Wait for visibility of element.
     * @param element
     */
    public void waitForVisibility(MobileElement element) {
        WebDriverWait wait = new WebDriverWait(driver, TestUtils.WAIT);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Clear the values present in the element.
     * @param element
     */
    public void clear(MobileElement element) {
        waitForVisibility(element);
        element.clear();
    }
    /**
     * Retrieve the text of the element.
     * @param element
     * @return txt
     */

    public String getText(MobileElement element) {
        String txt = null;
        txt = getAttribute(element, "text");
        return txt;
    }

    /**
     * Tap the mobile element.
     * @param element
     */

    public void tap(MobileElement element) {
        waitForVisibility(element);
        TouchAction t = new TouchAction(driver);
        t.tap(ElementOption.element(element)).perform();
    }

    /**
     * Swipe the screen.
     *
     */
    public static void swipeVertical() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int endX = startX;
        int startY = (int) (size.height * 0.6);
        int endY = (int) (size.height * 0.2);
        TouchAction t = new TouchAction(driver);
        t.press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
                .moveTo(PointOption.point(endX, endY))
                .release()
                .perform();
    }

    /**
     * Scroll to particular mobile element on page using UiScrollable class.
     *
     */
    public MobileElement scrollToElementForMonthAndYear(String month) {
        return (MobileElement) ((FindsByAndroidUIAutomator) driver).findElementByAndroidUIAutomator(
                "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
                        + "new UiSelector().text(\"" + month + "\"));");
    }

    /**
     * Get the test data from json file.
     * @param dataFileName pass the test data file path
     *
     */
    protected static JSONObject getData(String dataFileName) throws IOException {
        InputStream data = null;
        JSONObject jsonData;
        try {
            data = BaseTest.class.getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener tokener = new JSONTokener(data);
            jsonData = new JSONObject(tokener);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (data != null) {
                data.close();
            }
        }
        return jsonData;
    }

    /**
     * Set the extent report project details.
     * @param props
     *
     */
    public void setExtentReport(Properties props) {
        extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/Extent.html", true);
        extent.addSystemInfo("Device Name", props.getProperty("deviceName"));
        extent.addSystemInfo("Platform Name", props.getProperty("platformName"));
        extent.addSystemInfo("Environment", "Prod");
    }

    /**
     * Flush and close the report
     */
    public void endReport() {
        extent.flush();
        extent.close();
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(LogStatus.FAIL, result.getName() + " TEST CASE FAILED reason:" + result.getThrowable());
            String screenshotPath = getScreenshot(driver, result.getName());
            extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath));
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(LogStatus.SKIP, "Test Case SKIPPED IS " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());
        }
        extent.endTest(extentTest); // ending test and ends the current test and prepare to create html extent
    }

    @AfterTest(alwaysRun = true)
    public void afterTest() throws IOException {
        if (driver != null) {
            driver.quit();
        }
        endReport();
    }
    /**
     * Get the screenshot file
     * @param driver
     * @param screenshotName
     */
    public String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        //TakesScreenshot ts = (TakesScreenshot) driver;
        File source = getDriver().getScreenshotAs(OutputType.FILE);
        // after execution, you could see a folder "FailedTestsScreenshots"
        // under src folder
        String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName
                + ".jpeg";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }
}