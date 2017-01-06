package com.example.vaviorky.rssandroid.data.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vaviorky.rssandroid.R;
import com.example.vaviorky.rssandroid.data.model.ChannelItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        View view = inflater.inflate(R.layout.list_rss_channel_items, parent, false);
        return new RssItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RssItemViewHolder holder, int position) {
        ChannelItem item = items.get(position);
        holder.Title.setText(item.getName());
        holder.Description.setText(item.getDescription());
        //here I have to parse date to nice format
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateString = format.format(item.getPubDate());
        holder.Date.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setClickCallBack(RssItemCallClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface RssItemCallClickCallBack {
        void onItemClick(int p);
    }

    class RssItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView Title, Description, Date;
        private View Container;

        public RssItemViewHolder(View itemView) {
            super(itemView);
            this.Title = (TextView) itemView.findViewById(R.id.rss_item_title);
            this.Description = (TextView) itemView.findViewById(R.id.rss_item_description);
            this.Date = (TextView) itemView.findViewById(R.id.rss_item_date);
            this.Container = itemView.findViewById(R.id.rss_channel_items_card_view);
            this.Container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.rss_channel_items_card_view) {
                clickCallBack.onItemClick(getAdapterPosition());
            }
        }
    }
}
