package com.cs407.ut.bean;

import android.annotation.SuppressLint;
import android.database.Cursor;

public class Chat {
    public String username1;
    public String username2;
    public int id;
    public int userid1;
    public int userid2;

    public Chat(){}
    @SuppressLint("Range")
    public Chat(Cursor cursor){
        id = cursor.getInt(cursor.getColumnIndex("id"));
        userid1 = cursor.getInt(cursor.getColumnIndex("userid1"));
        userid2 = cursor.getInt(cursor.getColumnIndex("userid2"));
        username1 = cursor.getString(cursor.getColumnIndex("username1"));
        username2 = cursor.getString(cursor.getColumnIndex("username2"));
    }
}
