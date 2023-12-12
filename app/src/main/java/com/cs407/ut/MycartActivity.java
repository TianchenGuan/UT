package com.cs407.ut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MycartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ItemDataClass> dataList;
    AccountAdapter adapter;
    private boolean isEditMode = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();
        adapter = new AccountAdapter(dataList, this, item -> {});
        recyclerView.setAdapter(adapter);

        adapter = new AccountAdapter(dataList, this, new AccountAdapter.OnItemRemoveListener() {
            @Override
            public void onItemRemoved(ItemDataClass item) {
                SharedPreferences prefs = getSharedPreferences("addItems", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove(item.getItmeName());
                editor.apply();
            }
        });
        recyclerView.setAdapter(adapter);

        loadAddedItems();

        Button cleanButton = findViewById(R.id.button_clean_all);
        cleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAddedItems();
                loadAddedItems();
            }
        });

        Button payButton = findViewById(R.id.button_pay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double totalPrice = calculateTotalPrice();
                Intent intent = new Intent(MycartActivity.this, Payment.class);
                intent.putExtra("totalPrice", totalPrice);
                startActivity(intent);
            }
        });


        Button editButton = findViewById(R.id.button_edit);
        editButton.setOnClickListener(view -> {
            isEditMode = !isEditMode;
            adapter.setEditMode(isEditMode);
            editButton.setText(isEditMode ? "Done" : "Edit");
        });


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
    private void loadAddedItems() {
        SharedPreferences prefs = getSharedPreferences("addItems", MODE_PRIVATE);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Images");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ItemDataClass> allItems = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ItemDataClass item = snapshot.getValue(ItemDataClass.class);
                        if (item != null) {
                            allItems.add(item);
                        }
                    }
                }
                filterAndDisplayAddedItems(allItems, prefs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MycartActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void filterAndDisplayAddedItems(List<ItemDataClass> allItems, SharedPreferences prefs) {
        dataList.clear();
        for (ItemDataClass item : allItems) {
            if (prefs.contains(item.getItmeName()) && prefs.getAll().get(item.getItmeName()) instanceof Boolean) {
                boolean isAdded = prefs.getBoolean(item.getItmeName(), false);
                if (isAdded) {
                    dataList.add(item);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void clearAddedItems() {
        SharedPreferences prefs = getSharedPreferences("addItems", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        dataList.clear();
        adapter.notifyDataSetChanged();
    }

    private double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (ItemDataClass item : dataList) {
            totalPrice += Double.parseDouble(item.getItemPrice());
        }
        return totalPrice;
    }

}

