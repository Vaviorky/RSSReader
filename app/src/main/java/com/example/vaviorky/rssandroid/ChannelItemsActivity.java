package com.example.vaviorky.rssandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vaviorky.rssandroid.data.DBHelper;
import com.example.vaviorky.rssandroid.data.adapter.RssItemAdapter;
import com.example.vaviorky.rssandroid.data.model.ChannelItem;
import com.example.vaviorky.rssandroid.data.repo.ChannelItemRepo;

import java.util.List;


public class ChannelItemsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RssItemAdapter adapter;
    private List<ChannelItem> items;
    private ChannelItemRepo repo;
    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_items);
        helper = new DBHelper(this);
        repo = new ChannelItemRepo(helper);


        Bundle extras = getIntent().getBundleExtra("extras");
        setTitle(extras.get("title").toString());
        int ChannelId = (int) extras.get("id");
        items = repo.getData(ChannelId);
        adapter = new RssItemAdapter(items, this);
        recyclerView = (RecyclerView) this.findViewById(R.id.RssChannelItemsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new VerticalSpace(50));
        recyclerView.setAdapter(adapter);
    }
}
