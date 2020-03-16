package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpPage extends AppCompatActivity {
    private FirebaseAuth authenticator;
    private static final String TAG = "SignUp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        authenticator = FirebaseAuth.getInstance();
    }

    public void onCreateAccount(View view) {
        EditText userEM = (EditText) findViewById(R.id.email);
        final String userName = userEM.getText().toString();
        EditText userPWD = (EditText) findViewById(R.id.password);
        final String userPassword = userPWD.getText().toString();
        EditText userCFM = (EditText) findViewById(R.id.confirm);
        final String userConfirm = userCFM.getText().toString();

        if (!userConfirm.equals(userPassword)){
            Log.d(TAG, "password:"+userPassword);
            Log.d(TAG, "confirm:"+userConfirm);
            Toast.makeText(SignUpPage.this, "Passwords are different",
                    Toast.LENGTH_SHORT).show();
        } else {
            authenticator.createUserWithEmailAndPassword(userName, userPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = authenticator.getCurrentUser();
                                Intent intent = new Intent(SignUpPage.this,MainActivity.class);
                                authenticator.signOut();
                                intent.putExtra("message",userName);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpPage.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
        }


    }
}
