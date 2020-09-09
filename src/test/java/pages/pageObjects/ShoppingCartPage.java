package pages.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.core.BaseFunc;
import pages.fragments.ProductElement;
import pages.model.Product;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.xpath;

public class ShoppingCartPage {

    private final By PRODUCT_LIST = xpath("//ul[@data-plugin='productList']");
    private final By PRODUCT = xpath(".//li");
    private final By PRODUCT_IMAGE = xpath(".//img");
    private final By DELETE_BUTTON = xpath(".//*[@class = 'icon icon-remove']/..");
    private final By TOTAL_PRICE = cssSelector("[id='total_products_price_without_cupon']");

    private final BaseFunc baseFunc;
    private final ProductElement productElement;

    public ShoppingCartPage(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
        productElement = new ProductElement(baseFunc);
        baseFunc.waitForPresenceOfElement(PRODUCT_LIST);
        assertEquals(baseFunc.findElement(By.tagName("h1")).getText(), "Grozs");
    }

    @Step("Getting products on Cart page and set to model")
    public Product getProductsInCartAndSet() {
        Product productCart = new Product();
        List<String> names = new ArrayList<>();
        List<WebElement> productsInCart = baseFunc.findElement(PRODUCT_LIST).findElements(PRODUCT);
        for (WebElement product : productsInCart) {
            names.add(product.findElement(PRODUCT_IMAGE).getAttribute("alt"));
        }
        productCart.setProductNames(names);
        productCart.setProductPrices(productsInCart, productElement.PRODUCT_PRICE);

        return productCart;
    }

    @Step("Get from Products price and sum it")
    public Double totalPriceInCart() {
        List<WebElement> productsInCart = baseFunc.findElement(PRODUCT_LIST).findElements(productElement.PRODUCT_PRICE);
        Double totalPrice = 0.00;
        for (WebElement price : productsInCart) {
            totalPrice = totalPrice + parseDouble(price.getText().substring(1,price.getText().length()-2));
        }
        return totalPrice;
    }

    @Step("Get Total Price in Shopping Cart")
    public Double totalProductPrice() {
        return parseDouble(baseFunc.findElement(TOTAL_PRICE).getText().substring(1,baseFunc.findElement(TOTAL_PRICE).getText().length()-2));
    }

    @Step("Deleting {amountOfProductsToDelete} Products from Shopping Cart page")
    public Product deleteRandomProduct(int amountOfProductsToDelete, List<String> totalProducts) {
        Product deletedProduct = new Product();
        List<String> deletedNames = new ArrayList<>();
        List<Double> deletedPrices = new ArrayList<>();
        if (amountOfProductsToDelete >= 1 && !(amountOfProductsToDelete > totalProducts.size())) {
            for (int i = 0; i < amountOfProductsToDelete; i++) {
                List<WebElement> productsOnPage = baseFunc.findElement(PRODUCT_LIST).findElements(PRODUCT);
                WebElement element = productsOnPage.get(baseFunc.randomNum(productsOnPage));
                deletedNames.add(element.findElement(PRODUCT_IMAGE).getAttribute("alt"));
                deletedPrices.add(productElement.getProductPrice(element));
                baseFunc.moveMouseToElement(element.findElement(DELETE_BUTTON));
                baseFunc.clickButtonJS(element.findElement(DELETE_BUTTON));
                baseFunc.waitForPresenceOfElement(PRODUCT_LIST);
                baseFunc.saveScreenshotPNG();
            }
            deletedProduct.setProductNames(deletedNames);
            deletedProduct.setProductPrices(deletedPrices);
            deletedProduct.setTotalPrice(deletedPrices);
        }
        if (amountOfProductsToDelete == 0 || amountOfProductsToDelete>totalProducts.size()) {
            deletedProduct = getProductsInCartAndSet();
            System.out.println("Products not deleted or too big number entered, nothing deleted!");
        }
        return deletedProduct;
    }
}