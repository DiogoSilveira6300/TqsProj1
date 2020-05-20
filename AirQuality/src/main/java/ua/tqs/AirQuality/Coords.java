package ua.tqs.AirQuality;

import java.util.Objects;

public class Coords {

    private double lat, lon;

    //constr
    public Coords(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    // getters
    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon + 3;
    }

    // setters
    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return Double.compare(coords.lat, lat) == 0 &&
                Double.compare(coords.lon, lon) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon);
    }
}
