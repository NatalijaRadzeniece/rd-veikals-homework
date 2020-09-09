package pages.fragments;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.core.BaseFunc;
import pages.model.Product;

import static org.openqa.selenium.By.cssSelector;

public class SeenProductTab {

    public final By SEEN_TAB = cssSelector("[id=seen-product-panel]");

    private final BaseFunc baseFunc;
    private final ProductElement productElement;

    public SeenProductTab(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
        productElement = new ProductElement(baseFunc);
    }

    @Step("Get info from 'Product Seen' tab")
    public Product getProductsInfoFooter() {
        Product productFooter = new Product();
        productFooter.setProductNames(productElement.getProductsList(SEEN_TAB), productElement.PRODUCT_TITLE);

        return productFooter;
    }
}