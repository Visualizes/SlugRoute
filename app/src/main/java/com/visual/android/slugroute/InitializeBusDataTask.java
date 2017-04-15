package com.visual.android.slugroute;

import android.content.Context;
import android.graphics.Color;

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

public class InitializeBusDataTask extends RetrieveBusData {

    private Context context;
    private GoogleMap mMap;

    LatLng[] busStops = {
            new LatLng(36.982779, -122.062705), //Empire Grade Inner Bus Stop
            new LatLng(36.983694, -122.064934), //Empire Grade Outer Bus Stop
            new LatLng(36.989959, -122.067191), //Heller Dr Outer Bus Stop
            new LatLng(36.990600, -122.066087), //Heller Dr Inner Bus Stop
            new LatLng(36.991796, -122.066798), //Heller Dr Outer Bus Stop
            new LatLng(36.993012, -122.065209), //Heller Dr Outer Bus Stop
            new LatLng(36.996712, -122.063596), //Heller Dr Inner Bus Stop
            new LatLng(36.992799, -122.064856), //Heller Dr Inner Bus Stop
            new LatLng(36.999209, -122.064332), //Heller/McLaughlin Dr Inner Bus Stop
            new LatLng(36.999301, -122.064572), //Heller/McLaughlin Dr Outer Bus Stop
            new LatLng(36.999981, -122.062336), //McLaughlin Dr Outer Bus Stop
            new LatLng(36.999854, -122.062173), //McLaughlin Dr Inner Bus Stop
            new LatLng(36.999923, -122.058325), //McLaughlin Dr Outer Bus Stop
            new LatLng(36.999758, -122.058306), //McLaughlin Dr Inner Bus Stop
            new LatLng(36.999005, -122.055176), //McLaughlin/Hagar Dr Outer Bus Stop
            new LatLng(36.997504, -122.055055), //Hagar Dr Outer Bus Stop
            new LatLng(36.996657, -122.055414), //Hagar Dr Inner Bus Stop
            new LatLng(36.994259, -122.055510), //Hagar Dr Outer Bus Stop (GYM)
            new LatLng(36.991315, -122.054662), //Hagar Dr Outer Bus Stop
            new LatLng(36.991275, -122.054903), //Hagar Dr Inner Bus Stop
            new LatLng(36.985918, -122.053543), //Hagar/Village Dr Outer Bus Stop
            new LatLng(36.985546, -122.053556), //Hagar/Village Dr Inner Bus Stop
            new LatLng(36.981373, -122.051925), //Coolidge Dr Outer Bus Stop
            new LatLng(36.981567, -122.052115), //Coolidge Dr Inner Bus Stop
            new LatLng(36.977641, -122.053678), //High St Outer Bus Stop
            new LatLng(36.977317, -122.054302), //High St Inner Bus Stop
            new LatLng(36.978820, -122.057727), //High/Western Inner Bus Stop
            new LatLng(36.978645, -122.057823), //High/Western Outer Bus Stop
            new LatLng(36.979885, -122.059326) //High/Tosca Terrace Outer Bus Stop

    };

    public InitializeBusDataTask(Context context, GoogleMap googleMap){
        this.context = context;
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
        List<Bus> buses = new ArrayList<>();

        mMap.clear();

        for (LatLng busStop : busStops){
            mMap.addCircle(new CircleOptions()
                    .center(busStop)
                    .radius(3)
                    .fillColor(Color.CYAN)
                    .strokeColor(Color.CYAN));
        }

        while (m.find()) {
            String busData = m.group(1);
            int id = Integer.parseInt(busData.substring(busData.indexOf(keyWordID) + keyWordID.length(), busData.indexOf(keyWordLon)));
            float lon = Float.parseFloat(busData.substring(busData.indexOf(keyWordLon) + keyWordLon.length(), busData.indexOf(keyWordLat)));
            float lat = Float.parseFloat(busData.substring(busData.indexOf(keyWordLat) + keyWordLat.length(), busData.indexOf(keyWordType)));
            String type = busData.substring(busData.indexOf(keyWordType) + keyWordType.length(), busData.length()-1);
            System.out.println(type);

            LatLng busLatLng = new LatLng(lat, lon);
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(busLatLng)
                    .radius(30)
                    .strokeWidth(5.0f)
                    .strokeColor(Color.WHITE));


            switch (type){
                case "LOOP":
                    circle.setFillColor(context.getResources().getColor(R.color.blue));
                    break;
                case "UPPER CAMPUS":
                    circle.setFillColor(context.getResources().getColor(R.color.yellow));
                    break;
                case "NIGHT OWL":
                    circle.setFillColor(context.getResources().getColor(R.color.green));
                    break;
                case "SPECIAL EVENT":
                    circle.setFillColor(context.getResources().getColor(R.color.purple));
                    break;
                default:
                    circle.setFillColor(context.getResources().getColor(R.color.gray));
                    break;
            }



            buses.add(new Bus(id, lon, lat, type, circle));
            //mMap.addMarker(new MarkerOptions().position(busLatLng).title("BUS"));
        }


        MapsActivity.recursiveBusDataTask = new RecursiveBusDataTask(context, buses, mMap);
        MapsActivity.recursiveBusDataTask.execute();

        super.onPostExecute(s);
    }

}
