package ua.tqs.AirQuality;

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
        return lon;
    }

    // setters
    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
