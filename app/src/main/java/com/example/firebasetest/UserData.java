//package com.example.firebasetest;
//
//import android.widget.EditText;
//
//
//import androidx.annotation.Keep;
//
//import java.io.Serializable;
//@Keep
//public class UserData implements Serializable {
//
//    String UserID;
//    String name;
//    String Address;
//    String PhoneNumber;
//    String Email;
//    public UserData() {}
//    public UserData( String UserID, String name, String Address, String PhoneNumber, String Email){
//
//        this.UserID = UserID;
//        this.name = name;
//        this.Address = Address;
//        this.PhoneNumber = PhoneNumber;
//        this.Email = Email;
//
//    }
//
//
//    public void userName(String name) {
//        this.name = name;
//    }
//
//    public void setAddress(String Address) {
//        this.Address = Address;
//    }
//
//    public void setPhoneNumber(String PhoneNumber) {
//        this.PhoneNumber = PhoneNumber;
//    }
//
//    public void setEmail(String Email) {
//        this.Email = Email;
//    }
//
//
//    public String userName() {
//        return name;
//    }
//
//
//    public String setAddress() {
//        return Address;
//    }
//
//    public String setPhoneNumber() {
//        return PhoneNumber;
//    }
//
//    public String setEmail() {
//        return Email;
//    }
//
//
//}
package com.example.firebasetest;

import com.google.firebase.database.DatabaseReference;

public class UserData {

    String UserID;
    String name;
    String Address;
    String PhoneNumber;
    String Email;

    public UserData(){}


    public UserData( String UserID, String name , String Address , String PhoneNumber, String Email){
        this.UserID = UserID;
        this.name = name;
        this.Address = Address;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;

    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }


    public String getUserID() {
        return UserID;
    }

    public String getName() {return name;}

    public String getAddress() {
        return Address;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getEmail() {
        return Email;
    }


}

