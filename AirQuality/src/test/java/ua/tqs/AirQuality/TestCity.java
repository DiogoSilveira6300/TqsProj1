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
        assertEquals(city, newCity);
    }

    @Test
    public void equalsNull(){
        assertNotEquals(city, null);
    }

    @Test
    public void equalsNotCity(){
        Object o = new Object();
        assertNotEquals(city, o);
    }

    @Test
    public void testHashCode(){
        City newCity = city.copy();
        assertEquals(city.hashCode(), newCity.hashCode());
    }

    @Test
    public void testHashCodeDiff(){
        City newCity = city.copy();
        city.setName("Porto");
        city.setState("Porto");
        city.setCountry("Portugal");
        assertNotEquals(city.hashCode(), newCity.hashCode());
    }
}
