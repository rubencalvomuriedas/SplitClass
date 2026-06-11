package org.example.slipclass_demo_1.model;

/**
 * Representa el estado actual de un grupo dentro de la aplicación SplitClass.
 * Define las fases por las que puede pasar un grupo (ej. Borrador, Activo, Archivado, Eliminado).
 * * @author TuNombre
 * @version 1.0
 */
public class estadoGrupo {

    private int id_estado;
    private String nombreEstado;

    /**
     * Constructor para crear una instancia de estado de grupo.
     * * @param nombre El nombre o descripción del estado.
     */
    public estadoGrupo(String nombre) {
        this.nombreEstado = nombre;
    }

    /**
     * Obtiene el identificador único del estado.
     * * @return El ID del estado.
     */
    public int getId_estado() {
        return id_estado;
    }

    /**
     * Obtiene el nombre descriptivo del estado.
     * * @return El nombre del estado.
     */
    public String getNombre() {
        return nombreEstado;
    }

    /**
     * Devuelve una representación textual detallada del estado del grupo.
     * * @return Una cadena con el ID y el nombre del estado.
     */
    @Override
    public String toString() {
        return "EstadoGrupo{" +
                "id_estado: " + id_estado +
                ", nombreEstado: '" + nombreEstado + '\'' +
                '}';
    }
}