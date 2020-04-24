package com.example.firebasetest;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class LoadingPopUp {

    public LoadingPopUp() {

    }

    public void showPopUp(View anchor) {
        LayoutInflater inflater = (LayoutInflater) anchor.getContext().getSystemService(anchor.getContext().LAYOUT_INFLATER_SERVICE);

        View loadingView = inflater.inflate(R.layout.loading_pop_up, null);

        final PopupWindow loadingPopUp = new PopupWindow(loadingView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        loadingPopUp.showAtLocation(anchor, 17, 0, 0);
    }
}
