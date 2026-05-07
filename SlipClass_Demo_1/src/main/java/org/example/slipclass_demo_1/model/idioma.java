package org.example.slipclass_demo_1.model;

public class idioma {

    private int id_idioma;
    private String nombre;
    private String codigo;

    public idioma(int id_idioma, String nombre, String codigo) {
        this.id_idioma = id_idioma;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public int getId_idioma() {
        return id_idioma;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return "idioma{" +
                "id_idioma=" + id_idioma +
                ", nombre='" + nombre + '\'' +
                ", codigo='" + codigo + '\'' +
                '}';
    }
}
