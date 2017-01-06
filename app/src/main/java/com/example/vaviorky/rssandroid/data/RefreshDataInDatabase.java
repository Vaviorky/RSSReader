package com.example.vaviorky.rssandroid.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
    private RSSChannelRepo channelDAO;
    private Context context;
    private ChannelItemRepo itemDAO;

    public RefreshDataInDatabase(Context context) {
        this.context = context;
        DBHelper helper = new DBHelper(context);
        channelDAO = new RSSChannelRepo(helper);
        itemDAO = new ChannelItemRepo(helper);
    }

    public void Refresh() throws ParseException, IOException, FeedException {
        RSSParser parser = new RSSParser();
        DBHelper helper = new DBHelper(context);
        DatabaseManager.initializeInstance(helper);
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ArrayList<RSSChannel> channels = channelDAO.GetAll();
        for (RSSChannel channel : channels) {
            ArrayList<ChannelItem> items = parser.getChannelItemsRome(channel.getLink());
            for (ChannelItem it : items) {
                if (!ifExists(it.getPubDate(), db)) {
                    it.setChannelId(channel.getChannelId());
                    itemDAO.Insert(it);
                }
            }
        }
        DatabaseManager.getInstance().closeDatabase();
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