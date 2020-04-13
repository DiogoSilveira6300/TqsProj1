package ua.tqs.AirQuality;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// run w/ mvn verify failsafe:integration-test
@WebMvcTest(RestHandler.class)
public class RestHandlerTestIT {

    String response;
    City city;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Cache cache;

    @MockBean
    private ExternalAPI externalAPI;

    @BeforeEach
    public void setUp() {
        InputStream input = RestHandler.class.getResourceAsStream("/test/response.json");
        response = new BufferedReader(new InputStreamReader(input)).lines().collect(Collectors.joining("\n"));

        city = new City("Lisbon", "Lisbon", "Portugal", new Coords(-9.131168, 38.721676),
                4, "p2", "2020-04-11T23:00:00.000Z");
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenCity_whenGetCity_thenReturnJSONString() throws Exception {
        given(externalAPI.requestForCity(anyString(), anyString(), anyString())).willReturn(response);

        mockMvc.perform(get("/city/Lisbon/state/Lisbon/country/Portugal")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(city.getName())))
                .andExpect(jsonPath("$.state", is(city.getState())))
                .andExpect(jsonPath("$.country", is(city.getCountry())));
        verify(externalAPI, VerificationModeFactory.times(1))
                .requestForCity(anyString(), anyString(), anyString());
        reset(externalAPI);
    }

    @Test
    public void givenCoords_whenGetCoords_thenReturnJSONString() throws Exception {
        given(externalAPI.requestForCoords(anyString(), anyString())).willReturn(response);

        mockMvc.perform(get("/coords/-9.131168/38.721676")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.coords.lat", is(city.getCoords().getLat())))
                .andExpect(jsonPath("$.coords.lon", is(city.getCoords().getLon())));
        verify(externalAPI, VerificationModeFactory.times(1))
                .requestForCoords(anyString(), anyString());
        reset(externalAPI);
    }

    @Test
    public void whenGetCacheStats_thenReturnJSONString() throws Exception {
        int hits = 2;
        int misses = 3;
        given(cache.getStats()).willReturn(new Cache.CacheStats(hits, misses));

        mockMvc.perform(get("/cache_stats")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.misses", is(misses)))
                .andExpect(jsonPath("$.hits", is(hits)))
                .andExpect(jsonPath("$.requests", is(hits + misses)));
        verify(cache, VerificationModeFactory.times(1))
                .getStats();
        reset(cache);
    }
}
