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
    public Uri imgUir;

    private static final int IMAGE_PICK_CODE = 1000;

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
        name = (EditText)findViewById(R.id.name);
        Address = (EditText)findViewById(R.id.editText3);
        PhoneNumber = (EditText)findViewById(R.id.editText2);
        Email = (EditText)findViewById(R.id.editText3);
        updateimage = (Button)findViewById(R.id.imageButton);
        updateButton = (Button)findViewById(R.id.button3);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUploader();
                updateInfo();

            }
        });

        updateimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUploader();
            }
        });
    }

    private void pickImageFromGallay(){

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);

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
                        Toast.makeText(Account_Setting.this, "Uploading Image Successful", Toast.LENGTH_LONG).show();
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
    public void updateInfo(){
        FirebaseUser user = auth.getCurrentUser();
        String ID = user.getUid();
        String Username = name.getText().toString().trim();
        String addressStr = Address.getText().toString().trim();
      String emailAddress = Email.getText().toString().trim();
      String phone = PhoneNumber.getText().toString().trim();


      CollectionReference UserInfo = mFirestore.collection("UserData");
      UserData userData = new UserData(ID ,Username, addressStr,phone ,emailAddress  );
        UserInfo.add(userData);
    }


}
