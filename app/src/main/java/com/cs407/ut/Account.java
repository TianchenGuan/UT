package com.cs407.ut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // My Post
        Button myPostButton = findViewById(R.id.account_myPost);
        myPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this, AccountMyPost.class);
                startActivity(intent);
            }
        });

        // Purchases
        Button purchaseButton = findViewById(R.id.account_purchases);
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this, Purchase.class);
                startActivity(intent);
            }
        });

        // Sell
        Button sellButton = findViewById(R.id.account_selling);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this, Sell.class);
                startActivity(intent);
            }
        });

        //Help
        Button helpButton = findViewById(R.id.account_help);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this, Help.class);
                startActivity(intent);
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.bottom_account);

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
                startActivity(new Intent(getApplicationContext(), AddItem.class));
                overridePendingTransition(R.anim.silde_in_right, R.anim.silde_out_left);
                finish();
                return true;
            } else if (title.equals("Message")) {
                startActivity(new Intent(getApplicationContext(), Message.class));
                overridePendingTransition(R.anim.silde_in_right, R.anim.silde_out_left);
                finish();
                return true;
            } else if (title.equals("Account")) {
                // No action needed if already on Account
                return true;
            }

            return false;
        });
    }

}
