package com.michaelhorta.webindexer.utils;

public class Url {
    private static Url ourInstance = new Url();

    public static Url getInstance() {
        return ourInstance;
    }

    private Url() {
    }

    public static String removeLastSlash(String url) {
        if (url.charAt(url.length() - 1) == '/')
            return url.substring(0, url.length() - 1);

        return url;
    }
}
