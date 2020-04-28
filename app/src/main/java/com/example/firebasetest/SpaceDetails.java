package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SpaceDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_details);
        TextView Name = findViewById(R.id.textView3);
        TextView Price = findViewById(R.id.textView4);
        TextView Address = findViewById(R.id.textView5);
        TextView Description = findViewById(R.id.textView6);
        Intent intent = getIntent();
        String name = intent.getStringExtra("ID");
        String price = intent.getStringExtra("price");
        String address = intent.getStringExtra("address");
        String description = intent.getStringExtra("description");
        Name.setText(name);
        Price.setText(price);
        Address.setText(address);
        Description.setText(description);




    }
}
