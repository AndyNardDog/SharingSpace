package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button shareSpaceButton = (Button) findViewById(R.id.shareSpotButton);
        Button profileButton = (Button) findViewById(R.id.profileButton);
        Button purchasedParkingButton = (Button) findViewById(R.id.purchasedParkingButton);
        Button settingsButton = (Button) findViewById(R.id.settingsButton);
        Button contactButton = (Button) findViewById(R.id.contactInfoButton);
        Button helpButton = (Button) findViewById(R.id.helpButton);

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