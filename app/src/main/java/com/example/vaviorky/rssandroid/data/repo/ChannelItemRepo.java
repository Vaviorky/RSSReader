package com.example.vaviorky.rssandroid.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vaviorky.rssandroid.data.DatabaseManager;
import com.example.vaviorky.rssandroid.data.model.ChannelItem;
import com.example.vaviorky.rssandroid.data.model.RSSChannel;

/**
 * Created by Vaviorky on 04.01.2017.
 */

public class ChannelItemRepo {
    private static ChannelItem item;
    private final String TAG = ChannelItemRepo.class.getSimpleName();

    public ChannelItemRepo() {
        item = new ChannelItem();
    }

    public static String CreateTable() {
        Log.d(ChannelItem.TAG, "CreateTable() called");
        return "create table " + ChannelItem.TABLE + "("
                + ChannelItem.KEY_ItemId + " INTEGER primary key autoincrement, "
                + ChannelItem.KEY_Name + " text not null,"
                + ChannelItem.KEY_Description + " text not null,"
                + ChannelItem.KEY_Link + " text not null,"
                + ChannelItem.KEY_Date + " text not null,"
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
        values.put(ChannelItem.KEY_ChannelId, channelItem.getChannel().getChannelId());
        long result = db.insert(ChannelItem.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return result != -1;
    }

    public Cursor getData(int channelId) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor res = db.rawQuery("select * from " + ChannelItem.TABLE + " where ChannelId =" + channelId, null);
        DatabaseManager.getInstance().closeDatabase();
        return res;
    }

}
