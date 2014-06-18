package com.diegolorden.imagesearch.app;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class FiltersFragment extends DialogFragment {

    private Spinner spImageType;
    private Spinner spImageColor;
    private Spinner spImageSize;
    private EditText etImageSite;

    public FiltersFragment() {
        // Required empty public constructor
    }

    public static FiltersFragment newInstance(GoogleImagesAPIFilters filters) {
        FiltersFragment filtersFragment = new FiltersFragment();
        Bundle args = new Bundle();
        args.putString("title", "Filters");
        filtersFragment.setArguments(args);
        return filtersFragment;
    }

    @Override                           
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filters, container, false);
        String title = getArguments().getString("title");
        getDialog().setTitle(title);
        // getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("ImageSearchPreferences", 0);
        final SharedPreferences.Editor editor = pref.edit();

        spImageType = (Spinner) view.findViewById(R.id.spImageType);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.image_types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageType.setAdapter(typeAdapter);
        int imageTypePosition = typeAdapter.getPosition(pref.getString("imageType", "Any"));
        spImageType.setSelection(imageTypePosition);

        spImageColor = (Spinner) view.findViewById(R.id.spImageColor);
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.image_colors, android.R.layout.simple_spinner_item);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageColor.setAdapter(colorAdapter);
        int imageColorPosition = colorAdapter.getPosition(pref.getString("imageColor", "Any"));
        spImageColor.setSelection(imageColorPosition);

        spImageSize = (Spinner) view.findViewById(R.id.spImageSize);
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.image_sizes, android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageSize.setAdapter(sizeAdapter);
        int imageSizePosition = sizeAdapter.getPosition(pref.getString("imageSize", "Any"));
        spImageSize.setSelection(imageSizePosition);

        etImageSite = (EditText) view.findViewById(R.id.etImageSite);
        etImageSite.setText(pref.getString("imageSite", ""));

        Button btnSaveFilters = (Button) view.findViewById(R.id.btnSaveFilters);
        btnSaveFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedType = (String) spImageType.getSelectedItem();
                String selectedColor = (String) spImageColor.getSelectedItem();
                String selectedSize = (String) spImageSize.getSelectedItem();
                String selectedSite = etImageSite.getText().toString();

                // Store filters
                editor.putString("imageType", selectedType);
                editor.putString("imageColor", selectedColor);
                editor.putString("imageSize", selectedSize);
                editor.putString("imageSite", selectedSite);
                editor.commit();

                // Perform search
                SearchActivity act = (SearchActivity) getActivity();
                act.searchFilters = new GoogleImagesAPIFilters();
                act.searchFilters.imageType = selectedType;
                act.searchFilters.imageColor = selectedColor;
                act.searchFilters.imageSize = selectedSize;
                act.searchFilters.imageSite = selectedSite;
                act.search();
                dismiss();
            }
        });

        Button btnClearFilters = (Button) view.findViewById(R.id.btnClearFilters);
        btnClearFilters.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Clear preferences
                editor.putString("imageType", "Any");
                editor.putString("imageColor", "Any");
                editor.putString("imageSize", "Any");
                editor.putString("imageSite", "");
                editor.commit();

                // Perform search
                SearchActivity act = (SearchActivity) getActivity();
                act.searchFilters = new GoogleImagesAPIFilters();
                act.search();
                dismiss();

            }
        });

        return view;
    }

}
