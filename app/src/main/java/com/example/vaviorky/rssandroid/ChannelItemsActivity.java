package com.example.vaviorky.rssandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        intent.putExtra("newsitem", item);
        startActivity(intent);
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
