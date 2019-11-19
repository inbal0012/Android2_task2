package com.example.task2android2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.task2android2.Models.WeathersModel;
import com.example.task2android2.R;

import java.util.ArrayList;

public class WeathersModelAdapter extends RecyclerView.Adapter<WeathersModelAdapter.MyViewHolder>{
    final String TAG = "my_WeathersModelAdapter";
    Context context;
    private ArrayList<WeathersModel> modelList;


    public WeathersModelAdapter(ArrayList<WeathersModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public WeathersModelAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_card, parent, false);

        return new WeathersModelAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeathersModelAdapter.MyViewHolder holder, int position) {
        WeathersModel model = modelList.get(position);
        holder.city.setText(model.getCity_name());
        holder.time.setText(model.getTime());
        holder.deg.setText(model.getCurrent_temp() + "°C");
        holder.forecast.setText(model.getWeather_description());
        setImage(holder, model);
    }

    private void setImage(MyViewHolder holder, WeathersModel model) {
        switch (model.getWeather_main()) {
            case "Thunderstorm":
                holder.image.setImageResource(R.drawable.storm);
                break;
            case "Drizzle":
            case "Rain":
                holder.image.setImageResource(R.drawable.rain);
                break;
            case "Snow":
                holder.image.setImageResource(R.drawable.snowing);
                break;
            case "Clear":
                holder.image.setImageResource(R.drawable.sun);
                break;
            case "Clouds":
                holder.image.setImageResource(R.drawable.cloudy);
                break;
            default:
                holder.image.setImageResource(R.drawable.cloudy_removebg_preview);
        }
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView city;
        TextView deg;
        TextView forecast;
        TextView time;
        ImageView image;

        public MyViewHolder(View view) {
            super(view);

            city = view.findViewById(R.id.city);
            time = view.findViewById(R.id.time);
            deg = view.findViewById(R.id.deg);
            forecast = view.findViewById(R.id.forecast);
            image = view.findViewById(R.id.weather_image);
        }
    }
}
