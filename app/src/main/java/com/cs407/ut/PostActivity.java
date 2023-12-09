package com.cs407.ut;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cs407.ut.databinding.ActivityAddItemBinding;
import com.cs407.ut.databinding.ActivityPostBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPostBinding binding = ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.customProductPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                textView.setTypeface(getResources().getFont(R.font.luckiestguy));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.addItemBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.customProductSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                标题+分类+图片
                String title = binding.customProductName.getText().toString();
                String type = binding.customProductPrice.getSelectedItem().toString();
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis());
                if (title.isEmpty()) {
                    Toast.makeText(PostActivity.this, "请输入标题", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (image.isEmpty()) {
                    Toast.makeText(PostActivity.this, "请传入图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                Database.get(PostActivity.this).addPost(title, type, date, image, "张三", 0);
                startActivity(new Intent(PostActivity.this, Group.class));
                finish();
            }
        });
        binding.customProductImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), 100);
            }
        });
    }

    private String image = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            image = getImagePath(this, data.getData());
        }
    }

    public static String getImagePath(Context context, Uri uri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            File file = new File(context.getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
            inputStream.close();
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
