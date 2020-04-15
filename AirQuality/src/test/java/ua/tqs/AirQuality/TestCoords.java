package ua.tqs.AirQuality;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestCoords {

    Coords coords = new Coords(-9.131168, 38.721676);

    @Test
    public void testCoordsEquals(){
        Coords newCoords = new Coords(-9.131168, 38.721676);
        assertEquals(coords, newCoords);
    }

    @Test
    public void testCoordsNotEquals(){
        Coords newCoords = new Coords(-10.0001, 35.999991);
        assertNotEquals(newCoords, coords);
    }

    @Test
    public void testCoordsNotEqualsNull(){
        assertNotEquals(coords, null);
    }

    @Test
    public void testCoordsNotEqualsDiffClass(){
        assertNotEquals(coords, new Object());
    }

    @Test
    public void testHashCode(){
        Coords newCoords = new Coords(-9.131168, 38.721676);
        assertEquals(newCoords.hashCode(), coords.hashCode());
    }

    @Test
    public void testHashCodeDiff(){
        Coords newCoords = new Coords(-10.0001, 35.999991);
        assertNotEquals(newCoords.hashCode(), coords.hashCode());
    }
}
