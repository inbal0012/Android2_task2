package com.example.task2android2.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.palette.graphics.Palette;

import com.example.task2android2.R;
import com.squareup.picasso.Picasso;

public class PicassoClient {
    public static void LoadImage(final Context c, String url, ImageView img, final TextView textView) {


        if (url != null && url.length() > 0) {
            try {
                Picasso.with(c).load(url).placeholder(R.drawable.placeholder).into(img);


                BitmapDrawable p = (BitmapDrawable) img.getDrawable();
                Bitmap bitmap = p.getBitmap();

                Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        try {
                            int bgColor = palette.getMutedColor(c.getResources().getColor(android.R.color.black));
                            textView.setBackgroundColor(bgColor);
                        } catch (Exception n) {

                        }

                    }
                });
            } catch (Exception b) {
                Picasso.with(c).load(R.drawable.placeholder).into(img);
            }


        } else {
            img.setVisibility(View.INVISIBLE);

        }
    }

    public static void LoadImageWebView(final Context c, String url, ImageView img, final Toolbar toolbar) {


        if (url != null && url.length() > 0) {
            try {
                Picasso.with(c).load(url).placeholder(R.drawable.placeholder).into(img);

                BitmapDrawable p = (BitmapDrawable) img.getDrawable();
                Bitmap bitmap = p.getBitmap();

                Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        try {
                            int bgColor = palette.getMutedColor(c.getResources().getColor(android.R.color.black));
                            toolbar.setBackgroundColor(bgColor);


                        } catch (Exception n) {

                        }

                    }
                });
            } catch (Exception b) {
                Picasso.with(c).load(R.drawable.placeholder).into(img);
            }


        } else {
            img.setVisibility(View.INVISIBLE);

        }
    }
}
