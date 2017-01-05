package com.example.vaviorky.rssandroid.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vaviorky.rssandroid.data.model.ChannelItem;
import com.example.vaviorky.rssandroid.data.model.RSSChannel;
import com.example.vaviorky.rssandroid.data.repo.RSSChannelRepo;

import org.w3c.dom.Document;

import java.util.ArrayList;

/**
 * Created by Vaviorky on 04.01.2017.
 */

public class RefreshDataInDatabase {
    private RSSChannelRepo channelDAO;
    private Context context;

    public RefreshDataInDatabase(Context context) {
        this.context = context;
        DBHelper helper = new DBHelper(context);
        channelDAO = new RSSChannelRepo(helper);
    }

    public void Refresh() {
        Cursor cursor = null;
        RSSParser parser = new RSSParser();
        DBHelper helper = new DBHelper(context);
        DatabaseManager.initializeInstance(helper);
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ArrayList<RSSChannel> channels = channelDAO.GetAll();
        String query = "select * from ChannelItem where ChannelId=?";
        Log.d(this.getClass().getSimpleName(), "Refresh: before for");
        for (RSSChannel channel : channels) {
            Log.d(this.getClass().getSimpleName(), channel.getName());
            Boolean isPresent = false;
            Document document = parser.GetData(channel.getLink());
            ArrayList<ChannelItem> items = parser.ItemsInChannel(document);

            for (ChannelItem it : items) {

                if (!ifExists(it.getPubDate(), db)) {
                    Log.d(this.getClass().getSimpleName(), "jest wpis w bazie!");
                } else {
                    Log.d(this.getClass().getSimpleName(), "nie ma wpisu w bazie");
                }

                /*try {
                    cursor = db.rawQuery(query, new String[]{channel.getChannelId() + ""});
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                    }
                } finally {
                    cursor.close();
                }*/
            }
        }
        DatabaseManager.getInstance().closeDatabase();
    }

    private boolean ifExists(String date, SQLiteDatabase db) {
        String query = "Select * from " + ChannelItem.TABLE + " where date = " + date;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }
}