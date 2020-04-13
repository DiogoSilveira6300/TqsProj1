package ua.tqs.AirQuality;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TestRestHandler {

    String response, error;
    City city;

    @Mock
    Cache cache;

    @Mock
    ExternalAPI externalAPI;

    @InjectMocks
    RestHandler restHandler;

    @BeforeEach
    public void setUp() {
        InputStream input = RestHandler.class.getResourceAsStream("/test/response.json");
        response = new BufferedReader(new InputStreamReader(input)).lines().collect(Collectors.joining("\n"));
        input = RestHandler.class.getResourceAsStream("/test/error.json");
        error = new BufferedReader(new InputStreamReader(input)).lines().collect(Collectors.joining("\n"));
        city = new City("Lisbon", "Lisbon", "Portugal", new Coords(-9.131168, 38.721676),
                4, "p2", "2020-04-11T23:00:00.000Z");
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetDataForCityBeforeCached()
            throws IOException {
        when(externalAPI.requestForCity(anyString(), anyString(), anyString())).thenReturn(response);
        assertEquals(restHandler.getDataForCity("Lisbon", "Lisbon", "Portugal"), city);
    }

    @Test
    public void testGetDataForCoordsBeforeCached()
            throws IOException {
        when(externalAPI.requestForCoords(anyString(), anyString())).thenReturn(response);
        assertEquals(restHandler.getDataForCoords("-9.13", "38.72"), city);
    }

    @Test
    public void testGetDataForCityAfterCached()
            throws IOException {
        city.setCached(true);
        when(cache.get(anyString())).thenReturn(city);
        assertEquals(restHandler.getDataForCity("Lisbon", "Lisbon", "Portugal"), city);
    }

    @Test
    public void testGetDataForCoordsAfterCached()
            throws IOException {
        city.setCached(true);
        when(cache.get(anyString())).thenReturn(city);
        assertEquals(restHandler.getDataForCoords( "-9.13", "38.72"), city);
    }

    @Test
    public void testGetDataForCityBadArgs()
            throws IOException {
        when(externalAPI.requestForCity(anyString(), anyString(), anyString())).thenReturn(error);
        assertThrows(IllegalArgumentException.class, () -> {
            restHandler.getDataForCity("Lisbon", "Lisbon", "USA");
        });
    }

    @Test
    public void testGetDataForCoordsBadArgs()
            throws IOException {
        when(externalAPI.requestForCoords(anyString(), anyString())).thenReturn(error);
        assertThrows(IllegalArgumentException.class, () -> {restHandler.getDataForCoords("35", "43");});
    }

    @Test
    public void testCacheStatsHits(){
        when(cache.getStats()).thenReturn(new Cache.CacheStats(2, 3));
        assertEquals(2, cache.getStats().getHits());
    }

    @Test
    public void testCacheStatsMisses(){
        when(cache.getStats()).thenReturn(new Cache.CacheStats(2, 3));
        assertEquals(3, cache.getStats().getMisses());
    }

    @Test
    public void testCacheStatsRequests(){
        when(cache.getStats()).thenReturn(new Cache.CacheStats(2, 3));
        assertEquals(5, cache.getStats().getRequests());
    }
}
