package com.diegolorden.imagesearch.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> {

    private ArrayList<ImageResult> images;
    private SearchActivity context;

    public ImageResultArrayAdapter(Context context, ArrayList<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
        this.context = (SearchActivity) context;
        this.images = images;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageResult imageInfo = this.getItem(position);
        final ImageView ivImage;
        if (convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            ivImage = (ImageView) inflator.inflate(R.layout.item_image_result, parent, false);
        } else {
            ivImage = (ImageView) convertView;
            ivImage.setImageResource(android.R.color.transparent);
        }
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.openImage(position);
            }
        });

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.loadImage(imageInfo.getThumbnailUrl(), new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage){
                ivImage.setImageBitmap(loadedImage);
            }
        });

        return ivImage;
    }

    @Override
    public ImageResult getItem(int position) {
        return images.get(position);
    }
}
