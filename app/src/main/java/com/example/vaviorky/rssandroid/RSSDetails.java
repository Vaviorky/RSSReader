package com.example.vaviorky.rssandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RSSDetails extends AppCompatActivity {
    //init
    private TextView title, date, description;
    private Button button;
    private Bundle details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssdetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        details = getIntent().getBundleExtra("details");
        title = (TextView) findViewById(R.id.RssNewsDetailsTitle);
        title.setText(details.getString("title"));
        date = (TextView) findViewById(R.id.RssNewsDetailsDate);
        date.setText(details.getString("date"));
        description = (TextView) findViewById(R.id.RssNewsDetailsDescription);
        description.setText(details.getString("description"));

        button = (Button) findViewById(R.id.ButtonGoToSite);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RSSDetails.this, WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", details.getString("url"));
                intent.putExtra("channel", bundle);
                startActivityForResult(intent, 0);
            }
        });
    }
}
