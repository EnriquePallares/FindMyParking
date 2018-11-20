package com.example.eapal.findmyparking;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Datos {
    private static String db ="usuarios";

    private static DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference();
    public static ArrayList<MapMarkers> markers = new ArrayList<>();

    public static ArrayList<UserParqueadero> user = new ArrayList<>();

    public static void agregar(UserParqueadero p){
        databaseReference.child(db).child(p.getId()).setValue(p);
    }

    public static void eliminar(UserParqueadero p){
        databaseReference.child(db).child(p.getId()).removeValue();
    }

    public static void editar(UserParqueadero p){
        databaseReference.child(db).child(p.getId()).setValue(p);
    }

    public static String getId(){
        return databaseReference.push().getKey();
    }

    public static ArrayList<UserParqueadero> obtener(){
        return user;
    }

    public static void agregarMarcador(MapMarkers m){
        databaseReference.child(db).child(m.getId()).setValue(m);
    }

    public static void setPersonas(ArrayList<UserParqueadero> userParqueaderos){
        Datos.user= userParqueaderos;
    }

    public static void setMarcadores(ArrayList<MapMarkers> marker){
        Datos.markers= marker;
    }
}
