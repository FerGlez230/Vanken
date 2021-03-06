package com.example.vanken.Modelos;

public class Persona {
private String nombre;
private String apellido;
private String numero;
private int id;
private String calificacion;

    public Persona(int id, String nombre, String apellido, String numero, String calificacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numero = numero;
        this.id = id;
        this.calificacion = calificacion;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
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
