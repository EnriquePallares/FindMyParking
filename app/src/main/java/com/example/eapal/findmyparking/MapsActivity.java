package com.example.eapal.findmyparking;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private int MY_LOCATION_REQUEST_CODE;
    private GoogleMap mMap;
    private DatabaseReference databaseReference;
    private FusedLocationProviderClient mFusedLocationClient;
    private ArrayList<Marker> tmpTouchMarkers = new ArrayList<>();
    private ArrayList<MapMarkers> touchMarkers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ubicacionActual();
        setMarkers();
        viewMarkers();
    }

    public void ubicacionActual() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {

            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_LOCATION_REQUEST_CODE);
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double lat = location.getLatitude();
                            double lgn = location.getLongitude();

                            LatLng myPosition = new LatLng(lat, lgn);
                            mMap.addMarker(new MarkerOptions().position(myPosition).title("Ubicación Actual"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 16));
                        }
                    }
                });
    }

    public void setMarkers() {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(point.latitude, point.longitude));
                tmpTouchMarkers.add(mMap.addMarker(markerOptions));

                Map<String, Object> latlgn = new HashMap<>();
                latlgn.put("lat", point.latitude);
                latlgn.put("lgn", point.longitude);

                databaseReference.child("direcciones").push().setValue(latlgn);
            }
        });
    }

    public void viewMarkers() {
        databaseReference.child("direcciones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (Marker marker: tmpTouchMarkers){
                    marker.remove();
                }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    MapMarkers mm = snapshot.getValue(MapMarkers.class);
                    Double lat = mm.getLat();
                    Double lgn = mm.getLgn();
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(lat, lgn));

                    touchMarkers.add(mm);
                    tmpTouchMarkers.add(mMap.addMarker(markerOptions));
                }
                Datos.setMarcadores(touchMarkers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
