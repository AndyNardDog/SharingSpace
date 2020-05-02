package com.example.firebasetest;

import com.google.firebase.database.DatabaseReference;

public class sellerData {

    String sellerID;
String address;
String price;
String description;
int isRented ;
String imagepath;

    public sellerData(){}


public sellerData( String sellerID, String address , String price , String description, String imagepath, int isRented){
        this.sellerID = sellerID;
    this.address = address;
    this.price = price;
    this.description = description;
    this.isRented = isRented;
    this.imagepath = imagepath;

}

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public void setIsRented(int imagepath) {
        this.isRented = isRented;
    }

    public String getSellerID() {
        return sellerID;
    }


    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImagepath() {
        return imagepath;
    }

    public int getIsRented() {
        return isRented;
    }


}
