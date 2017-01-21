package com.example.vaviorky.rssandroid.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vaviorky.rssandroid.data.model.ChannelItem;
import com.example.vaviorky.rssandroid.data.model.RSSChannel;
import com.example.vaviorky.rssandroid.data.repo.ChannelItemRepo;
import com.example.vaviorky.rssandroid.data.repo.RSSChannelRepo;
import com.rometools.rome.io.FeedException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Vaviorky on 04.01.2017.
 */

public class RefreshDataInDatabase {
    private final String TAG = RefreshDataInDatabase.class.getSimpleName();
    private RSSChannelRepo channelDAO;
    private Context context;
    private ChannelItemRepo itemDAO;
    private RSSParser parser;
    public RefreshDataInDatabase(Context context) {
        this.context = context;
        DBHelper helper = new DBHelper(context);
        channelDAO = new RSSChannelRepo(helper);
        itemDAO = new ChannelItemRepo();
        parser = new RSSParser();
    }

    public void Refresh() throws ParseException, IOException, FeedException {
        DBHelper helper = new DBHelper(context);
        DatabaseManager.initializeInstance(helper);
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ArrayList<RSSChannel> channels = channelDAO.GetAll();
        for (RSSChannel channel : channels) {
            try {
                ArrayList<ChannelItem> items = parser.getChannelItemsRome(channel.getLink());
                for (ChannelItem it : items) {
                    if (!ifExists(it.getPubDate(), db)) {
                        it.setChannelId(channel.getChannelId());
                        itemDAO.Insert(it);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "Refresh: " + channel.getLink());
            }

        }
        DatabaseManager.getInstance().closeDatabase();
    }

    public boolean isRssChannel(String URL) {
        try {
            parser.getChannelItemsRome(URL);
        } catch (FeedException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean ifExists(long date, SQLiteDatabase db) {
        String query = "Select * from " + ChannelItem.TABLE + " where date = \"" + date + "\";";
        Cursor cursor = db.rawQuery(query, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            cursor.getLong(4);
        }
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}