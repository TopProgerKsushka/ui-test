import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderTest {
    Util util;
    String homeBasketPath = "//a[@href='/checkout/cart/'][contains(./span/text(), 'Корзина')]";
//    String firstChoicePath = "//*[@href=\"/p/rtlaax044601/beauty_accs-puma-maski-dlya-litsa-zaschitnye-sht/\"]";
    String firstChoicePath = "//div[contains(@class, 'grid__catalog')]/div//a";
    String toBasketPath = "//button[@aria-label=\"Добавить в корзину\"]";
    String confirmPath = "//a[@href='/checkout/cart/'][contains(./text(), 'Перейти в корзину')]";
    //String namePath = "//*[@class='input__input'][@name='Имя']";
    //String surnamePath = "//*[@class='input__input'][@name='Фамилия']";
    String phonePath = "//*[@class='input__input'][@name='Телефон']";
    //String email = "//*[@class='input__input'][@name='Электронная почта']";
    String townPath = "//*[@class='input__input'][@name='Населённый пункт']";
    String townSubtreePath = "//div[contains(./div/span/@class, \"notranslate\")][contains(./div/span/text(), \"Санкт-Петербург\")]";
    String deliveryPath = "//label[contains(@class, 'x-radio-group__option')][contains(./b/text(), \"курьерская доставка\")]";
    String streetPath = "//*[@class='input__input'][@name='street_suggest']";
    String housePath = "//*[@class='input__input'][@name='house_suggest']";
    String flatPath = "//*[@class='input__input'][@name='Квартира']";
    String noGoodPath = "//*[@class=\"_2JSp8mN_DdUcjyfOLCxGOj\"]";

    //static String searchButtonPath = "//*/button[contains(@class, 'x-button_32')]";
    static String searchButtonPath = "//*[@type=\"button\"][@role=\"button\"]";
    static String searchFieldPath = "//*[@type=\"text\"]";
    static String validateMessagePath = "//div[contains(@class, \"input__validation-message\")][contains(./b/text(), \"Номер\") | contains(./b/text(), \"пункт\") | contains(./b/text(), \"Имя\") | contains(./b/text(), \"Фамилия\")]";

    @Before
    public void setUp() {
        util = new Util();
    }

    public void doOrder(WebDriver driver) throws InterruptedException {
        driver.get("https://www.lamoda.ru/");

        util.tryClick(driver, By.xpath(AuthTest.getLoginButtonPath()));

        util.tryType(driver, By.xpath(AuthTest.getLoginPath()), util.getLogin());
        util.tryType(driver, By.xpath(AuthTest.getPasswordPath()), util.getPassword());
        util.tryClick(driver, By.xpath(AuthTest.getButtonPath()));
        //Авторизация готова

        Thread.sleep(5000);

        util.tryClick(driver, By.xpath(searchButtonPath));
        util.tryType(driver, By.xpath(searchFieldPath), "маска");
        util.tryClick(driver, By.xpath(searchButtonPath));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //Вбили товар в поиск

        util.tryClick(driver, By.xpath(firstChoicePath));
        util.tryClick(driver, By.xpath(toBasketPath));
        util.tryClick(driver, By.xpath(confirmPath));

        util.tryType(driver, By.xpath(phonePath), "+79117540690");
        util.tryType(driver, By.xpath(townPath), "г. Санкт-Петербург");
        util.tryClick(driver, By.xpath(townSubtreePath));
        Thread.sleep(5000);
        util.tryClick(driver, By.xpath(deliveryPath));
        util.tryType(driver, By.xpath(streetPath), "ул. Гжатская");
        util.tryType(driver, By.xpath(housePath), "22");
        util.tryType(driver, By.xpath(flatPath), "567");
    }

    public void doEmptyOrder(WebDriver driver) throws InterruptedException {
        //AuthTest authTest = new AuthTest();
        //Util util = new Util;
        //authTest.authWithDriver(driver, util);
        driver.get("https://www.lamoda.ru/");

        util.tryClick(driver, By.xpath(AuthTest.getLoginButtonPath()));

        util.tryType(driver, By.xpath(AuthTest.getLoginPath()), util.getLogin());
        util.tryType(driver, By.xpath(AuthTest.getPasswordPath()), util.getPassword());

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        util.tryClick(driver, By.xpath(AuthTest.getButtonPath()));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        Thread.sleep(5000);

        util.tryClick(driver, By.xpath(homeBasketPath));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void correctOrder() throws MalformedURLException, InterruptedException {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver driver1 = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
        doOrder(driver1);
        assertFalse(util.isElementPresent(driver1, By.xpath(validateMessagePath)));
        driver1.quit();

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), firefoxOptions);
        doOrder(driver);
        assertFalse(util.isElementPresent(driver, By.xpath(validateMessagePath)));
        driver.quit();
    }

    @Test
    public void emptyOrder() throws MalformedURLException, InterruptedException {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver driver1 = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
        doEmptyOrder(driver1);
        assertTrue(util.isElementPresent(driver1, By.xpath(noGoodPath)));
        driver1.quit();

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"), firefoxOptions);
        doEmptyOrder(driver);
        assertTrue(util.isElementPresent(driver, By.xpath(noGoodPath)));
        driver.quit();
    }
}
