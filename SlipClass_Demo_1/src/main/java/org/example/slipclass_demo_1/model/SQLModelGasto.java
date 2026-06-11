package org.example.slipclass_demo_1.model;

import org.example.slipclass_demo_1.configuration.SQLDataAccess;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase de acceso a datos (DAO) para la gestión de gastos y categorías.
 * Proporciona métodos CRUD para interactuar con la tabla GASTO en la base de datos.
 *
 * @author TuNombre
 * @version 1.0
 */
public class SQLModelGasto {

    /**
     * Recupera todos los gastos almacenados en la base de datos.
     * @return Una lista de objetos {@link gasto}.
     */
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

    /**
     * Obtiene todas las categorías de gasto disponibles.
     * @return Una lista de objetos {@link categoria}.
     */
    public static List<categoria> getAllCategorias() {
        List<categoria> listaCategorias = new LinkedList<>();
        String sql = "SELECT id_categoria, Nombre FROM CATEGORIA";

        try (Connection conn = SQLDataAccess.getConnection();
             Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery(sql)) {

            while (rs.next()) {
                listaCategorias.add(new categoria(rs.getInt("id_categoria"), rs.getString("Nombre")));
            }
        } catch (SQLException e) {
            System.err.println("Error en getAllCategorias: " + e.getMessage());
        }
        return listaCategorias;
    }

    /**
     * Registra un nuevo gasto en la base de datos generando automáticamente su código.
     * @param g Objeto {@link gasto} con la información a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public static boolean createGasto(gasto g) {
        String sql = "INSERT INTO GASTO (codGasto, Concepto, Monto_total, Fecha, Id_Grupo, Id_Categoria, Id_Usuario_Pagador) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = SQLDataAccess.getConnection();
             PreparedStatement stat = con.prepareStatement(sql)){

            String nuevoCodigo = CodeGenerator.generarCodigo(con, "GASTO", "codGasto", "GST");
            stat.setString(1, nuevoCodigo);
            stat.setString(2, g.getConcepto());
            stat.setDouble(3, g.getMonto_total());
            stat.setDate(4, Date.valueOf(g.getFecha()));
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

    /**
     * Realiza un borrado lógico desactivando el gasto.
     * @param idGasto ID del gasto a desactivar.
     * @return true si se actualizó el registro, false en caso contrario.
     */
    public static boolean deleteGasto(int idGasto) {
        String sql = "UPDATE GASTO SET activo = false WHERE Id_Gasto = ?";
        try (Connection con = SQLDataAccess.getConnection();
             PreparedStatement stat = con.prepareStatement(sql)) {
            stat.setInt(1, idGasto);
            return stat.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al desactivar el gasto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza la información de un gasto existente.
     * @param g Objeto {@link gasto} con los nuevos datos.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
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

    /**
     * Obtiene los gastos asociados a los grupos en los que participa un usuario específico.
     * @param idUsuario ID del usuario para filtrar los gastos.
     * @return Lista de gastos activos del usuario.
     */
    public static List<gasto> getGastosPorUsuario(int idUsuario) {
        List<gasto> listaGastos = new LinkedList<>();
        String sql = "SELECT DISTINCT g.* FROM GASTO g " +
                "JOIN MIEMBROS_GRUPO mg ON g.Id_Grupo = mg.id_Grupo " +
                "WHERE mg.id_Usuario = ? AND g.activo = true";

        try (Connection conn = SQLDataAccess.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    listaGastos.add(new gasto(
                            rs.getInt("Id_Gasto"), rs.getString("codGasto"), rs.getString("Concepto"),
                            rs.getDouble("Monto_total"), rs.getDate("Fecha").toLocalDate(),
                            rs.getInt("Id_Grupo"), rs.getInt("Id_Categoria"), rs.getInt("Id_Usuario_Pagador")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener gastos filtrados por usuario: " + e.getMessage());
        }
        return listaGastos;
    }
}