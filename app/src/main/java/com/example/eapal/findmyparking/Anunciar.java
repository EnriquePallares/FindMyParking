package com.example.eapal.findmyparking;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Anunciar extends AppCompatActivity implements View.OnClickListener,ActivityCompat.OnRequestPermissionsResultCallback,LocationListener {

    private EditText txtNombreParqueadero, txtTelefonoParqueadero, txtPrecioParqueadero, txtHorario;
    double lat;
    double lgn;
    private Location loc;
    private LocationManager locManager;
    private FirebaseAuth mAuth;
    private Button ubicarme, registro;
    public String bestProvider;
    private String dueno;
    private Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anunciar);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ubicarme = findViewById(R.id.btnUbicar);
        registro = findViewById(R.id.btnRegistro);
        txtNombreParqueadero = findViewById(R.id.txtParqueo);
        txtTelefonoParqueadero = findViewById(R.id.txtTelePar);
        txtPrecioParqueadero = findViewById(R.id.txtPrecio);
        sp = findViewById(R.id.spHorario);
        registro.setEnabled(false);
        registro.setOnClickListener(this);
        ubicarme.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            dueno = user.getDisplayName();
        }
    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "usa esta app")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
ubicar();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }
    private boolean isLocationEnabled() {
        return locManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }




        public void ubicar() {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            } else {

                Criteria criteria = new Criteria();

                Location location = locManager.getLastKnownLocation(locManager
                        .getBestProvider(criteria, false));
                bestProvider = String.valueOf(locManager.getBestProvider(criteria, true)).toString();

                if (location != null) {
                    lat = location.getLatitude();
                    lgn = location.getLongitude();
                    registro.setEnabled(true);
                    ubicarme.setEnabled(false);
                }
                else{
                    locManager.requestLocationUpdates(bestProvider, 1000, 0,  this);
                }
            }
        }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUbicar:
                ubicar();
                break;
            case R.id.btnRegistro:
                if(validar()){
                    registro();
                }
                break;
        }

    }

    public void registro(){
        String nom = txtNombreParqueadero.getText().toString(), tel = txtTelefonoParqueadero.getText().toString(), horario = sp.getSelectedItem().toString();
        int pre = Integer.parseInt(txtPrecioParqueadero.getText().toString());
        UserParqueadero u = new UserParqueadero(Datos.getId(),nom,tel,"",pre,new MapMarkers(lat,lgn),horario,dueno,"");
        u.guardar();
        Snackbar.make(findViewById(R.id.txtPrecio),getText(R.string.exitoSave), Snackbar.LENGTH_LONG).show();
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
        if (sp.getSelectedItemPosition()==0){
            Toast.makeText(this, getText(R.string.errorSelect), Toast.LENGTH_LONG).show();
        }

        return true;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
