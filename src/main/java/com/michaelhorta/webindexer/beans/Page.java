package com.michaelhorta.webindexer.beans;

import java.util.List;

public class Page {
    private String url;
    private String title;
    private String content;
    private List outlinks;

    public Page(String url, String title, String content, List outlinks) {
        this.url = url;
        this.title = title;
        this.content = content;
        this.outlinks = outlinks;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List getOutlinks() {
        return outlinks;
    }

    public void setOutlinks(List outlinks) {
        this.outlinks = outlinks;
    }
}
