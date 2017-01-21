package com.example.vaviorky.rssandroid.data.model;

/**
 * Created by Vaviorky on 04.01.2017.
 */

public class RSSChannel {
    public static final String TAG = RSSChannel.class.getSimpleName();
    public static final String TABLE = "RSSChannel";

    public static final String KEY_ChannelId = "ChannelId";
    public static final String KEY_Name = "Name";
    public static final String KEY_Link = "Link";

    private int ChannelId;
    private String Name;
    private String Link;

    public RSSChannel() {
    }

    public RSSChannel(String name, String link) {
        this.Name = name;
        this.Link = link;
    }
    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getChannelId() {
        return ChannelId;
    }

    public void setChannelId(int channelId) {
        ChannelId = channelId;
    }
}
