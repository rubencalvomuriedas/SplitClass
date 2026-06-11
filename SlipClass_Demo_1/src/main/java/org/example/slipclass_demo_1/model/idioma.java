package org.example.slipclass_demo_1.model;

/**
 * Representa un idioma disponible en la aplicación SplitClass.
 * Se utiliza para configurar las preferencias de idioma de los usuarios.
 *
 * @author TuNombre
 * @version 1.0
 */
public class idioma {

    private int id_idioma;
    private String nombre;
    private String codigo;

    /**
     * Constructor para crear una instancia de idioma.
     *
     * @param nombre El nombre completo del idioma (ej. "Español").
     * @param codigo El código abreviado del idioma (ej. "es").
     */
    public idioma(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    /**
     * Obtiene el identificador único del idioma.
     *
     * @return El ID del idioma.
     */
    public int getId_idioma() {
        return id_idioma;
    }

    /**
     * Obtiene el nombre completo del idioma.
     *
     * @return El nombre del idioma.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el código abreviado del idioma.
     *
     * @return El código del idioma.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Devuelve una representación textual del objeto idioma, incluyendo su ID,
     * nombre y código.
     *
     * @return Cadena de texto con los detalles del idioma.
     */
    @Override
    public String toString() {
        return "idioma{" +
                "id_idioma=" + id_idioma +
                ", nombre='" + nombre + '\'' +
                ", codigo='" + codigo + '\'' +
                '}';
    }
}