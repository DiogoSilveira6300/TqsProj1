package ua.tqs.AirQuality;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

public class WebPageTest {
  private WebDriver driver;

  @BeforeEach
  public void setUp() {
    driver = new FirefoxDriver();
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  @Disabled //comment after mvn verify and run the .jar file
  public void title(){
    Home home = new Home(driver);
    assertEquals("Air Quality", home.getTitle());
  }

  @Test
  @Disabled // comment after mvn verify and run the .jar file
  public void lisbonShows() {
    Home home = new Home(driver);

    home = home.search("Lisbon", "Lisbon", "Portugal");

    String expectedCity = "Lisbon";
    String expectedState = "Lisbon";
    String expectedCountry = "Portugal";

    {
      WebDriverWait wait = new WebDriverWait(driver, 10);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".row > #city")));
    }

    String actualCity = home.getCity();
    String actualState = home.getState();
    String actualCountry = home.getCountry();

    assertEquals(expectedCity, actualCity);
    assertEquals(expectedState, actualState);
    assertEquals(expectedCountry, actualCountry);
  }

  @Test
  @Disabled // comment after mvn verify and run the .jar file
  public void aveiroShows() {
    Home home = new Home(driver);

    home = home.quickAveiro();

    String expectedCity = "Aveiro";
    String expectedState = "Aveiro";
    String expectedCountry = "Portugal";

    {
      WebDriverWait wait = new WebDriverWait(driver, 10);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".row > #city")));
    }

    String actualCity = home.getCity();
    String actualState = home.getState();
    String actualCountry = home.getCountry();

    assertEquals(expectedCity, actualCity);
    assertEquals(expectedState, actualState);
    assertEquals(expectedCountry, actualCountry);
  }
}
