package com.cs407.ut;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cs407.ut.adapter.BindAdapter;
import com.cs407.ut.databinding.ItemMessageBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageListActivity extends AppCompatActivity {
    private BindAdapter<ItemMessageBinding, com.cs407.ut.bean.Message> adapter = new BindAdapter<ItemMessageBinding, com.cs407.ut.bean.Message>() {
        @Override
        public ItemMessageBinding createHolder(ViewGroup parent) {
            return ItemMessageBinding.inflate(getLayoutInflater(), parent, false);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void bind(ItemMessageBinding item, com.cs407.ut.bean.Message message, int position) {

            if (message.userid == Database.get(MessageListActivity.this).getLoginUserId()) {
                item.left.setVisibility(View.GONE);
                item.tvRightName.setText(message.username);
                item.right.setVisibility(View.VISIBLE);
                item.tvMessage.setText(message.content);

            } else {
                item.tvLeftName.setText(message.username);
                item.right.setVisibility(View.GONE);
                item.left.setVisibility(View.VISIBLE);
                item.tvMessage1.setText(message.content);

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        int chatId = getIntent().getIntExtra("chatId", 0);
        RecyclerView rvMessage = findViewById(R.id.rv_message);
        adapter.getData().addAll(Database.get(this).getMessage(chatId));
        rvMessage.setAdapter(adapter);

        findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etMessage = findViewById(R.id.et_message);
                String content = etMessage.getText().toString();
                if (content.isEmpty()) return;
                com.cs407.ut.bean.Message message = new com.cs407.ut.bean.Message();
                message.userid = Database.get(MessageListActivity.this).getLoginUserId();
                message.chatId = chatId;
                message.username = "Me";
                message.content = content;
                Database.get(MessageListActivity.this)
                        .addMessage(message);
                adapter.getData().add(message);
                adapter.notifyItemInserted(adapter.getData().size() - 1);
                etMessage.setText("");
            }
        });
    }
}