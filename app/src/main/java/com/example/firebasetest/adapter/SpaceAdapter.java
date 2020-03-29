package com.example.firebasetest.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebasetest.R;
import com.example.firebasetest.model.Space;
import com.example.firebasetest.util.SpaceUtil;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * RecyclerView adapter for a list of Restaurants.
 */
public class SpaceAdapter extends FirestoreAdapter<SpaceAdapter.ViewHolder> {

    @Override
    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

    }

    public interface OnSpaceSelectedListener {

        void onSpaceSelected(DocumentSnapshot space);
    }

    private OnSpaceSelectedListener mListener;

    public SpaceAdapter(Query query, OnSpaceSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_space, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameView;
       // MaterialRatingBar ratingBar;
        TextView numRatingsView;
        TextView priceView;
        TextView categoryView;
        TextView cityView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.space_item_image);
            nameView = itemView.findViewById(R.id.space_item_name);
            //ratingBar = itemView.findViewById(R.id.space_item_rating);
            numRatingsView = itemView.findViewById(R.id.space_item_num_ratings);
            priceView = itemView.findViewById(R.id.space_item_price);
            categoryView = itemView.findViewById(R.id.space_item_category);
            cityView = itemView.findViewById(R.id.space_item_city);
        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnSpaceSelectedListener listener) {

            Space space = snapshot.toObject(Space.class);
            Resources resources = itemView.getResources();

            // Load image
            Glide.with(imageView.getContext())
                    .load(space.getPhoto())
                    .into(imageView);

            nameView.setText(space.getName());
            //ratingBar.setRating((float) space.getAvgRating());
            cityView.setText(space.getCity());
            categoryView.setText(space.getCategory());
            numRatingsView.setText(resources.getString(R.string.fmt_num_ratings,
                    space.getNumRatings()));
            priceView.setText(SpaceUtil.getPriceString(space));

            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onSpaceSelected(snapshot);
                    }
                }
            });
        }

    }


}
