package com.michaelhorta.webindexer.service.impl;

import com.michaelhorta.webindexer.beans.IndexerResponse;
import com.michaelhorta.webindexer.beans.Page;
import com.michaelhorta.webindexer.beans.SearcherResponse;
import com.michaelhorta.webindexer.config.WebIndexerConfig;
import com.michaelhorta.webindexer.processors.Crawler;
import com.michaelhorta.webindexer.processors.Indexer;
import com.michaelhorta.webindexer.processors.Searcher;
import com.michaelhorta.webindexer.service.IManagerService;
import com.michaelhorta.webindexer.utils.Url;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Component
public class ManagerService implements IManagerService {

    @Autowired
    private WebIndexerConfig webIndexerConfig;

    private List<Page> pagesToIndex;

    public ManagerService() {
        pagesToIndex = new ArrayList<>();
    }

    @Override
    public IndexerResponse executeIndexer(String url) throws IOException, ParseException {
        if (isIndexedOutlink(url))
            return new IndexerResponse(0, 0);

        crawlUrl(url, 0);
        int countOfWordsIndexed = indexPages();

        return new IndexerResponse(pagesToIndex.size(), countOfWordsIndexed);
    }

    private void crawlUrl(String url, int depth) throws IOException, ParseException {

        Crawler crawler = new Crawler(Url.removeLastSlash(url));
        Page page = crawler.execute();
        if (page != null) {
            addPageToIndex(page);
            List outlinks = page.getOutlinks();
            Iterator iterator = outlinks.iterator();

            if (depth + 1 == webIndexerConfig.getDepth())
                return;

            while (iterator.hasNext()) {
                String outlinkUrl = (String) iterator.next();
                if (!isRegisteredOutlink(outlinkUrl))
                    crawlUrl(outlinkUrl, depth + 1);
            }
        }
    }

    private void addPageToIndex(Page page) {
        pagesToIndex.add(page);
    }

    private boolean isRegisteredOutlink(String url) {
        Iterator iterator = pagesToIndex.iterator();
        while (iterator.hasNext()) {
            String url1 = ((Page) iterator.next()).getUrl();
            if (url1.equals(url)) {
                return true;
            }
        }

        return false;
    }

    private boolean isIndexedOutlink(String url) throws IOException, ParseException {
        url = Url.removeLastSlash(url);
        Searcher searcher = new Searcher();
        return searcher.existsDocument(webIndexerConfig.getIndexesDirectoryPath(), "code", String.valueOf(Math.abs(url.hashCode())));
    }

    private int indexPages() throws IOException, ParseException {
        int countOfWordsIndexed = 0;
        Iterator iterator = pagesToIndex.iterator();
        while (iterator.hasNext()) {
            Page page = ((Page) iterator.next());
            Indexer indexer = new Indexer(webIndexerConfig.getIndexesDirectoryPath(), webIndexerConfig.getDirectoryPath() + "/" + Math.abs(page.getUrl().hashCode()), page);
            indexer.execute();
            countOfWordsIndexed += indexer.countOfWordsIndexed();
        }

        return countOfWordsIndexed;
    }

    public boolean executeClearer(String url) throws IOException, ParseException {
        url = Url.removeLastSlash(url);
        Indexer indexer = new Indexer(webIndexerConfig.getIndexesDirectoryPath(), webIndexerConfig.getDirectoryPath() + "/" + Math.abs(url.hashCode()));
        indexer.deleteIndex(url);
        return true;
    }

    public SearcherResponse executeSearcher(String word) throws IOException, ParseException {
        Searcher searcher = new Searcher(webIndexerConfig.getIndexesDirectoryPath(), webIndexerConfig.getDirectoryPath(), word);
        return searcher.execute();
    }

}
