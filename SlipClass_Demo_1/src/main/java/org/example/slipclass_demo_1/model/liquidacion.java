package org.example.slipclass_demo_1.model;

import java.time.LocalDate;

/**
 * Representa una liquidación financiera entre dos usuarios dentro de un grupo
 * en la aplicación SplitClass.
 * Gestiona el movimiento de dinero, el estado de la liquidación y los participantes involucrados.
 *
 * @author TuNombre
 * @version 1.0
 */
public class liquidacion {

    private int id_liquidacion;
    private String codLiquidacion;
    private double monto;
    private LocalDate fecha_movimiento;
    private String concepto;
    private int estado;
    private int id_emisor;
    private int id_receptor;
    private int id_grupo;

    /**
     * Constructor para crear una nueva instancia de liquidación.
     *
     * @param monto El importe de dinero liquidado.
     * @param fecha_movimiento La fecha en la que se realiza el movimiento.
     * @param concepto Breve descripción del concepto de la liquidación.
     * @param estado El ID que representa el estado actual de la liquidación.
     * @param id_emisor ID del usuario que realiza el pago.
     * @param id_receptor ID del usuario que recibe el pago.
     * @param id_grupo ID del grupo al que pertenece esta liquidación.
     */
    public liquidacion(double monto, LocalDate fecha_movimiento, String concepto, int estado, int id_emisor, int id_receptor, int id_grupo) {
        this.monto = monto;
        this.fecha_movimiento = fecha_movimiento;
        this.concepto = concepto;
        this.estado = estado;
        this.id_emisor = id_emisor;
        this.id_receptor = id_receptor;
        this.id_grupo = id_grupo;
    }

    /** @return El identificador único de la liquidación. */
    public int getId_liquidacion() {
        return id_liquidacion;
    }

    /** @return El importe total de la liquidación. */
    public double getMonto() {
        return monto;
    }

    /** @return La fecha en que se registró el movimiento. */
    public LocalDate getFecha_movimiento() {
        return fecha_movimiento;
    }

    /** @return El concepto de la liquidación. */
    public String getConcepto() {
        return concepto;
    }

    /** @return El ID del estado actual de la liquidación. */
    public int getEstado() {
        return estado;
    }

    /** @return El ID del usuario que emite el pago. */
    public int getId_emisor() {
        return id_emisor;
    }

    /** @return El ID del usuario que recibe el pago. */
    public int getId_receptor() {
        return id_receptor;
    }

    /** @return El ID del grupo asociado a la liquidación. */
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

    /**
     * Devuelve una representación textual detallada de la liquidación.
     *
     * @return Cadena de texto con los atributos de la liquidación.
     */
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