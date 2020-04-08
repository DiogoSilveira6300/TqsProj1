package ua.tqs.AirQuality;

import java.io.*;
import okhttp3.*;

import java.io.IOException;

public class ExternalAPI {
    // airvisual
    //https://api.airvisual.com/v2/cities?state=New York&country=USA&key=5f04858d-63b8-4aa7-b1d9-f870b2132550

    private static final String KEY = "5f04858d-63b8-4aa7-b1d9-f870b2132550";

    public static String request(String city, String state, String name) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(String.format("https://api.airvisual.com/v2/city?city=%s&state=%s&country=%s&key=%s", city, state, name, KEY))
                .method("GET", null)
                .build();
        return client.newCall(request).execute().body().string();
    }
}
