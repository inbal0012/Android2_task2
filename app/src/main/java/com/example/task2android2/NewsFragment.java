package com.example.task2android2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.task2android2.Parsers.ArticlesJsonParser;
import com.example.task2android2.Models.ArticlesModel;
import com.example.task2android2.Adapters.ArticlesModelAdapter;
import com.example.task2android2.Utils.Constants;
import com.example.task2android2.Utils.RecyclerTouchListener;


import java.util.ArrayList;


public class NewsFragment extends Fragment implements GetLatestArticle {
    private static final String TAG = "my_NewsFragment";

    static ArrayList<ArticlesModel> articles;
    static RequestQueue queue;

    RecyclerView recyclerView_horizontal;
    View rootView;
    static View viewSource;




        @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.news_fragment_layout, container, false);

        recyclerView_horizontal = rootView.findViewById(R.id.all_news_horizontal_recyclerView);
        recyclerView_horizontal.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView_horizontal, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                if (viewSource != null) {
                    viewSource.setBackgroundColor(Color.WHITE);
                }
                viewSource = view;
                view.setBackgroundColor(Color.rgb(255, 144, 64));
                //swipe_refresh_layout.setRefreshing(true);
                getRecyclerView_articles();

                Intent intent = new Intent(getActivity(), ReadArticle.class);
                intent.putExtra(Constants.KEY_URL_TAG, articles.get(position).getUrl());
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        getRecyclerView_articles();

        return rootView;
    }


    void getRecyclerView_articles() {

        requestDataArticles(Constants.ARTICLES_END_POINT + "&apiKey=" + Constants.API_KEY);

    }

    public void setRecyclerView_articles(ArrayList<ArticlesModel> articlesModelArrayList) {
//        articles = articlesModelArrayList;
//        ArticlesModelAdapter adapter;
//
//        recyclerView_horizontal = rootView.findViewById(R.id.all_news_horizontal_recyclerView);
//
//        adapter = new ArticlesModelAdapter(articlesModelArrayList, getContext());
//        adapter.notifyDataSetChanged();


        articles = articlesModelArrayList;
        ArticlesModelAdapter adapter;

        adapter = new ArticlesModelAdapter(articlesModelArrayList, getContext());
        adapter.notifyDataSetChanged();

        recyclerView_horizontal = rootView.findViewById(R.id.all_news_horizontal_recyclerView);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        recyclerView_horizontal.setLayoutManager(mLayoutManager);
        recyclerView_horizontal.setItemAnimator(new DefaultItemAnimator());

        recyclerView_horizontal.setAdapter(adapter);
    }

    public void requestDataArticles(String uri) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ArrayList<ArticlesModel> articlesModelArrayList;

                        if (response != null || !response.isEmpty()) {

                            articlesModelArrayList = ArticlesJsonParser.parseData(response);

                            setRecyclerView_articles(articlesModelArrayList);
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
    public ArticlesModel getLatestArticle() {
        return articles.get(0);
    }
}


interface GetLatestArticle {
    ArticlesModel getLatestArticle();
}
