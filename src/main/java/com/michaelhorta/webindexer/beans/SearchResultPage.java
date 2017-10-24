package com.michaelhorta.webindexer.beans;

public class SearchResultPage {
    private PageIndex pageIndex;
    private int occurrences;

    public SearchResultPage(PageIndex pageIndex, int occurrences) {
        this.pageIndex = pageIndex;
        this.occurrences = occurrences;
    }

    public PageIndex getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(PageIndex pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }
}
