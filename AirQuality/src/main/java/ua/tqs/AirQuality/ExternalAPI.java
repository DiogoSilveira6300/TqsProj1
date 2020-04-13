package ua.tqs.AirQuality;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ExternalAPI {

    private final String KEY = "5f04858d-63b8-4aa7-b1d9-f870b2132550";

    public String requestForCity(String city, String state, String name) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(String.format("https://api.airvisual.com/v2/city?city=%s&state=%s&country=%s&key=%s", city, state, name, KEY))
                .method("GET", null)
                .build();
        return client.newCall(request).execute().body().string();
    }

    public String requestForCoords(String lat, String lon) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(String.format("https://api.airvisual.com/v2/nearest_city?lat=%s&lon=%s&key=%s", lat, lon, KEY))
                .method("GET", null)
                .build();
        return client.newCall(request).execute().body().string();
    }
}
