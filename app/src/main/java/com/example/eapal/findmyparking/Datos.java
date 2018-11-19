package com.example.eapal.findmyparking;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Datos {
    private static String db ="usuarios";

    private static DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference();

    public static ArrayList<Usuario> user = new ArrayList<>();

    public static void agregar(Usuario p){

        databaseReference.child(db).child(p.getId()).setValue(p);
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

    public static void setuser(ArrayList<Usuario> user){
        Datos.user = user;
    }

    public static ArrayList<Usuario> obtener(){
        return user;
    }

    public static void setPersonas(ArrayList<Usuario> usuarios){
        Datos.user= usuarios;
    }
}
