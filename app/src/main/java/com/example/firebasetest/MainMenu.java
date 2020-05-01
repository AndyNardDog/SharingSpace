package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenu extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        auth = FirebaseAuth.getInstance();

        ImageButton shareSpaceButton = findViewById(R.id.shareSpotButton);
        ImageButton findParkingButton = findViewById(R.id.findParkingButton);
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        ImageButton contactButton = findViewById(R.id.mapButton);
    }

    public void onStart() {
        super.onStart();

        FirebaseUser user = auth.getCurrentUser();

//        if(user != null) {
//            usernameText = (TextView) findViewById(R.id.);
//            usernameText.setText("Hello " + user.getEmail());
//        }
    }
    public void onLogout(View view) {
        auth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onShareSpaceButtonPress(View view) {
        Intent intent = new Intent(this, SellerPage.class);
        startActivity(intent);
    }

    public void onFindSpaceButtonPress(View view) {
        Intent intent = new Intent(this, Second_Main.class);
        startActivity(intent);
    }

    public void onSettingsButtonPress(View view) {
        Intent intent = new Intent(this, Account_Setting.class);
        startActivity(intent);
    }

    public void onMapButtonPress(View view) {
        Intent intent = new Intent(this, ParkingMap.class);
        startActivity(intent);
    }

}
