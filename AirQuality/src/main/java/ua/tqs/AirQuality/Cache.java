package ua.tqs.AirQuality;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Component
public class Cache {

    private static final long EXPIRE_AFTER = 1; // changed to 1 for testing more quickly, it should be 5 though

    private static class CityWrapper {

        City city;

        Long timestamp, expireAfter;

        CityWrapper(City city){
            this.expireAfter = Cache.EXPIRE_AFTER;
            this.city = city;
            this.timestamp = System.currentTimeMillis();
        }

        City getCity(){
            return city;
        }

        long age(){
            return TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - timestamp);
        }

        boolean expired(){
            return age() >= expireAfter;
        }
    }

    private HashMap<String, CityWrapper> map;

    private int hits, misses;

    public Cache(){
        this.map = new HashMap<>();
        hits = 0;
        misses = 0;
    }

    public City get(String key){
        CityWrapper value = map.get(key);
        if (value != null && !value.expired()){
            hits++;
            return value.getCity();
        }
        else misses++;
        return null;
    }

    public void add(String key, City city){
        if (!map.containsKey(key) || map.get(key).expired()){
            city.setCached(true);
            map.put(key, new CityWrapper(city));
        }
    }

    public int getRequests(){
        return hits + misses;
    }

    public int getHits(){
        return hits;
    }

    public int getMisses(){
        return misses;
    }
}
