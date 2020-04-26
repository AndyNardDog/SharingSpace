package com.example.firebasetest;

public class SpaceModel {

    private String address;
    private String description;
    private String price;
    private String sellerID;

    public SpaceModel() {

    }

    public SpaceModel(String address, String description, String price, String sellerID) {
        this.address = address;
        this.description = description;
        this.price = price;
        this.sellerID = sellerID;
    }

    public String getAddress() { return address; }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getSellerID() { return sellerID; }
}
