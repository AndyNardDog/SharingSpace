package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class Account_Setting extends AppCompatActivity {
    ImageView Profileimage;
    EditText name;
    EditText Address;
    EditText PhoneNumber;
    EditText Email;
    Button updateimage;
    Button updateButton;
    public Uri imgUir;
    private static final String TAG = "AccountSetting";

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private FirebaseAuth auth;
    private StorageReference mStorageRef;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__setting);

        auth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("Images"); // Storage for the image


        Profileimage = (ImageView)findViewById(R.id.imageView);
        name = (EditText)findViewById(R.id.editText);
        Address = (EditText)findViewById(R.id.editText3);
        PhoneNumber = (EditText)findViewById(R.id.editText2);
        Email = (EditText)findViewById(R.id.emailaddress);
        updateimage = (Button)findViewById(R.id.imageButton);
        updateButton = (Button)findViewById(R.id.button3);

//        updateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FileUploader();
//                updateInfo();
//
//            }
//        });
//
//        updateimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FileUploader();
//            }
//        });
        updateimage.setOnClickListener(new View.OnClickListener() {
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
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();
                FileUploader();
            }
        });
    }

    private void pickImageFromGallay(){

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            imgUir = data.getData();
            Profileimage.setImageURI(data.getData());
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
//        final StorageReference Ref = mStorageRef.child(System.currentTimeMillis()+ "." + getExtension(imgUir));
//
//        Ref.putFile(imgUir)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        // Get a URL to the uploaded content
////                    Uri downloadUrl = Ref.getDownloadUrl();
//                        Toast.makeText(Account_Setting.this, "Uploading Image Successful", Toast.LENGTH_LONG).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle unsuccessful uploads
//                        // ...
//                    }
//                });
        final StorageReference Ref = mStorageRef.child(System.currentTimeMillis()+ "." + getExtension(imgUir));

        //Uri file = Uri.fromFile(new File("storage/emulated/0/DCIM/Camera/IMG_20200329_023443.jpg"));
        Uri file = Uri.fromFile(new File(getRealFilePath(this,imgUir)));
        UploadTask uploadTask =Ref.putFile(file);
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpg")
                .build();

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.d(TAG, "unsuccessful");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...

                Log.d(TAG, "successful");
            }
        });


    }
    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    public void updateInfo(){
        FirebaseUser user = auth.getCurrentUser();
        String ID = user.getUid();
        String Username = name.getText().toString().trim();
        String addressStr = Address.getText().toString().trim();
      String emailAddress = Email.getText().toString().trim();
      String phone = PhoneNumber.getText().toString().trim();
  //      String imagepath = getRealFilePath(this,imgUir);
        Boolean isRented=true;




        CollectionReference UserInfo = mFirestore.collection("UserInfo");
      UserData userData = new UserData(ID ,Username, addressStr,phone ,emailAddress  );
        UserInfo.add(userData);

    }


}
