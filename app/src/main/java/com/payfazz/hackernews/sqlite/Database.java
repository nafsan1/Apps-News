package com.payfazz.hackernews.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.payfazz.hackernews.model.Model;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    final static String DB_NAME = "db_news";
    private SQLiteDatabase database;

    public Database(Context context) {
        super(context, DB_NAME, null, 1);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS tbl_news(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "by_news TEXT, " + //1
                "id_news INTEGER, " + //2
                "time_news INTEGER, " + //3
                "title_news TEXT )"; //4
        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS tbl_hero");
        onCreate(db);
    }


    public List<Model> getNews() {

        List<Model> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_news", null);


        if (cursor.moveToFirst()) {
            do {
                Model data = new Model();
                data.setBy(cursor.getString(1));
                data.setId(cursor.getInt(2));
                data.setTime(cursor.getInt(3));
                data.setTitle(cursor.getString(4));
                // data.setPicture(cursor.getInt(1));
                // data.setFavourite(cursor.getString(2));

                list.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.e("TAG", cursor.getCount() + "");
        return list;
    }
    public void deleteData(int id_news) {


        String sql = "DELETE FROM tbl_news WHERE id_news " + "= '" + id_news + "'";
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(sql);
    }
    public Cursor getData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public void queryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertNews(String by_news, int id_news, int time_news, String title) {
        String sql = "INSERT INTO tbl_news (id, by_news,id_news,time_news,title_news ) VALUES (NULL, ? , ? , ? ,?)";
        SQLiteDatabase db = getReadableDatabase();
        SQLiteStatement statement = db.compileStatement(sql);
        //statement.clearBindings();

        statement.bindString(1, by_news);
        statement.bindLong(2, id_news);
        statement.bindLong(3, time_news);
        statement.bindString(4, title);

        statement.executeInsert();
        db.close();
    }

}