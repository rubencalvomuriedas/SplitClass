package org.example.slipclass_demo_1.model;

/**
 * Representa una categoría de gasto dentro de la aplicación SplitClass.
 * Esta clase sirve como modelo para organizar los diferentes tipos de gastos
 * (ej. Comida, Transporte, Ocio).
 * * @author TuNombre
 * @version 1.0
 */
public class categoria {

    private int id_categoria;
    private String nombre;

    /**
     * Constructor para crear una nueva categoría.
     * * @param id_categoria El identificador único de la categoría en la base de datos.
     * @param nombre El nombre descriptivo de la categoría.
     */
    public categoria(int id_categoria, String nombre) {
        this.id_categoria = id_categoria;
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador único de la categoría.
     * * @return El ID de la categoría.
     */
    public int getId_categoria() {
        return id_categoria;
    }

    /**
     * Obtiene el nombre de la categoría.
     * * @return El nombre de la categoría.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece un nuevo nombre para la categoría.
     * * @param nombre El nombre nuevo a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve una representación en texto de la categoría, utilizada principalmente
     * para mostrar el nombre en los componentes de interfaz gráfica como ComboBox.
     * * @return El nombre de la categoría.
     */
    @Override
    public String toString() {
        return nombre;
    }
}