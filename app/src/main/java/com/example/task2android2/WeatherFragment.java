package com.example.task2android2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.task2android2.Adapters.WeathersModelAdapter;
import com.example.task2android2.Models.WeathersModel;
import com.example.task2android2.Parsers.WeatherJsonParser;
import com.example.task2android2.Utils.Constants;
import com.example.task2android2.Utils.RecyclerTouchListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherFragment extends Fragment {
//    TextView resultTv;
//    TextView coordinateTv;
//
//    FusedLocationProviderClient client;
//    double latitude, longitude;
//
//    final int REQUEST_LOCATION_PERMISSION = 1;
//
//    final String WEATHER_SERVICE_LINK = "http://api.openweathermap.org/data/2.5/weather?id=2172797&APPID=5bda833ea98063658162aac5ac577075&units=metric&";
//    //lat=32.08337216&lon=34.77137702
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.weather_fragment_layout, container, false);
//        resultTv = rootView.findViewById(R.id.weather_result);
//        coordinateTv = rootView.findViewById(R.id.coordinate_tv);
//
//        if (Build.VERSION.SDK_INT >= 23) {
//            int hasLocationPermission = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
//            if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
//            } else getLocation();
//        } else getLocation();
//
//        return rootView;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == REQUEST_LOCATION_PERMISSION) {
//            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("Attention")
//                        .setMessage("Location is needed for the app to run properly. please accept location permission")
//                        .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                intent.setData(Uri.parse("package:" + getContext().getPackageName()));
//                                getContext().startActivity(intent);
//                            }
//                        }).show();
//            } else getLocation();
//        }
//    }

    private static final String TAG = "my_WeatherFragment";

    static ArrayList<WeathersModel> weathers;
    static RequestQueue queue;

    RecyclerView recyclerView_horizontal;
    View rootView;
    static View viewSource;

    String city;

    final int REQUEST_LOCATION_PERMISSION = 1;
    FusedLocationProviderClient client;
    double latitude,longitude;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.weather_fragment_layout, container, false);

        if (Build.VERSION.SDK_INT >= 23) {
            int hasLocationPermission = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
            if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            } else getLocation();
        } else getLocation();

        recyclerView_horizontal = rootView.findViewById(R.id.weather_horizontal_recyclerView);
        recyclerView_horizontal.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView_horizontal, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                if (viewSource != null) {
                    viewSource.setBackgroundColor(Color.WHITE);
                }
                viewSource = view;
                view.setBackgroundColor(Color.rgb(255, 144, 64));
                //swipe_refresh_layout.setRefreshing(true);
                getRecyclerView_weathers();

                Intent intent = new Intent(getActivity(), ShowWeather.class);
                intent.putExtra(Constants.KEY_WEATHER_TAG, weathers.get(position));
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return rootView;
    }


    void getRecyclerView_weathers() {
        //http://api.openweathermap.org/data/2.5/forecast/daily?lat=32.01801801801802&lon=34.787288881017254&cnt=14&id=2172797&APPID=5bda833ea98063658162aac5ac577075
        Log.d(TAG, "getRecyclerView_weathers: URL " + Constants.WEATHER_END_POINT_START + "lat=" + latitude + "&lon=" + longitude + Constants.WEATHER_END_POINT_END);
        requestDataWeathers(Constants.WEATHER_END_POINT_START + "lat=" + latitude + "&lon=" + longitude + Constants.WEATHER_END_POINT_END);

    }

    public void setRecyclerView_weathers(ArrayList<WeathersModel> weathersModelArrayList) {
        weathers = weathersModelArrayList;
        WeathersModelAdapter adapter;

        adapter = new WeathersModelAdapter(weathersModelArrayList, getContext());
        adapter.notifyDataSetChanged();

        recyclerView_horizontal = rootView.findViewById(R.id.weather_horizontal_recyclerView);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView_horizontal.setLayoutManager(mLayoutManager);
        recyclerView_horizontal.setItemAnimator(new DefaultItemAnimator());

        recyclerView_horizontal.setAdapter(adapter);
    }

    public void requestDataWeathers(String uri) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ArrayList<WeathersModel> weathersModelArrayList;

                        if (response != null || !response.isEmpty()) {

                            weathersModelArrayList = WeatherJsonParser.parseData(response);

                            setRecyclerView_weathers(weathersModelArrayList);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onErrorResponse: " + error.networkResponse);
                    }
                });

        queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Attention")
                        .setMessage("Location is needed for the app to run properly. please accept location permission")
                        .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getContext().getPackageName()));
                                getContext().startActivity(intent);
                            }
                        }).show();
            }
            else getLocation();
        }
    }


    private void getLocation() {
        client = LocationServices.getFusedLocationProviderClient(getContext());
        LocationRequest request = LocationRequest.create();
        request.setInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_LOW_POWER);

        LocationCallback callback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                Location location = locationResult.getLastLocation();

                //web service
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                getRecyclerView_weathers();
                //coordinateTv.setText("lat: " + latitude + " lon: " + longitude);

                //http://api.openweathermap.org/data/2.5/forecast/daily?lat=32.01801801801802&lon=34.787288881017254&cnt=14&id=2172797&APPID=5bda833ea98063658162aac5ac577075
//                RequestQueue queue = Volley.newRequestQueue(getContext());
//                StringRequest request1 = new StringRequest(Constants.WEATHER_SERVICE_LINK + "lat=" + latitude + "&lon=" + longitude, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            Log.d(TAG, "onResponse: URL " + Constants.WEATHER_SERVICE_LINK + "lat=" + latitude + "&lon=" + longitude);
//                            JSONObject rootObject = new JSONObject(response);
//                            city = rootObject.getString("name");
//                            Log.d(TAG, "onResponse: city " + city);
//                            getRecyclerView_weathers();
//                            //resultTv.setText("weather in " + city + ":\n");
//
//
//                            weathersModelArrayList = WeatherJsonParser.parseData(response);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//                queue.add(request1);
//                queue.start();
            }
        };

        if (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            client.requestLocationUpdates(request, callback, null);
    }

}
