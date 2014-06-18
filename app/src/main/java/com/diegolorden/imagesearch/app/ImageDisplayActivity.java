package com.diegolorden.imagesearch.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


public class ImageDisplayActivity extends Activity {

    ImageView ivFullImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        ivFullImage = (TouchImageView) findViewById(R.id.ivFullImage);
        getActionBar().hide();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.loadImage(getIntent().getStringExtra(SearchActivity.IMG_URL_KEY), new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage){
                ivFullImage.setImageBitmap(loadedImage);
            }
        });
    }

}
