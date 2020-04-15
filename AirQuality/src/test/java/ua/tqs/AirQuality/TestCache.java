package ua.tqs.AirQuality;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

// Cache unit testing
public class TestCache {

    private Cache cache;

    private City lisbon, porto, nyc;

    private String key0, key1, key2;

    @BeforeEach
    public void setUp(){
        lisbon = new City("Lisbon", "Lisbon", "Portugal", new Coords(40, -9), 1,
                "p2", "01-01-2020T00:00:00");
        porto = new City("Porto", "Porto", "Portugal", new Coords(48, -9), 1,
                "p2", "01-01-2020T00:00:00");
        nyc = new City("New York", "New York", "USA", new Coords(48, -50), 3,
                "p2", "01-01-2020T00:00:00");
        cache = new Cache();
        key0 = "/city/Lisbon/state/Lisbon/country/Portugal";
        key1 = "/coords/48/-9";
        key2 = "/city/New York/state/New York/coutry/USA";
        cache.add(key0, lisbon);
        cache.add(key1, porto);
    }

    @AfterEach
    public void tearDown(){
        lisbon = null; porto = null; nyc = null;
        key0 = null; key1 = null; key2 = null;
        cache = new Cache();
    }

    // tests conditions after initializing
    @Test
    public void testInit(){
        assertEquals(0, cache.getMisses());
        assertEquals(0, cache.getHits());
    }

    // test add
    @Test
    public void testAdd(){
        cache.add(key2, nyc);
        City city = cache.get(key2);
        assertEquals(city, nyc);
    }

    // tests for existing cached city
    @Test
    public void testGetGoodCityData(){
        City city = cache.get(key0);
        assertEquals(city, lisbon);
    }

    // tests for non-existing cached city
    @Test
    public void testGetBadCityData(){
        City city = cache.get(key2);
        assertNull(city);
    }

    // test expire
    @Test
    // @Disabled // because it takes too much time, comment to execute
    public void testExpire() throws InterruptedException {
        cache.add(key2, nyc);
        TimeUnit.SECONDS.sleep(70);
        City city = cache.get(key2);
        assertNull(city);
    }

    // tests hits
    @Test
    public void testGetHits(){
        cache.get(key0);
        cache.get(key1);
        cache.get(key2);
        assertEquals(2, cache.getHits());
    }

    // tests misses
    @Test
    public void testGetMisses(){
        cache.get(key0);
        cache.get(key1);
        cache.get(key2);
        assertEquals(1, cache.getMisses());
    }

    // test misses after expire
    // @Disabled // because it takes too much time, comment to execute
    @Test
    public void testGetMissesAfterExpire() throws InterruptedException {
        cache.add(key2, nyc);
        TimeUnit.SECONDS.sleep(70);
        City city = cache.get(key2);
        assertEquals(1, cache.getMisses());
    }

    // tests stats hits
    @Test
    public void testGetStatsHits(){
        cache.get(key0);
        cache.get(key1);
        cache.get(key2);
        assertEquals(2, cache.getStats().getHits());
    }

    // test stats misses
    @Test
    public void testGetStatsMisses(){
        cache.get(key0);
        cache.get(key1);
        cache.get(key2);
        assertEquals(1, cache.getStats().getMisses());
    }

    // test stats requests
    @Test
    public void testGetStatsRequests(){
        cache.get(key0);
        cache.get(key1);
        cache.get(key2);
        assertEquals(cache.getStats().getRequests(),
                cache.getStats().getHits() + cache.getStats().getMisses());
    }
}
