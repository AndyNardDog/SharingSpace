package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SellerPage extends AppCompatActivity {

    EditText Address;
    EditText price;
    EditText description;
    Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_page);

        Address = (EditText) findViewById(R.id.Address);
        price = (EditText) findViewById(R.id.Price);
        description = (EditText) findViewById(R.id.description);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void addSellerInfo(){

        String addressStr =  Address.getText().toString().trim();
        String priceStr =  price.getText().toString().trim();
        String descriptionStr =  description.getText().toString().trim();


        if(!TextUtils.isEmpty(addressStr) || !TextUtils.isEmpty(priceStr)|| !TextUtils.isEmpty(descriptionStr)){


        }else{
            Toast.makeText(this, "You should fill out the all the blank", Toast.LENGTH_LONG).show();

        }


    }




















}
