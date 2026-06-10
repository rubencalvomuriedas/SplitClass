package org.example.slipclass_demo_1.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CodeGenerator {

    public static String generarCodigo(Connection conn, String tabla, String columnaCodigo, String prefijo) throws SQLException {

        String sql = "SELECT MAX(" + columnaCodigo + ") FROM " + tabla;

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                String ultimoCodigo = rs.getString(1);
                int nuevoNumero = 1;

                if (ultimoCodigo != null) {
                    String numeroStr = ultimoCodigo.substring(prefijo.length());
                    nuevoNumero = Integer.parseInt(numeroStr) + 1;
                }

                return String.format("%s%03d", prefijo, nuevoNumero);
            }
        }
        return prefijo + "001";
    }

}
