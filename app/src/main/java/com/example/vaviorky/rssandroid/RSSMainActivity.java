package com.example.vaviorky.rssandroid;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vaviorky.rssandroid.data.AddingChannelAsync;
import com.example.vaviorky.rssandroid.data.DBHelper;
import com.example.vaviorky.rssandroid.data.LoadingRSSAsync;
import com.example.vaviorky.rssandroid.data.adapter.RssAdapter;
import com.example.vaviorky.rssandroid.data.model.RSSChannel;
import com.example.vaviorky.rssandroid.data.repo.RSSChannelRepo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RSSMainActivity extends AppCompatActivity implements RssAdapter.ItemClickCallBack {
    private DBHelper myDB;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private RssAdapter adapter;
    private ArrayList<RSSChannel> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssmain);
        myDB = new DBHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.RssChannelsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RSSChannelRepo repo = new RSSChannelRepo(myDB);
        items = repo.GetAll();
        adapter = new RssAdapter(items, this);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickCallBack(this);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.FAB);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(RSSMainActivity.this);
                View view = getLayoutInflater().inflate(R.layout.adding_rss_channel_layout, null);
                final EditText rssSource = (EditText) view.findViewById(R.id.rssPath);
                final EditText rssName = (EditText) view.findViewById(R.id.channelname);
                Button addChannel = (Button) view.findViewById(R.id.addrssButton);
                AlertDialog dialog = null;
                builder.setView(view);
                dialog = builder.create();
                final AlertDialog finalDialog = dialog;
                addChannel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!rssSource.getText().toString().isEmpty() && !rssName.getText().toString().isEmpty()) {
                            String source = rssSource.getText().toString();
                            String name = rssName.getText().toString();
                            if (IsStringHttp(source)) {
                                finalDialog.dismiss();
                                AddingChannelAsync async = new AddingChannelAsync(RSSMainActivity.this, source, name);
                                async.execute();
                            } else {
                                Toast.makeText(RSSMainActivity.this, R.string.EnterValidAddress, Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(RSSMainActivity.this, R.string.youhavetofillinfields, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                if (isOnline()) {
                    LoadingRSSAsync async = new LoadingRSSAsync(this);
                    async.execute();
                } else {
                    Toast.makeText(this, "Nie masz internetu!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private boolean IsStringHttp(String source) {
        String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(source);
        return m.find();
    }

    @Override
    public void onItemClick(int p) {
        RSSChannel channel = items.get(p);

        Intent intent = new Intent(this, ChannelItemsActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("title", channel.getName());
        bundle.putInt("id", channel.getChannelId());
        intent.putExtra("extras", bundle);
        startActivity(intent);
    }
}
