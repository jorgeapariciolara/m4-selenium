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

public class ToolTipsTest {

    private static final String URL = "https://demoqa.com/tool-tips";
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
    void tooltipTest() {

        WebElement button = driver.findElement(By.id("toolTipButton"));

        // hover
        Actions action = new Actions(driver);
        action.moveToElement(button).perform();
        // new Actions(driver).moveToElement(button).perform();

        new WebDriverWait(driver, Duration.ofSeconds(4))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonToolTip")));

        // System.out.println(driver.findElement(By.id("buttonToolTip")).getAttribute("innerHTML"));

        String tooltipText = driver.findElement(By.cssSelector("#buttonToolTip .tooltip-inner")).getText();
        assertEquals("You hovered over the Button", tooltipText);
    }


}
