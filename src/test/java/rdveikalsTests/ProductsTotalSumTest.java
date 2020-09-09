package rdveikalsTests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.core.BaseFunc;
import pages.fragments.CartInfoPopover;
import pages.fragments.Header;
import pages.fragments.NavigationBar;
import pages.fragments.ProductElement;
import pages.model.Product;
import pages.pageObjects.ShoppingCartPage;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsTotalSumTest {

    private final BaseFunc baseFunc = new BaseFunc();
    private final Logger LOGGER = LogManager.getLogger(this.getClass());
    private Header header;
    private NavigationBar navigationBar;
    private ProductElement productElement;
    private CartInfoPopover cartInfoPopover;
    private ShoppingCartPage shoppingCartPage;

    @BeforeEach
    void openSite() {
        LOGGER.info("Navigating to Rd Veikals site");
        baseFunc.openWebSite("www.rdveikals.lv/home/lv/");
        productElement = new ProductElement(baseFunc);
        header = new Header(baseFunc);
    }

    @Test
    void checkTotalSumTest() {
        LOGGER.info("Adding random product to cart from 'Home Page'");
        Product homeProduct = productElement.getRandomProductAndAddToCart();

        LOGGER.info("Setting name and price to common list");
        productElement.setAllProductNames(homeProduct.getName());
        productElement.setAllProductPrices(homeProduct.getPrice());

        LOGGER.info("Getting info from appeared Cart window");
        cartInfoPopover = new CartInfoPopover(baseFunc);
        Product cartProductHome = cartInfoPopover.getProductInfoCartPopover();

        LOGGER.info("Verifying product from home page same as in cart window");
        assertAll("checking products",
                () -> assertEquals(homeProduct.getName(), cartProductHome.getName(), "Products name do not match!"),
                () -> assertEquals(homeProduct.getPrice(), cartProductHome.getPrice(), "Price not match")
        );

        LOGGER.info("Clicking 'x' button in Cart window");
        cartInfoPopover.clickCloseButton();

        LOGGER.info("Verifying in cart 1 product");
        assertEquals(1, header.getNumberOfProductsInCart(), "In cart shows more than 1 prodcut");

        LOGGER.info("Navigating to 'Datoru korpusi' subcategory");
        navigationBar = new NavigationBar(baseFunc);
        navigationBar.hoverMouseOnCategory("Datortehnika");
        navigationBar.selectSubcategoryByName("Datoru korpusi ");

        LOGGER.info("Finding random PC product and adding to cart");
        Product pcProduct = productElement.getRandomProductAndAddToCart();

        LOGGER.info("Setting info about PC product");
        productElement.setAllProductNames(pcProduct.getName());
        productElement.setAllProductPrices(pcProduct.getPrice());

        LOGGER.info("Getting info from appeared Cart window for PC product");
        Product pcProductCart = cartInfoPopover.getProductInfoCartPopover();

        LOGGER.info("Verifying PC product same as in cart window");
        assertAll("checking product from Fan page same as in cart popover",
                () -> assertEquals(pcProduct.getName(), pcProductCart.getName(), "Products name do not match!"),
                () -> assertEquals(pcProduct.getPrice(), pcProductCart.getPrice(), "Price not match")
        );
        LOGGER.info("Clicking 'x' button in Cart window");
        cartInfoPopover.clickCloseButton();

        LOGGER.info("Verifying in cart 2 products");
        assertEquals(2, header.getNumberOfProductsInCart(), "In cart shows less/more than 2 products");

        LOGGER.info("Finding second random PC product and adding to cart");
        Product pcProductSecond = productElement.getRandomProductAndAddToCart();

        LOGGER.info("Setting info about second PC product");
        productElement.setAllProductNames(pcProductSecond.getName());
        productElement.setAllProductPrices(pcProductSecond.getPrice());

        LOGGER.info("Getting info from appeared Cart window for second PC product");
        Product pcProductCartSecond = cartInfoPopover.getProductInfoCartPopover();

        LOGGER.info("Verifying second PC product same as in cart window");
        assertAll("checking product from Fan page same as in cart popover vol2",
                () -> assertEquals(pcProductSecond.getName(), pcProductCartSecond.getName(), "Products name do not match!"),
                () -> assertEquals(pcProductSecond.getPrice(), pcProductCartSecond.getPrice(), "Price not match")
        );

        LOGGER.info("Clicking 'x' button in Cart window");
        cartInfoPopover.clickCloseButton();

        LOGGER.info("Verifying in cart 3 products");
        assertEquals(3, header.getNumberOfProductsInCart(), "In cart shows less/more than 3 products");

        LOGGER.info("Navigating to 'Inhalatori' subcategory");
        navigationBar.hoverMouseOnCategory("Skaistumam un veselibai");
        navigationBar.selectSubcategoryByName("Inhalatori ");

        LOGGER.info("Finding random Inhalator product and adding to cart");
        Product beautyProduct = productElement.getRandomProductAndAddToCart();

        LOGGER.info("Setting info about second Beauty product");
        productElement.setAllProductNames(beautyProduct.getName());
        productElement.setAllProductPrices(beautyProduct.getPrice());

        LOGGER.info("Getting info from appeared Cart window for second Beauty product");
        Product beautyProductCart = cartInfoPopover.getProductInfoCartPopover();

        LOGGER.info("Verifying second Beauty product same as in cart window");
        assertAll("checking Beauty product same as in cart popover",
                () -> assertEquals(beautyProduct.getName(), beautyProductCart.getName(), "Products name do not match!"),
                () -> assertEquals(beautyProduct.getPrice(), beautyProductCart.getPrice(), "Price not match")
        );

        LOGGER.info("Clicking 'x' button in Cart window");
        cartInfoPopover.clickCloseButton();

        LOGGER.info("Verifying in cart 4 products");
        assertEquals(4, header.getNumberOfProductsInCart(), "In cart shows less/more than 4 products");

        LOGGER.info("Navigating to 'Gamepad' subcategory");
        navigationBar.hoverMouseOnCategory("TV un elektronika");
        navigationBar.selectSubcategoryByName("Gamepad ");

        LOGGER.info("Finding random Gamepad product and adding to cart");
        Product gameProduct = productElement.getRandomProductAndAddToCart();

        LOGGER.info("Setting info about second Gamepad product");
        productElement.setAllProductNames(gameProduct.getName());
        productElement.setAllProductPrices(gameProduct.getPrice());

        LOGGER.info("Getting info from appeared Cart window of Gamepad product");
        Product gameProductCart = cartInfoPopover.getProductInfoCartPopover();

        LOGGER.info("Verifying Gamepad product same as in cart window");
        assertAll("checking product from Lamp page same as in cart popover",
                () -> assertEquals(gameProduct.getName(), gameProductCart.getName(), "Products name do not match!"),
                () -> assertEquals(gameProduct.getPrice(), gameProductCart.getPrice(), "Price not match")
        );

        LOGGER.info("Verifying in cart 5 products");
        assertEquals(5, header.getNumberOfProductsInCart(), "In cart shows less/more than 5 products");

        LOGGER.info("Summing all 5 prices");
        Double totalPrice = homeProduct.getPrice() + pcProduct.getPrice() + pcProductSecond.getPrice()
                + beautyProduct.getPrice() + gameProduct.getPrice();

        LOGGER.info("Navigating to Shopping cart page");
        cartInfoPopover.clickCartButton();
        shoppingCartPage = new ShoppingCartPage(baseFunc);

        LOGGER.info("Getting full info from shopping cart about all products");
        Product shoppingCartProduct = shoppingCartPage.getProductsInCartAndSet();

        LOGGER.info("Getting total price from shopping cart");
        Double totalPriceInCart = shoppingCartPage.totalProductPrice();

        LOGGER.info("Verifying all 3 prices, and names matching");
        assertAll("checking prodcuts match in shopping cart and checking total price",
                () -> assertEquals(totalPrice, shoppingCartPage.totalPriceInCart(), totalPriceInCart, "Total Price not the same!"),
                () -> assertTrue(productElement.allNames.containsAll(shoppingCartProduct.getProductNames()), "Products in shopping cart not the same"),
                () -> assertTrue(productElement.allPrices.containsAll(shoppingCartProduct.getProductPrices()), "Products in shopping cart not the same")
        );
    }

    @AfterEach
    void closeBrowser() {
        LOGGER.info("Test finished - closing browser");
        baseFunc.closeBrowser();
    }
}