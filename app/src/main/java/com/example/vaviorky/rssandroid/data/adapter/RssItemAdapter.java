package com.example.vaviorky.rssandroid.data.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vaviorky.rssandroid.data.model.ChannelItem;

import java.util.List;

public class RssItemAdapter extends RecyclerView.Adapter<RssItemAdapter.RssItemViewHolder> {

    private List<ChannelItem> items;
    private LayoutInflater inflater;
    private RssItemCallClickCallBack clickCallBack;

    public RssItemAdapter(List<ChannelItem> items, Context context) {
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RssItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = inflater.inflate(R.layout.)
        return null;
    }

    @Override
    public void onBindViewHolder(RssItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setClickCallBack(RssItemCallClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface RssItemCallClickCallBack {
        void onItemClick(int p);
    }

    class RssItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public RssItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
