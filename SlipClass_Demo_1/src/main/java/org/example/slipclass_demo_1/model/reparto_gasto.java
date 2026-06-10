package org.example.slipclass_demo_1.model;

public class reparto_gasto {

    private int idRepartoGasto;
    private String codRepartoGasto;
    private int id_gasto;
    private int id_usuario;
    private double cuota_deuda;

    public reparto_gasto(int id_gasto, int id_usuario, double cuota_deuda) {
        this.id_gasto = id_gasto;
        this.id_usuario = id_usuario;
        this.cuota_deuda = cuota_deuda;
    }

    public int getIdRepartoGasto() {
        return idRepartoGasto;
    }

    public int getId_gasto() {
        return id_gasto;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public double getCuota_deuda() {
        return cuota_deuda;
    }

    public void setCuota_deuda(double cuota_deuda) {
        this.cuota_deuda = cuota_deuda;
    }

    @Override
    public String toString() {
        return "reparto_gasto{" +
                "id_reparto_gasto=" + idRepartoGasto +
                ", id_gasto=" + id_gasto +
                ", id_usuario=" + id_usuario +
                ", cuota_deuda=" + cuota_deuda +
                '}';
    }
}
