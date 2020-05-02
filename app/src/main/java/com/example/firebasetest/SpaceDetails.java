package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SpaceDetails extends AppCompatActivity {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseFirestore db;
    private static final String TAG = "spaceDetail";
    private List<String> UserInfo = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_details);
        TextView Price = findViewById(R.id.priceTextView);
        TextView Address = findViewById(R.id.addressTextView);
        TextView Description = findViewById(R.id.descriptionTextView);
        ImageButton pickSpaceButton = findViewById(R.id.pickSpaceButton);
        ImageView image = findViewById(R.id.imageView3);

        Intent intent = getIntent();
        String ID = intent.getStringExtra("ID");
        String price = intent.getStringExtra("price");
        String address = intent.getStringExtra("address");
        String description = intent.getStringExtra("description");
        String imagepath = intent.getStringExtra("image");
        StorageReference gsReference = storage.getReferenceFromUrl(imagepath);
        db = FirebaseFirestore.getInstance();
        db.collection("UserInfo")
                //.whereEqualTo("userID", ID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String name =  document.getData().get("name").toString();
                                UserInfo.add(name);
                                String email =  document.getData().get("email").toString();
                                UserInfo.add(email);
                                //  String phone =  document.getData().get("phone").toString();
                                //UserInfo.add(phone);
                                Log.d(TAG, "size"+ UserInfo.size());


                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri downloadUrl)
            {
                //Uri c = a.getResult();
                String b = downloadUrl.toString();
                Picasso.get().load(b).into(image);
            }
        });


        Price.setText(price);
        Address.setText(address);
        Description.setText(description);

        final Intent intent2 = new Intent(this, PaymentPage.class);

        pickSpaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent2.putExtra("name", UserInfo.get(0));
                intent2.putExtra("email", UserInfo.get(1));
                intent2.putExtra("ID", ID);
                intent2.putExtra("price", price);
                intent2.putExtra("address", address);
                intent2.putExtra("description", description);
                startActivity(intent2);
            }
        });

    }
}
