package org.example.slipclass_demo_1.model;

import java.time.LocalDate;

public class gasto {

    private int id_gasto;
    private String Cod_Gasto;
    private String concepto;
    private double monto_total;
    private LocalDate fecha;
    private int id_grupo;
    private int id_categoria;
    private int id_usuarioPagador;

    public gasto(String concepto, double monto_total, int id_grupo, int id_categoria, int id_usuarioPagador) {
        this.concepto = concepto;
        this.monto_total = monto_total;
        this.fecha = fecha;
        this.id_grupo = id_grupo;
        this.id_categoria = id_categoria;
        this.id_usuarioPagador = id_usuarioPagador;
    }

    public int getId_gasto() {
        return id_gasto;
    }

    public String getConcepto() {
        return concepto;
    }

    public double getMonto_total() {
        return monto_total;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getId_grupo() {
        return id_grupo;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public int getId_usuarioPagador() {
        return id_usuarioPagador;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public void setMonto_total(double monto_total) {
        this.monto_total = monto_total;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }

    public void setId_usuarioPagador(int id_usuarioPagador) {
        this.id_usuarioPagador = id_usuarioPagador;
    }

    @Override
    public String toString() {
        return "gasto{" +
                "id_gasto=" + id_gasto +
                ", concepto='" + concepto + '\'' +
                ", monto_total=" + monto_total +
                ", fecha=" + fecha +
                ", id_grupo=" + id_grupo +
                ", id_categoria=" + id_categoria +
                ", id_usuarioPagador=" + id_usuarioPagador +
                '}';
    }
}
