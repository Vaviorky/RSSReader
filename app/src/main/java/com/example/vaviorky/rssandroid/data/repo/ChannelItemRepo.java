package com.example.vaviorky.rssandroid.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vaviorky.rssandroid.data.DBHelper;
import com.example.vaviorky.rssandroid.data.DatabaseManager;
import com.example.vaviorky.rssandroid.data.model.ChannelItem;
import com.example.vaviorky.rssandroid.data.model.RSSChannel;

import java.util.ArrayList;

/**
 * Created by Vaviorky on 04.01.2017.
 */

public class ChannelItemRepo {
    private static ChannelItem item;
    private final String TAG = ChannelItemRepo.class.getSimpleName();
    private DBHelper helper;

    public ChannelItemRepo(DBHelper helper) {
        item = new ChannelItem();
        this.helper = helper;
    }

    public static String CreateTable() {
        Log.d(ChannelItem.TAG, "CreateTable() called");
        return "create table " + ChannelItem.TABLE + "("
                + ChannelItem.KEY_ItemId + " INTEGER primary key autoincrement, "
                + ChannelItem.KEY_Name + " text not null,"
                + ChannelItem.KEY_Description + " text not null,"
                + ChannelItem.KEY_Link + " text not null,"
                + ChannelItem.KEY_Date + " text not null,"
                + ChannelItem.KEY_Author + " text not null,"
                + ChannelItem.KEY_ChannelId + " integer not null,"
                + ChannelItem.KEY_ThumbnailURL + " text not null,"
                + " foreign key (" + ChannelItem.KEY_ChannelId + ") references " + RSSChannel.TABLE + "(" + RSSChannel.KEY_ChannelId + "));";

    }

    public boolean Insert(ChannelItem channelItem) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(ChannelItem.KEY_Name, channelItem.getName());
        values.put(ChannelItem.KEY_Description, channelItem.getDescription());
        values.put(ChannelItem.KEY_Link, channelItem.getLink());
        values.put(ChannelItem.KEY_Date, channelItem.getPubDate());
        values.put(ChannelItem.KEY_ChannelId, channelItem.getChannelId());
        values.put(ChannelItem.KEY_ThumbnailURL, channelItem.getThumbnailURL());
        long result = db.insert(ChannelItem.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return result != -1;
    }

    public ArrayList<ChannelItem> getData(int channelId) {
        ArrayList<ChannelItem> items = new ArrayList<>();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from " + ChannelItem.TABLE + " where ChannelId = " + channelId, null);
        DatabaseManager.getInstance().closeDatabase();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            ChannelItem newsitem = new ChannelItem();
            newsitem.setChannelId(channelId);
            newsitem.setName(cursor.getString(1));
            newsitem.setDescription(cursor.getString(2));
            newsitem.setLink(cursor.getString(3));
            newsitem.setPubDate(cursor.getLong(4));
            newsitem.setThumbnailURL(cursor.getString(6));
            items.add(newsitem);
        }
        cursor.close();
        return items;
    }

    public void deleteAll() {

    }

}
