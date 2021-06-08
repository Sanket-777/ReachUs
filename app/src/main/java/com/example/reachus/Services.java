package com.example.reachus;

public class Services {

    private String mainJob;private String secondaryJob;
    private String Price;
    private String Description;
    private String userID;
    private String pincode;
    private String LegalName;
    private String Address_1;
    private String StoreName;
    private String Address_2;
    private String City;
    private String District;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLegalName() {
        return LegalName;
    }

    public void setLegalName(String legalName) {
        LegalName = legalName;
    }

    public String getAddress_1() {
        return Address_1;
    }

    public void setAddress_1(String address_1) {
        Address_1 = address_1;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getAddress_2() {
        return Address_2;
    }

    public void setAddress_2(String address_2) {
        Address_2 = address_2;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public Services(){}

    public Services(String mainJob, String secondaryJob,String Price,String Description){
        this.mainJob=mainJob;
        this.secondaryJob=secondaryJob;
        this.Price=Price;
        this.Description=Description;
    }

    public String getSecondaryJob() {
        return secondaryJob;
    }

    public void setSecondaryJob(String secondaryJob) {
        this.secondaryJob = secondaryJob;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
    public String getMainJob() {
        return mainJob;
    }

    public void setMainJob(String mainJob) {
        this.mainJob = mainJob;
    }
}