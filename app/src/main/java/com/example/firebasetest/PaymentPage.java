package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

public class PaymentPage extends AppCompatActivity {

    private CalendarPopUp startCalendar;
    private CalendarPopUp endCalendar;
    private LoadingPopUp loading;
    private static final String TAG = "PAY";

    EditText emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        TextView name = findViewById(R.id.ownerText);
        TextView price = findViewById(R.id.costText);
        TextView address = findViewById(R.id.locationText);
        TextView phone = findViewById(R.id.phoneNoText);

        Intent intent = getIntent();

        name.setText("Owner: " + intent.getStringExtra("name"));
        price.setText("(Hourly) Rate: $" + intent.getStringExtra("price"));
        address.setText("Address: " + intent.getStringExtra("address"));


        final TextView startDate = (TextView) findViewById(R.id.startDateTextField);
        final TextView endDate = (TextView) findViewById(R.id.endDateTextField);

        ImageButton confirmButton = findViewById(R.id.confirmButton);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCalendar = new CalendarPopUp(startDate, false);
                startCalendar.showPopUp(v);
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endCalendar = new CalendarPopUp(endDate, true);
                endCalendar.showPopUp(v);
            }
        });

        final Intent intent2 = new Intent(this, PaymentSucess.class);


/////////////////////////////////////////////////////////////////

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(startCalendar == null || endCalendar == null|| startCalendar.getDateSelected().after(endCalendar.getDateSelected())) {
                    Toast invalidRange = Toast.makeText(getApplicationContext(), "Please Pick a Valid Date Range", Toast.LENGTH_SHORT);
                    invalidRange.show();
                } else {
                    loading = new LoadingPopUp();
                    loading.showPopUp(v);
                    findViewById(R.id.payment_page).setAlpha((float) 0.6);
                    emailAddress= (EditText) findViewById(R.id.emailAddress);
                    String email = emailAddress.getText().toString();
                    Intent intent = getIntent();
                    String price = intent.getStringExtra("price");
                    String address = intent.getStringExtra("address");
                    FirebaseFirestore db;
                    db = FirebaseFirestore.getInstance();
                    Query space = db.collection("parkingspace").whereEqualTo("address",address);
                    db.collection("parkingspace")
                            .whereEqualTo("address",address)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {

                                            DocumentReference dr = db.collection("parkingspace").document(document.getId());
                                            dr
                                                    .update("isRented", 1)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d(TAG, "DocumentSnapshot successfully updated!");
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.w(TAG, "Error updating document", e);
                                                        }
                                                    });

                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });



//                    CountDownTimer timer = new CountDownTimer(5000, 1000) {
//                        @Override
//                        public void onTick(long millisUntilFinished) {
//
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            // DOTO: send a confirmation email to user.
//                            Log.d(TAG, "email: "+email);
//
//                            Intent it = new Intent(Intent.ACTION_SEND);
//                            it.setType("text/plain");
//                            it.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
//                            it.putExtra(Intent.EXTRA_SUBJECT, "Payment Successful\n");
//                            it.putExtra(Intent.EXTRA_TEXT, "Hi Guest, Welcome to Space Sharing.\n " +
//                                    "Your parking lot is successfully reserved.\n" +
//                                    "Contact to Customer service if you have any trouble.\n" +
//                                    "Best Regard,\nSharing Space Admin");
//
//                            try {
//
//                                    startActivity(Intent.createChooser(it, "Send mail..."));
//
//                                finish();
//                                Log.i("Finished sending email...", "");
//                            } catch (android.content.ActivityNotFoundException ex) {
//                                Toast.makeText(PaymentPage.this,
//                                        "There is no email client installed.", Toast.LENGTH_SHORT).show();
//                            }
//
//
//                        }
//                    };
//                    timer.start();
                    startActivity(intent2);
                }

            }
        });
    }

}
