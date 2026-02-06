package ru.effmob.aqa.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private static final Logger logger = LogManager.getLogger(LoginPage.class);
    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        logger.info("LoginPage init");
        this.driver = driver;
    }

    public void enterUsername(String username) {
        logger.debug("Enter username: {}", username);
        WebElement field = driver.findElement(By.id("user-name"));
        field.clear();
        field.sendKeys(username);
    }

    public void enterPassword(String password) {
        logger.debug("Enter password: {}", password);
        WebElement field = driver.findElement(By.id("password"));
        field.clear();
        field.sendKeys(password);
    }

    public void clickLoginButton() {
        logger.info("Click login button");
        driver.findElement(By.id("login-button")).click();
    }

    public String getErrorMessage() {
        String errorMessage = driver.findElement(By.xpath("//h3[@data-test='error']")).getText();
        logger.debug("Get error message: {}", errorMessage);
        return errorMessage;
    }

    public boolean isProductsPageDisplayed() {
        boolean result = driver.getCurrentUrl().contains("/inventory.html");
        logger.info("Products page displayed: {}", result);
        return result;
    }
}
