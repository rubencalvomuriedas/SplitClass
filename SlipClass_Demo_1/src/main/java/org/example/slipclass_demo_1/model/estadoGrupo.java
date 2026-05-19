package org.example.slipclass_demo_1.model;

public class estadoGrupo {

    private int id_estado;
    private String nombreEstado;

    public estadoGrupo(String nombre) {
        this.nombreEstado = nombre;
    }

    public int getId_estado() {
        return id_estado;
    }

    public String getNombre() {
        return nombreEstado;
    }

    @Override
    public String toString() {

        return "EstadoGrupo{" +
                "id_estado: " + id_estado +
                "\nNombre estado:" + nombreEstado + '\'' +
                '}';
    }
}
