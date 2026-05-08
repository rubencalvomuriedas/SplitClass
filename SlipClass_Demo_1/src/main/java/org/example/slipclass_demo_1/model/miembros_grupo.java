package org.example.slipclass_demo_1.model;

public class miembros_grupo {

    private int id_miembros;
    private int id_usuario;
    private int id_grupo;
    private String rol;

    public miembros_grupo(int id_usuario, int id_grupo, String rol) {

        this.id_usuario = id_usuario;
        this.id_grupo = id_grupo;
        this.rol = rol;
    }

    public int getId_miembros() {
        return id_miembros;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public int getId_grupo() {
        return id_grupo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

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
