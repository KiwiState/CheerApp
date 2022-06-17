package com.example.cheerapp;

public class JsonHelp {
    private String titulo;
    private String contacto;
    private String descripcion;
    private String URL;



    public JsonHelp(String titulo, String contacto, String descripcion, String URL) {
        this.titulo = titulo;
        this.contacto = contacto;
        this.descripcion = descripcion;
        this.URL = URL;
    }

    public String getURL() { return URL;}

    public void setURL(String URL) {this.URL = URL;}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
