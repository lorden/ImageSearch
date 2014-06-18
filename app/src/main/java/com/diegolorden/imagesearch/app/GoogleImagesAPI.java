package com.diegolorden.imagesearch.app;

import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleImagesAPI extends AsyncHttpClient {

    private String root = "https://ajax.googleapis.com/ajax/services/search/images";

    public void search(String query, GoogleImagesAPIFilters filters, final int page, final ArrayAdapter results) {
        String params = "rsz=8&v=1.0&start=" + (page * 8) + "&q=" + Uri.encode(query);
        if (!filters.toString().equals("")) {
            params += "&" + filters.toString();
        }
        String full_url = root + "?" + params;

        Log.d("FULL_URL", full_url);
        this.get(full_url,  new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(JSONObject response) {
                JSONArray imageJsonResults = null;
                try {
                    imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
                    if (page == 0) {
                        results.clear();
                    }
                    results.addAll(ImageResult.fromJSONArray(imageJsonResults));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Throwable t, JSONObject response) {
                // TODO
            }
        });
    }
}
