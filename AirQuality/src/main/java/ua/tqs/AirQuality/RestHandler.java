package ua.tqs.AirQuality;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RestHandler {

    @Autowired
    private Cache cache;

    @Autowired
    private ExternalAPI externalAPI;

    // proper REST API
    private City buildCity(JSONObject jObject) throws JSONException {
        Coords coords;
        int aqi;
        String name, state, country, mainProblem, timeStamp;

        name = jObject.getString("city");
        state = jObject.getString("state");
        country = jObject.getString("country");
        JSONArray coordinates = jObject.getJSONObject("location").getJSONArray("coordinates");

        coords = new Coords(coordinates.getDouble(0), coordinates.getDouble(1));

        jObject = jObject.getJSONObject("current").getJSONObject("pollution");

        aqi =  jObject.getInt("aqius");
        mainProblem = jObject.getString("mainus");
        timeStamp = jObject.getString("ts");
        return new City(name, state, country, coords, aqi, mainProblem, timeStamp);
    }

    private City getCityFromExternalApi(String name, String state, String country)
            throws JSONException, IOException {
        JSONObject jObject = new JSONObject(externalAPI.requestForCity(name, state, country)).getJSONObject("data");
        return buildCity(jObject);
    }

    private City getCoordsFromExternalApi(String lat, String lon)
            throws JSONException, IOException {
        JSONObject jObject = new JSONObject(externalAPI.requestForCoords(lat, lon)).getJSONObject("data");
        return buildCity(jObject);
    }

    @GetMapping("/city/{name}/state/{state}/country/{country}")
    public City getDataForCity(@PathVariable String name, @PathVariable String state, @PathVariable String country)
            throws IOException {
        String request = String.format("/city/%s/state/%s/country/%s", name, state, country);
        City cached = cache.get(request);
        if (cached != null) return cached;

        try {
            City newCity = getCityFromExternalApi(name, state, country);
            cache.add(request, newCity.copy());
            return newCity;
        } catch (JSONException e){
            throw new IllegalArgumentException("URL arguments do not match any valid response.");
        }
    }

    @GetMapping("/coords/{lat}/{lon}")
    public City getDataForCoords(@PathVariable String lat, @PathVariable String lon)
            throws IOException {
        String request = String.format("/coords/%s/%s", lat, lon);
        City cached = cache.get(request);
        if (cached != null) return cached;

        try {
            City newCity = getCoordsFromExternalApi(lat, lon);
            cache.add(request, newCity.copy());
            return newCity;
        } catch (JSONException e){
            throw new IllegalArgumentException("URL arguments do not match any valid response.");
        }
    }

    // cache stats

    static class CacheStats {
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
