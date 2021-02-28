package com.project.entity;

public class FileRepository {
    private String fileRepositoryId;
    private String userId;
    private Double currentSize;
    private Double maxSize;

    public FileRepository() {
    }

    public FileRepository(String fileRepositoryId, String userId, Double currentSize, Double maxSize) {
        this.fileRepositoryId = fileRepositoryId;
        this.userId = userId;
        this.currentSize = currentSize;
        this.maxSize = maxSize;
    }

    public String getFileRepositoryId() {
        return fileRepositoryId;
    }

    public void setFileRepositoryId(String fileRepositoryId) {
        this.fileRepositoryId = fileRepositoryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(Double currentSize) {
        this.currentSize = currentSize;
    }

    public Double getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Double maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public String toString() {
        return "FileRepository{" +
                "fileRepositoryId='" + fileRepositoryId + '\'' +
                ", userId='" + userId + '\'' +
                ", currentSize=" + currentSize +
                ", maxSize=" + maxSize +
                '}';
    }
}
