// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class AuthTest {
    static String loginButtonPath = "//*[@class=\"wCjUeog4KtWw64IplV1e6 _3A5-9K2JrODjfTiazRr7pk BLS-hOSrikRnPX76_f5Xr\"]";
    //static String loginButtonPath = "//*/button[contains(./text(), \"Войти\")]";
    public static String getLoginButtonPath() {
        return loginButtonPath;
    }
    static String loginPath = "//*[@id=\"vue-root\"]/div[10]/div[6]/div/div/div[2]/div[2]/div/div[2]/div/div[2]/div[1]/form/div[2]/div/div[1]/input";
    //static String loginPath = "//*[@type='email'][@class='input__input']"; // fails
    public static String getLoginPath() {
        return loginPath;
    }
    static String passwordPath = "//*[@name='password']";
    public static String getPasswordPath() {
        return passwordPath;
    }
    static String buttonPath = "//*[@role=\"button\"][@type=\"submit\"]";
    public static String getButtonPath() {
        return buttonPath;
    }
    String invalidErrorPath = "//*[@class=\"_2xyPzMQ7VZwaQbGuJjmVDX\"]";

    String profilePath = "//*[@href='/sales/current/order/history/']";
    String mainLabel = "//*[@aria-label=\"Главная\"]";
    Util util;
    //private Map<String, Object> vars;
    //JavascriptExecutor js;
    @Before
    public void setUp() {
        //System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ксюша\\Documents\\university\\testing\\ui-test\\src\\main\\resources\\chromedriver.exe");
        //js = (JavascriptExecutor) driver;
        //vars = new HashMap<String, Object>();
        util = new Util();
    }

    @Test
    public void correctAuth() throws MalformedURLException {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver driver1 = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
        authWithDriver(driver1);
        assertTrue(util.isElementPresent(driver1, By.xpath(mainLabel)));
        driver1.quit();

//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ксюша\\Documents\\university\\testing\\ui-test\\src\\main\\resources\\chromedriver.exe");
//        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Ксюша\\Documents\\university\\testing\\ui-test\\src\\main\\resources\\geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), firefoxOptions);
        authWithDriver(driver);
        assertTrue(util.isElementPresent(driver, By.xpath(mainLabel)));
        driver.quit();

    }

    @Test
    public void incorrectAuth() throws MalformedURLException {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver driver1 = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
        incorrectAuthWithDriver(driver1);
        assertTrue(util.isElementPresent(driver1, By.xpath(invalidErrorPath)));
        driver1.quit();

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), firefoxOptions);
        incorrectAuthWithDriver(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(invalidErrorPath)));
        assertTrue(util.isElementPresent(driver, By.xpath(invalidErrorPath)));
        driver.quit();
    }
    public void authWithDriver(WebDriver driver) {
        driver.get("https://www.lamoda.ru/");

        util.tryClick(driver, By.xpath(loginButtonPath));

        util.tryType(driver, By.xpath(loginPath), util.getLogin());
        util.tryType(driver, By.xpath(passwordPath), util.getPassword());
        util.tryClick(driver, By.xpath(buttonPath));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        /*WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
        w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"vue-root\"]/div[10]/div[6]/div/div/div[2]/div[2]/div/div[2]/div/div[2]/div[1]/form/div[2]/div/div/input")));
                                                     //*[@id="vue-root"]/div[10]/div[6]/div/div/div[2]/div[2]/div/div[2]/div/div[2]/div[1]/form/div[2]/div/div/input
        driver.findElement(By.xpath("//*[@id=\"vue-root\"]/div[10]/div[6]/div/div/div[2]/div[2]/div/div[2]/div/div[2]/div[1]/form/div[2]/div/div[1]/input")).sendKeys("KsuLabut@mail.ru");
        driver.findElement(By.xpath("//*[@id=\"vue-root\"]/div[10]/div[6]/div/div/div[2]/div[2]/div/div[2]/div/div[2]/div[1]/form/div[3]/div[1]/input")).sendKeys("1520abacaba");
        driver.findElement(By.xpath("//*[@id=\"vue-root\"]/div[10]/div[6]/div/div/div[2]/div[2]/div/div[2]/div/div[2]/div[1]/form/div[5]/button")).click();
        */
    }

    public void incorrectAuthWithDriver(WebDriver driver) {
        driver.get("https://www.lamoda.ru/");
        util.tryClick(driver, By.xpath(loginButtonPath));

        util.tryType(driver, By.xpath(loginPath), util.getLogin());
        util.tryType(driver, By.xpath(passwordPath), "123456");
        util.tryClick(driver, By.xpath(buttonPath));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
}
