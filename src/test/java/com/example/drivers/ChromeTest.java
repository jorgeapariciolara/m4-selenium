package com.example.drivers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChromeTest {

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
    void test1 () {
        // Abrir página web
        driver.get("https://github.com/mozilla/geckodriver");
        // Obtener el texto de la etiqueta <title> de la página que hemos abierto
        String title = driver.getTitle ();
        // Verificar que el texto del título coincicde conlo que queremos
        assertEquals("GitHub - mozilla/geckodriver: WebDriver for Firefox", title);
    }

    @Test
    void test2 () {
        driver.get("https://github.com/mozilla");
        String title = driver.getTitle ();
        assertEquals("Mozilla · GitHub", title);
    }

}
