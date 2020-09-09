package pages.fragments;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.core.BaseFunc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.xpath;

public class NavigationBar {

    private final By CATEGORY = xpath("//a[contains(@class, 'btn btn--nav ')]");
    private final By OPENED_LIST = xpath("./parent::li");
    private final By COLLAPSIBLE_LIST = xpath("//div[contains(@class, 'container nav-')]");
    private final By SUBCATEGORY = xpath(".//small/parent::a");
    private final By PRODUCT_AMOUNT = xpath(".//small");

    private final BaseFunc baseFunc;

    public NavigationBar(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
        baseFunc.waitForPresenceOfElement(CATEGORY);
        assertTrue(baseFunc.findElement(cssSelector("[class=nav-primary]")).isDisplayed(), "Navigation bar not displayed!");
    }

    @Step("Finding '{categoryName}' and hovering on it")
    public void hoverMouseOnCategory(String categoryName) {
        if (baseFunc.checkVisibilityOfElement(CATEGORY)) {
            String name;
            List<WebElement> categories = baseFunc.findElements(CATEGORY);
            for (WebElement category : categories) {
                String href = category.getAttribute("href");
                if (href.contains("Telefoni-un-")) {
                    name = "Telefoni un Viedpulksteni";
                } else if (href.contains("ves-tehnika")) {
                    name = "Sadzives tehnika";
                } else if (href.contains("Skaistumam-un-")) {
                    name = "Skaistumam un veselibai";
                } else {
                    name = category.getText();
                }
                if (name.toUpperCase().equals(categoryName.toUpperCase())) {
                    baseFunc.moveMouseToElement(category);
                    baseFunc.waitForAttributeToBe(category.findElement(OPENED_LIST), "class", "is-open");
                    break;
                }
            }
            baseFunc.saveScreenshotPNG();
        }
    }

    private WebElement getActiveList() {
        WebElement list = null;
        List<WebElement> lists = baseFunc.findElements(COLLAPSIBLE_LIST);
        for (WebElement collapsibleList : lists) {
            if (collapsibleList.getCssValue("pointer-events").equals("all")) {
                list = collapsibleList;
            }
        }
        return list;
    }

    @Step("Finding '{subcategoryName}' and clicking it")
    public void selectSubcategoryByName(String subcategoryName) {
        List<WebElement> subcategories = getActiveList().findElements(SUBCATEGORY);
        for (WebElement subcategory : subcategories) {
            if (subcategory.getCssValue("text-transform").equals("uppercase")) {
                if (subcategory.getText().replace(subcategory.findElement(PRODUCT_AMOUNT).getText(), "").equals(subcategoryName.toUpperCase())) {
                    baseFunc.moveMouseToElementAndClick(subcategory);
                    break;
                }
            } else {
                if (subcategory.getText().replace(subcategory.findElement(PRODUCT_AMOUNT).getText(), "").equals(subcategoryName)) {
                    baseFunc.moveMouseToElementAndClick(subcategory);
                    break;
                }
            }
        }
        baseFunc.saveScreenshotPNG();
    }
}