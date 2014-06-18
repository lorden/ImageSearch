package com.diegolorden.imagesearch.app;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.SearchView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    GridView gvResults;
    ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
    ImageResultArrayAdapter imageAdapter;
    GoogleImagesAPIFilters searchFilters = new GoogleImagesAPIFilters();
    int page;
    String query;
    GoogleImagesAPI api;
    static String IMG_URL_KEY = "img_url_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        getWindow().setWindowAnimations(0);
        Intent intent = getIntent();
        imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("ImageSearchPreferences", 0);
        searchFilters.imageType = pref.getString("imageType", "");
        searchFilters.imageColor = pref.getString("imageColor", "");
        searchFilters.imageSize = pref.getString("imageSize", "");
        searchFilters.imageSite = pref.getString("imageSite", "");


        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            GoogleImagesAPI api = new GoogleImagesAPI();
            query = intent.getStringExtra(SearchManager.QUERY);
            api.search(query, searchFilters, 0, imageAdapter);
        } else {
            Log.d("ImageLoader", "Initializing");
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                    .defaultDisplayImageOptions(options)
                    .build();
            ImageLoader.getInstance().init(config);
        }

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                GoogleImagesAPI api = new GoogleImagesAPI();
                api.search(query, searchFilters, page, imageAdapter);
            }
        });

    }

    public void openImage(int position){
        Intent i = new Intent(this, ImageDisplayActivity.class);
        i.putExtra(IMG_URL_KEY, imageAdapter.getItem(position).getUrl());
        startActivity(i);
    }

    public void setupViews(){
        gvResults = (GridView) findViewById(R.id.gvResults);
    }

    public void showFilters(MenuItem mi) {
        FragmentManager fm = getFragmentManager();
        FiltersFragment filtersFragment = FiltersFragment.newInstance(new GoogleImagesAPIFilters());
        filtersFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    public void search() {
        if (query != null) {
            GoogleImagesAPI api = new GoogleImagesAPI();
            api.search(query, searchFilters, 0, imageAdapter);
        }
    }
}
