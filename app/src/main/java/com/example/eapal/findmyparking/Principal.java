package com.example.eapal.findmyparking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Principal extends AppCompatActivity implements View.OnClickListener {

    private Button btnIngresar, btnRegistrar;
    private EditText correo, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        correo = findViewById(R.id.txtEmail);
        pass = findViewById(R.id.txtPass);

        btnIngresar.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIngresar:
                String cor = correo.getText().toString(), pas = pass.getText().toString();
                if (Metodos.logIn(cor, pas)){
                    Intent intent = new Intent(Principal.this, MapsActivity.class);
                    startActivity(intent);
                }else{
                    Snackbar.make(v, getResources().getText(R.string.errorLogin), Snackbar.LENGTH_LONG).show();
                }
                break;

            case R.id.btnRegistrar:
                Intent intent2 = new Intent(Principal.this, Registro.class);
                startActivity(intent2);
                break;
        }
    }
}
