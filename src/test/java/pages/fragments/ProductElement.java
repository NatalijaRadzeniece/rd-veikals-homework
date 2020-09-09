package pages.fragments;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.core.BaseFunc;
import pages.model.Product;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;
import static org.openqa.selenium.By.xpath;

public class ProductElement {

    private final By PRODUCT = xpath(".//li[contains (@class, ' product')]");
    public final By PRODUCT_TITLE = xpath(".//h3");
    public final By PRODUCT_PRICE = xpath(".//p");
    public final By PRODUCT_ADD_CART = xpath(".//a[@data-plugin='cartButton']");
    private final By PRODUCT_LIST = xpath("//ul[contains(@class, 'with-overlay row row--pad')]");

    public List<String> allNames = new ArrayList<>();
    public List<Double> allPrices = new ArrayList<>();

    private final BaseFunc baseFunc;

    public ProductElement(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
    }

    public List<WebElement> getProductsList(By containerLocator) {
        return baseFunc.findElement(containerLocator).findElements(PRODUCT);
    }

    public WebElement getProductPriceElement(WebElement product) {
        return product.findElement(PRODUCT_PRICE);
    }

    public WebElement getProductCartButtonElement(WebElement product) {
        return product.findElement(PRODUCT_ADD_CART);
    }

    public String getProductName(WebElement products) {
        String name = null;
        if (products.findElement(PRODUCT_TITLE).getText().contains("\n")) {
            String[] splitName = products.findElement(PRODUCT_TITLE).getText().split("\n");
            name = splitName[1] + " " + splitName[0];
        }
        return name;
    }

    public Double getProductPrice(WebElement specificElement) {
        String price = specificElement.findElement(PRODUCT_PRICE).getText();

        return parseDouble(price.substring(1, price.length() - 2));
    }

    private WebElement randomProduct(By whereProductsLocated) {
        baseFunc.waitForPresenceOfElement(whereProductsLocated);
        List<WebElement> products = getProductsList(whereProductsLocated);

        return products.get(baseFunc.randomNum(products));
    }

    @Step("Find random product on page and set it to model")
    public Product getRandomProductAndClickOnIt() {
        WebElement random = randomProduct(PRODUCT_LIST);
        Product randomProduct = new Product();
        randomProduct.setName(getProductName(random));
        randomProduct.setPrice(getProductPrice(random));
        baseFunc.saveScreenshotPNG();
        random.click();
        baseFunc.saveScreenshotPNG();

        return randomProduct;
    }

    @Step("Get Random product, set info to model and add to cart")
    public Product getRandomProductAndAddToCart() {
        WebElement random = randomProduct(PRODUCT_LIST);
        Product randomProduct = new Product();

        baseFunc.scrollInToViewJS(random.findElement(xpath("./child::a")));
        baseFunc.moveMouseToElement(getProductPriceElement(random));
        baseFunc.saveScreenshotPNG();
        baseFunc.moveMouseToElementAndClick(getProductCartButtonElement(random));
        baseFunc.saveScreenshotPNG();
        randomProduct.setName(getProductName(random));
        randomProduct.setPrice(getProductPrice(random));

        return randomProduct;
    }

    @Step("Add {name} to 'Names' list")
    @Description("Later we will check that products added before have the same names")
    public void setAllProductNames(String name) {
        allNames.add(name);
    }

    @Step("Add {price} to 'Price' list")
    @Description("Later we will check that products added before have the same price")
    public void setAllProductPrices(Double price) {
        allPrices.add(price);
    }
}