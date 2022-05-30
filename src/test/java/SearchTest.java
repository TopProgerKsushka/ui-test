import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SearchTest {
    Util util;
    static String noGood = "товар, которого тут нет";
    static String good = "рубашка";
    static String searchButtonPath = "//*[@type=\"button\"][@role=\"button\"]";
    public static String getSearchButtonPath() {
        return searchButtonPath;
    }
    static String searchFieldPath = "//*[@type=\"text\"]";
    public static String getSearchFieldPath() {
        return searchFieldPath;
    }
    String newMessage = "//*[@class='worm__text']";
    static String errorPath = "//*[@class=\"_1a1RZUQgVMII1k2sfVmjqm _1SeMIZUaPlXaMPC95IMoTr\"]";

    @Before
    public void setUp() {
        util = new Util();
    }
    public void doSearch(WebDriver driver, String text) {
        //AuthTest authTest = new AuthTest();
        //authTest.authWithDriver(driver);
        util.tryClick(driver, By.xpath(searchButtonPath));
        util.tryType(driver, By.xpath(searchFieldPath), text);
        util.tryClick(driver, By.xpath(searchButtonPath));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void doSearchWithNoResult(WebDriver driver, String text) {
        //AuthTest authTest = new AuthTest();
        //authTest.authWithDriver(driver);
        driver.get("https://www.lamoda.ru/");
        util.tryClick(driver, By.xpath(searchButtonPath));
        util.tryType(driver, By.xpath(searchFieldPath), text);
        util.tryClick(driver, By.xpath(searchButtonPath));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void search() throws MalformedURLException {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver driver1 = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
        driver1.get("https://www.lamoda.ru/");
        doSearch(driver1, good);
        assertFalse(util.isElementPresent(driver1, By.xpath(errorPath)));
        driver1.quit();

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), firefoxOptions);
        driver.get("https://www.lamoda.ru/");
        doSearch(driver, good);
        assertFalse(util.isElementPresent(driver, By.xpath(errorPath)));
        driver.quit();
    }

    @Test
    public void searchWithNoResult() throws MalformedURLException {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver driver1 = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
        driver1.get("https://www.lamoda.ru/");
        doSearchWithNoResult(driver1, noGood);
        assertTrue(util.isElementPresent(driver1, By.xpath(errorPath)));
        driver1.quit();

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), firefoxOptions);
        driver.get("https://www.lamoda.ru/");
        doSearchWithNoResult(driver, noGood);
        assertTrue(util.isElementPresent(driver, By.xpath(errorPath)));
        driver.quit();
    }
}
