package com.example.eapal.findmyparking;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorParqueadero extends RecyclerView.Adapter<AdaptadorParqueadero.ParqueaderoViewHolder>{
    private ArrayList<UserParqueadero> parqueadero;
    private OnParqueoClickListener clickListener;
    
    public AdaptadorParqueadero(ArrayList<UserParqueadero> parqueadero, OnParqueoClickListener clickListener){
        this.parqueadero=parqueadero;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public ParqueaderoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_parqueo,parent,false);
        return new ParqueaderoViewHolder(v);
    }

    public static class ParqueaderoViewHolder extends RecyclerView.ViewHolder{
        private ImageView foto;
        private TextView dir;
        private TextView nombre;
        private TextView dueno;
        private TextView precio;
        private View v;

        public ParqueaderoViewHolder(View itemView){
            super(itemView);
            v = itemView;
            foto = v.findViewById(R.id.foto);
            dueno = v.findViewById(R.id.lblDueno);
            nombre = v.findViewById(R.id.lblNombre);
            precio = v.findViewById(R.id.lblPrecio);
            dir = v.findViewById(R.id.lblDireccion);
        }
    }
    @Override
    public void onBindViewHolder(final AdaptadorParqueadero.ParqueaderoViewHolder holder, int i) {
        final UserParqueadero u = parqueadero.get(i);
        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference();
        /*storageReference.child(u.getFoto()).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(holder.foto);
                    }
                });*/
        //holder.foto.setImageResource(p.getFoto());
        holder.dueno.setText(u.getDueno());
        holder.nombre.setText(u.getNombreParqueo());
        holder.dir.setText(u.getDir());
        holder.precio.setText(""+u.getPrecio());

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onParqueoClick(u);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parqueadero.size();
    }

    public interface OnParqueoClickListener{
        void onParqueoClick(UserParqueadero u);
    }
}
