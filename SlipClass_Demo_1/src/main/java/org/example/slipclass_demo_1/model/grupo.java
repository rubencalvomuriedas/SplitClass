package org.example.slipclass_demo_1.model;

import java.util.Date;
import java.util.List;

public class grupo {

    private int id_grupo;
    private String codGrupo;
    private String titulo;
    private String descripcion;
    private String moneda;

    private Date fecha_creacion;
    private Date fecha_eliminacion;
    private List<estadoGrupo> estadoSelec; // Nuevo valor que determinara un grupo como realmente activo hasta que reciba un pago o movimiento

    public grupo(int id_grupo, String codGrupo, String titulo, String descripcion, String moneda, Date fecha_creacion, Date fecha_eliminacion, List<estadoGrupo> estadoSelec) {
        this.id_grupo = id_grupo;
        this.codGrupo = codGrupo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.moneda = moneda;
        this.fecha_creacion = fecha_creacion;
        this.fecha_eliminacion = fecha_eliminacion;
        this.estadoSelec = estadoSelec;
    }

    public grupo(int idGrupo, String codGrupo, String string, String titulo, String descripcion, java.sql.Date fechaCreacion, Date fecha_creacion, int idEstado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.moneda = moneda;
        this.fecha_creacion = fecha_creacion;
        this.fecha_eliminacion = null;
    }

    public grupo(int id_grupo, String codGrupo, String titulo) {
        this.id_grupo = id_grupo;
        this.codGrupo = codGrupo;
        this.titulo = titulo;
    }

    public grupo(String codGrupo, String titulo, String descripcion, String moneda, Date fecha_creacion) {
        this.codGrupo = codGrupo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.moneda = moneda;
        this.fecha_creacion = fecha_creacion;
    }

    public int getId_grupo() {
        return id_grupo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getMoneda() {
        return moneda;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public Date getFecha_eliminacion() {
        return fecha_eliminacion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    @Override
    public String toString() {
        return "grupo{" +
                "id_grupo=" + id_grupo +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", moneda='" + moneda + '\'' +
                ", fecha_creacion=" + fecha_creacion +
                ", fecha_eliminacion=" + fecha_eliminacion +
                '}';
    }

    public String getCodGrupo() {
        return codGrupo;
    }

    public void setCodGrupo(String codGrupo) {
        this.codGrupo = codGrupo;
    }

    public Object getId_Estado() {
        return false;
    }
}
