package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class SpaceDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_details);
        TextView Name = findViewById(R.id.nameTextView);
        TextView Price = findViewById(R.id.priceTextView);
        TextView Address = findViewById(R.id.addressTextView);
        TextView Description = findViewById(R.id.descriptionTextView);
        ImageButton pickSpaceButton = findViewById(R.id.pickSpaceButton);

        Intent intent = getIntent();
        String name = intent.getStringExtra("ID");
        String price = intent.getStringExtra("price");
        String address = intent.getStringExtra("address");
        String description = intent.getStringExtra("description");

        Name.setText(name);
        Price.setText(price);
        Address.setText(address);
        Description.setText(description);

        final Intent intent2 = new Intent(this, PaymentPage.class);

        pickSpaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra("ID", name);
                intent2.putExtra("price", price);
                intent2.putExtra("address", address);
                intent2.putExtra("description", description);
                startActivity(intent2);
            }
        });

    }
}
