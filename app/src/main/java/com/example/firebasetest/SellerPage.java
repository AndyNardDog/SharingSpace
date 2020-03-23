package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;


public class SellerPage extends AppCompatActivity {

    EditText Address;
    EditText price;
    EditText description;
    Button register;
    Button chooseImage;
    Boolean isRented = false;
    private FirebaseAuth auth;
    private FirebaseFirestore mFirestore;
    private StorageReference mStorageRef;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    ImageView View;
    public Uri imgUir;
//    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_page);

        auth = FirebaseAuth.getInstance();
        mFirestore= FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("Images"); // Storage for the image

        Address = (EditText) findViewById(R.id.Address);
        price = (EditText) findViewById(R.id.Price);
        description = (EditText) findViewById(R.id.description);
        register = (Button) findViewById(R.id.addButton);
        chooseImage = (Button)  findViewById(R.id.image);
        View = (ImageView) findViewById(R.id.imageView);


        chooseImage.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               // Check runtime permission
                                               if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                                   if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                                           == PackageManager.PERMISSION_DENIED){
                                                       // permission not granted. then request it
                                                       String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                                                       requestPermissions(permissions, PERMISSION_CODE);

                                                   }else{
                                                       //permition already granted
                                                       pickImageFromGallay();

                                                   }
                                               }else{
                                                   // OS is less than marshmello
                                                   pickImageFromGallay();
                                               }
                                           }
                                       }
        );


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUploader();
                addSellerInfo();
            }
        });

    }


    private void pickImageFromGallay(){

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE && data != null){
            imgUir = data.getData();
            View.setImageURI(data.getData());
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallay();
                }else{
                    Toast.makeText(this,"Permission denied!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }

    private void FileUploader(){
        final StorageReference Ref = mStorageRef.child(System.currentTimeMillis()+ "." + getExtension(imgUir));

        Ref.putFile(imgUir)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
//                    Uri downloadUrl = Ref.getDownloadUrl();
                        Toast.makeText(SellerPage.this, "Uploading Image Successful", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });

    }

    public void addSellerInfo(){
        FirebaseUser user = auth.getCurrentUser();
        String ID = user.getUid();
        String addressStr = Address.getText().toString().trim();
        String priceStr = price.getText().toString().trim();
        String descriptionStr = description.getText().toString().trim();
        isRented =true;

        CollectionReference parkspaces = mFirestore.collection("parkspaces");
        sellerData parkspace = new sellerData(ID, addressStr, priceStr, descriptionStr, isRented);
        parkspaces.add(parkspace);
    }





}



