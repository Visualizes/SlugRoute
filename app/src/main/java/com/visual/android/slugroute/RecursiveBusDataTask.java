package com.visual.android.slugroute;

import android.content.Context;
import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by RamiK on 4/13/2017.
 */

public class RecursiveBusDataTask extends RetrieveBusData {

    private Context context;
    private List<Bus> buses;
    private GoogleMap mMap;

    public RecursiveBusDataTask(Context context ,List<Bus> buses, GoogleMap googleMap){
        this.context = context;
        this.buses = buses;
        mMap = googleMap;
    }

    @Override
    protected void onPostExecute(String s) {

        Pattern p = Pattern.compile(Pattern.quote("{") + "(.*?)" + Pattern.quote("}"));
        Matcher m = p.matcher(s);
        String keyWordID = "\"id\":\"";
        String keyWordLon = "\",\"lon\":";
        String keyWordLat = ",\"lat\":";
        String keyWordType = ",\"type\":\"";

        for (int i = 0; m.find(); i++) {
            if (i < buses.size()) {
                String busData = m.group(1);
                int id = Integer.parseInt(busData.substring(busData.indexOf(keyWordID) + keyWordID.length(), busData.indexOf(keyWordLon)));
                float lon = Float.parseFloat(busData.substring(busData.indexOf(keyWordLon) + keyWordLon.length(), busData.indexOf(keyWordLat)));
                float lat = Float.parseFloat(busData.substring(busData.indexOf(keyWordLat) + keyWordLat.length(), busData.indexOf(keyWordType)));
                //System.out.println("we good fam");
                for (Bus bus : buses) {
                    if (bus.getId() == id) {
                        LatLng busLatLng = new LatLng(lat, lon);
                        bus.setLat(lat);
                        bus.setLon(lon);
                        bus.getCircle().setCenter(busLatLng);
                    }
                }
            } else {
                System.out.println("restart");
                MapsActivity.initializeBusDataTask = new InitializeBusDataTask(context, mMap);
                MapsActivity.initializeBusDataTask.execute();
            }
        }


        MapsActivity.recursiveBusDataTask = new RecursiveBusDataTask(context, buses, mMap);
        MapsActivity.recursiveBusDataTask.execute();

        super.onPostExecute(s);
    }
}
