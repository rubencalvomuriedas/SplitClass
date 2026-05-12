package org.example.slipclass_demo_1.model;

import java.time.LocalDate;

public class liquidacion {

    private int id_liquidacion;
    private String Cod_Liquidacion;
    private double monto;
    private LocalDate fecha_movimiento;
    private String concepto;
    private int estado;
    private int id_emisor;
    private int id_receptor;
    private int id_grupo;

    public liquidacion(double monto, LocalDate fecha_movimiento, String concepto, int estado, int id_emisor, int id_receptor, int id_grupo) {
            this.monto = monto;
            this.fecha_movimiento = fecha_movimiento;
            this.concepto = concepto;
            this.estado = estado;
            this.id_emisor = id_emisor;
            this.id_receptor = id_receptor;
            this.id_grupo = id_grupo;
    }

    public int getId_liquidacion() {
        return id_liquidacion;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFecha_movimiento() {
        return fecha_movimiento;
    }

    public String getConcepto() {
        return concepto;
    }

    public int getEstado() {
        return estado;
    }

    public int getId_emisor() {
        return id_emisor;
    }

    public int getId_receptor() {
        return id_receptor;
    }

    public int getId_grupo() {
        return id_grupo;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setId_emisor(int id_emisor) {
        this.id_emisor = id_emisor;
    }

    public void setId_receptor(int id_receptor) {
        this.id_receptor = id_receptor;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }

    @Override
    public String toString() {
        return "liquidacion{" +
                "id_liquidacion=" + id_liquidacion +
                ", monto=" + monto +
                ", fecha_movimiento=" + fecha_movimiento +
                ", concepto='" + concepto + '\'' +
                ", estado=" + estado +
                ", id_emisor=" + id_emisor +
                ", id_receptor=" + id_receptor +
                ", id_grupo=" + id_grupo +
                '}';
    }
}
