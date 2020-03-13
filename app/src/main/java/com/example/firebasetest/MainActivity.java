package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth authenticator; // for the log-in, and DB information

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authenticator = FirebaseAuth.getInstance();

    }

    public void onStart() {
        super.onStart();

        FirebaseUser user = authenticator.getCurrentUser();

        userLoggedIn(user);
    }

    public void userLoggedIn(FirebaseUser user) {
        if(user == null) {

        } else {

        }
    }
}


