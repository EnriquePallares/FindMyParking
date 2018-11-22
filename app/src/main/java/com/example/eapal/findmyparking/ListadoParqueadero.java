package com.example.eapal.findmyparking;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListadoParqueadero extends AppCompatActivity implements AdaptadorParqueadero.OnParqueoClickListener,OnMapReadyCallback {
    private RecyclerView lstParqueo;
    private String db = "usuarios";
    private int MY_LOCATION_REQUEST_CODE;
    private ArrayList<UserParqueadero> users;
    private LinearLayoutManager llm;
    private DatabaseReference databaseReference;
    private LocationManager locManager;
    double lat;
    double lgn;
    private Location loc;
    private FusedLocationProviderClient mFusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_parqueadero);
        lstParqueo = findViewById(R.id.lstParqueo);
        users = new ArrayList<>();
        final AdaptadorParqueadero adapter = new AdaptadorParqueadero(users,this);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lstParqueo.setLayoutManager(llm);
        lstParqueo.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        ActivityCompat.requestPermissions(ListadoParqueadero.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        databaseReference.child(db).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                        UserParqueadero u = snapshot.getValue(UserParqueadero.class);
                        users.add(u);
                    }
                }
                adapter.notifyDataSetChanged();
                Datos.setPersonas(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {

        }else
        {
            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lat = loc.getLatitude();
            lgn = loc.getLongitude();
        }
    }

    @Override
    public void onParqueoClick(UserParqueadero u) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}

