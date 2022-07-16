package com.example.PM1_firma;

import android.graphics.Bitmap;

public class ClaseImagenes {

    private String id;
    private String nombre;
    private Bitmap FirmaDigital;

    public ClaseImagenes()
    {
    }

    public ClaseImagenes(String id, String nombre, Bitmap FirmaDigital) {
        this.id = id;
        this.nombre = nombre;
        this.FirmaDigital = FirmaDigital;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Bitmap getFirmaDigital() {
        return FirmaDigital;
    }

    public void setFirmaDigital(Bitmap firmaDigital) {
        FirmaDigital = firmaDigital;
    }
}
