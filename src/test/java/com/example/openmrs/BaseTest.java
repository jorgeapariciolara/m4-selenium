package com.example.openmrs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {

    WebDriver driver;
    JavascriptExecutor js;

    private static final String URL = "https://demo.openmrs.org/openmrs/login.htm";
    private static final String USERNAME = "Admin";
    private static final String PASSWORD = "Admin123";

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

    void click(String id){
        driver.findElement(By.id(id)).click();
    }

    void login(){
        driver.get(URL);
        driver.findElement(By.id("username")).sendKeys(USERNAME);
        driver.findElement(By.id("password")).sendKeys(PASSWORD);
        driver.findElement(By.id("Pharmacy")).click();
        driver.findElement(By.id("loginButton")).click();
    }

}
