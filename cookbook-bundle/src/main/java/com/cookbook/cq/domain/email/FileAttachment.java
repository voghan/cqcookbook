package com.cookbook.cq.domain.email;

import javax.activation.DataSource;

/**
 * User: bvaughn
 */
public class FileAttachment {
    private String fileName;
    private transient DataSource dataSource;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
