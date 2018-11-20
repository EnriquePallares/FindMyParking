package com.example.eapal.findmyparking;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Datos {
    private static String db ="usuarios";
    private static String dbDirections ="direcciones";

    private static DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference();

    public static ArrayList<Usuario> user = new ArrayList<>();
    public static ArrayList<MapMarkers> markers = new ArrayList<>();

    public static void agregar(Usuario p){
        databaseReference.child(db).child(p.getId()).setValue(p);
    }

    public static void agregarMarcador(MapMarkers m){
        databaseReference.child(dbDirections).child(m.getId()).setValue(m);
    }

    public static void eliminar(Usuario p){
        databaseReference.child(db).child(p.getId()).removeValue();
    }

    public static void editar(Usuario p){
        databaseReference.child(db).child(p.getId()).setValue(p);
    }

    public static String getId(){
        return databaseReference.push().getKey();
    }

    public static ArrayList<Usuario> obtener(){
        return user;
    }

    public static ArrayList<MapMarkers> obtenerMarcadores(){
        return markers;
    }

    public static void setPersonas(ArrayList<Usuario> usuarios){
        Datos.user= usuarios;
    }

    public static void setMarcadores(ArrayList<MapMarkers> marker){
        Datos.markers= marker;
    }
}
