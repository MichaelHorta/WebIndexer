package com.michaelhorta.webindexer.service.impl;

import com.michaelhorta.webindexer.beans.IndexerResponse;
import com.michaelhorta.webindexer.beans.Page;
import com.michaelhorta.webindexer.config.WebIndexerConfig;
import com.michaelhorta.webindexer.service.IManagerService;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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
        return null;
    }



}
