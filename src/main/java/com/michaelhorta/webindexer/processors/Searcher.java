package com.michaelhorta.webindexer.processors;

import com.michaelhorta.webindexer.beans.PageIndex;
import com.michaelhorta.webindexer.beans.SearchResultPage;
import com.michaelhorta.webindexer.beans.SearcherResponse;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Searcher {
    private StandardAnalyzer analyzer;
    private Directory indexesDirectory;
    private IndexWriterConfig indexesIndexWriterConfig;
    private IndexWriter indexesIndexWriter;
    private String directoryPath;
    private String word;

    public Searcher(String indexesDirectoryPath, String directoryPath, String word) throws IOException {
        this.analyzer = new StandardAnalyzer();
        this.indexesDirectory = new SimpleFSDirectory(new File(indexesDirectoryPath).toPath());
        this.indexesIndexWriterConfig = new IndexWriterConfig(analyzer);
        this.indexesIndexWriter = new IndexWriter(indexesDirectory, indexesIndexWriterConfig);
        this.directoryPath = directoryPath;
        this.word = word;
    }

    public Searcher() {
        this.analyzer = new StandardAnalyzer();
    }

    public SearcherResponse execute() throws IOException, ParseException {
        ArrayList<PageIndex> indexes = getIndexes();
        ArrayList<SearchResultPage> searchResultPages = new ArrayList<>();
        Iterator<PageIndex> iterator = indexes.iterator();
        while (iterator.hasNext()) {
            PageIndex pageIndex = iterator.next();
            int occurrences = getCountOfWordInDocument(directoryPath + "/" + String.valueOf(pageIndex.getCode()));
            if (occurrences > 0)
                searchResultPages.add(new SearchResultPage(pageIndex, occurrences));
        }
        indexesIndexWriter.close();

        return new SearcherResponse(searchResultPages, word);
    }

    private ArrayList<PageIndex> getIndexes() throws IOException {
        ArrayList<PageIndex> indexes = new ArrayList<>();

        IndexReader reader = DirectoryReader.open(indexesDirectory);
        for (int i = 0; i < reader.maxDoc(); i++) {
            Document doc = reader.document(i);
            IndexableField code = doc.getField("code");
            IndexableField title = doc.getField("title");
            IndexableField url = doc.getField("url");
            IndexableField outlinks = doc.getField("outlinks");
            indexes.add(new PageIndex(code.stringValue(), title.stringValue(), url.stringValue()));
        }

        return indexes;
    }

    private int getCountOfWordInDocument(String directoryDocuemnt) throws ParseException, IOException {
        Directory directory = new SimpleFSDirectory(new File(directoryDocuemnt).toPath());
        Query query = new QueryParser("word", analyzer).parse(word);
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(query, 1);

        ScoreDoc[] hits = docs.scoreDocs;

        if (hits.length == 1) {
            Document document = searcher.doc(hits[0].doc);
            return document.getField("count").numericValue().intValue();
        }

        return 0;

    }

    public boolean existsDocument(String directoryDocuemnt, String key, String value) throws IOException, ParseException {
        return searchDocumentByKeyValue(directoryDocuemnt, key, value) != null;
    }

    public Document searchDocumentByKeyValue(String directoryDocuemnt, String key, String value) throws ParseException, IOException {
        File file = new File(directoryDocuemnt);
        if (!file.exists())
            return null;

        Directory directory = new SimpleFSDirectory(file.toPath());
//        Query query = new QueryParser("title", analyzer).parse("OnServi - Presupuestos gratuitos de profesionales");
        Query query = new QueryParser(key, analyzer).parse(value);

        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(query, 1);

        ScoreDoc[] hits = docs.scoreDocs;

        if (hits.length == 1) {
            return searcher.doc(hits[0].doc);
        }

        return null;
    }


}
