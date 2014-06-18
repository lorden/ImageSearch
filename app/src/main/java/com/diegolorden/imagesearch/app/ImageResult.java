package com.diegolorden.imagesearch.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ImageResult implements Serializable {
    private String url;
    private String thumbnailUrl;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();
        for(int i = 0; i < array.length(); i++){
            try {
                results.add(ImageResult.fromJSON(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    private static ImageResult fromJSON(JSONObject json) {
        ImageResult result = new ImageResult();
        try {
            result.setUrl(json.getString("url"));
            result.setThumbnailUrl(json.getString("tbUrl"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
