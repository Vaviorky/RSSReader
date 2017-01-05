package com.example.vaviorky.rssandroid.data.model;

/**
 * Created by Vaviorky on 04.01.2017.
 */

public class ChannelItem {
    public static final String TAG = ChannelItem.class.getSimpleName();
    public static final String TABLE = "ChannelItem";

    public static final String KEY_ItemId = "ItemId";
    public static final String KEY_Name = "name";
    public static final String KEY_Description = "description";
    public static final String KEY_Date = "date";
    public static final String KEY_Link = "link";
    public static final String KEY_ThumbnailURL = "ThumbnailURL";
    public static final String KEY_ChannelId = "ChannelId";

    private int ItemId;
    private String Name;
    private String Description;
    private String PubDate;
    private String Link;
    private String ThumbnailURL;
    private RSSChannel Channel;

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPubDate() {
        return PubDate;
    }

    public void setPubDate(String pubDate) {
        PubDate = pubDate;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public RSSChannel getChannel() {
        return Channel;
    }

    public void setChannel(RSSChannel channel) {
        Channel = channel;
    }

    public String getThumbnailURL() {
        return ThumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        ThumbnailURL = thumbnailURL;
    }
}
