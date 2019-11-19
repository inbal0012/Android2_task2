package com.example.task2android2;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.task2android2.Models.ArticlesModel;
import com.example.task2android2.Utils.Constants;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "my_MainActivity";
    final String NEWS_FRAGMENT_TAG = "news_fragment";
    final String WEATHER_FRAGMENT_TAG = "weather_fragment";


    //updates
    AlarmManager manager;

    EditText time_et;
    Spinner time_spinner;
    Spinner importance_spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        FragmentManager fragmentManagerNews = getSupportFragmentManager();
        FragmentTransaction transactionNews = fragmentManagerNews.beginTransaction();
        transactionNews.add(R.id.news_fragment_container, new NewsFragment(), NEWS_FRAGMENT_TAG);
        transactionNews.commit();

        FragmentManager fragmentManagerWeather = getSupportFragmentManager();
        FragmentTransaction transactionWeather = fragmentManagerWeather.beginTransaction();
        transactionWeather.add(R.id.weather_fragment_container, new WeatherFragment(), WEATHER_FRAGMENT_TAG);
        transactionWeather.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings)
        {
            //TODO notif dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View dialogView = getLayoutInflater().inflate(R.layout.updates_dialog, null);

            time_et = dialogView.findViewById(R.id.time_period_et);
            time_spinner = dialogView.findViewById(R.id.time_spinner);

            ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(this, R.array.time_spinner_arr, android.R.layout.simple_spinner_item);
            timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            time_spinner.setAdapter(timeAdapter);
            time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            importance_spinner = dialogView.findViewById(R.id.importance_spinner);
            ArrayAdapter<CharSequence> importanceAdapter = ArrayAdapter.createFromResource(this, R.array.importance_spinner_arr, android.R.layout.simple_spinner_item);
            importanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            importance_spinner.setAdapter(importanceAdapter);
            importance_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            builder.setView(dialogView);
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveUpdates();
                }
            }).setNegativeButton("Cancel updates", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, NewsBroadcastReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                    manager.cancel(pendingIntent);
                }
            }).show();

        }
        return super.onOptionsItemSelected(item);
    }

    private void saveUpdates() {
        GetLatestArticle latestArticle = new NewsFragment();
        ArticlesModel articlesModel = latestArticle.getLatestArticle();
        long seconds;
        Log.d(TAG, "saveUpdates: " + time_spinner.getSelectedItem());
        if(time_spinner.getSelectedItem().equals("hours")) {
            seconds = 360 * Integer.parseInt(time_et.getText().toString());
        }
        else
            seconds = 60 * Integer.parseInt(time_et.getText().toString());

        int importance = getImportance(importance_spinner.getSelectedItem().toString());

        Intent intent = new Intent(MainActivity.this, NewsBroadcastReceiver.class);
        intent.putExtra("seconds",seconds);
        intent.putExtra(Constants.IMPORTANCE,importance);
        intent.putExtra(Constants.KEY_URL_TAG,articlesModel.getUrl());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        manager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + seconds*1000,pendingIntent);


        Log.d(TAG, "saveUpdates: every " + seconds + " seconds.");
        Toast.makeText(this, "News updates saved! your next update is in " + seconds + "seconds.", Toast.LENGTH_SHORT).show();

    }

    private int getImportance(String selectedItem) {
        int im = 0;

        switch (selectedItem) {
            case "HIGH":
                im = NotificationManager.IMPORTANCE_HIGH;
                break;
            case "LOW":
                im = NotificationManager.IMPORTANCE_LOW;
                break;
            case "DEFAULT":
                im = NotificationManager.IMPORTANCE_DEFAULT;
                break;
            case "NONE":
                im = NotificationManager.IMPORTANCE_NONE;
                break;
        }
        return  im;
    }


    //TODO fix weather (location and times)
    //TODO add pic according to forecast
    //
}
