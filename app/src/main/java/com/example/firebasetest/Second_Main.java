package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Second_Main extends AppCompatActivity {
    public static int position;
    public List<SpaceModel> Lspaces = new List<SpaceModel>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<SpaceModel> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] a) {
            return null;
        }

        @Override
        public boolean add(SpaceModel spaceModel) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends SpaceModel> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends SpaceModel> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public SpaceModel get(int index) {
            return null;
        }

        @Override
        public SpaceModel set(int index, SpaceModel element) {
            return null;
        }

        @Override
        public void add(int index, SpaceModel element) {

        }

        @Override
        public SpaceModel remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<SpaceModel> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<SpaceModel> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<SpaceModel> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
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

        final Query spaces = fb.collection("parkspaces");

        FirestoreRecyclerOptions<SpaceModel> spacesAvailable = new FirestoreRecyclerOptions.Builder<SpaceModel>()
                .setQuery(spaces, SpaceModel.class)
                .build();

        final Intent intent2 = new Intent(this, SpaceDetails.class);

        adapter = new FirestoreRecyclerAdapter<SpaceModel, SpaceHolder>(spacesAvailable) {
            @Override
            protected void onBindViewHolder(final SpaceHolder spaceHolder, int i, final SpaceModel spaceModel) {
                spaceHolder.setAddress(spaceModel.getAddress());
                spaceHolder.setDescription(spaceModel.getDescription());
                spaceHolder.setPrice(spaceModel.getPrice());
                spaceHolder.setSellerId(spaceModel.getSellerID());

                spaceHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        position = spaceHolder.getAdapterPosition();
                        SpaceModel spaceModel1  = getItem(position);
                        intent2.putExtra("ID",spaceModel1.getSellerID());
                        intent2.putExtra("price",spaceModel1.getPrice());
                        intent2.putExtra("address",spaceModel1.getAddress());
                        intent2.putExtra("description",spaceModel1.getDescription());
                        startActivity(intent2);


                    }
                });
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
//    @Override
//    public void onRestaurantSelected(DocumentSnapshot restaurant) {
//        // Go to the details page for the selected restaurant
//        Intent intent = new Intent(this, SpaceDetail.class);
//        intent.putExtra(SpaceDetail.KEY_RESTAURANT_ID, restaurant.getId());
//
//        startActivity(intent);
//    }
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
