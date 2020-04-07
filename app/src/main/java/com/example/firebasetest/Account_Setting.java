package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Account_Setting extends AppCompatActivity {
    ImageView Profileimage;
    EditText name;
    EditText Address;
    EditText PhoneNumber;
    EditText Email;
    Button updateimage;
    Button updateButton;

    private FirebaseAuth auth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__setting);

        auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        Profileimage = (ImageView)findViewById(R.id.imageView);
        name = (EditText)findViewById(R.id.editText);
        Address = (EditText)findViewById(R.id.editText3);
        PhoneNumber = (EditText)findViewById(R.id.editText2);
        Email = (EditText)findViewById(R.id.editText3);
        updateimage = (Button)findViewById(R.id.imageButton);
        updateButton = (Button)findViewById(R.id.button3);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void updateInfo(){
        FirebaseUser user = auth.getCurrentUser();
        String ID = user.getUid();
        String Username = name.getText().toString().trim();
        String addressStr = Address.getText().toString().trim();
        String emailAddress = Email.getText().toString().trim();
        String phone = PhoneNumber.getText().toString().trim();


        CollectionReference UserInfo = mFirestore.collection("UserDate");
        UserData userData = new UserData(ID ,Username, addressStr,phone ,emailAddress  );
        UserInfo.add(UserInfo);
    }


}