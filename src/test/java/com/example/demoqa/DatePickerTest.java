package com.example.demoqa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DatePickerTest {

    private static final String URL = "https://demoqa.com/date-picker";
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
        driver.get(URL);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }


    @Test
    void todayDateTest() {
        WebElement input = driver.findElement(By.id("datePickerMonthYearInput"));
        String dateString = input.getAttribute("value");

        // opcion 1 usar un formateador de fechas

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        System.out.println(date.toString());
        System.out.println(LocalDate.now().toString());

        assertEquals(date.toString(),LocalDate.now().toString());

        // opcion 2 crear la fecha manualmente

        String[] dateParts = dateString.split("/");
        System.out.println(Arrays.toString(dateParts));

        LocalDate webDate = LocalDate.of(
                Integer.parseInt(dateParts[2]),
                Integer.parseInt(dateParts[0]),
                Integer.parseInt(dateParts[1])
        );

        assertEquals(webDate.toString(),LocalDate.now().toString());


    }


    @Test
    void selectDayTest() {
        WebElement input = driver.findElement(By.id("datePickerMonthYearInput"));
        input.click();

        // seleccionar 01-01-2021

        // month
        WebElement monthSelect = driver.findElement(By.className("react-datepicker__month-select"));
        monthSelect.click();
        WebElement monthOption = driver.findElement(By.xpath("//select//option[@value='0']"));
        monthOption.click();

        // year
        WebElement yearSelect = driver.findElement(By.className("react-datepicker__year-select"));
        yearSelect.click();
        WebElement yearOption = driver.findElement(By.xpath("//select//option[@value='2021']"));
        yearOption.click();

        // day
        List<WebElement> days = driver.findElements(By.className("react-datepicker__day--001"));
        assertTrue(days.size() >= 1);
        days.get(0).click(); // seleccionar el primer dia

        // extraer el texto del input y comprobarlo con assert
        input = driver.findElement(By.id("datePickerMonthYearInput"));
        String dateString = input.getAttribute("value");
        assertEquals("01/01/2021", dateString);
    }

    @Test
    void selectDateTimeTest() {
        WebElement input = driver.findElement(By.id("dateAndTimePickerInput"));
        input.click();

        WebElement time = driver.findElement(By.xpath("//li[text() = '09:00']"));
        time.click();

        // check text
        input = driver.findElement(By.id("dateAndTimePickerInput"));
        String dateString = input.getAttribute("value");
        // comprobar que dateString contiene 09:00 AM
        assertTrue(dateString.contains("9:00 AM"));

    }
}
