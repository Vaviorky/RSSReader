package com.example.vaviorky.rssandroid.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.example.vaviorky.rssandroid.R;
import com.example.vaviorky.rssandroid.data.adapter.RssAdapter;
import com.example.vaviorky.rssandroid.data.model.RSSChannel;
import com.example.vaviorky.rssandroid.data.repo.RSSChannelRepo;

import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Vaviorky on 05.01.2017.
 */

public class AddingChannelAsync extends AsyncTask<Void, Void, Void> {

    private ProgressDialog dialog;
    private String source, name;
    private Context context;
    private RssAdapter adapter;
    private int count;

    public AddingChannelAsync(Context context, String source, String name, RssAdapter adapter) {
        dialog = new ProgressDialog(context);
        dialog.setMessage(context.getString(R.string.AddingSourceAsync));
        this.source = source;
        this.name = name;
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(Void... params) {
        count = AddChannelToDatabase();
        Handler handler = new Handler(context.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (count == -1) {
                    Toast.makeText(context, "Błąd przy dodawaniu kanału.", Toast.LENGTH_SHORT).show();
                } else if (count == -2) {
                    Toast.makeText(context, "Podany kanał już istnieje.", Toast.LENGTH_SHORT).show();
                } else if (count == -3) {
                    Toast.makeText(context, "Nie udało się ustanowić połączenia", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Pomyślnie dodano kanał.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        final Handler handler = new Handler(context.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                DBHelper helper = new DBHelper(context);
                RSSChannelRepo repo = new RSSChannelRepo(helper);
                adapter.reload(repo.GetAll());
            }
        });
        super.onPostExecute(aVoid);
    }

    private int AddChannelToDatabase() {

        try {
            URL url = new URL(source);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(2000);
            connection.connect();
        } catch (Exception e) {
            e.printStackTrace();
            return -3;
        }
        RSSChannel channel = new RSSChannel();
        channel.setName(name);
        channel.setLink(source);
        DBHelper helper = new DBHelper(context);
        RSSChannelRepo repo = new RSSChannelRepo(helper);
        int result = repo.Insert(channel);
        return result;
    }
}
