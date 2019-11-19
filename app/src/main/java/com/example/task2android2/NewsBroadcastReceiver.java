package com.example.task2android2;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.task2android2.Models.ArticlesModel;
import com.example.task2android2.Parsers.ArticlesJsonParser;
import com.example.task2android2.Utils.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsBroadcastReceiver extends BroadcastReceiver {
    String TAG = "my_BroadcastReceiverrrr";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: NewsBroadcastReceiver started. URL " + intent.getExtras().getString(Constants.KEY_URL_TAG));

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int importance = intent.getIntExtra(Constants.IMPORTANCE, 0);

        String channelId = null;
        if (Build.VERSION.SDK_INT >= 26) {
            channelId = "news_channel_id";
            NotificationChannel channel = new NotificationChannel(channelId, "updates channel", importance);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        builder.setContentText("the latest article in your area" ).setContentTitle("News update!")
                .setSmallIcon(android.R.drawable.star_on);

        Intent actionIntent = new Intent(context, ReadArticle.class);
        actionIntent.putExtra(Constants.KEY_URL_TAG, intent.getExtras().getString(Constants.KEY_URL_TAG));
        PendingIntent playPendingIntent = PendingIntent.getActivity(context, 1, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_play, "Read", playPendingIntent));

        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_ALL;

        manager.notify(1, notification);


        if (intent.hasExtra("seconds")) {
            int seconds = intent.getIntExtra("seconds", 60);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + seconds * 1000, pendingIntent);
        }

    }
}
