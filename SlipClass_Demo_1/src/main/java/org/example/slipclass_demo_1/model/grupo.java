package org.example.slipclass_demo_1.model;

import java.util.Date;
import java.util.List;

/**
 * Representa un grupo de gastos dentro de la aplicación SplitClass.
 * Contiene la información básica del grupo, sus fechas de gestión y permite
 * la asociación de miembros y movimientos financieros.
 *
 * @author TuNombre
 * @version 1.0
 */
public class grupo {

    private int id_grupo;
    private String codGrupo;
    private String titulo;
    private String descripcion;
    private String moneda;
    private Date fecha_creacion;
    private Date fecha_eliminacion;
    private List<estadoGrupo> estadoSelec;

    /**
     * Constructor completo para grupos existentes recuperados de la base de datos.
     *
     * @param id_grupo ID único del grupo.
     * @param codGrupo Código alfanumérico identificativo.
     * @param titulo Nombre del grupo.
     * @param descripcion Detalles adicionales sobre el grupo.
     * @param moneda Divisa utilizada en el grupo (ej. EUR).
     * @param fecha_creacion Fecha de creación del grupo.
     * @param fecha_eliminacion Fecha en que se eliminó el grupo (si aplica).
     * @param idEstado ID del estado actual.
     */
    public grupo(int id_grupo, String codGrupo, String titulo, String descripcion, String moneda, Date fecha_creacion, Date fecha_eliminacion, int idEstado) {
        this.id_grupo = id_grupo;
        this.codGrupo = codGrupo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.moneda = moneda;
        this.fecha_creacion = fecha_creacion;
        this.fecha_eliminacion = fecha_eliminacion;
    }

    /**
     * Constructor simplificado para referencias rápidas de grupos.
     * @param id_grupo ID único.
     * @param codGrupo Código identificativo.
     * @param titulo Nombre del grupo.
     */
    public grupo(int id_grupo, String codGrupo, String titulo) {
        this.id_grupo = id_grupo;
        this.codGrupo = codGrupo;
        this.titulo = titulo;
    }

    /**
     * Constructor para la creación de nuevos grupos sin ID asignado aún.
     * @param codGrupo Código identificativo.
     * @param titulo Nombre del grupo.
     * @param descripcion Descripción.
     * @param moneda Divisa.
     * @param fecha_creacion Fecha de creación.
     * @param fecha_eliminacion Fecha de eliminación.
     * @param idEstado ID de estado inicial.
     */
    public grupo(String codGrupo, String titulo, String descripcion, String moneda, Date fecha_creacion, Date fecha_eliminacion, int idEstado) {
        this.codGrupo = codGrupo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.moneda = moneda;
        this.fecha_creacion = fecha_creacion;
        this.fecha_eliminacion = fecha_eliminacion;
    }

    /**
     * Constructor vacío para inicialización por defecto.
     */
    public grupo() {}

    /** @return ID único del grupo. */
    public int getId_grupo() { return id_grupo; }

    /** @return Título o nombre del grupo. */
    public String getTitulo() { return titulo; }

    /** @return Descripción del grupo. */
    public String getDescripcion() { return descripcion; }

    /** @return Moneda utilizada en el grupo. */
    public String getMoneda() { return moneda; }

    /** @return Fecha de creación. */
    public Date getFecha_creacion() { return fecha_creacion; }

    /** @return Fecha de eliminación (si existe). */
    public Date getFecha_eliminacion() { return fecha_eliminacion; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public void setMoneda(String moneda) { this.moneda = moneda; }

    /** @return Código alfanumérico del grupo. */
    public String getCodGrupo() { return codGrupo; }

    public void setCodGrupo(String codGrupo) { this.codGrupo = codGrupo; }

    /**
     * Obtiene el estado actual del grupo.
     * @return El estado del grupo.
     */
    public Object getId_Estado() { return false; }
}