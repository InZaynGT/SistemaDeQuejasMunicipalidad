package com.example.proyectofinalanalisis;

public class Quejas {
    private String descripcion, imagen, estado, fecha;
    private int idCategoria;

    public Quejas(String descripcion, String imagen, String estado, String fecha, int idCategoria) {
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.estado = estado;
        this.fecha = fecha;
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public String getEstado() {
        return estado;
    }

    public String getFecha() {
        return fecha;
    }

    public int getIdCategoria() {
        return idCategoria;
    }
}
