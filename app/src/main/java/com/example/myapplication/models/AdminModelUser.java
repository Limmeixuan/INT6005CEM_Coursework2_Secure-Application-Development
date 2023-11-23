package com.example.myapplication.models;

public class AdminModelUser {

    private String userId, name, email, age, timestamp, uid;

    public AdminModelUser() {

    }

    public AdminModelUser(String userId, String name, String email, String age, String timestamp, String uid) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.age = age;
        this.timestamp = timestamp;
        this.uid = uid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
