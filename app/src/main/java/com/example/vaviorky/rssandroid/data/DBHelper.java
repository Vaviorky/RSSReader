package com.example.vaviorky.rssandroid.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.vaviorky.rssandroid.data.model.ChannelItem;
import com.example.vaviorky.rssandroid.data.model.RSSChannel;
import com.example.vaviorky.rssandroid.data.repo.ChannelItemRepo;
import com.example.vaviorky.rssandroid.data.repo.RSSChannelRepo;

/**
 * Created by Vaviorky on 04.01.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "rss.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TAG = DBHelper.class.getSimpleName();

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(RSSChannelRepo.CreateTable());
            Log.d(TAG, "onCreate: rsschannelrepo");
            db.execSQL(ChannelItemRepo.CreateTable());
            Log.d(TAG, "onCreate: channelitemrepo");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + ChannelItem.TABLE);
        db.execSQL("drop table if exists " + RSSChannel.TABLE);
        onCreate(db);
    }
}
