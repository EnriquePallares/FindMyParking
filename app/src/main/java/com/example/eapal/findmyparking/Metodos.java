package com.example.eapal.findmyparking;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Metodos {
    private static String db = "usuarios";
    private static ArrayList<Usuario> users = new ArrayList<>();

    public static boolean logIn(final String user, String pass) {
        boolean exito = false;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(db).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Usuario u = snapshot.getValue(Usuario.class);
                        users.add(u);
                    }
                }
                Datos.setPersonas(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        for (int i = 0; i < Datos.obtener().size(); i++) {
            if (Datos.obtener().get(i).getCorreo().equals(user) && Datos.obtener().get(i).getPass().equals(pass)) {
                exito = true;
            }
        }
        return exito;
    }
}
