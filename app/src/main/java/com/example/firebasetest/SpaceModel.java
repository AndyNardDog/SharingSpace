package com.example.firebasetest;

public class SpaceModel {

    private String address;
    private String description;
    private String price;
    private String sellerID;
    private String imagepath;

    public SpaceModel() {

    }

    public SpaceModel(String address, String description, String price, String sellerID, String imagepath) {
        this.address = address;
        this.description = description;
        this.price = price;
        this.sellerID = sellerID;
        this.imagepath = imagepath;
    }

    //getters
    public String getAddress() { return address; }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getSellerID() { return sellerID; }

    public String getImagepath() { return imagepath; }
}
