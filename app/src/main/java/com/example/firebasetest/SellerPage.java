package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SellerPage extends AppCompatActivity {

    EditText Address;
    EditText price;
    EditText description;
    Button register;
    private FirebaseAuth auth;
    private FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_page);

        auth = FirebaseAuth.getInstance();
        mFirestore= FirebaseFirestore.getInstance();


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

    public void addSellerInfo(){
        FirebaseUser user = auth.getCurrentUser();
        String ID = user.getUid();
        String addressStr = Address.getText().toString().trim();
        String priceStr = price.getText().toString().trim();
        String descriptionStr = description.getText().toString().trim();

        CollectionReference parkspaces = mFirestore.collection("parkspaces");
        sellerData parkspace = new sellerData(ID, addressStr, priceStr, descriptionStr);
        parkspaces.add(parkspace);
    }





}
