package ua.tqs.AirQuality;

import lombok.*;

import javax.persistence.*;

@ToString
@EqualsAndHashCode()
@Data
public class City {

    private String name;
    private String state; // or region, province, district
    private String country;

    private Coords coords;

    private int aqi;
    private String mainProblem;

    private String timeStamp;

    private boolean cached;

    public City(String name, String state, String country,
                Coords coords, int aqi, String mainProblem,
                String timeStamp){
        this.name = name;
        this.state = state;
        this.country = country;
        this.coords = coords;
        this.aqi = aqi;
        this.mainProblem = mainProblem;
        this.timeStamp = timeStamp;
        this.cached = false;
    }

    public City copy(){
        return new City(name, state, country,
                coords, aqi, mainProblem,
                timeStamp);
    }
}
