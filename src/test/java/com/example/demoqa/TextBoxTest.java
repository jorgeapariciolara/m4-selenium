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

/**
 * https://demoqa.com/text-box
 * Posibles test:   submitEmptyFormTest()      submitOKTest()      submitOKTest()
 *
 */

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextBoxTest {

    private static final String URL = "https://demoqa.com/text-box" ;
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
    }

    @AfterEach
    void tearDown () {
        driver.quit();
    }

    @Test
    void submitEmptyFormTest(){
        driver.get(URL);
        // Comprobamos que existe el elemento undefined
        assertEquals(1,driver.findElements(By.className("undefined")).size());

        WebElement button = driver.findElement(By.id("submit"));
        button.click();

        assertEquals(1,driver.findElements(By.id("submit")).size());

    }

    @Test
    void submitIncorrectEmailTest(){
        driver.get(URL);
        // Comprobamos que existe el elemento undefined
        assertEquals(1,driver.findElements(By.className("undefined")).size());

        WebElement inputEmail = driver.findElement(By.id("userEmail"));
        inputEmail.sendKeys("prueba.com");

        WebElement button = driver.findElement(By.id("submit"));
        button.click();

        assertEquals(1,driver.findElements(By.id("submit")).size());

    }

    @Test
    void submitOKTest(){
        driver.get(URL);
        // Comprobamos que existe el elemento undefined
        assertEquals(1,driver.findElements(By.className("undefined")).size());

        // Rellenamos correctamente el formulario
        WebElement inputName = driver.findElement(By.id("userName"));
        inputName.sendKeys("Prueba Selenium");

        WebElement inputEmail = driver.findElement(By.id("userEmail"));
        inputEmail.sendKeys("prueba@prueba.com");

        WebElement inputAddress = driver.findElement(By.id("currentAddress"));
        inputAddress.sendKeys("Lorem ipsum current address");

        WebElement inputPermanent = driver.findElement(By.id("permanentAddress"));
        inputPermanent.sendKeys("Lorem ipsum permanent address");

        // Enviar formulario
        WebElement button = driver.findElement(By.id("submit"));
        js.executeScript("arguments[0].scrollIntoView();",button);
        button.click();

        assertEquals(0,driver.findElements(By.className("undefined")).size());

        WebElement name = driver.findElement(By.id("name"));
        assertTrue(name.getText().contains("Prueba Selenium"));

        WebElement email = driver.findElement(By.id("email"));
        assertTrue(email.getText().contains("prueba@prueba.com"));

        List<WebElement> currents = driver.findElements(By.id("currentAddress"));
        WebElement lastCurrent = currents.get(currents.size()-1);
        assertTrue(lastCurrent.getText().contains("Lorem ipsum current address"));

        WebElement lastPermanent = driver.findElement(By.cssSelector("#output #permanentAddress"));
        assertTrue(lastCurrent.getText().contains("Lorem ipsum permanent address"));
    }

    /*
    // Al ver que no funcionan los test, descubrimos que hay un anuncio tapando el botón y tenemos que
    // evitarlo, para lo que probamos varios test. Al final crea un JavaScript
    @Test
    void testScroll1(){
        driver.get(URL);
        WebElement element = driver.findElement(By.id("submit"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        element.click();
    }

    @Test
    void testScroll2(){
        driver.get(URL);
        WebElement gmailLink = driver.findElement(By.id("submit"));
        int xOffset = gmailLink.getRect().getX();
        int yOffset = gmailLink.getRect().getY();
        Actions actionProvider = new Actions(driver);
        actionProvider.moveByOffset(xOffset,yOffset).build().perform();
        gmailLink.click();
    }

    @Test
    void testScroll() throws InterruptedException {
        driver.get(URL);
            driver.manage().window().maximize();
        Thread.sleep(2000);
        WebElement gmailLink = driver.findElement(By.id("submit"));
        gmailLink.click();
    }
    */
}
