package com.visual.android.slugroute;

import com.google.android.gms.maps.model.Circle;

/**
 * Created by RamiK on 4/13/2017.
 */

public class Bus {

    private int id;
    private float lon;
    private float lat;
    private String type;
    private Circle circle;

    public Bus(int id, float lon, float lat, String type, Circle circle){
        this.id = id;
        this.lon = lon;
        this.lat = lat;
        this.type = type;
        this.circle = circle;
    }

    public int getId() {
        return id;
    }

    public float getLon() {
        return lon;
    }

    public float getLat() {
        return lat;
    }

    public String getType() {
        return type;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
