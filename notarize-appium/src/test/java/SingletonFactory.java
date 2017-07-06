import io.appium.java_client.android.AndroidDriver;

public class SingletonFactory{

    private static SingletonFactory instance = new SingletonFactory();
    private static AndroidDriver driver;

    private SingletonFactory() {
    }

    // Get the only object available
    public static SingletonFactory getInstance() {
        return instance;
    }

    // Get the only object available
    public void setDriver(AndroidDriver driver1) {
        driver = driver1;
    }

    public AndroidDriver getDriver() {
        return driver;
    }
}

