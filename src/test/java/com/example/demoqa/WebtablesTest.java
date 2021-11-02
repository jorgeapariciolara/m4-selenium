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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class WebtablesTest {

    private static final String URL = "https://demoqa.com/webtables" ;
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
    void deleteButtonTest(){

        assertEquals(3,driver.findElements(By.className("action-buttons")).size());

        List<WebElement> deleteButtons = driver.findElements(By.xpath("//span[contains(@id, 'delete-record-')]"));
        // List<WebElement> deleteButtons = driver.findElements(By.xpath("//span[contains(@title='Delete')]"));
        // List<WebElement> deleteButtons = driver.findElements(By.cssSelector("span[id*='delete-record-']"));

        deleteButtons.get(0).click();
        assertEquals(2,driver.findElements(By.className("action-buttons")).size());

        deleteButtons = driver.findElements(By.xpath("//span[contains(@id, 'delete-record-')]"));
        deleteButtons.get(0).click();
        assertEquals(1,driver.findElements(By.className("action-buttons")).size());

        deleteButtons = driver.findElements(By.xpath("//span[contains(@id, 'delete-record-')]"));
        deleteButtons.get(0).click();
        assertEquals(0,driver.findElements(By.className("action-buttons")).size());

        /*

        // Opción 2 --> Si tenemos muchos elementos, lo hacemos utilizando un bucle for:
        List<WebElement> deleteButtons = driver.findElements(By.xpath("//span[contains(@id, 'delete-record-')]"));
        for (int i = deleteButtons().size(); i > 0 ; i--) {
            deleteButtons = driver.findElements(By.xpath("//span[contains(@id, 'delete-record-')]"));
            deleteButtons.get(0).click();
            assertEquals(i - 1,driver.findElements(By.className("action-buttons")).size());
        }

         // Opción 3
        List<WebElement> deleteButtons = driver.findElements(By.xpath("//span[contains(@id, 'delete-record-')]"));
        int count = deleteButtons.size();
        do {
            deleteButtons.get(0).click();
            assertEquals(count - 1,driver.findElements(By.className("action-buttons")).size());
            deleteButtons = driver.findElements(By.xpath("//span[contains(@id, 'delete-record-')]"));
            count--;
        while (deleteButtons.size() != 0;

        */

    }

    @Test
    void searchOkTest(){
        assertEquals(3,driver.findElements(By.className("action-buttons")).size());

        WebElement search1 = driver.findElement(By.id("searchBox"));
        search1.sendKeys("Legal");

        assertEquals(1,driver.findElements(By.className("action-buttons")).size());
    }

    @Test
    void searchNotFoundTest() {
        assertEquals(3, driver.findElements(By.className("action-buttons")).size());

        WebElement search1 = driver.findElement(By.id("searchBox"));
        search1.sendKeys("Text");

        assertEquals(0, driver.findElements(By.className("action-buttons")).size());
    }

    @Test
    void addTest(){
        driver.findElement(By.id("addNewRecordButton")).click();

        assertTrue(driver.findElement(By.className("modal-content")).isDisplayed());

        WebElement button = driver.findElement(By.id("submit"));
        js.executeScript("arguments[0].scrollIntoView();",button);
        button.click();

        assertTrue(driver.findElement(By.className("modal-content")).isDisplayed());
    }

    @Test
    void nameLengthTest(){
        driver.findElement(By.id("addNewRecordButton")).click();

        // Probamos 30 letras
        driver.findElement(By.id("firstName")).sendKeys("ygtfrdeswaqzxcvbnmkijuygtrfdew");

        // Límite de 25 letras
        assertEquals(25,driver.findElement(By.id("firstName")).getAttribute("value"));
    }

    @Test
    void addOkTest(){
        assertEquals(3, driver.findElements(By.className("action-buttons")).size());

        driver.findElement(By.id("firstName")).sendKeys("FirstName");
        driver.findElement(By.id("lastName")).sendKeys("LastName");
        driver.findElement(By.id("userEmail")).sendKeys("valid@valid.com");
        driver.findElement(By.id("age")).sendKeys("44");
        driver.findElement(By.id("salary")).sendKeys("44000");
        driver.findElement(By.id("department")).sendKeys("Insurance");

        WebElement button = driver.findElement(By.id("submit"));
        js.executeScript("arguments[0].scrollIntoView();",button);
        button.click();

        assertEquals(4, driver.findElements(By.className("action-buttons")).size());
    }

    @Test
    void editRowTest(){

        List<WebElement> editButtons = driver.findElements(By.xpath("//span[contains(@id,'edit-record-')]"));
        editButtons.get(0).click();
        assertTrue(driver.findElement(By.className("modal-content")).isDisplayed());
        assertEquals("Cierra",driver.findElement(By.id("firstName")).getAttribute("value"));
        assertEquals("Vega",driver.findElement(By.id("lastName")).getAttribute("value"));
        assertEquals("cierra@example.com",driver.findElement(By.id("userEmail")).getAttribute("value"));
        assertEquals("39",driver.findElement(By.id("age")).getAttribute("value"));
        assertEquals("10000",driver.findElement(By.id("salary")).getAttribute("value"));
        assertEquals("Insurance",driver.findElement(By.id("department")).getAttribute("value"));
    }

    @Test
    void closeModalTest() throws InterruptedException {

        driver.findElement(By.id("addNewRecordButton")).click();

        assertTrue(driver.findElement(By.className("modal-content")).isDisplayed());

        driver.findElement(By.className("close")).click();

        Thread.sleep(5000L);

        assertEquals(0,driver.findElements(By.className("modal-content")).size());
    }

    @Test
    void paginationTest() {
        // comprobamos que exiten 10 páginas
        // .pagination-bottom select
        // div[@class='pagination-botom']//select
        assertEquals(10, driver.findElements(By.className("rt-ttr-group")).size());

        WebElement selector = driver.findElement(By.cssSelector(".pagination-bottom select"));
        js.executeScript("arguments[0].scrollIntoView();",selector);
        selector.click();
        // seleccionamos el option de 20 páginas
        // .pagination-bottom select option[value='20']
        // div[@class='pagination-botom']//select//option[@value='20']
        WebElement option20 = driver.findElement(By.cssSelector(".pagination-bottom select option[value='20']"));
        option20.click();
    }

}
