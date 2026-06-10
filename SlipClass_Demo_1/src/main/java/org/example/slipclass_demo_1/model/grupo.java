package org.example.slipclass_demo_1.model;

import java.util.Date;

public class grupo {
    private int id_grupo;
    private String codGrupo;
    private String titulo;
    private String descripcion;
    private String moneda;
    private Date fecha_creacion;
    private Date fecha_eliminacion;
    private int idEstado; // Añadido para que coincida con tus 8 parámetros

    // CONSTRUCTOR VACÍO (Requerido por JavaFX)
    public grupo() {}

    // CONSTRUCTOR DE 8 PARÁMETROS (Este es el que te pide SQLModelGrupo)
    public grupo(int id_grupo, String codGrupo, String titulo, String descripcion, String moneda, Date fecha_creacion, Date fecha_eliminacion, int idEstado) {
        this.id_grupo = id_grupo;
        this.codGrupo = codGrupo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.moneda = moneda;
        this.fecha_creacion = fecha_creacion;
        this.fecha_eliminacion = fecha_eliminacion;
        this.idEstado = idEstado;
    }

    // GETTERS Y SETTERS
    public int getId_grupo() { return id_grupo; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getMoneda() { return moneda; }
    public Date getFecha_creacion() { return fecha_creacion; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
}