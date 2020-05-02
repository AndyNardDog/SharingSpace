package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class SpaceDetails extends AppCompatActivity {
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_details);
        TextView Name = findViewById(R.id.nameTextView);
        TextView Price = findViewById(R.id.priceTextView);
        TextView Address = findViewById(R.id.addressTextView);
        TextView Description = findViewById(R.id.descriptionTextView);
        ImageButton pickSpaceButton = findViewById(R.id.pickSpaceButton);
        ImageView image = findViewById(R.id.imageView3);

        Intent intent = getIntent();
        String name = intent.getStringExtra("ID");
        String price = intent.getStringExtra("price");
        String address = intent.getStringExtra("address");
        String description = intent.getStringExtra("description");
        String imagepath = intent.getStringExtra("image");
        StorageReference gsReference = storage.getReferenceFromUrl(imagepath);

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



        Name.setText(name);
        Price.setText(price);
        Address.setText(address);
        Description.setText(description);

        final Intent intent2 = new Intent(this, PaymentPage.class);

        pickSpaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra("ID", name);
                intent2.putExtra("price", price);
                intent2.putExtra("address", address);
                intent2.putExtra("description", description);
                startActivity(intent2);
            }
        });

    }
}
