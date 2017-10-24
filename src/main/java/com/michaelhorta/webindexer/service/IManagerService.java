package com.michaelhorta.webindexer.service;

import com.michaelhorta.webindexer.beans.IndexerResponse;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;

public interface IManagerService {

    IndexerResponse executeIndexer(String url) throws IOException, ParseException;
}
