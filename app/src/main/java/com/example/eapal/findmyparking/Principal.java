package com.example.eapal.findmyparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Principal extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);




    }
    public void ingresar(View v){
        Intent intent = new Intent(Principal.this, MapsActivity.class);
        startActivity(intent);
    }

    public void registrarse(View v){
        Intent i = new Intent(Principal.this, Registro.class);
        startActivity(i);
    }
}
