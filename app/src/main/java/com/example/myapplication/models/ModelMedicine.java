package com.example.myapplication.models;

public class ModelMedicine {

    private String medicineId, medicineTitle, medicineDescription, timestamp, uid;

    public ModelMedicine() {

    }

    public ModelMedicine(String medicineId, String medicineTitle, String medicineDescription, String timestamp, String uid) {
        this.medicineId = medicineId;
        this.medicineTitle = medicineTitle;
        this.medicineDescription = medicineDescription;
        this.timestamp = timestamp;
        this.uid = uid;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineTitle() {
        return medicineTitle;
    }

    public void setMedicineTitle(String medicineTitle) {
        this.medicineTitle = medicineTitle;
    }

    public String getMedicineDescription() {
        return medicineDescription;
    }

    public void setMedicineDescription(String medicineDescription) {
        this.medicineDescription = medicineDescription;
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
