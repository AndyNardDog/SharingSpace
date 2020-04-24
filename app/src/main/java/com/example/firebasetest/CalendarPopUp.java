package com.example.firebasetest;

import android.content.Context;
import android.view.View;
import android.widget.CalendarView;
import android.widget.PopupWindow;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

public class CalendarPopUp {

    private CalendarView calendar;
    private TextView dateText;
    private Date dateSelected;
    private Context paymentPageContext;
    private boolean endCalendar;


    public CalendarPopUp(TextView dateText, boolean endCalendar) {
        this.dateText = dateText;
        this.endCalendar = endCalendar;
    }

    public void showPopUp(View anchor) {
        LayoutInflater inflater = (LayoutInflater) anchor.getContext().getSystemService(anchor.getContext().LAYOUT_INFLATER_SERVICE);

        //LayoutInflater inflater = LayoutInflater.from(anchor.getContext());
        View calendarView = inflater.inflate(R.layout.calendar_pop_up, null);

        paymentPageContext = anchor.getContext();

        final PopupWindow calendarPopUp = new PopupWindow(calendarView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        calendar = calendarView.findViewById(R.id.calendarView);

        //calendarPopUp.showAsDropDown(anchor);
        calendarPopUp.showAtLocation(anchor, 17, 0, 0);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                Calendar dateChosen = Calendar.getInstance();
                dateChosen.set(year, month, dayOfMonth);

                Date today = Calendar.getInstance().getTime();
                Date dateChosenDate = dateChosen.getTime();

                if(dateChosenDate.after(today)) {

                    dateSelected = dateChosenDate;

                    String date = "";

                    month++;

                    String monthString = Integer.toString(month);
                    String dayOfMonthString = Integer.toString(dayOfMonth);
                    String yearString = Integer.toString(year);

                    if(month < 10) {
                        monthString = "0" + monthString;
                    }

                    if(dayOfMonth < 10) {
                        dayOfMonthString = "0" + dayOfMonthString;
                    }

                    date = monthString + "/" + dayOfMonthString + "/" + yearString;

                    dateText.setText(date);

                    calendarPopUp.dismiss();

                } else {
                    Toast invalidDate = Toast.makeText(paymentPageContext, "Please Select a Valid Date", Toast.LENGTH_SHORT);
                    invalidDate.show();
                }
            }
        });

    }

    public Date getDateSelected() {
        return dateSelected;
    }

}
