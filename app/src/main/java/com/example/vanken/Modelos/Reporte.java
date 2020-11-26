package com.example.vanken.Modelos;

public class Reporte {
    private String nombre;
    private String fecha;
    private String cuerpo;

    public Reporte(String nombre, String fecha, String cuerpo) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.cuerpo = cuerpo;
    }

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
