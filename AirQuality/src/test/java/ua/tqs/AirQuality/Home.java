package ua.tqs.AirQuality;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

public class Home {

    private WebDriver driver;

    public Home(WebDriver driver){
        this.driver = driver;
        String baseUrl = "http://localhost:8080/home";
        driver.get(baseUrl + "/");
    }

    public Home search(String city, String state, String country){
        try {
            WebElement cityField = driver.findElement(By.id("city"));
            WebElement stateField = driver.findElement(By.id("state"));
            WebElement countryField = driver.findElement(By.id("country"));
            WebElement search = driver.findElement(By.id("search"));

            cityField.clear();
            stateField.clear();
            countryField.clear();
            cityField.sendKeys(city);
            stateField.sendKeys(state);
            countryField.sendKeys(country);

            search.click();
        } catch (RuntimeException e) {
            takeScreenShot(e, "searchError");
        }
        return this;
    }

    public Home quickAveiro(){
        try {
            WebElement cityField = driver.findElement(By.id("city"));
            WebElement stateField = driver.findElement(By.id("state"));
            WebElement countryField = driver.findElement(By.id("country"));

            cityField.clear();
            stateField.clear();
            countryField.clear();

            WebElement btAveiro = driver.findElement(By.id("aveiro"));
            btAveiro.click();
        } catch (RuntimeException e) {
            takeScreenShot(e, "searchError");
        }
        return this;
    }

    public String getTitle(){
        return driver.getTitle();
    }

    public String getCity() {
        WebElement result = driver.findElement(By.cssSelector(".row > #city"));
        return result.getText();
    }

    public String getState() {
        WebElement result = driver.findElement(By.cssSelector(".row > #state"));
        return result.getText();
    }

    public String getCountry() {
        WebElement result = driver.findElement(By.cssSelector(".row > #country"));
        return result.getText();
    }

    private void takeScreenShot(RuntimeException e, String fileName) {
        File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenShot, new File(fileName + ".png"));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe.getMessage(), ioe);
        }
        throw e;
    }
}
