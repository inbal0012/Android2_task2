package com.example.task2android2.Utils;

public class Constants {

    public final static String API_KEY = "8d2f93512ebf4ca6b07d43fef788bca1";
    public final static String ARTICLES_END_POINT = "https://newsapi.org/v2/top-headlines?country=il";
    public final static String SOURCES_END_POINT = "https://newsapi.org/v1/sources";

    public final static String KEY_URL_TAG = "key_url";
    public final static String KEY_URL_TO_IMAGE_TAG = "key_url_to_image";

    //ARTICLES
    public final static String KEY_ARTICLE_AUTOR = "author";
    public final static String KEY_ARTICLE_TITLE = "title";
    public final static String KEY_ARTICLE_DESCRIPTION = "description";
    public final static String KEY_ARTICLE_URL = "url";
    public final static String KEY_ARTICLE_URLTOIMAGE = "urlToImage";
    public final static String KEY_ARTICLE_PUBLISHEDAT = "publishedAt";

    //REQUEST CODES
    public final static int KEY_ARTICLE_REQUEST = 200;
    public final static int KEY_SOURCES_REQUEST = 300;


    //WEATHER
    public static final String WEATHER_APPID = "5bda833ea98063658162aac5ac577075";
    public final static String KEY_WEATHER_CITY_NAME = "name";
    public final static String KEY_WEATHER_WEATHER_DESCRIPTION = "description";
    public final static String KEY_WEATHER_WEATHER_MAIN = "main";
    public final static String KEY_WEATHER_CURRENT_TEMP = "temp";
    public final static String KEY_WEATHER_MIN_TEMP = "temp_min";
    public final static String KEY_WEATHER_MAX_TEMP = "temp_max";
    public final static String KEY_WEATHER_HUMIDITY = "humidity";
    public static final String KEY_WEATHER_TIME = "dt_txt";
    public static final String KEY_WEATHER_WIND = "speed";

    public static final String WEATHER_END_POINT_START = "http://api.openweathermap.org/data/2.5/forecast?";
    public static final String WEATHER_END_POINT_END ="&units=metric&id=2172797&APPID=5bda833ea98063658162aac5ac577075";

    public static final String KEY_WEATHER_TAG = "weather";

    public static final String IMPORTANCE = "importance_lvl";


}