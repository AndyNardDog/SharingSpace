package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//display all parking space whose addresses are valid
//can improve
// 1. add a new field into firestore :boolean isRented; demonstrate whether this parking space has been rented,
// 2. calculate distance from current location to every parking space, only display parking spaces within certain distance, sort by distance

public class ParkingMap extends FragmentActivity {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 12;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private final LatLng mDestinationLatLng = new LatLng(43.0714994, -89.4083679);
    private GoogleMap mMap;
    private static final String TAG = "Map";
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vextorResId){
        Drawable vectorDrable = ContextCompat.getDrawable(context,vextorResId);
        vectorDrable.setBounds(0,0,vectorDrable.getIntrinsicWidth(),vectorDrable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrable.getIntrinsicWidth(),vectorDrable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);

    }

    private List<String> parkaddresses = new ArrayList<>();
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //get address from firestore tostring
        db = FirebaseFirestore.getInstance();
        db.collection("parkingspace")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String   useraddress =  document.getData().get("address").toString();
                                parkaddresses.add(useraddress);
                                Log.d(TAG, "useraddress: "+useraddress);

                                Log.d(TAG, "parkaddresses: "+parkaddresses.size());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        super.onCreate(savedInstanceState);
        //initiate mapfragment, display current location
        setContentView(R.layout.activity_parking_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mapFragment.getMapAsync(googleMap ->{
            mMap = googleMap;
            displayMylocation();
        });

    }

    public void onShowPakingSpace( View view){
        //display all parking space
        List<LatLng> parkspace = getListItems();
        Log.d(TAG, "parkspace: "+parkspace);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mapFragment.getMapAsync(googleMap ->{
            mMap = googleMap;
            for(LatLng l: parkspace) {
                googleMap.addMarker(new MarkerOptions().position(l).title("Destination").icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_local_parking_black_24dp)));
            }
        });

    }


    private void displayMylocation(){
        int permission = ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if(permission== PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        else {
            mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(this, task -> {
                Location mLastKnownLocation = task.getResult();
                if (task.isSuccessful() && mLastKnownLocation != null){
                    LatLng mCurrentLatLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(mCurrentLatLng).title("Current"));

                }
            });
        }
    }
    private List<LatLng> getListItems() {
        // transfer string address to longitude and latitude(if the address is valid)
        Log.d(TAG, "parkaddresses final: "+parkaddresses.size());
        Geocoder coder = new Geocoder(this);
        List<Address> addresslatLng = new ArrayList<>();
        List<LatLng> latLngs = new ArrayList<>();

        try {
            //Get latLng from String
            for (String s : parkaddresses) {
                addresslatLng = coder.getFromLocationName(s, 5);
                //check for null
                Log.d(TAG, "addresslatLng final: "+addresslatLng);
                if (addresslatLng.size() == 0) {

                    Log.d(TAG, "This adress is not valid: "+s);
                    continue;
                } else {
                    latLngs.add(new LatLng(addresslatLng.get(0).getLatitude(), addresslatLng.get(0).getLongitude()));
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Log.d(TAG, "LatLng: "+latLngs.size());
        return latLngs;
    }




    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode ==PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayMylocation();
            }
        }
    }
}
