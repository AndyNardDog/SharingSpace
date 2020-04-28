package com.example.firebasetest;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class SpaceHolder extends RecyclerView.ViewHolder {

    public View view;
    private TextView addressText;
    private TextView descriptionText;
    private TextView priceText;
    private TextView sellerIdText;

    SpaceHolder(View spaceView) {
        super(spaceView);
        view = spaceView;

        addressText = view.findViewById(R.id.Address);
        descriptionText = view.findViewById(R.id.Description);
        priceText = view.findViewById(R.id.Price);
        sellerIdText = view.findViewById(R.id.SellerID);
    }

    public void setAddress(String address) {
        addressText.setText(address);
    }

    public void setDescription(String description) {
        descriptionText.setText(description);
    }

    public void setPrice(String price) { priceText.setText(price); }

    public void setSellerId(String sellerId) { sellerIdText.setText(sellerId); }

}
