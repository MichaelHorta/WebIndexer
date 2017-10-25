package com.michaelhorta.webindexer.controller;

import com.michaelhorta.webindexer.beans.IndexerResponse;
import com.michaelhorta.webindexer.beans.SearcherResponse;
import com.michaelhorta.webindexer.service.IHomeService;
import com.michaelhorta.webindexer.service.IManagerService;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WebIndexerController {

    @Autowired
    private IManagerService managerService;

    @RequestMapping(value = "/indexer", method = RequestMethod.GET)
    public Map<String, IndexerResponse> executeIndexer(@RequestParam(value = "url", required = true) String url) {
        IndexerResponse indexerResponse = null;
        try {
            indexerResponse = managerService.executeIndexer(url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Map<String, IndexerResponse> map = new HashMap<>();
        map.put("result", indexerResponse);
        return map;
    }

    @RequestMapping(value = "/clearer", method = RequestMethod.GET)
    public Map<String, Boolean> executeClearer(@RequestParam(value = "url", required = true) String url) {
        boolean clearerResponse = false;
        try {
            clearerResponse = managerService.executeClearer(url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Map<String, Boolean> map = new HashMap<>();
        map.put("result", clearerResponse );
        return map;
    }

    @RequestMapping(value = "/searcher", method = RequestMethod.GET)
    public Map<String, SearcherResponse> executeSearcher(@RequestParam(value = "word", required = true) String word, @RequestParam(value = "page", required = true) int page) {
        SearcherResponse searcherResponse = null;
        try {
            searcherResponse = managerService.executeSearcher(word);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Map<String, SearcherResponse> map = new HashMap<>();
        map.put("result", searcherResponse);
        return map;
    }


}
