package com.example.task2android2.Models;

import java.io.Serializable;

public class WeathersModel implements Serializable {

    String city_name;
    String weather_description;
    String weather_main;
    String current_temp;
    String min_temp;
    String max_temp;
    String humidity;
    String wind;
    String time;

    public WeathersModel() {
    }

    public WeathersModel(String city_name, String weather_description, String current_temp, String min_temp, String max_temp, String humidity, String wind, String time) {
        this.city_name = city_name;
        this.weather_description = weather_description;
        this.current_temp = current_temp;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.humidity = humidity;
        this.wind = wind;
        this.time = time;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getWeather_description() {
        return weather_description;
    }

    public void setWeather_description(String weather_description) {
        this.weather_description = weather_description;
    }

    public String getCurrent_temp() {
        return current_temp;
    }

    public void setCurrent_temp(String current_temp) {
        this.current_temp = current_temp;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(String min_temp) {
        this.min_temp = min_temp;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(String max_temp) {
        this.max_temp = max_temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeather_main() {
        return weather_main;
    }

    public void setWeather_main(String weather_main) {
        this.weather_main = weather_main;
    }
}
