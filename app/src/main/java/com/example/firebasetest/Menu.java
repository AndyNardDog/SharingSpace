package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageButton shareSpaceButton = findViewById(R.id.shareSpotButton);
        ImageButton profileButton = findViewById(R.id.profileButton);
        ImageButton purchasedParkingButton = findViewById(R.id.purchasedParkingButton);
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        ImageButton contactButton = findViewById(R.id.contactInfoButton);
        ImageButton helpButton = findViewById(R.id.helpButton);

    }

    private void onShareSpaceButtonPress(View view) {

    }

    private void onProfileButtonPress(View view) {

    }

    private void onPurchasedParkingButtonPress(View view) {

    }

    private void onSettingsButtonPress(View view) {

    }

    private void onContactButtonPress(View view) {

    }

    private void onHelpButtonPress(View view) {

    }
}