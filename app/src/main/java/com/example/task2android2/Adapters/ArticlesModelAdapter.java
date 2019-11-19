package com.example.task2android2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.task2android2.Models.ArticlesModel;
import com.example.task2android2.R;
import com.example.task2android2.Utils.PicassoClient;

import java.io.Serializable;
import java.util.ArrayList;

    public class ArticlesModelAdapter extends RecyclerView.Adapter<ArticlesModelAdapter.MyViewHolder> {
    Context context;
    private ArrayList<ArticlesModel> modelList;


    public ArticlesModelAdapter(ArrayList<ArticlesModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ArticlesModel model = modelList.get(position);
        holder.textName.setText(model.getTitle());
        holder.textDescription.setText(model.getDescription());
        if (model.getPublishedAt().equals("null") || model.getPublishedAt().isEmpty()) {
            holder.textDate.setText("");
        } else {
            holder.textDate.setText(model.getPublishedAt());
        }

        if (model.getUrlToImage().equals("null") || model.getUrlToImage().equalsIgnoreCase("null")) {


        }

        PicassoClient.LoadImage(context, model.getUrlToImage(),
                holder.loadedImage, holder.textDate);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textName;
        private TextView textDate;
        private TextView textDescription;

        private ImageView loadedImage;


        public MyViewHolder(View view) {
            super(view);

            textName = (TextView) view.findViewById(R.id.txt_title);
            textDate = (TextView) view.findViewById(R.id.txt_date);
            textDescription = (TextView) view.findViewById(R.id.txt_description);
            loadedImage = (ImageView) view.findViewById(R.id.img_article);

        }
    }
}
