package org.example.slipclass_demo_1.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase de utilidad encargada de generar códigos alfanuméricos únicos para las tablas
 * de la base de datos (ej. "USR001", "GRP005", "GST001").
 * Utiliza el valor máximo actual de la columna para calcular el siguiente correlativo.
 * * @author TuNombre
 * @version 1.0
 */
public class CodeGenerator {

    /**
     * Genera un nuevo código basado en el máximo valor existente en una columna específica.
     * Si no existen registros previos, comienza la numeración en 001.
     *
     * @param conn La conexión activa a la base de datos.
     * @param tabla El nombre de la tabla donde buscar el último código.
     * @param columnaCodigo El nombre de la columna que contiene el código (ej. "codUsuario").
     * @param prefijo El prefijo alfanumérico deseado (ej. "USR").
     * @return Un nuevo código generado en formato "PREFIJO00X".
     * @throws SQLException Si ocurre un error al consultar la base de datos.
     */
    public static String generarCodigo(Connection conn, String tabla, String columnaCodigo, String prefijo) throws SQLException {

        String sql = "SELECT MAX(" + columnaCodigo + ") FROM " + tabla;

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                String ultimoCodigo = rs.getString(1);
                int nuevoNumero = 1;

                if (ultimoCodigo != null) {
                    // Extrae la parte numérica después del prefijo y suma 1
                    String numeroStr = ultimoCodigo.substring(prefijo.length());
                    nuevoNumero = Integer.parseInt(numeroStr) + 1;
                }

                // Formatea el nuevo número con ceros a la izquierda (ej. 001, 002...)
                return String.format("%s%03d", prefijo, nuevoNumero);
            }
        }
        return prefijo + "001";
    }
}