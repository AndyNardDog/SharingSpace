package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SellerPage extends AppCompatActivity {

    EditText Address;
    EditText price;
    EditText description;
    Button register;
    DatabaseReference databaseForSeller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_page);

        databaseForSeller = FirebaseDatabase.getInstance().getReference("SellerInfo");

        Address = (EditText) findViewById(R.id.Address);
        price = (EditText) findViewById(R.id.Price);
        description = (EditText) findViewById(R.id.description);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSellerInfo();
            }
        });
    }

    private void addSellerInfo(){

        String addressStr =  Address.getText().toString().trim();
        String priceStr =  price.getText().toString().trim();
        String descriptionStr =  description.getText().toString().trim();


        if(!TextUtils.isEmpty(addressStr) || !TextUtils.isEmpty(priceStr)|| !TextUtils.isEmpty(descriptionStr)){

          String sellerID =   databaseForSeller.push().getKey();

         sellerData sellerinfo = new sellerData(  sellerID,  addressStr ,  priceStr ,  descriptionStr);

         databaseForSeller.child(sellerID).setValue(sellerinfo);

            Toast.makeText(this, "The parking lot is registered", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "You should fill out the all the blank", Toast.LENGTH_LONG).show();

        }


    }




















}
