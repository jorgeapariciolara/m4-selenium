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

public class FormTest {

    private static final String URL = "https://demoqa.com/text-box";
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
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }


    @Test
    void submitEmpyFormTest() {
        driver.get(URL);
        // comprobar que existe el elemento undefined
        assertEquals(1, driver.findElements(By.className("undefined")).size());

        WebElement button = driver.findElement(By.id("submit"));
        button.submit();

        assertEquals(1, driver.findElements(By.className("undefined")).size());

    }

    @Test
    void submitIncorrectEmailTest() {
        driver.get(URL);
        // comprobar que existe el elemento undefined
        assertEquals(1, driver.findElements(By.className("undefined")).size());

        WebElement inputEmail = driver.findElement(By.id("userEmail"));
        inputEmail.sendKeys("prueba.com");

        WebElement button = driver.findElement(By.id("submit"));
        button.submit();

        assertEquals(1, driver.findElements(By.className("undefined")).size());

    }


    @Test
    void submitOkTest() {
        driver.get(URL);

        // comprobar que existe el elemento undefined
        assertEquals(1, driver.findElements(By.className("undefined")).size());

        // rellenar correctamente el formulario
        WebElement inputName = driver.findElement(By.id("userName"));
        inputName.sendKeys("Prueba Selenium");

        WebElement inputEmail = driver.findElement(By.id("userEmail"));
        inputEmail.sendKeys("prueba@prueba.com");

        WebElement inputAddress = driver.findElement(By.id("currentAddress"));
        inputAddress.sendKeys("Lorem ipsum input current");

        WebElement inputPermanent = driver.findElement(By.id("permanentAddress"));
        inputPermanent.sendKeys("Lorem ipsum input permanent");


        // enviar formulario
        WebElement button = driver.findElement(By.id("submit"));
        // IMPORTANTE: hay anuncios de Google ads tapando el botón a pulsar, por tanto hay que desplazarse para que se muestre primero
        js.executeScript("arguments[0].scrollIntoView();", button);
        button.click();

        assertEquals(0, driver.findElements(By.className("undefined")).size());

        WebElement name = driver.findElement(By.id("name"));
        assertTrue(name.getText().contains("Prueba Selenium"));

        WebElement email = driver.findElement(By.id("email"));
        assertTrue(email.getText().contains("prueba@prueba.com"));

        List<WebElement> currents = driver.findElements(By.id("currentAddress"));
        WebElement lastCurrent = currents.get(currents.size() - 1);
        assertTrue(lastCurrent.getText().contains("Lorem ipsum input current"));

        WebElement lastPermanent = driver.findElement(By.cssSelector("#output #permanentAddress"));
        assertTrue(lastPermanent.getText().contains("Lorem ipsum input permanent"));

    }
}
