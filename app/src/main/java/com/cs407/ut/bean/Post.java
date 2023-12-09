package com.cs407.ut.bean;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

public class Post {
    public int id;
    public int userid;
    public String username;
    public String title;
    public String type;
    public String image;
    public String time;

    public Post(){}
    @SuppressLint("Range")
    public Post(Cursor cursor){
        id = cursor.getInt(cursor.getColumnIndex("id"));
        userid = cursor.getInt(cursor.getColumnIndex("userid"));
        title = cursor.getString(cursor.getColumnIndex("title"));
        type = cursor.getString(cursor.getColumnIndex("type"));
        image = cursor.getString(cursor.getColumnIndex("image"));
        time = cursor.getString(cursor.getColumnIndex("time"));
        username = cursor.getString(cursor.getColumnIndex("username"));
    }
}
