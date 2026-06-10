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


    public static boolean createGasto(gasto g) {
        String sql = "INSERT INTO GASTO (codGasto, Concepto, Monto_total, Fecha, Id_Grupo, Id_Categoria, Id_Usuario_Pagador) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = SQLDataAccess.getConnection();
             PreparedStatement stat = con.prepareStatement(sql)){

            String nuevoCodigo = CodeGenerator.generarCodigo(con, "GASTO", "codGasto", "GST");

            stat.setString(1, nuevoCodigo);
            stat.setString(2, g.getConcepto());
            stat.setDouble(3, g.getMonto_total());
            stat.setDate(4, Date.valueOf(g.getFecha()));

            // CORRECCIÓN AQUÍ: Asegúrate de usar los getters correctos de tu modelo gasto
            stat.setInt(5, g.getId_grupo());
            stat.setInt(6, g.getId_categoria());
            stat.setInt(7, g.getId_usuarioPagador());

            stat.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al crear gasto: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteGasto(int idGasto) {
        String sql = "DELETE FROM GASTO WHERE Id_Gasto = ?";
        try (Connection con = SQLDataAccess.getConnection();
             PreparedStatement stat = con.prepareStatement(sql)) {

            stat.setInt(1, idGasto);
            return stat.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar gasto: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateGasto(gasto g) {
        String sql = "UPDATE GASTO SET Concepto = ?, Monto_total = ?, Fecha = ?, Id_Grupo = ?, Id_Categoria = ?, Id_Usuario_Pagador = ? WHERE Id_Gasto = ?";
        try (Connection con = SQLDataAccess.getConnection();
             PreparedStatement stat = con.prepareStatement(sql)) {

            stat.setString(1, g.getConcepto());
            stat.setDouble(2, g.getMonto_total());
            stat.setDate(3, Date.valueOf(g.getFecha()));
            stat.setInt(4, g.getId_grupo());
            stat.setInt(5, g.getId_categoria());
            stat.setInt(6, g.getId_usuarioPagador());
            stat.setInt(7, g.getId_gasto());

            return stat.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar gasto: " + e.getMessage());
            return false;
        }
    }

    public static List<gasto> getGastosPorUsuario(int idUsuario) {
        List<gasto> listaGastos = new LinkedList<>();
        // Trae los gastos si el usuario pertenece al grupo donde se registró el gasto
        String sql = "SELECT DISTINCT g.* FROM GASTO g " +
                "JOIN MIEMBROS_GRUPO mg ON g.Id_Grupo = mg.id_Grupo " +
                "WHERE mg.id_Usuario = ?";

        try (Connection conn = SQLDataAccess.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener gastos filtrados por usuario: " + e.getMessage());
        }
        return listaGastos;
    }

}