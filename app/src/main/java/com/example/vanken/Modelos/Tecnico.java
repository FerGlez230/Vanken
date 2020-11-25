package com.example.vanken.Modelos;

public class Tecnico {
private String nombre;
private String numero;
private double calificacion;

    public Tecnico(String nombre, String numero, double calificacion) {
        this.nombre = nombre;
        this.numero = numero;
        this.calificacion = calificacion;
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
