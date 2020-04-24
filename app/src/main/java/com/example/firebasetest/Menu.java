package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        ImageButton shareSpaceButton = findViewById(R.id.shareSpotButton);
        ImageButton profileButton = findViewById(R.id.profileButton);
        ImageButton purchasedParkingButton = findViewById(R.id.purchasedParkingButton);
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        ImageButton contactButton = findViewById(R.id.contactInfoButton);
        ImageButton helpButton = findViewById(R.id.helpButton);

    }

    public void onShareSpaceButtonPress(View view) {
        Intent intent = new Intent(this, SellerPage.class);
        startActivity(intent);
    }

    public void onProfileButtonPress(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void onPurchasedParkingButtonPress(View view) {
        Intent intent = new Intent(this, PastParking.class);
        startActivity(intent);
    }

    public void onSettingsButtonPress(View view) {
        Intent intent = new Intent(this, Account_Setting.class);
        startActivity(intent);
    }

    public void onContactButtonPress(View view) {
        Intent intent = new Intent(this, CurrentOrder.class);
        startActivity(intent);
    }

    public void onHelpButtonPress(View view) {
        Intent intent = new Intent(this, Help.class);
        startActivity(intent);
    }
}