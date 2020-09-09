package pages.fragments;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.core.BaseFunc;
import pages.model.Product;

import java.util.List;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.By.*;

public class Header {

    private final By HEADER_ICONS = xpath("//nav[@class='group']/child::a");
    private final By ROW = xpath("//ul[contains(@class, ' product-list product-list')]");
    private final By CART_PRODUCT_NUMBER = cssSelector("[id='top_cart_counter']");

    private final BaseFunc baseFunc;
    private final ProductElement productElement;

    public Header(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
        productElement = new ProductElement(baseFunc);
        baseFunc.waitForPresenceOfElement(HEADER_ICONS);
        assertTrue(baseFunc.findElement(tagName("header")).isDisplayed(), "Header not displayed!");
    }

    @Step("Hover on '{iconName}'")
    public void hoverOnHeaderIcon(String iconName) {
        List<WebElement> headerOptions = baseFunc.findElements(HEADER_ICONS);
        String name = null;
        for (WebElement popover : headerOptions) {
            if (popover.getAttribute("href").contains("recent_history")) {
                name = "Apskatitie";
            } else if (popover.getAttribute("href").contains("compare/")) {
                name = "Salidzinajums";
            }
            if (name.equals(iconName)) {
                baseFunc.moveMouseToElement(popover);
                baseFunc.waitForAttributeToBe(popover, "class", "btn btn--smaller btn--link popover-trigger is-active");
                break;
            }
        }
        baseFunc.saveScreenshotPNG();
    }

    @Step("Get info from hover-on popover Cart")
    public Product getProductInfoHeader() {
        Product headerProducts = new Product();
        List<WebElement> products = productElement.getProductsList(ROW);
        for (WebElement product : products) {
            if (product.getAttribute("class").endsWith("-list__right")) {
                products.remove(product);
                break;
            }
        }
        headerProducts.setProductNames(products, productElement.PRODUCT_TITLE);
        return headerProducts;
    }

    @Step("In yellow square getting number of added products")
    public Integer getNumberOfProductsInCart() {
        return parseInt(baseFunc.findElement(CART_PRODUCT_NUMBER).getText());
    }
}