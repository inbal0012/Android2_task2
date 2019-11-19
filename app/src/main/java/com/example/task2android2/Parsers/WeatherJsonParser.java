package com.example.task2android2.Parsers;

import android.util.Log;

import com.example.task2android2.Models.WeathersModel;
import com.example.task2android2.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class WeatherJsonParser {
    static ArrayList<WeathersModel> weathersModelArrayList;
    private static final String TAG = "my_WeatherJsonParser";

    public static ArrayList<WeathersModel> parseData(String content) {

        JSONArray weather_arry = null;
        WeathersModel model = null;
        try {
            weathersModelArrayList = new ArrayList<>();
            JSONObject jObj = new JSONObject(content);
            weather_arry = jObj.getJSONArray("list");
            JSONObject cityObj = jObj.getJSONObject("city");
            String city = cityObj.getString("name");

            for (int i = 0; i < weather_arry.length(); i++) {

                JSONObject obj = weather_arry.getJSONObject(i);
                model = new WeathersModel();

                model.setCity_name(city);
                model.setTime(obj.getString(Constants.KEY_WEATHER_TIME));
                JSONArray weatherArr = obj.getJSONArray("weather");
                if(weatherArr.length() > 0) {
                    JSONObject bestResult = weatherArr.getJSONObject(0);
                    model.setWeather_description(bestResult.getString(Constants.KEY_WEATHER_WEATHER_DESCRIPTION));
                    model.setWeather_main(bestResult.getString(Constants.KEY_WEATHER_WEATHER_MAIN));
                }

                DecimalFormat df = new DecimalFormat("#");

                JSONObject mainObj = obj.getJSONObject("main");
                model.setCurrent_temp(df.format(Double.parseDouble(mainObj.getString(Constants.KEY_WEATHER_CURRENT_TEMP))));
                model.setMin_temp(df.format(Double.parseDouble(mainObj.getString(Constants.KEY_WEATHER_MIN_TEMP))));
                model.setMax_temp(df.format(Double.parseDouble(mainObj.getString(Constants.KEY_WEATHER_MAX_TEMP))));
                model.setHumidity(mainObj.getString(Constants.KEY_WEATHER_HUMIDITY));

                JSONObject windObj = obj.getJSONObject("wind");
                model.setWind(windObj.getString(Constants.KEY_WEATHER_WIND));

                weathersModelArrayList.add(model);
            }
            return weathersModelArrayList;

        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
