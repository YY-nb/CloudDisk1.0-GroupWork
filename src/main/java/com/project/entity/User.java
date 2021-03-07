package com.project.entity;

import java.time.LocalDateTime;

public class User {
    private String userId;
    private String fileRepositoryId;
    private String userName;
    private String email;

    private String password;
    private LocalDateTime registerTime;
    private String avatar;
    private String role;  //0代表管理员，1代表普通用户

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setId(String userId) {
        this.userId = userId;
    }

    public String getFileRepositoryId() {
        return fileRepositoryId;
    }

    public void setFileRepositoryId(String fileRepositoryId) {
        this.fileRepositoryId = fileRepositoryId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", fileRepositoryId='" + fileRepositoryId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registerTime=" + registerTime +
                ", avatar='" + avatar + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
