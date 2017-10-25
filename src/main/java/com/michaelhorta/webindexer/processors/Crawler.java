package com.michaelhorta.webindexer.processors;

import com.michaelhorta.webindexer.beans.Page;
import com.michaelhorta.webindexer.utils.Url;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;

public class Crawler {

    private String url;

    public Crawler(String url) {
        this.url = url;
    }

    public Page execute() {
        try {
            Document document = Jsoup.connect(this.url).timeout(60 * 1000).get();

            String location = document.location();

            String title = document.title();
            String s = document.ownText();
            String content = document.body().text();
            Elements outlinkElements = document.select("a[href]");
            Iterator<Element> elementIterator = outlinkElements.iterator();
            ArrayList<String> outlinks = new ArrayList<>();
            while (elementIterator.hasNext()) {
                String href = elementIterator.next().attr("href");
                if(Url.isUrl(href)) {
                    String outlinkUrl = Url.removeLastSlash(href);
                    outlinks.add(outlinkUrl);
                }
            }
            return new Page(this.url, title, content, outlinks);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
