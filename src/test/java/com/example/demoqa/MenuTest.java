package com.example.demoqa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {

    private static final String URL = "https://demoqa.com/menu";
    WebDriver driver;
    JavascriptExecutor js;


    @BeforeEach
    void setUp() {
        String dir = System.getProperty("user.dir"); // ruta del proyecto
        String driverUrl = "/drivers/chromedriver.exe";
        String url = dir + driverUrl;
        System.setProperty("webdriver.chrome.driver", url);
        driver = new ChromeDriver(); // Google Chrome
        js = (JavascriptExecutor) driver;
        driver.get(URL);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }


    @Test
    void menuTest() {

        // NIVEL 1
        assertFalse(driver.findElement(By.cssSelector(".nav-menu-container #nav > li:nth-child(2) ul")).isDisplayed());

        // hover
        WebElement item2 = driver.findElement(By.cssSelector(".nav-menu-container #nav > li:nth-child(2)"));
        Actions action = new Actions(driver);
        action.moveToElement(item2).perform();

        assertTrue(driver.findElement(By.cssSelector(".nav-menu-container #nav > li:nth-child(2) ul")).isDisplayed());

        // NIVEL 2
        assertFalse(driver.findElement(By.cssSelector(".nav-menu-container #nav > li:nth-child(2) ul li:nth-child(3) ul")).isDisplayed());

        WebElement item3 = driver.findElement(By.cssSelector(".nav-menu-container #nav > li:nth-child(2) ul li:nth-child(3)"));
        action.moveToElement(item3).perform();

        assertTrue(driver.findElement(By.cssSelector(".nav-menu-container #nav > li:nth-child(2) ul li:nth-child(3) ul")).isDisplayed());

        // comprobar numero list items

        assertEquals(2, driver.findElements(
                By.cssSelector(".nav-menu-container #nav > li:nth-child(2) ul li:nth-child(3) ul li")).size());
    }


}
