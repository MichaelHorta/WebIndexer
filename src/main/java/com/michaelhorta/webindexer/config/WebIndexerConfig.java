package com.michaelhorta.webindexer.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class WebIndexerConfig {

//    @Value("${temp.file.dir}")
//    private String fileDir;

    @Value("${depth}")
    private int depth;

    @Value("${directoryPath}")
    private String directoryPath;

    @Value("${indexesDirectoryPath}")
    private String indexesDirectoryPath;

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getIndexesDirectoryPath() {
        return indexesDirectoryPath;
    }

    public void setIndexesDirectoryPath(String indexesDirectoryPath) {
        this.indexesDirectoryPath = indexesDirectoryPath;
    }
}
