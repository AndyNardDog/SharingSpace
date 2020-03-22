package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth authenticator; // for the log-in, and DB information

    public void clickFunction(View view) {
        goToActivity2();
    }

    public void goToActivity2(){
        Intent intent = new Intent(this, Main2Activity.class );
        startActivity(intent);
    }

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
            Button login = (Button) findViewById(R.id.login);
            Button signup = (Button) findViewById(R.id.signup);
        } else {

        }
    }

    public void onLogin() {

    }

    public void onSignUp() {

    }
}


