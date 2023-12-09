package com.cs407.ut.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.cs407.ut.R;

public class PostCategoryAdapter {

    private Spinner categorySpinner;
    private Context context;

    public PostCategoryAdapter(Context context, Spinner categorySpinner) {
        this.context = context;
        this.categorySpinner = categorySpinner;
        setupAdapter();
    }

    private void setupAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }
}
