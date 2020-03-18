package com.example.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LogIn";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth authenticator; // for the log-in, and DB information
    EditText UserE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authenticator = FirebaseAuth.getInstance();
        UserE = (EditText) findViewById(R.id.Address);
        Intent intent = getIntent();
        String str = intent.getStringExtra("message");
        UserE.setText(str);

    }

    public void onStart() {
        super.onStart();

        FirebaseUser user = authenticator.getCurrentUser();
        if(user != null) {
            gotoNext();
        }
    }
    public void onSignup(View view) {
        Intent intent = new Intent(this, SignUpPage.class);
        startActivity(intent);

    }


    public void onLogin(View view) {
        EditText userEM = (EditText) findViewById(R.id.Address);
        final String userName = userEM.getText().toString();
        EditText userPWD = (EditText) findViewById(R.id.passwords);
        final String userPassword = userPWD.getText().toString();
        authenticator.signInWithEmailAndPassword(userName, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = authenticator.getCurrentUser();
                            //gotoNext();
                            gotoNext();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                });

    }
    private void gotoNext() {
        Intent intent = new Intent(this, SellerPage.class);
        startActivity(intent);

    }

    public void skip(View view) {
        Intent intent = new Intent(this, PaymentPage.class);
        startActivity(intent);
    }
//    public void onTesting(){
//        Intent intent = new Intent(this, SellerPage.class);
//        startActivity(intent);
//
//    }

}


