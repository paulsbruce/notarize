import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class TestBase {

    protected AndroidDriver driver;

    protected Logger logger;

    public TestBase() {
        logger = Logger.getLogger(TestBase.class.getName());
        prepareAndroidForAppium();
    }

    protected void prepareAndroidForAppium() {

        try {
            SingletonFactory factory = SingletonFactory.getInstance();
            if(factory.getDriver() == null) {

                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("device", "selendroid");

                //mandatory capabilities
                capabilities.setCapability("deviceName", "Android");
                capabilities.setCapability("platformName", "Android");

                //other caps
                capabilities.setCapability("appPackage", "com.android.calculator2");
                capabilities.setCapability("appActivity", "com.android.calculator2.Calculator");

                capabilities.setCapability("noReset", true);

                driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
                factory.setDriver(driver);
                driver.launchApp();
            }
            else{
                driver = factory.getDriver();
            }
        } catch(MalformedURLException e) {
            logger.log(Level.SEVERE,"Could not create AndroidDriver", e);
        }
    }

    @Before
    public void clearInput() {
        if(driver.findElements(By.id("clr")).isEmpty())
            driver.findElement(By.id("eq")).click();
        if(!driver.findElements(By.id("clr")).isEmpty())
            driver.findElement(By.id("clr")).click();
    }


    // helper functions that apply to any given screen in the app

    protected WebElement id(String id) {
        return driver.findElement(By.id(id));
    }

    private static char[] numerics = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    protected void typeNumber(BigDecimal n) throws IllegalArgumentException {
        typeExpression(n.toString());
    }
    protected void typeExpression(String s) throws IllegalArgumentException {
        for(char c : s.toCharArray()) {

            if (ArrayUtils.contains(numerics, c)) {
                id("digit_" + c).click();
            } else if(c == '.') {
                id("dec_point").click();
            } else if(c == '+') {
                id("op_add").click();
            } else if(c == '-') {
                id("op_sub").click();
            } else if(c == '/') {
                id("op_div").click();
            } else if(c == '*') {
                id("op_mul").click();
            } else {
                throw new IllegalArgumentException("Character '" + c + "' is not accepted as input by DataUtils::typeNumber");
            }
        }
    }

    protected void pressAdd() {
        id("op_add").click();
    }
}