package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {

    private Integer fileId;
    private String filename;
    private String contentType;
    private Integer userId;
    private byte[] fileData;

    public File(String filename, String contentType, Integer userId, byte[] data) {
        this.filename = filename;
        this.contentType = contentType;
        this.userId = userId;
        this.fileData = data;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public byte[] getData() {
        return fileData;
    }

    public void setData(byte[] data) {
        this.fileData = data;
    }
}
