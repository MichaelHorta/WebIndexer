package com.michaelhorta.webindexer.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Url {
    private static Url ourInstance = new Url();

    public static Url getInstance() {
        return ourInstance;
    }

    private Url() {
    }

    public static boolean isUrl(String url) {

        if (url.length() > 4 && url.substring(0, 4).equals("http")) {
            return true;
        }

        return false;
    }

    public static String removeLastSlash(String url) {
        System.out.println(url);
        if (url.charAt(url.length() - 1) == '/')
            return url.substring(0, url.length() - 1);

        return url;
    }
}
