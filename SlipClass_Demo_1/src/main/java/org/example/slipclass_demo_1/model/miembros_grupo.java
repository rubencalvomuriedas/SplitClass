package org.example.slipclass_demo_1.model;

/**
 * Representa la relación de pertenencia de un usuario a un grupo específico
 * dentro de la aplicación SplitClass.
 * Permite gestionar quién forma parte de cada grupo y qué rol desempeña.
 *
 * @author TuNombre
 * @version 1.0
 */
public class miembros_grupo {

    private int id_miembros;
    private String codMiembrosGrupo;
    private int id_usuario;
    private int id_grupo;
    private String rol;

    /**
     * Constructor para asignar un usuario a un grupo con un rol determinado.
     *
     * @param id_usuario ID del usuario que se añade al grupo.
     * @param id_grupo ID del grupo al que se vincula el usuario.
     * @param rol El rol que el usuario tendrá dentro del grupo (ej. "Administrador", "Miembro").
     */
    public miembros_grupo(int id_usuario, int id_grupo, String rol) {
        this.id_usuario = id_usuario;
        this.id_grupo = id_grupo;
        this.rol = rol;
    }

    /** @return Identificador único de esta relación de miembro. */
    public int getId_miembros() {
        return id_miembros;
    }

    /** @return ID del usuario vinculado. */
    public int getId_usuario() {
        return id_usuario;
    }

    /** @return ID del grupo vinculado. */
    public int getId_grupo() {
        return id_grupo;
    }

    /** @return El rol asignado al usuario en el grupo. */
    public String getRol() {
        return rol;
    }

    /**
     * Actualiza el rol del usuario en el grupo.
     * @param rol El nuevo rol a asignar.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Devuelve una representación textual de la relación miembro-grupo.
     *
     * @return Cadena de texto con los detalles de la asociación.
     */
    @Override
    public String toString() {
        return "miembros_grupo{" +
                "id_miembros=" + id_miembros +
                ", id_usuario=" + id_usuario +
                ", id_grupo=" + id_grupo +
                ", rol='" + rol + '\'' +
                '}';
    }
}