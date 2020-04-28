package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends AppCompatActivity {
    TextView textView2;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        auth = FirebaseAuth.getInstance();

    }

    public void onStart() {
        super.onStart();

        FirebaseUser user = auth.getCurrentUser();

        if(user != null) {
            textView2 = (TextView) findViewById(R.id.textView2);
            textView2.setText("Hello " + user.getEmail());
        }
    }
    public void onLogout(View view) {
        auth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void sellerpage(View view){
        Intent intent = new Intent(this, SellerPage.class);
        startActivity(intent);
    }
    public void SecondMain(View view) {
        Intent intent = new Intent(this, Second_Main.class);
        startActivity(intent);
    }
    public void AccountSetting(View view) {
        Intent intent = new Intent(this, Account_Setting.class);
        startActivity(intent);
    }
    public void Map(View view) {
        Intent intent = new Intent(this, ParkingMap.class);
        startActivity(intent);
    }
}