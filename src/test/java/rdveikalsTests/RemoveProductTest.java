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

public class RemoveProductTest {
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
        header = new Header(baseFunc);
        productElement = new ProductElement(baseFunc);
        navigationBar = new NavigationBar(baseFunc);
    }

    @Test
    void removeProductsFromCartTest() {
        LOGGER.info("Adding random product to cart from 'Home Page'");
        Product homeProduct = productElement.getRandomProductAndAddToCart();

        LOGGER.info("Setting info about random product from home page");
        cartInfoPopover = new CartInfoPopover(baseFunc);
        productElement.setAllProductNames(homeProduct.getName());
        productElement.setAllProductPrices(homeProduct.getPrice());

        LOGGER.info("Clicking 'x' button in Cart window");
        cartInfoPopover.clickCloseButton();

        LOGGER.info("Verifying in cart 1 product");
        assertEquals(1, header.getNumberOfProductsInCart(), "In cart shows more than 1 prodcut");

        LOGGER.info("Navigating to 'Procesors' subcategory");
        navigationBar.hoverMouseOnCategory("Gaming zona");
        navigationBar.selectSubcategoryByName("Procesori ");

        LOGGER.info("Finding random Game product and adding to cart");
        Product gameProduct = productElement.getRandomProductAndAddToCart();

        LOGGER.info("Setting info about random Game product");
        productElement.setAllProductNames(gameProduct.getName());
        productElement.setAllProductPrices(gameProduct.getPrice());

        LOGGER.info("Clicking 'x' button in Cart window");
        cartInfoPopover.clickCloseButton();

        LOGGER.info("Verifying in cart 2 products");
        assertEquals(2, header.getNumberOfProductsInCart(), "In cart shows less/more than 2 products");

        LOGGER.info("Finding second random Game product and adding to cart");
        Product gameProductSecond = productElement.getRandomProductAndAddToCart();

        LOGGER.info("Setting info about second random Game product");
        productElement.setAllProductNames(gameProductSecond.getName());
        productElement.setAllProductPrices(gameProductSecond.getPrice());

        LOGGER.info("Clicking 'x' button in Cart window");
        cartInfoPopover.clickCloseButton();

        LOGGER.info("Verifying in cart 3 products");
        assertEquals(3, header.getNumberOfProductsInCart(), "In cart shows less/more than 3 products");

        LOGGER.info("Navigating to 'Skeneri' subcategory");
        navigationBar.hoverMouseOnCategory("Datortehnika");
        navigationBar.selectSubcategoryByName("Skeneri ");

        LOGGER.info("Finding random Tech product and adding to cart");
        Product techProduct = productElement.getRandomProductAndAddToCart();

        LOGGER.info("Setting info about random Tech product");
        productElement.setAllProductNames(techProduct.getName());
        productElement.setAllProductPrices(techProduct.getPrice());

        LOGGER.info("Clicking 'x' button in Cart window");
        cartInfoPopover.clickCloseButton();

        LOGGER.info("Verifying in cart 4 products");
        assertEquals(4, header.getNumberOfProductsInCart(), "In cart shows less/more than 4 products");

        LOGGER.info("Navigating to 'Videokameras' subcategory");
        navigationBar.hoverMouseOnCategory("TV un elektronika");
        navigationBar.selectSubcategoryByName("Videokameras ");

        LOGGER.info("Finding random Videocam product and adding to cart");
        Product videoProduct = productElement.getRandomProductAndAddToCart();

        LOGGER.info("Setting info about random Videocam product");
        productElement.setAllProductNames(videoProduct.getName());
        productElement.setAllProductPrices(videoProduct.getPrice());

        LOGGER.info("Verifying in cart 5 products");
        assertEquals(5, header.getNumberOfProductsInCart(), "In cart shows less/more than 5 products");

        LOGGER.info("Summing all 5 prices");
        Double totalPrice = homeProduct.getPrice() + gameProduct.getPrice() + gameProductSecond.getPrice()
                + techProduct.getPrice() + videoProduct.getPrice();

        LOGGER.info("Navigating to Shopping cart page");
        cartInfoPopover.clickCartButton();
        shoppingCartPage = new ShoppingCartPage(baseFunc);

        LOGGER.info("Getting full info from shopping cart about all products");
        Product allProductsCart = shoppingCartPage.getProductsInCartAndSet();

        LOGGER.info("Getting total price from shopping cart");
        Double totalProductPrice = shoppingCartPage.totalProductPrice();

        LOGGER.info("Verifying all 2 prices and names matching");
        assertAll("checking products match in shopping cart and checking total price",
                () -> assertEquals(totalPrice, shoppingCartPage.totalPriceInCart(), totalProductPrice, "Total Price not the same!"),
                () -> assertTrue(productElement.allPrices.containsAll(allProductsCart.getProductPrices()), "Products in shopping cart not the same")
        );

        LOGGER.info("Deleting some products from cart");
        Product deletedProducts = shoppingCartPage.deleteRandomProduct(2, allProductsCart.getProductNames());

        LOGGER.info("Setting info about products after deleting");
        Product productsAfterDelete = shoppingCartPage.getProductsInCartAndSet();

        LOGGER.info("Getting new total price");
        Double totalPriceAfterDelete = shoppingCartPage.totalProductPrice();

        LOGGER.info("Verifying after delete shopping cart changed");
        assertAll("checking products deleted",
                () -> assertFalse(productsAfterDelete.getProductNames().containsAll(deletedProducts.getProductNames()), "After delete something went wrong!"),
                () -> assertEquals(totalPrice - deletedProducts.getTotalPrice(), shoppingCartPage.totalPriceInCart(), totalPriceAfterDelete, "Prices after delete not the same")
        );
    }

    @AfterEach
    void closeBrowser() {
        baseFunc.closeBrowser();
    }
}