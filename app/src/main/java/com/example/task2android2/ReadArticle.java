package com.example.task2android2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.task2android2.Models.ArticlesModel;
import com.example.task2android2.Utils.Constants;
import com.example.task2android2.Utils.MyAppWebViewClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReadArticle extends AppCompatActivity {
    private static final String TAG = "my_ReadArticle";
    WebView webView;
    ProgressBar progressBar;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_article);

        Intent intent = getIntent();
        final String url = intent.getStringExtra(Constants.KEY_URL_TAG);
        Log.d(TAG, "onCreate: url " + url);

        final ImageView img = new ImageView(this);

        webView = findViewById(R.id.webView);

        webView.setWebViewClient(new MyAppWebViewClient(url));

        webView.getSettings().setJavaScriptEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //webView.getSettings().setUseWideViewPort(true);
        //webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setAllowFileAccess(true);


        webView.loadUrl(url);

        webView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }
                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }


        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
