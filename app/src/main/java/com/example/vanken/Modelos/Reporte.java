package com.example.vanken.Modelos;

public class Reporte {
    private int id;
    private String nombre;
    private String fecha;
    private String cuerpo;

    public Reporte(int id, String nombre, String fecha, String cuerpo) {
        this.id=id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.cuerpo = cuerpo;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }
}
