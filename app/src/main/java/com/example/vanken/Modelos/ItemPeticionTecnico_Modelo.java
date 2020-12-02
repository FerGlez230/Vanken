package com.example.vanken.Modelos;

public class ItemPeticionTecnico_Modelo {

    private String cliente;
    private String domicilio;
    private String fecha;
    private int numServicio;
    private String categoria;
    private long longitud;
    private long latitud;
    private String comentario;

    public ItemPeticionTecnico_Modelo() {
        this.cliente = " ";
        this.domicilio = " ";
        this.fecha = " ";
        this.numServicio = 0;
        this.categoria = " ";
        this.longitud = 0;
        this.latitud = 0;
        this.comentario = " ";
    }

    public ItemPeticionTecnico_Modelo(String cliente, String domicilio, String fecha, int numServicio, String categoria, long longitud, long latitud, String comentario) {
        this.cliente = cliente;
        this.domicilio = domicilio;
        this.fecha = fecha;
        this.numServicio = numServicio;
        this.categoria = categoria;
        this.longitud = longitud;
        this.latitud = latitud;
        this.comentario = comentario;
    }

    public String getCliente() { return cliente; }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNumServicio() {
        return numServicio;
    }

    public void setNumServicio(int numServicio) {
        this.numServicio = numServicio;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public long getLongitud() {
        return longitud;
    }

    public void setLongitud(long longitud) {
        this.longitud = longitud;
    }

    public long getLatitud() {
        return latitud;
    }

    public void setLatitud(long latitud) {
        this.latitud = latitud;
    }
}
