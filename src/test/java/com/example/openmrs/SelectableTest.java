package com.example.openmrs;

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelectableTest extends BaseTest{

    private static final String URL = "https://demo.openmrs.org/openmrs/login.htm";
    private static final String USERNAME = "Admin";
    private static final String PASSWORD = "Admin123";

    @Test
    void loginSuccess() {
        driver.get(URL);

        // rellenar username
        driver.findElement(By.id("username")).sendKeys(USERNAME);
        // rellenar password
        driver.findElement(By.id("password")).sendKeys(PASSWORD);
        // Pharmacy
        click("Pharmacy");
        // Pulsar Log In
        click("loginButton");
        // comprobar login success
        assertEquals("Logged in as Super User (admin) at Pharmacy.", driver.findElement(By.tagName("h4")).getText());
    }

    @Test
    void logout() {
        login();
        driver.findElement(By.cssSelector(".logout a")).click();
        assertEquals(URL, driver.getCurrentUrl());
        assertEquals(1, driver.findElements(By.id("login-form")).size());
    }

    @Test
    void loginFailure() {
    }

    @Test
    void registerPatient() {
        login();
        driver.findElement(By.id("referenceapplication-registrationapp-registerPatient-homepageLink-referenceapplication-registrationapp-registerPatient-homepageLink-extension")).click();
        // 1. Pantalla name
        driver.findElement(By.name("givenName")).sendKeys("Usuario Selenium 1");
        driver.findElement(By.name("familyName")).sendKeys("Selenium");
        click("next-button");
        assertEquals(1, driver.findElements(By.cssSelector(".question-legend.focused")).size());

        // 2. Pantalla Gender
        driver.findElement(By.xpath("//select[@id='gender-field']//option[@value='F']")).click();
        click("next-button");
        assertEquals(2, driver.findElements(By.cssSelector(".question-legend.done")).size());

        // 3. Pantalla birthday
        driver.findElement(By.id("birthdateDay-field")).sendKeys("14");
        driver.findElement(By.id("birthdateYear-field")).sendKeys("1990");
        click("birthdateMonth-field");
        driver.findElement(By.xpath("//select[@id='birthdateMonth-field']//option[@value='1']")).click();
        click("next-button");
        assertEquals(3, driver.findElements(By.cssSelector(".question-legend.done")).size());

        // 4. Address: 1. Address, Address 2, city, province, country, postal code
        driver.findElement(By.id("address1")).sendKeys("Calle Falsa");
        driver.findElement(By.id("address2")).sendKeys("Calle Verdadera");
        driver.findElement(By.id("cityVillage")).sendKeys("Gotham");
        driver.findElement(By.id("country")).sendKeys("Spain");
        click("next-button");
        // 5. Phone mumber
        driver.findElement(By.name("phoneNumber")).sendKeys("666444555");
        click("next-button");
        click("next-button");
        // 7. Confirm
        click("submit");
        // Esperar a que cargue la pantalla
        new WebDriverWait(driver, Duration.ofSeconds(4))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("PersonName-givenName")));

        assertEquals("Usuario Selenium 1", driver.findElement(By.className("PersonName-givenName")).getText());

        // 8. comprobar que aparece en el listado de pacientes

    }


    void login(){
        driver.get(URL);
        driver.findElement(By.id("username")).sendKeys(USERNAME);
        driver.findElement(By.id("password")).sendKeys(PASSWORD);
        driver.findElement(By.id("Pharmacy")).click();
        driver.findElement(By.id("loginButton")).click();
    }
}
