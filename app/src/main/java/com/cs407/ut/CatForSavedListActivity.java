package com.cs407.ut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CatForSavedListActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_savelist);

        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadAndCategorizeSavedItems();



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            String title = item.getTitle().toString();

            if (title.equals("Home")) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.silde_in_right, R.anim.silde_out_left);
                finish();
                return true;
            } else if (title.equals("Group")) {
                startActivity(new Intent(getApplicationContext(), Group.class));
                overridePendingTransition(R.anim.silde_in_right, R.anim.silde_out_left);
                finish();
                return true;
            } else if (title.equals("Add")) {
                startActivity(new Intent(getApplicationContext(), PostActivity.class));
                overridePendingTransition(R.anim.silde_in_right, R.anim.silde_out_left);
                finish();
                return true;
            } else if (title.equals("Message")) {
                startActivity(new Intent(getApplicationContext(), Message.class));
                overridePendingTransition(R.anim.silde_in_right, R.anim.silde_out_left);
                finish();
                return true;
            } else if (title.equals("Account")) {
                startActivity(new Intent(getApplicationContext(), Account.class));
                overridePendingTransition(R.anim.silde_in_right, R.anim.silde_out_left);
                finish();
                return true;
            }

            return false;
        });

    }

    private void loadAndCategorizeSavedItems() {
        SharedPreferences prefs = getSharedPreferences("SavedItems", MODE_PRIVATE);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Images");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, List<ItemDataClass>> categorizedItems = new HashMap<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ItemDataClass item = snapshot.getValue(ItemDataClass.class);
                        if (item != null && prefs.getBoolean(item.getItmeName(), false)) {
                            categorizedItems.computeIfAbsent(item.getItemCategory(), k -> new ArrayList<>()).add(item);
                        }
                    }
                }
                displayCategorizedItems(categorizedItems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CatForSavedListActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayCategorizedItems(Map<String, List<ItemDataClass>> categorizedItems) {
        LinearLayout layout = findViewById(R.id.categoryLayout);

        for (Map.Entry<String, List<ItemDataClass>> entry : categorizedItems.entrySet()) {
            TextView categoryTitle = new TextView(this);
            categoryTitle.setText(entry.getKey() + ": ");
            categoryTitle.setTextSize(25);
            categoryTitle.setTypeface(null, Typeface.BOLD);
            layout.addView(categoryTitle);

            for (ItemDataClass item : entry.getValue()) {
                TextView itemView = new TextView(this);
                itemView.setText(item.getItmeName());
                itemView.setTextSize(20);
                layout.addView(itemView);
            }
        }
    }

}

