package com.example.eapal.findmyparking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListadoParqueadero extends AppCompatActivity implements AdaptadorParqueadero.OnParqueoClickListener{
    private RecyclerView lstParqueo;
    private String db = "usuarios";
    private ArrayList<UserParqueadero> users;
    private LinearLayoutManager llm;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_parqueadero);
        lstParqueo = findViewById(R.id.lstParqueo);
        users = new ArrayList<>();
        final AdaptadorParqueadero adapter = new AdaptadorParqueadero(users,this);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lstParqueo.setLayoutManager(llm);
        lstParqueo.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(db).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot :dataSnapshot.getChildren()){
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
    }

    @Override
    public void onParqueoClick(UserParqueadero u) {

    }
}
