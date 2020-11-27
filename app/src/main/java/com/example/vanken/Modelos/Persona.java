package com.example.vanken.Modelos;

public class Persona {
private String nombre;
private String numero;
private int id;
private double calificacion;

    public Persona(int id, String nombre, String numero, double calificacion) {
        this.id=id;
        this.nombre = nombre;
        this.numero = numero;
        this.calificacion = calificacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
