package ua.tqs.AirQuality;

import lombok.*;

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

    @Override
    public boolean equals(Object o){
        City other = (City) o;
        return this.name.equals(((City) o).name) &&
                this.state.equals(((City) o).state) &&
                this.country.equals(((City) o).country) &&
                this.coords.equals(((City) o).coords) &&
                this.aqi == ((City) o).aqi &&
                this.mainProblem.equals(((City) o).mainProblem) &&
                this.timeStamp.equals(((City) o).timeStamp);
    }
}
