package org.example.slipclass_demo_1.model;

public class estadoGrupo {

    private int id_estado;
    private String nombre;

    public estadoGrupo(String nombre) {
        this.nombre = nombre;
    }

    public int getId_estado() {
        return id_estado;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "EstadoGrupo{" +
                "id_estado=" + id_estado +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
