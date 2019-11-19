package com.example.task2android2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.task2android2.Models.WeathersModel;
import com.example.task2android2.Utils.Constants;

public class ShowWeather extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather);

        Intent intent = getIntent();
        WeathersModel weathersModel =(WeathersModel) intent.getExtras().getSerializable(Constants.KEY_WEATHER_TAG);

        TextView city = findViewById(R.id.city);
        TextView time = findViewById(R.id.time);
        TextView deg = findViewById(R.id.deg);
        TextView forecast = findViewById(R.id.forecast);
        TextView humidity = findViewById(R.id.humidity);
        TextView wind = findViewById(R.id.wind);
        ImageView image = findViewById(R.id.image);

        city.setText(weathersModel.getCity_name());
        time.setText(weathersModel.getTime());
        deg.setText(weathersModel.getCurrent_temp() + "°C");
        forecast.setText(weathersModel.getWeather_description());
        humidity.setText(weathersModel.getHumidity() + "%");
        wind.setText(weathersModel.getWind());
        setImage(image, weathersModel);
    }


    private void setImage(ImageView image, WeathersModel model) {
        switch (model.getWeather_main()) {
            case "Thunderstorm":
                image.setImageResource(R.drawable.thunder_icon_128);
                break;
            case "Drizzle":
            case "Rain":
                image.setImageResource(R.drawable.rain_icon_128);
                break;
            case "Snow":
                image.setImageResource(R.drawable.sleet_icon_512);
                break;
            case "Clear":
                image.setImageResource(R.drawable.day_removebg_preview);
                break;
            case "Clouds":
                image.setImageResource(R.drawable.partly_cloudy_day_icon_512);
                break;
            default:
                image.setImageResource(R.drawable.night_removebg_preview);
        }
    }
}
