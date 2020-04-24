package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class PaymentPage extends AppCompatActivity {

    private CalendarPopUp startCalendar;
    private CalendarPopUp endCalendar;
    private LoadingPopUp loading;


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

        final Intent intent = new Intent(this, CurrentOrder.class);
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

                    CountDownTimer timer = new CountDownTimer(5000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            startActivity(intent);
                        }
                    };
                    timer.start();
                }

            }
        });
    }

}
