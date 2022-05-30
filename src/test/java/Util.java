import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
public class Util {
    private final String login = "KsuLabut@mail.ru";
    private final String password = "1520abacaba";

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void tryClick(WebDriver driver, By selector) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(selector));
        driver.findElement(selector).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void tryType(WebDriver driver, By selector, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(selector));
        driver.findElement(selector).sendKeys(text);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

}