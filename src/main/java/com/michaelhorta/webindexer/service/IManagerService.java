package com.michaelhorta.webindexer.service;

import com.michaelhorta.webindexer.beans.IndexerResponse;
import com.michaelhorta.webindexer.beans.SearcherResponse;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;

public interface IManagerService {

    IndexerResponse executeIndexer(String url) throws IOException, ParseException;

    boolean executeClearer(String url) throws IOException, ParseException;

    SearcherResponse executeSearcher(String word) throws IOException, ParseException;
}
