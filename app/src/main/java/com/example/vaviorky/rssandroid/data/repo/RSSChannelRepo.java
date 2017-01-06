package com.example.vaviorky.rssandroid.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vaviorky.rssandroid.data.DBHelper;
import com.example.vaviorky.rssandroid.data.DatabaseManager;
import com.example.vaviorky.rssandroid.data.model.RSSChannel;

import java.util.ArrayList;


/**
 * Created by Vaviorky on 04.01.2017.
 */

public class RSSChannelRepo {
    private DBHelper helper;

    public RSSChannelRepo(DBHelper helper) {
        this.helper = helper;
    }

    public static String CreateTable() {
        Log.d(RSSChannelRepo.class.getSimpleName(), "CreateTable: RSSChannel");
        return "CREATE TABLE  " + RSSChannel.TABLE + " ("
                + RSSChannel.KEY_ChannelId + " INTEGER primary key autoincrement, "
                + RSSChannel.KEY_Name + " text,"
                + RSSChannel.KEY_Link + " text)";
    }

    public int Insert(RSSChannel rssChannel) {
        int itemchannelId;
        DatabaseManager.initializeInstance(helper);
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        if (ifExists(rssChannel, db)) {
            return -2;
        }

        ContentValues values = new ContentValues();
        values.put(RSSChannel.KEY_Name, rssChannel.getName());
        values.put(RSSChannel.KEY_Link, rssChannel.getLink());

        itemchannelId = (int) db.insert(RSSChannel.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return itemchannelId;
    }

    public int Delete(RSSChannel channel) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        int delete = db.delete(RSSChannel.TABLE, "id = ?", new String[]{Integer.toString(channel.getChannelId())});
        DatabaseManager.getInstance().closeDatabase();
        return delete;
    }

    public ArrayList<RSSChannel> GetAll() {
        ArrayList<RSSChannel> list = new ArrayList<>();
        DatabaseManager.initializeInstance(helper);
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "Select  * from " + RSSChannel.TABLE;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            RSSChannel channel = new RSSChannel();
            channel.setChannelId(cursor.getInt(0));
            channel.setName(cursor.getString(1));
            channel.setLink(cursor.getString(2));
            list.add(channel);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    private boolean ifExists(RSSChannel channel, SQLiteDatabase db) {
        String query = "Select * from " + RSSChannel.TABLE + " where link=\"" + channel.getLink() + "\";";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }

    public RSSChannel getById(int channelId) {
        RSSChannel channel = new RSSChannel();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "Select * from " + RSSChannel.TABLE + " where ChannelId = " + channelId;
        Cursor cursor = db.rawQuery(query, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            channel.setChannelId(cursor.getInt(1));
            channel.setName(cursor.getString(1));
            channel.setLink(cursor.getString(2));
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return channel;
    }

}
