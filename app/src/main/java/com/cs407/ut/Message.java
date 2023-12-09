package com.cs407.ut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cs407.ut.adapter.BindAdapter;
import com.cs407.ut.bean.Chat;
import com.cs407.ut.databinding.ItemChatBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Date;
import java.util.List;

public class Message extends AppCompatActivity {
    private BindAdapter<ItemChatBinding, Chat> adapter = new BindAdapter<ItemChatBinding, Chat>() {
        @Override
        public ItemChatBinding createHolder(ViewGroup parent) {
            return ItemChatBinding.inflate(getLayoutInflater(), parent, false);
        }

        @Override
        public void bind(ItemChatBinding item, Chat data, int position) {
            if (Database.get(Message.this).getLoginUserId() == data.userid1) {
                item.tvName.setText(data.username2);
            } else {
                item.tvName.setText(data.username1);
            }
            item.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Message.this, MessageListActivity.class);
                    intent.putExtra("chatId", data.id);
                    startActivity(intent);
                }
            });
            item.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(Message.this)
                            .setMessage("Are you sure you want to delete this session")
                            .setPositiveButton("SURE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Database.get(Message.this)
                                            .deleteChat(data.id);
                                    adapter.getData().remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            }).show();
                    return true;
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        RecyclerView rvChat = findViewById(R.id.messageListView);
        List<Chat> chat = Database.get(this).getChat(Database.get(this).getLoginUserId());
        adapter.getData().addAll(chat);
        rvChat.setAdapter(adapter);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.bottom_message);

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
                // No action needed if already on message
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

}
