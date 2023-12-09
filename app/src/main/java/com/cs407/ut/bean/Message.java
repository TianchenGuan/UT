package com.cs407.ut.bean;

import android.annotation.SuppressLint;
import android.database.Cursor;

public class Message {
    public String username;
    public String content;
    public int id;
    public int userid;
    public int chatId;

    public Message() {
    }

    @SuppressLint("Range")
    public Message(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex("id"));
        userid = cursor.getInt(cursor.getColumnIndex("userid"));
        chatId = cursor.getInt(cursor.getColumnIndex("chatId"));
        username = cursor.getString(cursor.getColumnIndex("username"));
        content = cursor.getString(cursor.getColumnIndex("content"));

    }
}
