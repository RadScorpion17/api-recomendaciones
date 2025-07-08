package com.etica.apirecomendaciones.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlaceModel {
    @JsonProperty("id")
    private int id;
    
    @JsonProperty("nombre")
    private String nombre;
    
    @JsonProperty("descuento")
    private double descuento;

    public PlaceModel() {
    }
    
    // Constructor with all fields
    public PlaceModel(int id, String nombre, double descuento) {
        this.id = id;
        this.nombre = nombre;
        this.descuento = descuento;
    }
    
    // Getters and setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public double getDescuento() {
        return descuento;
    }
    
    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
}