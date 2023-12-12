package com.cs407.ut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
public class DetailActivity extends AppCompatActivity {
    private ItemDataClass currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        currentItem = new ItemDataClass();

        Button saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(v -> {
            saveItem(currentItem);
        });

        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView titleView = findViewById(R.id.title);
        TextView priceView = findViewById(R.id.price);
        TextView descriptionView = findViewById(R.id.description);
        TextView catView = findViewById(R.id.category);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("title");
            String price = extras.getString("price");
            String description = extras.getString("description");
            String category = extras.getString("category");

            currentItem.setItmeName(title);
            currentItem.setItemPrice(price);
            currentItem.setItemDescriptions(description);
            currentItem.setItemCategory(category);

            titleView.setText(title);
            priceView.setText(price);
            descriptionView.setText(description);
            catView.setText(category);
        }
    }

    private void saveItem(ItemDataClass item) {
        if (item != null && item.getItmeName() != null && !item.getItmeName().isEmpty()) {
            SharedPreferences prefs = getSharedPreferences("SavedItems", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(item.getItmeName(), true); // Use item name as key
            editor.apply();

            Toast.makeText(this, item.getItmeName() + " saved!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error: Item is not available", Toast.LENGTH_SHORT).show();
        }
    }
}

