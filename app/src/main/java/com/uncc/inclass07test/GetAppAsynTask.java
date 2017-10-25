package com.uncc.inclass07test;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by gaurav on 10/23/2017.
 */

public class GetAppAsynTask extends AsyncTask<String, Void, ArrayList<AppData>> {
    ArrayList<AppData> appList;
    public AsyncResponse response;
    public GetAppAsynTask(AsyncResponse response) {
         this.response = response;
     }
    @Override
    protected ArrayList<AppData> doInBackground(String... params) {
        try{
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == con.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null){
                    sb.append(line);
                    line = reader.readLine();
                }

                return AppUtil.AppJSONParser.parseApps(sb.toString());
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<AppData> musicList) {
        super.onPostExecute(musicList);
        Log.d("demo",musicList.toString());
         response.processFinish(musicList);

    }
    public interface AsyncResponse {
        void processFinish(ArrayList<AppData> musicList);
    }
}
