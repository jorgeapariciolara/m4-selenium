package com.example.openmrs;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class SelectableTest extends BaseTest {

    private static final String URL = "https://demo.openmrs.org/openmrs/login.htm";
    private static final String USERNAME = "Admin";
    private static final String PASSWORD = "Admin123";

    @Test
    void loginSucces() {
        login();
        // comprobar login success
        assertEquals("Logged in as Super User (admin) at Pharmacy.",
                driver.findElement(By.tagName("h4")).getText());
    }

    @Test
    void logout() {
        login();
        driver.findElement(By.cssSelector(".logout a")).click();
        assertEquals(URL,driver.getCurrentUrl());
        assertEquals(1,driver.findElements(By.id("login-form")).size());
    }

    @Test
    void loginFailureSucces() {
    }

    @Test
    void registerPatientTest() {
        login();
        driver.findElement(By.id("referenceapplication-registrationapp-registerPatient-homepageLink-" +
                "referenceapplication-registrationapp-register"));
        driver.findElement(By.name("givenName")).sendKeys("Usuario Selenium 1");
        driver.findElement(By.name("familyName")).sendKeys("Selenium");
        click("next-button");
        assertEquals(1,driver.findElements(By.cssSelector(".question-legend.focused")).size());
        // 2. Pantalla Gender
        driver.findElement(By.xpath("//select[@id='gender-field']//option[@value='F']")).click();
        click("next-button");
        assertEquals(2,driver.findElements(By.cssSelector(".question-legend.done")).size());
        // 3. Pantalla Birthdate
        driver.findElement(By.id("birthdateDay-field")).sendKeys("14");
        driver.findElement(By.id("birthdateYear-field")).sendKeys("1990");
        click("birthdateMonth-field");
        driver.findElement(By.xpath("//select[@id='birthdateMonth-field']//option[@value='1']")).click();
        assertEquals(3,driver.findElements(By.cssSelector(".question-legend.done")).size());
        // 4. Pantalla Address
        driver.findElement(By.id("address1")).sendKeys("Calle Primera, nº1, 3ºD");
        driver.findElement(By.id("address2")).sendKeys("Calle Segunda, nº3, 4ºC");
        driver.findElement(By.id("cityVillage")).sendKeys("Gotham");
        driver.findElement(By.id("country")).sendKeys("GominoLand");
        click("next-button");
        // 5. Pantalla Phone Number
        driver.findElement(By.name("phoneNumber")).sendKeys("654654654");
        click("next-button");
        click("next-button");
        click("submit");
        new WebDriverWait(driver, Duration.ofSeconds(4))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("PersonName-givenName")));
        assertEquals("Usuario Selenium 1",driver.findElement(By.className("PersonName-givenName")).getText());
    }

    private void birthDateScreen() {
        driver.findElement(By.id("birthdateDay-field")).sendKeys("14");
        driver.findElement(By.id("birthdateYear-field")).sendKeys("1990");
        driver.findElement(By.id("birthdateMonth-field")).click();
        driver.findElement(By.xpath("//select[@id='birthdateMonth-field']//option[@value='1']")).click();
    }

    private void login() {
        driver.get(URL);
        // rellenar username
        driver.findElement(By.id("username")).sendKeys(USERNAME);
        // rellenar password
        driver.findElement(By.id("username")).sendKeys(PASSWORD);
        // Pharmacy
        driver.findElement(By.id("Pharmacy")).click();
        // Pulsar Log In
        driver.findElement(By.id("loginButton")).click();
    }
}
