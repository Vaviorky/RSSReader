package com.example.vaviorky.rssandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vaviorky.rssandroid.data.model.ChannelItem;
import com.example.vaviorky.rssandroid.data.model.RSSChannel;
import com.example.vaviorky.rssandroid.data.repo.RSSChannelRepo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class RSSDetails extends AppCompatActivity {
    //init
    private final String TAG = RSSDetails.class.getSimpleName();
    private TextView title, date, description;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssdetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ChannelItem item = (ChannelItem) getIntent().getSerializableExtra("newsitem");
        setTitle(item.getName());
        title = (TextView) findViewById(R.id.RssNewsDetailsTitle);
        title.setText(item.getName());
        date = (TextView) findViewById(R.id.RssNewsDetailsDate);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateString = format.format(item.getPubDate());
        if (item.getAuthor().isEmpty()) {
            date.setText(dateString);

        } else {
            date.setText(dateString + ", " + item.getAuthor());
            Log.d(TAG, "onCreate:" + item.getAuthor());
        }
        description = (TextView) findViewById(R.id.RssNewsDetailsDescription);
        description.setText(item.getDescription());

        button = (Button) findViewById(R.id.ButtonGoToSite);
        button.setBackgroundColor(Color.RED);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RSSDetails.this, WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", item.getLink());
                RSSChannel rssChannel = RSSChannelRepo.getById(item.getChannelId());
                bundle.putString("site", rssChannel.getName());
                intent.putExtra("channel", bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
