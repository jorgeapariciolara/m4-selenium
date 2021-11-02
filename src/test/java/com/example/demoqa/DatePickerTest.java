package com.example.demoqa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class DatePickerTest {

    private static final String URL = "https://demoqa.com/date-picker" ;
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
    void todayDateTest() {
        WebElement input = driver.findElement(By.id("datePickerMonthYearInput"));
        String dateString = input.getAttribute("value");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateString,formatter);
        System.out.println(date.toString());
        System.out.println(LocalDate.now().toString());

        assertEquals(date.toString(),LocalDate.now().toString());
    }
}
