package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Second_Main extends AppCompatActivity {

    private RecyclerView spacesRec;
    private RecyclerView.Adapter spacesAdapter;
    private FirebaseFirestore fb;
    private FirestoreRecyclerAdapter<SpaceModel, SpaceHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second__main);

        fb = FirebaseFirestore.getInstance();

        EditText search = findViewById(R.id.search_word);


        spacesRec = (RecyclerView) findViewById(R.id.spacesRecycler);
        spacesRec.setLayoutManager(new LinearLayoutManager(this));

        Query spaces = fb.collection("parkspaces");

        FirestoreRecyclerOptions<SpaceModel> spacesAvailable = new FirestoreRecyclerOptions.Builder<SpaceModel>()
                .setQuery(spaces, SpaceModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<SpaceModel, SpaceHolder>(spacesAvailable) {
            @Override
            protected void onBindViewHolder(SpaceHolder spaceHolder, int i, SpaceModel spaceModel) {
                spaceHolder.setAddress(spaceModel.getAddress());
                spaceHolder.setDescription(spaceModel.getDescription());
                spaceHolder.setPrice(spaceModel.getPrice());
                spaceHolder.setSellerId(spaceModel.getSellerID());
            }

            @NonNull
            @Override
            public SpaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.space, parent, false);
                return new SpaceHolder(view);
            }

        };

        spacesRec.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
