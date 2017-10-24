package com.michaelhorta.webindexer.beans;

import java.util.ArrayList;

public class SearcherResponse {
    private ArrayList<SearchResultPage> searchResultPages;
    private String word;

    public SearcherResponse(ArrayList<SearchResultPage> searchResultPages, String word) {
        this.searchResultPages = searchResultPages;
        this.word = word;
    }

    public ArrayList<SearchResultPage> getSearchResultPages() {
        return searchResultPages;
    }

    public void setSearchResultPages(ArrayList<SearchResultPage> searchResultPages) {
        this.searchResultPages = searchResultPages;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
