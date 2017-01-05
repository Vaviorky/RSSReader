package com.example.vaviorky.rssandroid.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.d(this.getClass().getSimpleName(), "doInBackground: before refresh");
        RefreshDataInDatabase inDatabase = new RefreshDataInDatabase(context);
        inDatabase.Refresh();
        Log.d(this.getClass().getSimpleName(), "doInBackground: after refresh");

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
