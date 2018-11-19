package com.example.eapal.findmyparking;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Registro extends AppCompatActivity implements View.OnClickListener {
    private EditText cedula, nombre, apellido, correo, telefono, fechaN, pass;
    private int rol;
    private Button registro;
    private Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        cedula = findViewById(R.id.txtCedula);
        nombre = findViewById(R.id.txtNombre);
        apellido = findViewById(R.id.txtApellido);
        correo = findViewById(R.id.txtCorreo);
        telefono = findViewById(R.id.txtTelefono);
        fechaN = findViewById(R.id.txtFecha);
        pass = findViewById(R.id.txtPass);
        sp = findViewById(R.id.opcRol);
        registro = findViewById(R.id.btnRegistro);

        registro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegistro:
                String ced = cedula.getText().toString(), nom = nombre.getText().toString(),
                        ape = apellido.getText().toString(), cor = correo.getText().toString(),
                        tele = telefono.getText().toString(), fecha = fechaN.getText().toString(),
                        passw = pass.getText().toString(), id = Datos.getId();
                int rol = sp.getSelectedItemPosition();

                Usuario u = new Usuario(id, ced,nom,ape,cor,tele, fecha, passw, rol);
                u.guardar();
                Snackbar.make(v,getResources().getText(R.string.exitoRegistro), Snackbar.LENGTH_LONG).show();
                break;
        }
    }
}
