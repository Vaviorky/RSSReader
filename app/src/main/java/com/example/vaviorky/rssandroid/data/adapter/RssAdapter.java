package com.example.vaviorky.rssandroid.data.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vaviorky.rssandroid.R;
import com.example.vaviorky.rssandroid.data.model.RSSChannel;

import java.util.List;


/**
 * Created by Vaviorky on 05.01.2017.
 */

public class RssAdapter extends RecyclerView.Adapter<RssAdapter.MyViewHolder> {

    private List<RSSChannel> listData;
    private LayoutInflater inflater;

    private ItemClickCallBack itemClickCallBack;

    public RssAdapter(List<RSSChannel> listData, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.listData = listData;

    }

    public void setItemClickCallBack(final ItemClickCallBack itemClickCallBack) {
        this.itemClickCallBack = itemClickCallBack;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_rss_channels, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RSSChannel channel = listData.get(position);
        holder.RssName.setText(channel.getName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void reload(List<RSSChannel> list) {
        listData.clear();
        listData.addAll(list);
        notifyDataSetChanged();
    }

    public interface ItemClickCallBack {
        void onItemClick(int p);

    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView RssName;
        private View Container;

        public MyViewHolder(View itemView) {
            super(itemView);
            RssName = (TextView) itemView.findViewById(R.id.RssNameInList);
            Container = itemView.findViewById(R.id.list_rss_layout);
            Container.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.list_rss_layout) {
                itemClickCallBack.onItemClick(getAdapterPosition());
            }
        }
    }
}
