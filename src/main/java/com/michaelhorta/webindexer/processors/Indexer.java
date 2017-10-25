package com.michaelhorta.webindexer.processors;

import com.michaelhorta.webindexer.beans.Page;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Indexer {
    private StandardAnalyzer analyzer;
    private Directory directory;
    private IndexWriterConfig indexWriterConfig;
    private IndexWriter indexWriter;
    private Directory indexesDirectory;
    private IndexWriterConfig indexesIndexWriterConfig;
    private IndexWriter indexesIndexWriter;
    private Page page;
    private HashMap<String, Integer> documentsMap;
    private String directoryPath;
    private String indexesDirectoryPath;

    public Indexer(String indexesDirectoryPath, String directoryPath, Page page) throws IOException {
        this.analyzer = new StandardAnalyzer();
        this.directory = new SimpleFSDirectory(new File(directoryPath).toPath());
        this.indexWriterConfig = new IndexWriterConfig(analyzer);
        this.indexWriter = new IndexWriter(directory, indexWriterConfig);


        this.indexesDirectory = new SimpleFSDirectory(new File(indexesDirectoryPath).toPath());
        this.indexesIndexWriterConfig = new IndexWriterConfig(analyzer);
        this.indexesIndexWriter = new IndexWriter(indexesDirectory, indexesIndexWriterConfig);
        documentsMap = new HashMap<>();

        this.page = page;
    }

    public Indexer(String indexesDirectoryPath, String directoryPath) throws IOException {
        this.indexesDirectoryPath = indexesDirectoryPath;
        this.directoryPath = directoryPath;
    }

    public void execute() throws IOException, ParseException {
        String[] words = page.getContent().split(" ");
        for (String word : words) {
            registerDocumentInMap(word);
//            addDocument(word);
        }
        addDocuments();
        addIndexDocument();
        indexWriter.close();
        indexesIndexWriter.close();
    }

    private void registerDocumentInMap(String word) {
        int count = 1;
        if (documentsMap.containsKey(word))
            count = documentsMap.get(word) + 1;
        documentsMap.put(word, count);
    }

    private void addDocuments() throws IOException, ParseException {
        Iterator<Map.Entry<String, Integer>> iteratorDocumentsMap = documentsMap.entrySet().iterator();
        while (iteratorDocumentsMap.hasNext()) {
            Map.Entry<String, Integer> entry = iteratorDocumentsMap.next();
            addDocument(entry.getKey(), entry.getValue());
        }
    }

    private void addDocument(String word, int count) throws IOException, ParseException {
        Document document = new Document();
        document.add(new TextField("word", word, Field.Store.YES));
        document.add(new IntField("count", count, Field.Store.YES));
        indexWriter.addDocument(document);
    }

    private void addIndexDocument() throws IOException, ParseException {
        Document document = new Document();
        document.add(new TextField("code", String.valueOf(Math.abs(page.getUrl().hashCode())), Field.Store.YES));
        document.add(new TextField("title", page.getTitle(), Field.Store.YES));
        document.add(new TextField("url", page.getUrl(), Field.Store.YES));
        System.out.println(page.outlinksToString());
        document.add(new TextField("outlinks", page.outlinksToString(), Field.Store.YES));
        indexesIndexWriter.addDocument(document);
    }

    public int countOfWordsIndexed() {
        return documentsMap.size();
    }

    public void deleteIndex(String url) throws IOException, ParseException {
        int code = Math.abs(url.hashCode());
        Searcher searcher = new Searcher();
        Document document = searcher.searchDocumentByKeyValue(indexesDirectoryPath, "code", String.valueOf(code));

//        System.out.println(document.getField("code"));
//        System.out.println(document.getField("title"));
//        System.out.println(document.getField("url"));
//        System.out.println(document.getField("outlinks"));

        indexesDirectory = new SimpleFSDirectory(new File(indexesDirectoryPath).toPath());
        indexesIndexWriterConfig = new IndexWriterConfig(analyzer);
        indexesIndexWriter = new IndexWriter(indexesDirectory, indexesIndexWriterConfig);
        Term term = new Term("code", String.valueOf(code));
        indexesIndexWriter.deleteDocuments(term);
        indexesIndexWriter.close();

        File file = new File(directoryPath);
        directory = new SimpleFSDirectory(file.toPath());
        indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriter = new IndexWriter(directory, indexWriterConfig);
        indexWriter.deleteAll();
        indexWriter.close();
        boolean b = file.delete();
        System.out.println(b);
    }
}
