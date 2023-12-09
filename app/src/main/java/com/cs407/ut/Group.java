package com.cs407.ut;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cs407.ut.adapter.BindAdapter;
import com.cs407.ut.adapter.LeftListAdapter;
import com.cs407.ut.bean.Post;
import com.cs407.ut.databinding.ItemPostBinding;
import com.cs407.ut.databinding.LeftListItemBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Group extends AppCompatActivity {

    private View rootView;
    private RecyclerView rvPost;
    private LeftListAdapter mLeftListAdapter;
    private int index = 0;

    private BindAdapter<LeftListItemBinding, String> adapter = new BindAdapter<LeftListItemBinding, String>() {
        @Override
        public LeftListItemBinding createHolder(ViewGroup parent) {
            return LeftListItemBinding.inflate(getLayoutInflater(), parent, false);
        }

        @Override
        public void bind(LeftListItemBinding item, String data, int position) {
            item.name.setText(data);
            if (index == position) {
                item.getRoot().setBackgroundColor(Color.parseColor("#f6f6f6"));
            } else {
                item.getRoot().setBackgroundColor(Color.parseColor("#ffffff"));
            }
            item.getRoot().setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    index = position;
                    notifyDataSetChanged();
                    getPostData();
                }
            });
        }
    };

    @SuppressLint("NotifyDataSetChanged")
    private void getPostData() {
        postAdapter.getData().clear();
        postAdapter.getData().addAll(Database.get(this).getPost(adapter.getData().get(index)));
        postAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPostData();
    }

    private BindAdapter<ItemPostBinding, Post> postAdapter = new BindAdapter<ItemPostBinding, Post>() {
        @Override
        public ItemPostBinding createHolder(ViewGroup parent) {
            return ItemPostBinding.inflate(getLayoutInflater(), parent, false);
        }

        @Override
        public void bind(ItemPostBinding item, Post data, int position) {
            item.tvName.setText(data.username);
            item.tvContent.setText(data.title);
            item.tvDate.setText(data.time);
            Glide.with(item.ivAvatar).load(data.image).into(item.ivImage);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        RecyclerView groupList = findViewById(R.id.group_list);
        adapter.getData().addAll(Arrays.asList(getResources().getStringArray(R.array.type)));
        groupList.setAdapter(adapter);

        rvPost = findViewById(R.id.rv_post);
        rvPost.setAdapter(postAdapter);
//        groupList.setAdapter();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.bottom_group);
        MaterialToolbar materialToolbar = findViewById(R.id.homepage_title);
        materialToolbar.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                MenuItem item = menu.add(0, 0, 0, "Publish");
                item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                startActivity(new Intent(Group.this, PostActivity.class));
                return true;
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            String title = item.getTitle().toString();

            if (title.equals("Home")) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.silde_in_right, R.anim.silde_out_left);
                finish();
                return true;
            } else if (title.equals("Group")) {

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
                startActivity(new Intent(getApplicationContext(), Account.class));
                overridePendingTransition(R.anim.silde_in_right, R.anim.silde_out_left);
                finish();
                return true;
            }

            return false;
        });

    }

}
