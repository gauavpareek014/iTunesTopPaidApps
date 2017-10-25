package com.uncc.inclass07test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gaurav on 10/23/2017.
 */

public class AppUtil {
    static public class AppJSONParser {
        static ArrayList<AppData> parseApps(String in) throws JSONException {
            ArrayList<AppData> appList = new ArrayList<AppData>();
            JSONObject root = new JSONObject(in);
            JSONObject jsonObject = root.getJSONObject("feed");
            JSONArray trackJSONOArray = jsonObject.getJSONArray("entry");
            for (int i = 0; i < trackJSONOArray.length(); i++) {
                JSONObject appJSONObject = trackJSONOArray.getJSONObject(i);
                AppData appData = new AppData();
                if (appJSONObject.has("im:name")) {
                    JSONObject imName = appJSONObject.getJSONObject("im:name");
                    if (imName.has("label")) {
                        appData.setAppName(imName.getString("label"));

                    }
                }
                if (appJSONObject.has("im:image")) {
                    JSONArray imImage = appJSONObject.getJSONArray("im:image");
                    JSONObject appJSONOImageObject = imImage.getJSONObject(0);
                    if (appJSONOImageObject.has("label")) {
                        appData.setAppThumb(appJSONOImageObject.getString("label"));

                    }

                    JSONObject appJSONOLargeImageObject = imImage.getJSONObject(2);
                    if (appJSONOLargeImageObject.has("label")) {
                        appData.setThumb_url(appJSONOLargeImageObject.getString("label"));

                    }
                }

                if (appJSONObject.has("im:price")) {
                    JSONObject imPrice = appJSONObject.getJSONObject("im:price");
                    if (imPrice.has("label")) {
                        appData.setAppPrice(imPrice.getString("label").substring(1));
                        if(Double.parseDouble(appData.getAppPrice())>=0 && Double.parseDouble(appData.getAppPrice())<=1.99){
                        appData.setDollarImage(R.drawable.price_low+"");
                        }else if(Double.parseDouble(appData.getAppPrice())>=2.00 && Double.parseDouble(appData.getAppPrice())<=5.99){
                        appData.setDollarImage(R.drawable.price_medium+"");
                        }else if(Double.parseDouble(appData.getAppPrice())>=6.00){
                        appData.setDollarImage(R.drawable.price_high+"");
                        }
                    }
                }

                appList.add(appData);
            }
            return appList;
        }
    }
}
