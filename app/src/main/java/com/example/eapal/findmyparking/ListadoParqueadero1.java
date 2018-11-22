package com.example.eapal.findmyparking;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;

public class ListadoParqueadero1 extends AppCompatActivity
        implements AdaptadorParqueadero.OnParqueoClickListener, NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView lstParqueo;
    private String db = "usuarios";
    private int MY_LOCATION_REQUEST_CODE;
    private ArrayList<UserParqueadero> users;
    private FirebaseAuth mAuth;
    private LinearLayoutManager llm;
    private DatabaseReference databaseReference;
    private LocationManager locManager;
    private TextView txtNombreNav;
    double lat;
    double lgn;
    private Location loc;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_parqueadero1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.txtNombreNav);
        TextView nav_email = (TextView) hView.findViewById(R.id.txtCorreo);
        navigationView.setNavigationItemSelectedListener(this);
        lstParqueo = findViewById(R.id.lstParqueo1);
        txtNombreNav = (TextView) findViewById(R.id.txtNombreNav);
        users = new ArrayList<>();
        final AdaptadorParqueadero adapter = new AdaptadorParqueadero(users, this);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lstParqueo.setLayoutManager(llm);
        lstParqueo.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        ActivityCompat.requestPermissions(ListadoParqueadero1.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        databaseReference.child(db).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        } else {
           /* locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lat = loc.getLatitude();
            lgn = loc.getLongitude();
            UserParqueadero u = new UserParqueadero(Datos.getId(),"La 84","123456","CRA 6E #98C 40", 3000, new MapMarkers(lat,lgn),"8:00 am a 10 pm","Jorge Aldana","");
            u.guardar();*/
        }
        setSupportActionBar(toolbar);


        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            nav_user.setText(user.getDisplayName());
            nav_email.setText(user.getEmail());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.listado_parqueadero1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mAuth.signOut();
            new CountDownTimer(2000, 1000) {

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {

                    finish();

                }

            }.start();


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

      if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_anunciar) {
            Intent i = new Intent(ListadoParqueadero1.this,Anunciar.class);
            startActivity(i);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onParqueoClick(UserParqueadero u) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
