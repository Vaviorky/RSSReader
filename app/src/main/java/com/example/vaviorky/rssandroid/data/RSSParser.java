package com.example.vaviorky.rssandroid.data;

import android.util.Log;

import com.example.vaviorky.rssandroid.data.model.ChannelItem;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vaviorky on 04.01.2017.
 */

public class RSSParser {
    private static String TAG = RSSParser.class.getSimpleName();

    public RSSParser() {
    }

    public ArrayList<ChannelItem> ItemsInChannel(Document document) throws ParseException {
        ArrayList<ChannelItem> RssItems = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        if (document != null) {
            Element root = document.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node currentChild = items.item(i);
                if (currentChild.getNodeName().equalsIgnoreCase("item")) {
                    ChannelItem item = new ChannelItem();
                    NodeList itemChilds = currentChild.getChildNodes();
                    for (int j = 0; j < itemChilds.getLength(); j++) {
                        Node current = itemChilds.item(j);
                        if (current.getNodeName().equalsIgnoreCase("title")) {
                            item.setName(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("description")) {
                            //String desc
                            item.setDescription(current.getTextContent());
                            if (item.getDescription().contains("img")) {
                                String[] content = item.getDescription().split("src=\"");
                                String[] tempcontentwithURL = content[1].split("\"");
                                item.setThumbnailURL(tempcontentwithURL[0]);
                            }
                        } else if (current.getNodeName().equalsIgnoreCase("pubDate")) {
                            String rssDate = current.getTextContent();
                            Date date = formatter.parse(rssDate);
                            long timestamp = date.getTime();
                            Log.d("Data parserg", "ItemsInChannel: " + date.getTime());
                            item.setPubDate(timestamp);
                        } else if (current.getNodeName().equalsIgnoreCase("link")) {
                            item.setLink(current.getTextContent());
                        }
                    }
                    RssItems.add(item);
                }
            }
        }

        return RssItems;
    }

    public ArrayList<ChannelItem> getChannelItemsRome(String feedurl) throws IOException, FeedException {
        ArrayList<ChannelItem> list = new ArrayList<>();
        URL url = new URL(feedurl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        SyndFeedInput input = new SyndFeedInput();
        XmlReader reader = new XmlReader(connection);
        SyndFeed feed = input.build(reader);

        List entries = feed.getEntries();
        Iterator itEntries = entries.iterator();
        while (itEntries.hasNext()) {
            SyndEntry entry = (SyndEntry) itEntries.next();
            String author = entry.getAuthor();
            if (author.contains("(")) {
                int openBracket = author.indexOf('(') + 1;
                int closeBracket = author.indexOf(')');
                author = author.substring(openBracket, closeBracket);
            }

            String description = entry.getDescription().getValue();
            if (description.contains("<p>")) {
                int beginP = description.indexOf("<p>") + 3;
                int endP = description.indexOf("</p>");
                description = description.substring(beginP, endP);
            }
            ChannelItem item = new ChannelItem();
            item.setName(entry.getTitle());
            item.setAuthor(author);
            item.setLink(entry.getLink());
            item.setDescription(description);
            Date date = entry.getPublishedDate();
            item.setPubDate(date.getTime());
            item.setThumbnailURL("");
            list.add(item);
        }
        return list;
    }
}