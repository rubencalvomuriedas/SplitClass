package org.example.slipclass_demo_1.model;

import org.example.slipclass_demo_1.configuration.SQLDataAccess;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SQLModelGasto {

    public static List<gasto> getAllGastos() {
        List<gasto> listaGastos = new LinkedList<>();
        String sql = "SELECT Id_Gasto, codGasto, Concepto, Monto_total, Fecha, Id_Grupo, Id_Categoria, Id_Usuario_Pagador FROM GASTO";

        try (Connection conn = SQLDataAccess.getConnection();
             Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery(sql)) {

            while (rs.next()) {
                gasto g = new gasto(
                        rs.getInt("Id_Gasto"),
                        rs.getString("codGasto"),
                        rs.getString("Concepto"),
                        rs.getDouble("Monto_total"),
                        rs.getDate("Fecha").toLocalDate(),
                        rs.getInt("Id_Grupo"),
                        rs.getInt("Id_Categoria"),
                        rs.getInt("Id_Usuario_Pagador")
                );
                listaGastos.add(g);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener gastos: " + e.getMessage());
        }
        return listaGastos;
    }

    public static List<categoria> getAllCategorias() {
        List<categoria> listaCategorias = new LinkedList<>();
        String sql = "SELECT id_categoria, Nombre FROM CATEGORIA";

        try (Connection conn = SQLDataAccess.getConnection();
             Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_categoria");
                String nombre = rs.getString("Nombre");

                listaCategorias.add(new categoria(id, nombre));
            }

        } catch (SQLException e) {
            System.err.println("Error en getAllCategorias: " + e.getMessage());
        }

        return listaCategorias;
    }
}