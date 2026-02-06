package ru.effmob.aqa.tests;

import io.qameta.allure.Description;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.effmob.aqa.pages.LoginPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("Тестирование авторизации")
public class LoginTest {
    private static final Logger logger = LogManager.getLogger(LoginTest.class);
    private WebDriver driver;
    private LoginPage loginPage;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        logger.info("Start test");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterEach
    void tearDown() {
        logger.info("Finish test");
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Успешный логин")
    @Description("Проверяет успешную авторизацию с корректными учетными данными")
    void successfulLogin() {
        logger.info("Test: successful login");
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        assertTrue(loginPage.isProductsPageDisplayed());
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    @Description("Тест сценария с неверным паролем. Ожидается сообщение об ошибке авторизации.")
    void loginWithWrongPassword() {
        logger.info("Test: login with wrong password");
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("wrong_password");
        loginPage.clickLoginButton();
        assertEquals("Epic sadface: Username and password do not match any user in this service", loginPage.getErrorMessage());
    }

    @Test
    @DisplayName("Логин заблокированного пользователя")
    @Description("Проверяет поведение для locked_out_user")
    void lockedOutUserLogin() {
        logger.info("Test: locked out user login");
        loginPage.enterUsername("locked_out_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        assertEquals("Epic sadface: Sorry, this user has been locked out.", loginPage.getErrorMessage());
    }

    @Test
    @DisplayName("Логин с пустыми полями")
    @Description("Валидация обязательных полей формы логина")
    void loginWithEmptyFields() {
        logger.info("Test: login with empty fields");
        loginPage.clickLoginButton();
        assertEquals("Epic sadface: Username is required", loginPage.getErrorMessage());
    }

    @Test
    @DisplayName("Логин пользователя performance_glitch_user")
    @Description("Проверяет авторизацию пользователя с медленной производительностью")
    void performanceGlitchUserLogin() {
        logger.info("Test: performance glitch user login");
        loginPage.enterUsername("performance_glitch_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        wait.until(ExpectedConditions.urlContains("/inventory.html"));
        assertTrue(loginPage.isProductsPageDisplayed());
    }
}
