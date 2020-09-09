package pages.core;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BaseFunc {

    private final WebDriver driver;
    private final Actions actions;
    private final WebDriverWait wait;

    public BaseFunc() {
        String userDir = System.getProperty("user.dir") + "\\lib\\";
        System.setProperty("webdriver.chrome.driver", userDir + "chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--window-size=1280,800");
        driver = new ChromeDriver(chromeOptions);

        actions = new Actions(driver);
        wait = new WebDriverWait(driver, 10);
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG () {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @Step("cheking URL and navigating to web site")
    public void openWebSite(String url) {
        if (!url.startsWith("htpp://") && !url.startsWith("htpps://")) {
            url = "https://" + url;
        }
        driver.get(url);
        saveScreenshotPNG();
    }

    @Step("Test Finished - closing browser")
    public void closeBrowser() {
        assertNotNull(driver, "Window already closed");
        driver.close();
    }

    @Step("Checking that element presented on page {locator}")
    public boolean checkVisibilityOfElement(By locator) {
        List<WebElement> products = findElements(locator);
        return products.size() != 0;
    }

    public List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    public void moveMouseToElement(WebElement element) {
        actions.moveToElement(element).perform();
    }

    public void moveMouseToElementAndClick(WebElement element) {
        actions.moveToElement(element).click().build().perform();
    }

    public void clickButtonJS(WebElement buttonToClickOn) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", buttonToClickOn);
    }

    public void waitForAttributeToBe(WebElement element, String attribute, String value) {
        wait.until(ExpectedConditions.attributeToBe(element, attribute, value));
    }

    public void waitForPresenceOfElement(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitForVisibilityOfElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForInvisibilityOfElement(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void scrollInToViewJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public Integer randomNum(List<WebElement> elements) {
        Random random = new Random();
        int randomNumber = random.nextInt(elements.size() - 1) + 1;
        return randomNumber;
    }
}