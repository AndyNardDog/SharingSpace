package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaymentSucess extends AppCompatActivity {

    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_sucess);

        goBack = (Button) findViewById(R.id.goBack);
    }

    public void goBack (View view){
        Intent go = new Intent(this, Second_Main.class);
        startActivity(go);
    }
}
