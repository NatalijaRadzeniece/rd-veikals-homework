package pages.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;

public class Product {
    private String name;
    private Double price;
    private List<String> productNames;
    private List<Double> productPrices;
    private Double totalPrice;

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(List<Double> prices) {
        Double totalPrice = 0.00;
        for (Double priceCart : prices) {
            totalPrice = totalPrice + priceCart;
        }
        this.totalPrice = totalPrice;
    }

    public List<Double> getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(List<WebElement> listOfElements, By locator) {
        List<Double> productPrices = new ArrayList<>();
        for (WebElement element : listOfElements) {
            productPrices.add(parseDouble(element.findElement(locator).getText().substring(1, element.findElement(locator).getText().length() - 2)));
        }
        this.productPrices = productPrices;
    }

    public void setProductPrices(List<Double> productPrices) {
        this.productPrices = productPrices;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<WebElement> listOfElements, By elementLocator) {
        List<String> productNames = new ArrayList<>();
        for (WebElement element : listOfElements) {
            if (element.findElement(elementLocator).getText().contains("\n")) {
                String[] splitName = element.findElement(elementLocator).getText().split("\n");
                productNames.add(splitName[1] + " " + splitName[0]);
            } else {
                productNames.add(element.findElement(elementLocator).getText());
            }
        }
        this.productNames = productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.contains("\n")) {
            this.name = name.replace("\n", " ");
        } else {
            this.name = name;
        }
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}