package com.example.eapal.findmyparking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Anunciar extends AppCompatActivity {

    private EditText txtNombreParqueadero, txtTelefonoParqueadero, txtPrecioParqueadero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anunciar);

        txtNombreParqueadero = findViewById(R.id.txtParqueo);
        txtTelefonoParqueadero = findViewById(R.id.txtTelePar);
        txtPrecioParqueadero = findViewById(R.id.txtPrecio);
    }

    public boolean validar() {
        if (txtNombreParqueadero.getText().toString().isEmpty()) {
            txtNombreParqueadero.setError(getResources().getString(R.string.error_field_required));
            txtNombreParqueadero.requestFocus();

            return false;
        }
        if (txtTelefonoParqueadero.getText().toString().isEmpty()) {
            txtTelefonoParqueadero.setError(getResources().getString(R.string.error_field_required));
            txtTelefonoParqueadero.requestFocus();

            return false;
        }
        if (Integer.parseInt(txtTelefonoParqueadero.getText().toString()) == 0) {
            txtTelefonoParqueadero.setError(getResources().getString(R.string.error_cero));
            txtTelefonoParqueadero.requestFocus();

            return false;
        }
        if (txtTelefonoParqueadero.getText().length() < 6 || txtTelefonoParqueadero.getText().length() > 15) {
            txtTelefonoParqueadero.setError(getResources().getString(R.string.error_longitud));
            txtTelefonoParqueadero.requestFocus();

            return false;
        }
        if (txtPrecioParqueadero.getText().toString().isEmpty()) {
            txtPrecioParqueadero.setError(getResources().getString(R.string.error_field_required));
            txtPrecioParqueadero.requestFocus();

            return false;
        }
        if (Integer.parseInt(txtPrecioParqueadero.getText().toString()) == 0) {
            txtPrecioParqueadero.setError(getResources().getString(R.string.error_cero));
            txtPrecioParqueadero.requestFocus();

            return false;
        }

        return true;
    }
}
