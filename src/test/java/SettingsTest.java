import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SettingsTest {
    Util util;
    static String profilePath = "//*/a[contains(./text(), \"Профиль\")]";
    static String dataPath = "//*/a[contains(./text(), \"Мои данные\")]";
    //static String savePath = "//*[@class='x-button x-button_primaryFilledWeb7184 x-button_40 x-button_intrinsic-width']";
    //static String savePath = "//*[@id=\"vue-root\"]/main/div/div/div/div[2]/div/div[2]/div[7]/div[2]/button";
    static String savePath = "//*/button[contains(./text(), \"Сохранить\")]";
    static String checkSavePath = "//*[@class=\"_1z_7UoOP7w4Rpm0YrqZTsp jUmGHBm1DK4RMsGq-sZjT _2cFVuYoDTBHONKB2F17xzq\"]";
    static String fieldPath = "//*[@name='Имя'][@class='input__input']";
    static String errorPath = "//*[@class=\"input__validation-message input__validation-message_default-theme\"]";

    @Before
    public void setUp() {
        util = new Util();
    }
    public void doChange(WebDriver driver) {
        AuthTest authTest = new AuthTest();
        authTest.util = new Util();
        authTest.authWithDriver(driver);
        util.tryClick(driver, By.xpath(profilePath));
        util.tryClick(driver, By.xpath(dataPath));
        util.tryType(driver, By.xpath(fieldPath), "Масюся");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        util.tryClick(driver, By.xpath(savePath));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void doIncorrectChange(WebDriver driver) {
        AuthTest authTest = new AuthTest();
        authTest.util = new Util();
        authTest.authWithDriver(driver);
        util.tryClick(driver, By.xpath(profilePath));
        util.tryClick(driver, By.xpath(dataPath));
        driver.findElement(By.xpath(fieldPath)).sendKeys("\b\b\b\b\b\b\b");
        util.tryType(driver, By.xpath(fieldPath), "");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void correctChange() throws MalformedURLException {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), firefoxOptions);
        doChange(driver);
        assertTrue(util.isElementPresent(driver, By.xpath(checkSavePath)));
        driver.quit();

        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver driver1 = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
        doChange(driver1);
        assertTrue(util.isElementPresent(driver1, By.xpath(checkSavePath)));
        driver1.quit();
    }

    @Test
    public void incorrectChange() throws MalformedURLException {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver driver1 = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
        doIncorrectChange(driver1);
        assertTrue(util.isElementPresent(driver1, By.xpath(errorPath)));
        driver1.quit();

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), firefoxOptions);
        doIncorrectChange(driver);
        assertTrue(util.isElementPresent(driver, By.xpath(errorPath)));
        driver.quit();
    }
}
