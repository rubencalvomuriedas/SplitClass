package org.example.slipclass_demo_1.model;

import java.time.LocalDate;

/**
 * Representa un gasto registrado en un grupo de la aplicación SplitClass.
 * Contiene información sobre el concepto, el importe total, la fecha y las
 * relaciones con el grupo, la categoría y el usuario pagador.
 * * @author TuNombre
 * @version 1.0
 */
public class gasto {

    private int id_gasto;
    private String cod_Gasto;
    private String concepto;
    private double monto_total;
    private LocalDate fecha;
    private int id_grupo;
    private int id_categoria;
    private int id_usuarioPagador;

    /**
     * Constructor completo utilizado al recuperar gastos existentes desde la base de datos.
     * * @param id_gasto ID único del gasto.
     * @param cod_Gasto Código alfanumérico identificativo del gasto.
     * @param concepto Breve descripción del gasto.
     * @param monto_total Importe monetario total del gasto.
     * @param fecha Fecha en la que se realizó el gasto.
     * @param id_grupo ID del grupo al que pertenece el gasto.
     * @param id_categoria ID de la categoría del gasto.
     * @param id_usuarioPagador ID del usuario que realizó el pago.
     */
    public gasto(int id_gasto, String cod_Gasto, String concepto, double monto_total, LocalDate fecha, int id_grupo, int id_categoria, int id_usuarioPagador) {
        this.id_gasto = id_gasto;
        this.cod_Gasto = cod_Gasto;
        this.concepto = concepto;
        this.monto_total = monto_total;
        this.fecha = fecha;
        this.id_grupo = id_grupo;
        this.id_categoria = id_categoria;
        this.id_usuarioPagador = id_usuarioPagador;
    }

    /**
     * Constructor simplificado utilizado para crear nuevos gastos antes de persistirlos.
     * * @param concepto Descripción del gasto.
     * @param monto_total Importe del gasto.
     * @param fecha Fecha del gasto.
     * @param id_categoria ID de la categoría asociada.
     * @param id_grupo ID del grupo asociado.
     * @param id_usuarioPagador ID del usuario pagador.
     */
    public gasto(String concepto, double monto_total, LocalDate fecha, int id_categoria, int id_grupo, int id_usuarioPagador) {
        this.concepto = concepto;
        this.monto_total = monto_total;
        this.fecha = fecha;
        this.id_categoria = id_categoria;
        this.id_grupo = id_grupo;
        this.id_usuarioPagador = id_usuarioPagador;
    }

    /** @return ID único del gasto. */
    public int getId_gasto() { return id_gasto; }

    /** @return Concepto del gasto. */
    public String getConcepto() { return concepto; }

    /** @return Monto total del gasto. */
    public double getMonto_total() { return monto_total; }

    /** @return Fecha del gasto. */
    public LocalDate getFecha() { return fecha; }

    /** @return ID del grupo asociado. */
    public int getId_grupo() { return id_grupo; }

    /** @return ID de la categoría asociada. */
    public int getId_categoria() { return id_categoria; }

    /** @return ID del usuario pagador. */
    public int getId_usuarioPagador() { return id_usuarioPagador; }

    public void setConcepto(String concepto) { this.concepto = concepto; }

    public void setMonto_total(double monto_total) { this.monto_total = monto_total; }

    public void setId_categoria(int id_categoria) { this.id_categoria = id_categoria; }

    public void setId_grupo(int id_grupo) { this.id_grupo = id_grupo; }

    public void setId_usuarioPagador(int id_usuarioPagador) { this.id_usuarioPagador = id_usuarioPagador; }

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