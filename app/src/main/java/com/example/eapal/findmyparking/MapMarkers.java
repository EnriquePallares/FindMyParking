package com.example.eapal.findmyparking;

public class MapMarkers {

    private String id;
    private double lat;
    private double lgn;

    public MapMarkers() {
    }

    public MapMarkers(String id, double lat, double lgn) {
        this.id = id;
        this.lat = lat;
        this.lgn = lgn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLgn() {
        return lgn;
    }

    public void setLgn(double lgn) {
        this.lgn = lgn;
    }

    public void guardar(){
        Datos.agregarMarcador(this);
    }
}
