package com.michaelhorta.webindexer.beans;

public class IndexerResponse {
    private int pagesCount;
    private int wordsCount;

    public IndexerResponse(int pagesCount, int wordsCount) {
        this.pagesCount = pagesCount;
        this.wordsCount = wordsCount;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(int wordsCount) {
        this.wordsCount = wordsCount;
    }
}
