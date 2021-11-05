package com.example.openmrs;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.lang.model.element.Element;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ServiceTypeTest extends BaseTest{

    @Test
    void deleteRowTest() {

        // 1º. Navegamos hacia la pantalla deseada
        login();
        driver.findElement(By.id("appointmentschedulingui-homeAppLink-appointmentschedulingui-homeAppLink-extension")).click();
        driver.findElement(By.id("appointmentschedulingui-manageAppointmentTypes-app")).click();
        assertEquals(1,driver.findElements(By.id("appointmentTypesTable")).size());

        // 2º. Obtenemos la primera columna de cada fila
        List<WebElement> titleElements = driver.findElements(By.cssSelector("#appointmentTypesTable tbody tr:first-child"));
        assertEquals(1,titleElements.size());

        // 3º. Extraemos el título de la lista que queremos borrar
        String title = titleElements.get(1).getText();
        assertFalse(title.isEmpty());

        // 4º. Comprobar el número de elementos de la lista
        String entries = driver.findElement(By.id("appointmentTypesTable_info")).getText();
        String[] palabras = entries.split(" ");
        String penultima = palabras[palabras.length - 2];
        Integer totalRows = Integer.parseInt(penultima);

        // 4º. Borramos la fila en cuestión
        driver.findElement(By.id("appointmentschedulingui-delete-" + title)).click();
        driver.findElement(By.cssSelector("#delete-appointment-type-dialog:last-child .confirm")).click();
        new WebDriverWait(driver, Duration.ofSeconds(4))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//table[@id='appointmentTypeTable]//tbody//tr//td[text()='" + title + "']")));

        // 5º. Comprobamos que no aparece el título en las filas
        titleElements = driver.findElements(By.cssSelector("#appointmentTypesTable tbody tr td:first-child"));
        // 1. Crear un array de string con todos los títulos a partir del array WebElement
        List<String> titles = new ArrayList<>();
        for(WebElement titleElement : titleElements) {
            String titulo = titleElement.getText();
            titles.add(titulo);
        }
        // 2. Comprobar que el título no existe en la lista
        assertFalse(titles.contains(title));

        // 6º. Verificamos que ha disminuido el número de registros
        entries = driver.findElement(By.id("appointmentTypesTable_info")).getText();
        palabras = entries.split(" ");
        penultima = palabras[palabras.length - 2];
        Integer actualRows = Integer.parseInt(penultima);
        assertEquals(totalRows - 1,actualRows);
    }

    @Test
    void editRowTest() {

        // 1º. Navegamos hacia la pantalla deseada
        login();
        driver.findElement(By.id("appointmentschedulingui-homeAppLink-appointmentschedulingui-homeAppLink-extension")).click();
        driver.findElement(By.id("appointmentschedulingui-manageAppointmentTypes-app")).click();
        assertEquals(1, driver.findElements(By.id("appointmentTypesTable")).size());

        // 2º. Obtenemos la primera columna de cada fila
        List<WebElement> titleElements = driver.findElements(By.cssSelector("#appointmentTypesTable tbody tr:first-child"));
        assertEquals(1, titleElements.size());

        // 3º. Extraemos el título de la lista que queremos editar: segunda fila = String[1]
        String titleBeforeEdit = titleElements.get(1).getText();
        assertFalse(titleBeforeEdit.isEmpty());

        // 4º. Editamos la fila en cuestión
        driver.findElement(By.id("appointmentschedulingui-delete-" + titleBeforeEdit)).click();
        // 4.1. Editamos name
        WebElement inputName = driver.findElement(By.id("name-field"));
        String name = inputName.getAttribute("value");
        // Enviamos el texto que se quiere agregar
        // inputName.sendKeys( "EDITADO");
        // Borramos el texto y agregamos el nuevo
        inputName.clear();
        inputName.sendKeys(name + "EDITADO");
        // 4.2. Editamos duration
        WebElement inputDuration = driver.findElement(By.id("duration-field"));
        inputDuration.clear();
        inputDuration.sendKeys("99");
        // 4.3. Editamos description
        WebElement inputDescription = driver.findElement(By.id("description-field"));
        inputDescription.clear();
        inputDescription.sendKeys("Lorem ipsum ");

        // 5º. Guardamos cambios
        driver.findElement(By.id("save-button")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("appointmentTypesTable")));

        // Aserciones sobre columnas de la fila 2
        List<WebElement> columns = driver.findElements(By.cssSelector("#appointmentTypesTable tbody tr:nth-child(2) td"));
        // 1.Título (en la columna 1)
        String titleAfterEdit = columns.get(0).getText();
        assertFalse(titleAfterEdit.isEmpty());
        assertNotEquals(titleBeforeEdit,titleAfterEdit);
        // 2.Duration (en la columna 2)
        assertEquals("99", columns.get(1).getText());
        // 3.Description (en la columna 3)
        assertEquals("Lorem ipsum ", columns.get(2).getText());
    }

    @Test
    void addRowTest() {

        // 1º. Navegamos hacia la pantalla deseada
        login();
        driver.findElement(By.id("appointmentschedulingui-homeAppLink-appointmentschedulingui-homeAppLink-extension")).click();
        driver.findElement(By.id("appointmentschedulingui-manageAppointmentTypes-app")).click();
        assertEquals(1, driver.findElements(By.id("appointmentTypesTable")).size());

        // 2º. Comprobar el número de elementos de la lista
        String entries = driver.findElement(By.id("appointmentTypesTable_info")).getText();
        String[] palabras = entries.split(" ");
        String penultima = palabras[palabras.length - 2];
        Integer rowsBeforeAdd = Integer.parseInt(penultima);

        // 3º. Añadimos un nuevo elemento
        // 3.1. Buscamos el botón de añadir y hacemos click
        driver.findElement(By.cssSelector(".confirm.appointment-type-label")).click();

        // 4.1. Editamos name
        WebElement inputName = driver.findElement(By.id("name-field"));
        inputName.clear();
        inputName.sendKeys("Awesome service");
        // 4.2. Editamos duration
        WebElement inputDuration = driver.findElement(By.id("duration-field"));
        inputDuration.clear();
        inputDuration.sendKeys("109");
        // 4.3. Editamos description
        WebElement inputDescription = driver.findElement(By.id("description-field"));
        inputDescription.clear();
        inputDescription.sendKeys("Lorem ipsum dolor dorime ameno");

        // 5º. Guardamos cambios
        driver.findElement(By.id("save-button")).click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("appointmentTypesTable")));

        // Aserciones sobre columnas de la fila 2
        List<WebElement> columns = driver.findElements(By.cssSelector("#appointmentTypesTable tbody tr:nth-child(2) td"));
        // 1.Título (en la columna 1)
        assertEquals("Awesome service",columns.get(0).getText());
        // 2.Duration (en la columna 2)
        assertEquals("109", columns.get(1).getText());
        // 3.Description (en la columna 3)
        assertEquals("Lorem ipsum dolor dorime ameno", columns.get(2).getText());
        // 4.Número de registros
        entries = driver.findElement(By.id("appointmentTypesTable_info")).getText();
        palabras = entries.split(" ");
        penultima = palabras[palabras.length - 2];
        Integer actualRows = Integer.parseInt(penultima);
        assertEquals(rowsBeforeAdd + 1,actualRows);
    }


}
