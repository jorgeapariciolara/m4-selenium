package com.example.demoqa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SelectableTest {

    private static final String URL = "https://demoqa.com/selectable" ;
    WebDriver driver;
    JavascriptExecutor js;

    @BeforeEach
    void setUp (){
        String dir = System.getProperty("user.dir"); // ruta del proyecto
        String driverUrl = "/drivers/chromedriver.exe";
        String url =  dir + driverUrl;
        System.setProperty("webdriver.chrome.driver",url);
        driver = new ChromeDriver(); // Google chrome
        js = (JavascriptExecutor) driver;
        driver.get(URL);
    }

    @AfterEach
    void tearDown () {
        driver.quit();
    }

    @Test
    void clickTest() {
        assertEquals(0,
                driver.findElements(By.cssSelector("#verticalListContainer .active")).size());

        List<WebElement> items = driver.findElements(By.cssSelector("#verticalListContainer li"));
        assertTrue(items.size() >= 1);
        items.get(0).click();

        assertEquals(1,
                driver.findElements(By.cssSelector("#verticalListContainer .active")).size());

        items = driver.findElements(By.cssSelector("#verticalListContainer li"));
        items.get(0).click();

        assertEquals(0,
                driver.findElements(By.cssSelector("#verticalListContainer .active")).size());
    }

    @Test
    void clickMultipleTest() {

        // seleccionar varios (2 - 3) y comprobar que se añade la clase active los seleccionados

    }
}
