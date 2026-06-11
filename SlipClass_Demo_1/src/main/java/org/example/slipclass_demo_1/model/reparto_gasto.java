package org.example.slipclass_demo_1.model;

/**
 * Representa el reparto de un gasto específico entre los miembros del grupo.
 * Define la cantidad (cuota) que cada usuario debe pagar o asumir respecto a un gasto concreto.
 *
 * @author TuNombre
 * @version 1.0
 */
public class reparto_gasto {

    private int idRepartoGasto;
    private String codRepartoGasto;
    private int id_gasto;
    private int id_usuario;
    private double cuota_deuda;

    /**
     * Constructor para registrar un nuevo reparto de deuda.
     *
     * @param id_gasto ID del gasto al que se asocia este reparto.
     * @param id_usuario ID del usuario responsable de esta parte de la deuda.
     * @param cuota_deuda Cantidad monetaria que corresponde pagar al usuario.
     */
    public reparto_gasto(int id_gasto, int id_usuario, double cuota_deuda) {
        this.id_gasto = id_gasto;
        this.id_usuario = id_usuario;
        this.cuota_deuda = cuota_deuda;
    }

    /** @return El identificador único del reparto. */
    public int getIdRepartoGasto() {
        return idRepartoGasto;
    }

    /** @return El ID del gasto asociado. */
    public int getId_gasto() {
        return id_gasto;
    }

    /** @return El ID del usuario que tiene la deuda. */
    public int getId_usuario() {
        return id_usuario;
    }

    /** @return El importe de la deuda asignada. */
    public double getCuota_deuda() {
        return cuota_deuda;
    }

    /**
     * Actualiza el importe de la deuda para este reparto.
     * @param cuota_deuda La nueva cuota de deuda.
     */
    public void setCuota_deuda(double cuota_deuda) {
        this.cuota_deuda = cuota_deuda;
    }

    /**
     * Devuelve una representación textual de la relación del reparto.
     *
     * @return Cadena de texto con los detalles del reparto.
     */
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