package com.example.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelectorsTest {

    WebDriver driver;

    @BeforeEach
    void setUp (){
        String dir = System.getProperty("user.dir"); // ruta del proyecto
        String driverUrl = "/drivers/chromedriver.exe";
        String url =  dir + driverUrl;
        System.setProperty("webdriver.chrome.driver",url);
        driver = new ChromeDriver(); // Google chrome
    }

    @AfterEach
    void tearDown () {
        driver.quit();
    }

    @Test
    void tagNameSelectorTest () {
        driver.get("https://github.com/mozilla");
        WebElement h1 = driver.findElement(By.tagName("h1"));
        String h1Text = h1.getText();
        assertEquals("Mozilla",h1Text);
    }

    @Test
    void idSelectorTest () {
        driver.get("https://github.com/mozilla");
        WebElement repositories = driver.findElement(By.id("org-profile-repositories"));
        assertTrue(repositories.isDisplayed());
        String css = repositories.getAttribute("class");
        assertEquals("my-3",css);
    }

    @Test
    void classSelectorTest () {
        driver.get("https://github.com/mozilla");
        List<WebElement> repositories = driver.findElements(By.className("Box-row"));
        assertEquals(10,repositories.size());
    }

    @Test
    void cssSelectorTest () {
        driver.get("https://github.com/mozilla");
        List<WebElement> topics = driver.findElements(By.cssSelector(".topic-tag.topic-tag-link"));
        assertEquals(5,topics .size());
    }

}
