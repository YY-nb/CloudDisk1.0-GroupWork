package com.project.entity;

import java.time.LocalDateTime;

public class MyFile {
    private String fileId;
    private String fileRepositoryId;
    private String parentFolderId;  //父文件夹id
    private String fileName;
    private String filePath;
    private LocalDateTime uploadTime;
    private Double size;
    private String type;   //文件类型
    private String state; //审核状态，0表示未审核，1表示已审核

    public MyFile() {
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileRepositoryId() {
        return fileRepositoryId;
    }

    public void setFileRepositoryId(String fileRepositoryId) {
        this.fileRepositoryId = fileRepositoryId;
    }

    public String getParentFolderId() {
        return parentFolderId;
    }

    public void setParentFolderId(String parentFolderId) {
        this.parentFolderId = parentFolderId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "MyFile{" +
                "fileId='" + fileId + '\'' +
                ", fileRepositoryId='" + fileRepositoryId + '\'' +
                ", parentFolderId='" + parentFolderId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", uploadTime=" + uploadTime +
                ", size=" + size +
                ", type='" + type + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
