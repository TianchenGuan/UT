package com.cs407.ut;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.cs407.ut.bean.Chat;
import com.cs407.ut.bean.Message;
import com.cs407.ut.bean.Post;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context) {
        super(context, "database_", null, 1);
    }

    public static Database get(Context context) {
        return new Database(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists post(id integer primary key autoincrement,userid integer,title text,username text,type text,time text,image text)");
        db.execSQL("create table if not exists chat(id integer primary key autoincrement," + "userid1 integer,userid2 integer,username1 text,username2 text)");

        db.execSQL("create table if not exists message(id integer primary key autoincrement," + "userid integer,chatId integer,content text,username text)");

        db.execSQL("insert into chat(userid1,userid2,username1,username2) values(?,?,?,?)", new Object[]{0, 2, "Anna", "Bob"});
        db.execSQL("insert into chat(userid1,userid2,username1,username2) values(?,?,?,?)", new Object[]{0, 3, "Anna", "Cook"});
        db.execSQL("insert into chat(userid1,userid2,username1,username2) values(?,?,?,?)", new Object[]{0, 4, "Anna", "Daisy"});
        String insertSQL = "insert into post(userid,title,username,type,time,image) values(?,?,?,?,?,?)";
        db.execSQL(insertSQL,new Object[]{0,"And good friends saw a heavy snow, snow falling really beautiful!","Anna","Life","2023-12-09 22:13:00","https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2Ff3a049ed-64e7-47a5-a5f5-dbb6b37bfdcf%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1704723289&t=77d815488033ac732990a2391966f472"});
        db.execSQL(insertSQL,new Object[]{0,"I have some secondhand books, who needs","Anna","Second-hand","2023-12-09 22:13:00","https://ns-strategy.cdn.bcebos.com/ns-strategy/upload/fc_big_pic/part-00492-3391.jpg"});
        db.execSQL(insertSQL,new Object[]{0,"Xiaomi 14 is really good for taking pictures.","Anna","Electronic","2023-12-09 22:13:00","https://cdn.cnbj1.fds.api.mi-img.com/product-images/xiaomi14proy4y29r/index/28064.png?x-fds-process=image/resize,q_90,f_webp"});
        db.execSQL(insertSQL,new Object[]{0,"Wear today to share","Anna","Fashion","2023-12-09 22:13:00","https://img0.baidu.com/it/u=2472523372,2402192524&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=631"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addPost(String title, String type, String time, String image, String username, int userid) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("type", type);
        values.put("time", time);
        values.put("time", time);
        values.put("username", username);
        values.put("userid", userid);
        values.put("image", image);
        writableDatabase.insert("post", null, values);
    }

    public List<Post> getPost(String type) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor = writableDatabase.rawQuery("select * from post where type = ?", new String[]{type});
        ArrayList<Post> posts = new ArrayList<>();
        while (cursor.moveToNext()) {
            posts.add(new Post(cursor));
        }
        return posts;
    }

    public List<Message> getMessage(int chatId) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor = writableDatabase.rawQuery("select* from message where chatId = ?", new String[]{String.valueOf(chatId)});
        ArrayList<Message> messages = new ArrayList<>();
        while (cursor.moveToNext()) {
            messages.add(new Message(cursor));
        }
        return messages;
    }

    @SuppressLint("Range")
    public int getChat(String username1, int userid1, String username2, int userid2) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor = writableDatabase.rawQuery("select* from chat where (userid1 = ? and userid2 = ?) or(userid1 = ? and userid2 = ?)", new String[]{String.valueOf(userid1), String.valueOf(userid2), String.valueOf(userid2), String.valueOf(userid1)});
        if (cursor.moveToNext()) {
            return cursor.getInt(cursor.getColumnIndex("id"));
        }
        ContentValues values = new ContentValues();
        values.put("userid1", userid1);
        values.put("userid2", userid2);
        values.put("username1", username1);
        values.put("username2", username2);
        return (int) writableDatabase.insert("chat", null, values);
    }

    public List<Chat> getChat(int userid) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor = writableDatabase.rawQuery("select * from chat where userid1=? or userid2 = ?", new String[]{String.valueOf(userid), String.valueOf(userid)});
        ArrayList<Chat> chats = new ArrayList<>();
        while (cursor.moveToNext()) {
            chats.add(new Chat(cursor));
        }
        return chats;
    }

    public int getLoginUserId() {
        return 0;
    }

    public void addMessage(Message message) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("chatId", message.chatId);
        values.put("userid", message.userid);
        values.put("username", message.username);
        values.put("content", message.content);
        writableDatabase.insert("message", null, values);
    }

    public void deleteChat(int id) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete("chat", "id = ?", new String[]{String.valueOf(id)});
    }
}
