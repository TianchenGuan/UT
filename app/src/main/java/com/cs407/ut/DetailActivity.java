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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Button saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(v -> {
            saveItem();
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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("title");
            String price = extras.getString("price");
            String description = extras.getString("description");

            titleView.setText(title);
            priceView.setText(price);
            descriptionView.setText(description);
        }
    }

    private void saveItem() {
        String title = getIntent().getStringExtra("title");

        SharedPreferences sharedPreferences = getSharedPreferences("SavedItems", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(title, title);
        editor.apply();

        Toast.makeText(this, "Item saved", Toast.LENGTH_SHORT).show();
    }
}

