package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SendKeysTest {

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
    void testSendKeys () {
        driver.get("https://seleniumbase.io/demo_page");

        WebElement input = driver.findElement(By.id("myTextInput"));
        // Enviamos información al campo del formulario
        input.sendKeys("Texto desde Selenium");
        // Le damos 10 segundos para que nos de tiempo a comprobar que está apareciendo todo
        sleep10();
        // Comprobar que el input tiene el texto introducido
        input = driver.findElement(By.id("myTextInput"));
        String inputValue = input.getAttribute("value");
        assertEquals("Texto desde Selenium",inputValue);
    }

    @Test
    void testSearchDuckDuckGo(){
        driver.get("https://www.duckduckgo.com");
        WebElement input = driver.findElement((By.name("q")));
        input.sendKeys("Selenium Java Examples" + Keys.ENTER);
        sleep10();
    }

    @Test
    void testSearchDuckDuckGo2(){
        driver.get("https://www.duckduckgo.com");
        WebElement input = driver.findElement((By.name("q")));
        input.sendKeys("Selenium Java Examples");
        input.submit();
        sleep5();
        input = driver.findElement((By.name("q")));
        input.clear();
        input.sendKeys("J-Unit5");
        sleep5();
        input.submit();
        sleep5();
    }

    @Test
    void testSearchGooglePopUp(){
        driver.get("https://www.google.es");
        // 1. Aceptar términos y condiciones
        sleep5();
        WebElement acceptButton = driver.findElement(By.xpath("//div[text()='Acepto']"));
        assertEquals("jyfHyd",acceptButton.getAttribute("class"));
        acceptButton.click();
        sleep5();
        // 2. Realizar búsqueda
        WebElement input = driver.findElement((By.name("q")));
        input.sendKeys("Selenium Java Examples");
        // Para pulsar ENTER de forma elegante y  no tener que concatenar  + Keys.ENTER utilizamos input.submit()
        input.submit();
        sleep5();
    }

    @Test
    void testDropDown(){
        driver.get("https://seleniumbase.io/demo_page");
        // Abrir el selector
        WebElement selector = driver.findElement(By.id("mySelect"));
        selector.click();
        // Seleccionar la opción deseada utilizando selector css
        List<WebElement> options = driver.findElements(By.cssSelector("#mySelect option"));
        assertEquals(4,options.size());
        options.get(3).click();
        // Seleccionar la opción deseada utilizando xpath
        WebElement option = driver.findElement(By.xpath("//select[@id='mySelect']/option[@value='100%']"));
        assertEquals(4,options.size());
        options.get(3).click();
    }

    // Creamos un método para darle 10 segundos y que nos de tiempo a comprobar que está apareciendo todo
    private void sleep10(){
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void sleep5(){
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
