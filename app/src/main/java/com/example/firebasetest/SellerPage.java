package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SellerPage extends AppCompatActivity {

    EditText Address;
    EditText price;
    EditText description;
    Button register;
    private FirebaseAuth auth;
    private FirebaseFirestore mFirestore;
    private static int RESULT_LOAD_IMAGE = 1;
    Button FetchImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_page);

        auth = FirebaseAuth.getInstance();
        mFirestore= FirebaseFirestore.getInstance();


        Address = (EditText) findViewById(R.id.Address);
        price = (EditText) findViewById(R.id.Price);
        description = (EditText) findViewById(R.id.description);
        register = (Button) findViewById(R.id.addButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSellerInfo();
            }
        });
    }

    public void gallary(View view ){
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }

        public void addSellerInfo(){
        FirebaseUser user = auth.getCurrentUser();
        String ID = user.getUid();
        String addressStr = Address.getText().toString().trim();
        String priceStr = price.getText().toString().trim();
        String descriptionStr = description.getText().toString().trim();

        CollectionReference parkspaces = mFirestore.collection("parkspaces");
        sellerData parkspace = new sellerData(ID, addressStr, priceStr, descriptionStr);
        parkspaces.add(parkspace);
    }





}
