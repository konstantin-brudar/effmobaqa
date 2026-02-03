package ru.effmob.aqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        WebElement field = driver.findElement(By.id("user-name"));
        field.clear();
        field.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement field = driver.findElement(By.id("password"));
        field.clear();
        field.sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(By.id("login-button")).click();
    }

    public String getErrorMessage() {
        return driver.findElement(By.xpath("//h3[@data-test='error']")).getText();
    }

    public boolean isProductsPageDisplayed() {
        return driver.getCurrentUrl().contains("/inventory.html");
    }
}
