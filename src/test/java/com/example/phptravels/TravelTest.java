package com.example.phptravels;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TravelTest {

    private static final String URL = "https://www.phptravels.net/";
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
    void travelTest() throws InterruptedException {

        //driver.manage().window().maximize();

        driver.findElement(By.id("select2-hotels_city-container")).click();

        driver.findElement(By.className("select2-search__field")).sendKeys("Dubai");

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@id='select2-hotels_city-results']//li[text()='Dubai,United Arab Emirates']")));

        // Thread.sleep(5000);

        //System.out.println(driver.findElement(By.cssSelector("#select2-hotels_city-results li")).getAttribute("innerHTML"));
        driver.findElement(By.cssSelector("#select2-hotels_city-results li")).click();

        driver.findElement(By.id("submit")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("data")));

        // hotels_amenities_ Hay 6 hoteles
        // By.className("hotels_amenities_")
        // By.cssSelector(".hotels_amenities_")
        List<WebElement> hotels = driver.findElements(By.className("hotels_amenities_"));
        assertEquals(6, hotels.size());

        // testear filtro 5 estrellas
        /*
        KARIM
        List<WebElement> result = driver.findElements(By.cssSelector("markers section ul li"));
        assertTrue(result.size() >= 6);
        driver.findElement(By.xpath("//label[@for='stars_5']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.attributeContains
                        (driver.findElements(By.xpath("//div[@id='makers']/section/ul/li")).get(0)
                        ,"style","display: none;"));
        hotels = driver.findElements(By.xpath("//div[@id='makers']/section/ul/ li[@style='display: none']"));
        assertEquals(5, hotels.size());

        DAVID
        driver.manage().window().maximize();
        WebElement element = driver.findElement(By.xpath("//label[@for='stars_5']"));
        Actions action = new Actions(driver);
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='stars_5']")));
        action.moveToElement(element).click().perform();
        List<WebElement> hotelsAfter = driver.findElements(By.className("stars_5"));
        assertEquals(1,hotelsAfter.size());
         */

        WebElement stars5Filter = driver.findElement(By.xpath("//label[@for='stars_5']"));
        js.executeScript("arguments[0].scrollIntoView();",stars5Filter);
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(stars5Filter));
        stars5Filter.click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//li[contains(@class,'stars_1') or " +
                        "[contains(@class,'stars_2') or [contains(@class,'stars_3') or [contains(@class,'stars_4')]"
                )));
        hotels = driver.findElements(By.className("hotels_amenities_"));
        String hotelName = "";
        for(WebElement hotel : hotels) {
            System.out.println(hotel.getAttribute("class"));
            if(hotel.isDisplayed()){
                assertTrue(hotel.getAttribute("class").contains("stars_5"));
                hotelName = hotel.findElement(By.className("card-title")).getText();
            } else {
                assertFalse(hotel.getAttribute("class").contains("stars_5"));
            }
        }

        assertFalse(hotelName.isEmpty() || hotelName.isBlank());
        System.out.println(hotelName);

        // testear pantalla Details: pulsar en el primer hotel en el botón Details y comprobar la pantalla que aparece, que sea de Dubai

        List<WebElement> detailsButtons = driver.findElements(By.cssSelector(".stars_5 .more_details"));
        assertTrue(detailsButtons.size() >= 1);
        detailsButtons.get(0).click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("description")));
        assertEquals(hotelName, driver.findElement(By.cssSelector(".title.font-size-26")).getText());
        assertEquals(5, driver.findElements(By.cssSelector("h3 .stars")).size());

        //Para comprobar que aparece Dubai en la descripción del hotel, definimos un selector como hermano de
        String hotelDescription = driver.findElement(By.xpath("//h3[contains8@class,'title font-size-26')]/following-sibling::div//p")).getText();
        assertTrue(hotelDescription.contains("Dubai"));
    }


}
