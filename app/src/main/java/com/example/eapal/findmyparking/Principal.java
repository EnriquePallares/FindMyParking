package com.example.eapal.findmyparking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Principal extends AppCompatActivity implements View.OnClickListener {

    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    private FusedLocationProviderClient mFusedLocationClient;
    private DatabaseReference databaseReference;
    private Button btnIngresar, btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrar = findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);

        subirLatLgnToFirebase();
    }

    private void subirLatLgnToFirebase() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Principal.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.e("Posición:", "Latitud: "+location.getLatitude()+" Longitud: "+location.getLongitude());

                            Map<String, Object> latlgn = new HashMap<>();
                            latlgn.put("lat", location.getLatitude());
                            latlgn.put("lgn", location.getLongitude());

                            databaseReference.child("usuarios").push().setValue(latlgn);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIngresar:
                Intent intent = new Intent(Principal.this, MapsActivity.class);
                startActivity(intent);
                break;

            case R.id.btnRegistrar:
                Intent intent2 = new Intent(Principal.this, Registro.class);
                startActivity(intent2);
                break;
        }
    }
}
