package com.project.entity;

import java.time.LocalDateTime;

public class FileFolder {
    private String fileFolderId;
    private String fileRepositoryId;
    private String parentFolderId;
    private String fileFolderName;
    private LocalDateTime createTime;  //文件夹创建时间
    private String fileFolderPath;

    public FileFolder() {
    }

    public String getFileFolderId() {
        return fileFolderId;
    }

    public void setFileFolderId(String fileFolderId) {
        this.fileFolderId = fileFolderId;
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

    public String getFileFolderName() {
        return fileFolderName;
    }

    public void setFileFolderName(String fileFolderName) {
        this.fileFolderName = fileFolderName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getFileFolderPath() {
        return fileFolderPath;
    }

    public void setFileFolderPath(String fileFolderPath) {
        this.fileFolderPath = fileFolderPath;
    }

    @Override
    public String toString() {
        return "FileFolder{" +
                "fileFolderId='" + fileFolderId + '\'' +
                ", fileRepositoryId='" + fileRepositoryId + '\'' +
                ", parentFolderId='" + parentFolderId + '\'' +
                ", fileFolderName='" + fileFolderName + '\'' +
                ", createTime=" + createTime +
                ", fileFolderPath='" + fileFolderPath + '\'' +
                '}';
    }
}
