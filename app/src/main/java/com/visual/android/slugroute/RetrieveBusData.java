package com.visual.android.slugroute;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * Created by RamiK on 4/13/2017.
 */

public abstract class RetrieveBusData extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... voids) {

        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL("http://bts.ucsc.edu:8081/location/get?nocache=" + new Date().getTime());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            url.openStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        try{
            //Log.d("Sleep", "successful");
            Thread.sleep(500);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        return sb.toString();
    }


}
