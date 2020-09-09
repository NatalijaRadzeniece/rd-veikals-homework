package rdveikalsTests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.core.BaseFunc;
import pages.fragments.Header;
import pages.fragments.NavigationBar;
import pages.fragments.ProductElement;
import pages.fragments.SeenProductTab;
import pages.model.Product;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryTest {

    private final BaseFunc baseFunc = new BaseFunc();
    private final Logger LOGGER = LogManager.getLogger(this.getClass());
    private SeenProductTab seenTab;
    private Header header;
    private NavigationBar navigationBar;
    private ProductElement productElement;

    @BeforeEach
    void openSite() {
        baseFunc.openWebSite("www.rdveikals.lv/home/lv/");
        seenTab = new SeenProductTab(baseFunc);
        header = new Header(baseFunc);
        productElement = new ProductElement(baseFunc);
    }

    @Test
    void historyTest() {
        LOGGER.info("Verifying 'Product Seen' tab not visible");
        assertFalse(baseFunc.checkVisibilityOfElement(seenTab.SEEN_TAB), "'Seen Product' tab on page! clean cache!");

        LOGGER.info("Navigating to TV category");
        navigationBar = new NavigationBar(baseFunc);
        navigationBar.hoverMouseOnCategory("TV un elektronika");

        LOGGER.info("Navigating to 'Smart Tv consoles' subcategory");
        navigationBar.selectSubcategoryByName("Smart TV konsoles ");

        LOGGER.info("Finding random TV product and clicking on it");
        Product tvProduct = productElement.getRandomProductAndClickOnIt();

        LOGGER.info("Checking 'Product Seen' tab appeared");
        assertTrue(baseFunc.checkVisibilityOfElement(seenTab.SEEN_TAB), "'Seen Product' tab not on page! something wrong!");

        LOGGER.info("Get info from 'Product seen' tab and verifying that products match");
        List<String> productPageNames = seenTab.getProductsInfoFooter().getProductNames();
        assertAll("checking 'Product Seen' tab product the same from what we selected",
                () -> assertEquals(1, productPageNames.size(), "In 'Product Seen' tab more than 1 product!"),
                () -> assertEquals(tvProduct.getName(), productPageNames.get(0), "Name of product in history different")
        );

        LOGGER.info("Hovering on 'Gaming Zona'");
        navigationBar.hoverMouseOnCategory("Gaming zona");

        LOGGER.info("Navigating on 'NINTENDO SWITCH' subcategory");
        navigationBar.selectSubcategoryByName("NINTENDO SWITCH ");

        LOGGER.info("Checking 'Product Seen' tab still showed after we switched pages");
        assertTrue(baseFunc.checkVisibilityOfElement(seenTab.SEEN_TAB), "'Seen Product' tab not on page! something wrong!");

        LOGGER.info("Get info from 'Product seen' tab and verifying that nothing changed");
        List<String> productNamesSubcategoryPage = seenTab.getProductsInfoFooter().getProductNames();
        assertAll("checking that after we navigated on next page - nothing changed",
                () -> assertEquals(1, productNamesSubcategoryPage.size(), "In 'Product Seen' tab more than 1 product!"),
                () -> assertEquals(tvProduct.getName(), productNamesSubcategoryPage.get(0), "Name of product in history different")
        );

        LOGGER.info("Finding Switch random product and clicking on it");
        Product nintendoProduct = productElement.getRandomProductAndClickOnIt();

        LOGGER.info("Checking 'Product Seen' tab still there");
        assertTrue(baseFunc.checkVisibilityOfElement(seenTab.SEEN_TAB), "'Seen Product' tab not on page! something wrong!");

        LOGGER.info("Get info from 'Product seen' tab and check 2 products there");
        List<String> productNames = seenTab.getProductsInfoFooter().getProductNames();
        assertAll("verifying that in 'Product Tab', History in header and added before products match",
                () -> assertEquals(2, productNames.size(), "In 'Product Seen' tab not enough seen products!"),
                () -> assertEquals(nintendoProduct.getName(), productNames.get(0), "Name of product in history different"),
                () -> assertEquals(tvProduct.getName(), productNames.get(1), "Name of product in history different")
        );

        LOGGER.info("Hovering on 'History' icon in Header");
        header.hoverOnHeaderIcon("Apskatitie");

        LOGGER.info("Getting info from footer and header");
        List<String> productFooter = seenTab.getProductsInfoFooter().getProductNames();
        List<String> productHeader = header.getProductInfoHeader().getProductNames();

        LOGGER.info("Verifying product in header and footer match");
        assertEquals(productFooter, productHeader, "Products not matching");
    }

    @AfterEach
    void closeBrowser() {
        LOGGER.info("Test finished - closing browser");
        baseFunc.closeBrowser();
    }
}