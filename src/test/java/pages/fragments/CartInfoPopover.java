package pages.fragments;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.core.BaseFunc;
import pages.model.Product;

import static org.openqa.selenium.By.xpath;

public class CartInfoPopover {

    private final By POPOVER_INFO = xpath("//div[contains(@class, 'popover__info')]");
    private final By PRODUCT_CART = xpath(".//li");
    private final By CLOSE_CART_BUTTON = xpath(".//a[contains(@class, 'lightbox__close js-close')]");
    private final By BUTTON_TO_CART = xpath("//a[@class='btn btn--secondary']");

    private final BaseFunc baseFunc;
    private final ProductElement productElement;

    public CartInfoPopover(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
        productElement = new ProductElement(baseFunc);
    }

    @Step("Getting info from appeared cart window")
    public Product getProductInfoCartPopover() {
        baseFunc.waitForPresenceOfElement(POPOVER_INFO);
        baseFunc.waitForVisibilityOfElement(baseFunc.findElement(POPOVER_INFO).findElement(PRODUCT_CART).findElement(productElement.PRODUCT_TITLE));
        baseFunc.saveScreenshotPNG();
        Product cartProduct = new Product();
        cartProduct.setName(productElement.getProductName(baseFunc.findElement(POPOVER_INFO).findElement(PRODUCT_CART)));
        cartProduct.setPrice(productElement.getProductPrice(baseFunc.findElement(POPOVER_INFO).findElement(PRODUCT_CART)));

        return cartProduct;
    }

    @Step("In Cart popover click 'x' button")
    public void clickCloseButton() {
        baseFunc.waitForPresenceOfElement(POPOVER_INFO);
        baseFunc.waitForPresenceOfElement(CLOSE_CART_BUTTON);
        baseFunc.findElement(POPOVER_INFO).findElement(CLOSE_CART_BUTTON).click();
        baseFunc.waitForInvisibilityOfElement(baseFunc.findElement(POPOVER_INFO));
        baseFunc.saveScreenshotPNG();
    }

    @Step("Clicking 'Shopping Cart' button")
    public void clickCartButton() {
        baseFunc.findElement(BUTTON_TO_CART).click();
        baseFunc.saveScreenshotPNG();
    }
}