package com.example.vaviorky.rssandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.vaviorky.rssandroid.data.model.ChannelItem;

import java.util.ArrayList;


public class ChannelItemsActivity extends AppCompatActivity {

    private ArrayList<ChannelItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_items);

        Bundle extras = getIntent().getBundleExtra("extras");
        setTitle(extras.get("title").toString());
    }
}
