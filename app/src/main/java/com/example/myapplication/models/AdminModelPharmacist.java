package com.example.myapplication.models;

public class AdminModelPharmacist {

    private String pharmacistId, name, email, age, accountType, timestamp, uid;

    public AdminModelPharmacist() {

    }

    public AdminModelPharmacist(String pharmacistId, String name, String email, String age, String accountType, String timestamp, String uid) {
        this.pharmacistId = pharmacistId;
        this.name = name;
        this.email = email;
        this.age = age;
        this.accountType = accountType;
        this.timestamp = timestamp;
        this.uid = uid;
    }

    public String getPharmacistId() {
        return pharmacistId;
    }

    public void setPharmacistId(String pharmacistId) {
        this.pharmacistId = pharmacistId;
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
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
