package com.example.vaviorky.rssandroid.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.rometools.rome.io.FeedException;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Vaviorky on 04.01.2017.
 */

public class LoadingRSSAsync extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progressDialog;
    private Context context;

    public LoadingRSSAsync(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Wczytywanie...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected Void doInBackground(Void... params) {
        RefreshDataInDatabase inDatabase = new RefreshDataInDatabase(context);
        try {
            try {
                inDatabase.Refresh();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FeedException e) {
                e.printStackTrace();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
        super.onPostExecute(aVoid);
    }


}
