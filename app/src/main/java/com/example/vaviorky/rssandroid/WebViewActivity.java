package com.example.vaviorky.rssandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bundle = getIntent().getBundleExtra("channel");
        setTitle(bundle.getString("site"));
        webView = (WebView) findViewById(R.id.WebView);
        webView.loadUrl(bundle.getString("url"));
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
