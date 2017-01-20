package com.example.vaviorky.rssandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.vaviorky.rssandroid.data.DBHelper;
import com.example.vaviorky.rssandroid.data.adapter.RssItemAdapter;
import com.example.vaviorky.rssandroid.data.model.ChannelItem;
import com.example.vaviorky.rssandroid.data.repo.ChannelItemRepo;

import java.util.List;


public class ChannelItemsActivity extends AppCompatActivity implements RssItemAdapter.RssItemCallClickCallBack {
    private static final String TAG = ChannelItemsActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RssItemAdapter adapter;
    private List<ChannelItem> items;
    private ChannelItemRepo repo;
    private DBHelper helper;
    private int ChannelId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_items);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        helper = new DBHelper(this);
        repo = new ChannelItemRepo();


        Bundle extras = getIntent().getBundleExtra("extras");
        setTitle(extras.get("title").toString());
        ChannelId = (int) extras.get("id");
        Log.d(TAG, "onCreate: Checking id in channel: " + ChannelId);

        items = repo.getData(ChannelId);
        adapter = new RssItemAdapter(items, this);
        adapter.setClickCallBack(this);
        recyclerView = (RecyclerView) this.findViewById(R.id.RssChannelItemsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new VerticalSpace(50));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int p) {
        ChannelItem item = items.get(p);
        Intent intent = new Intent(this, RSSDetails.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getItemId());
        bundle.putString("title", item.getName());
        bundle.putString("description", item.getDescription());
        bundle.putString("url", item.getLink());
        bundle.putLong("date", item.getPubDate());
        bundle.putInt("channelId", ChannelId);
        bundle.putString("img", item.getThumbnailURL());
        intent.putExtra("details", bundle);
        startActivityForResult(intent, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
