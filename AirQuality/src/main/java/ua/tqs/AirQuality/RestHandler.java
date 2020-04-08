package ua.tqs.AirQuality;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
public class RestHandler {

    @Autowired
    private Cache cache;

    // proper REST API

    private City getFromExternalApi(String name, String state, String country)
            throws JSONException, IOException {
        Coords coords;
        int aqi;
        String mainProblem, timeStamp;

        JSONObject jObject = new JSONObject(ExternalAPI.request(name, state, country)).getJSONObject("data");
        JSONArray coordinates = jObject.getJSONObject("location").getJSONArray("coordinates");

        coords = new Coords(coordinates.getDouble(0), coordinates.getDouble(1));

        jObject = jObject.getJSONObject("current").getJSONObject("pollution");

        aqi =  jObject.getInt("aqius");
        mainProblem = jObject.getString("mainus");
        timeStamp = jObject.getString("ts");
        return new City(name, state, country, coords, aqi, mainProblem, timeStamp);
    }

    @GetMapping("/city/{name}/state/{state}/country/{country}")
    public City getDataForCity(@PathVariable String name, @PathVariable String state, @PathVariable String country)
            throws IOException, JSONException {
        String request = String.format("/city/%s/state/%s/country/%s", name, state, country);
        City cached = cache.get(request);
        if (cached != null) return cached;

        City newCity = getFromExternalApi(name, state, country);
        cache.add(request, newCity.copy());
        return newCity;
    }

    // cache stats

    private static class CacheStats {
        private final int requests;
        private final int hits;
        private final int misses;

        CacheStats(Cache cache){
            this.requests = cache.getRequests();
            this.hits = cache.getHits();
            this.misses = cache.getMisses();
        }

        public int getRequests(){
            return requests;
        }

        public int getHits(){
            return hits;
        }

        public int getMisses(){
            return misses;
        }
    }

    @GetMapping("/cache_stats")
    public CacheStats cacheStats(){
        return new CacheStats(cache);
    }
}
