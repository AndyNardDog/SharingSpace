package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class PaymentPage extends AppCompatActivity {

    private CalendarPopUp startCalendar;
    private CalendarPopUp endCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        final TextView startDate = (TextView) findViewById(R.id.startDateTextField);
        final TextView endDate = (TextView) findViewById(R.id.endDateTextField);

        ImageButton confirmButton = findViewById(R.id.confirmButton);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCalendar = new CalendarPopUp(startDate);
                startCalendar.showPopUp(v);
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endCalendar = new CalendarPopUp(endDate);
                endCalendar.showPopUp(v);
            }
        });
    }

}
