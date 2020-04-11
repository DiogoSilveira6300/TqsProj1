package ua.tqs.AirQuality;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCity {

    City city;

    @BeforeEach
    public void setUp(){
        city = new City("Lisbon", "Lisbon", "Portugal", new Coords(40, -9), 1,
                "p2", "01-01-2020T00:00:00");
    }

    @AfterEach
    public void tearDown(){

    }

    @Test
    public void testCopy(){
        City newCity = city.copy();
        assertEquals(newCity, city);
    }
}
