package com.example.eapal.findmyparking;

import java.util.Date;

public class UserParqueadero {
    private String id;
    private String nombreParqueo;
    private String telefono;
    private String dir;
    private int Precio;
    private MapMarkers map;
    private String horario;
    private String dueno;
    private String foto;

    public UserParqueadero() {

    }

    public UserParqueadero(String id, String nombreParqueo, String telefono, String dir, int precio, MapMarkers map, String horario, String dueno, String foto) {
        this.id = id;
        this.nombreParqueo = nombreParqueo;
        this.telefono = telefono;
        this.dir = dir;
        Precio = precio;
        this.map = map;
        this.horario = horario;
        this.dueno = dueno;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreParqueo() {
        return nombreParqueo;
    }

    public void setNombreParqueo(String nombreParqueo) {
        this.nombreParqueo = nombreParqueo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int precio) {
        Precio = precio;
    }

    public MapMarkers getMap() {
        return map;
    }

    public void setMap(MapMarkers map) {
        this.map = map;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDueno() {
        return dueno;
    }

    public void setDueno(String dueno) {
        this.dueno = dueno;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void guardar(){
        Datos.agregar(this);
    }

    public void eliminar(){Datos.eliminar(this);}

    public void editar(){Datos.editar(this);}
}
