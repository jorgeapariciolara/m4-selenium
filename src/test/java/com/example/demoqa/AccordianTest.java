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

import static org.junit.jupiter.api.Assertions.*;

/*
        SELECTORES  de CSS:

            Por id                      ===>    #idname
            Por clase                   ===>    .classname
            Si hay más de una clase     ===>    .classname1.classname2.classname3
            Si hay id más clases        ===>    #idname.classname1
                                                #idname.classname1.classname2.classsname3
            Por nombre de etiqueta html ===>    div.classname
                                                div#idname.classname
*/

public class AccordianTest {

    private static final String URL = "https://demoqa.com/accordian" ;
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
    void section1Test() {

        assertEquals(1,driver.findElements(By.cssSelector("#accordianContainer .collapse.show")).size());

        WebElement heading1 = driver.findElement(By.id("section1Heading"));
        js.executeScript("arguments[0].scrollIntoView();",heading1);
        heading1.click();

        assertEquals(0,driver.findElements(By.cssSelector("#accordianContainer .collapse.show")).size());
    }

    @Test
    void section2Test() {

        assertEquals(1,driver.findElements(By.id("section2Content")).size());

        WebElement section2 = driver.findElement(By.id("section2Content"));
        assertFalse(section2.isDisplayed());
        assertTrue(driver.findElement(By.id("section1Content")).isDisplayed());

        WebElement heading2 = driver.findElement(By.id("section2Heading"));
        js.executeScript("arguments[0].scrollIntoView();",heading2);
        heading2.click();

        section2 = driver.findElement(By.id("section2Content"));
        assertTrue(section2.isDisplayed());
        assertFalse(driver.findElement(By.id("section1Content")).isDisplayed());
    }



}
