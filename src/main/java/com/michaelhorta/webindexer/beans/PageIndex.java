package com.michaelhorta.webindexer.beans;

public class PageIndex {
    private String code;
    private String title;
    private String url;

    public PageIndex(String code, String title, String url) {
        this.code = code;
        this.title = title;
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
