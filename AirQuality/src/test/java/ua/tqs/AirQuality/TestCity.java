package ua.tqs.AirQuality;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCity {

    City city;

    @BeforeEach
    public void setUp(){
        city = new City("Lisbon", "Lisbon", "Portugal", new Coords(-9.131168, 38.721676),
                4, "p2", "2020-04-11T23:00:00.000Z");
    }

    @Test
    public void testCoordsEquals(){
        Coords coords = new Coords(-9.131168, 38.721676);
        assertEquals(coords, city.getCoords());
    }

    @Test
    public void testCoordsNotEquals(){
        Coords coords = new Coords(-10.0001, 35.999991);
        assertNotEquals(coords, city.getCoords());
    }

    @Test
    public void testEquals(){
        City newCity = new City("Lisbon", "Lisbon", "Portugal", new Coords(-9.131168, 38.721676),
                4, "p2", "2020-04-11T23:00:00.000Z");
        assertEquals(city, newCity);
    }

    @Test
    public void testNotEquals(){
        City newCity = new City("Porto", "Porto", "Portugal", new Coords(-9.131168, 38.721676),
                4, "p2", "2020-04-11T23:00:00.000Z");
        assertNotEquals(city, newCity);
    }

    @Test
    public void testCopy(){
        City newCity = city.copy();
        assertEquals(newCity, city);
    }
}
