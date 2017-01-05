package com.example.vaviorky.rssandroid.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.vaviorky.rssandroid.R;
import com.example.vaviorky.rssandroid.data.model.RSSChannel;
import com.example.vaviorky.rssandroid.data.repo.RSSChannelRepo;

/**
 * Created by Vaviorky on 05.01.2017.
 */

public class AddingChannelAsync extends AsyncTask<Void, Void, Void> {

    private ProgressDialog dialog;
    private String source, name;
    private Context context;

    public AddingChannelAsync(Context context, String source, String name) {
        dialog = new ProgressDialog(context);
        dialog.setMessage(context.getString(R.string.AddingSourceAsync));
        this.source = source;
        this.name = name;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        AddChannelToDatabase();
        return null;
    }

    @Override
    protected void onPreExecute() {
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
        super.onPostExecute(aVoid);
    }

    private boolean AddChannelToDatabase() {
        RSSChannel channel = new RSSChannel();
        channel.setName(name);
        channel.setLink(source);
        DBHelper helper = new DBHelper(context);
        RSSChannelRepo repo = new RSSChannelRepo(helper);
        boolean result = repo.Insert(channel);
        return result;
    }
}
