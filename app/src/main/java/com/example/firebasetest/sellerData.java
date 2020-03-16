package com.example.firebasetest;

import com.google.firebase.database.DatabaseReference;

public class sellerData {

    String sellerID;
String address;
String price;
String description;


    public sellerData(){}


public sellerData( String sellerID, String address , String price , String description){
        this.sellerID = sellerID;
    this.address = address;
    this.price = price;
    this.description = description;


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


}
