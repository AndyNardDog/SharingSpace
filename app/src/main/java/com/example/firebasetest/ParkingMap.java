package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
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


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//display all parking space whose addresses are valid
//can improve
// 1. add a new field into firestore :boolean isRented; demonstrate whether this parking space has been rented,
// 2. calculate distance from current location to every parking space, only display parking spaces within certain distance, sort by distance

public class ParkingMap extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 12;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private final LatLng mCurrentLatLng = new LatLng(43.0714994, -89.4083679);
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
    private List<String> parkingprice = new ArrayList<>();
    private List<String> parkUID = new ArrayList<>();
    private List<String> parkdescription = new ArrayList<>();
    private List<String> parkimagepath = new ArrayList<>();
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initiate mapfragment, display current location
        setContentView(R.layout.activity_parking_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mapFragment.getMapAsync(googleMap ->{
            mMap = googleMap;
            displayMylocation();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLatLng, 11));

            // Zoom in, animating the camera.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11), 20, null);

        });
        //get address from firestore tostring
        db = FirebaseFirestore.getInstance();
        db.collection("parkingspace")
                .whereEqualTo("isRented",0)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String   useraddress =  document.getData().get("address").toString();
                                String   price =  document.getData().get("price").toString();
                                parkaddresses.add(useraddress);
                                Log.d(TAG, "useraddress: "+useraddress);
                                parkingprice.add(price);
                                Log.d(TAG, "parkaddresses: "+parkaddresses.size());
                                String   description =  document.getData().get("description").toString();
                                parkdescription.add(description);
                                String   imagepath =  document.getData().get("imagepath").toString();
                                parkimagepath.add(imagepath);
                                String   UID =  document.getData().get("sellerID").toString();
                                parkUID.add(UID);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
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
            int i=0;
            for(LatLng l: parkspace) {
                googleMap.addMarker(new MarkerOptions().position(l).title("$" +parkingprice.get(i)).snippet(parkaddresses.get(i)).icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_local_parking_black_24dp)));
                i++;
            }
        });
        mMap.setOnInfoWindowClickListener((GoogleMap.OnInfoWindowClickListener) this);


    }




    private void displayMylocation(){
        int permission = ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if(permission== PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        else {
            mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(this, task -> {
                Location mLastKnownLocation = task.getResult();
                Log.d(TAG, "mLastKnownLocation "+mLastKnownLocation);
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


    @Override
    public void onInfoWindowClick(final Marker marker) {

        // Retrieve the data from the marker.
        marker.getTag();
        String address = marker.getSnippet();
        String sprice = marker.getTitle();
        String price = sprice.substring(1,sprice.length());
        String ID = null;
        String description = null;
        String imagepath = null;
        for(int i = 0; i<parkaddresses.size();i++){
            if(address.equals(parkaddresses.get(i))){
                ID = parkUID.get(i);
                description = parkdescription.get(i);
                imagepath = parkimagepath.get(i);
            }
        }
        final Intent intent2 = new Intent(this, SpaceDetails.class);
        intent2.putExtra("ID",ID);
        intent2.putExtra("price",price);
        intent2.putExtra("address",address);
        intent2.putExtra("description",description);
        intent2.putExtra("image",imagepath);
        startActivity(intent2);



        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).

    }


}
