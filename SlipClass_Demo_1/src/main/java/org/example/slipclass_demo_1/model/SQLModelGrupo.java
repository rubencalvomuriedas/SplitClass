package org.example.slipclass_demo_1.model;

import org.example.slipclass_demo_1.configuration.SQLDataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SQLModelGrupo {

    public static List<grupo> getAllGrupos() {
        List<grupo> listaGrupos = new LinkedList<>();
        String sql = "SELECT Id_Grupo, codGrupo, Titulo FROM GRUPO";

        try (Connection con = SQLDataAccess.getConnection();
             Statement stat = con.createStatement();
             ResultSet rs = stat.executeQuery(sql)) {

            while(rs.next()) {
                grupo g = new grupo(
                        rs.getInt("Id_Grupo"),
                        rs.getString("codGrupo"),
                        rs.getString("Titulo")
                );
                listaGrupos.add(g);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los grupos: " + e.getMessage());
        }
        return listaGrupos;
    }


    public static List <grupo> getAllGruposTabla() {

            List<grupo> listaGrupos = new LinkedList<>();
            String sql = "SELECT * FROM GRUPO";

            try (Connection con = SQLDataAccess.getConnection();
                 Statement stat = con.createStatement();
                 ResultSet rs = stat.executeQuery(sql)) {

                while (rs.next()) {
                    grupo g = new grupo(
                            rs.getInt("Id_Grupo"),
                            rs.getString("codGrupo"),
                            rs.getString("Titulo"),
                            rs.getString("Descripcion"),
                            rs.getString("Moneda"),
                            rs.getDate("Fecha_creacion").toLocalDate(),
                            rs.getDate("fecha_eliminacion").toLocalDate(),
                            rs.getInt("Id_Estado")
                    );
                    listaGrupos.add(g);
                }
            } catch (SQLException e) {
                System.err.println("Error al obtener los grupos: " + e.getMessage());
            }
            return listaGrupos;

    }

    public static boolean createGrupoConCreador(grupo g, int idUsuarioCreador) {
        String sqlGrupo = "INSERT INTO GRUPO (codGrupo, Titulo, Descripcion, Moneda, Id_Estado) VALUES (?, ?, ?, ?, ?)";
        String sqlMiembro = "INSERT INTO MIEMBROS_GRUPO (codMiembrosGrupo, id_Usuario, id_Grupo) VALUES (?, ?, ?)";

        Connection conn = null;
        try {
            conn = SQLDataAccess.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sqlGrupo, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, CodeGenerator.generarCodigo(conn, "GRUPO", "codGrupo", "GRP"));
                ps.setString(2, g.getTitulo());
                ps.setString(3, g.getDescripcion());
                ps.setString(4, g.getMoneda());
                ps.setInt(5, 1);
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int idNuevoGrupo = rs.getInt(1);

                    try (PreparedStatement psMiembro = conn.prepareStatement(sqlMiembro)) {
                        psMiembro.setString(1, CodeGenerator.generarCodigo(conn, "MIEMBROS_GRUPO", "codMiembrosGrupo", "MGR"));
                        psMiembro.setInt(2, idUsuarioCreador);
                        psMiembro.setInt(3, idNuevoGrupo);
                        psMiembro.executeUpdate();
                    }
                }
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            System.err.println("Error al crear grupo: " + e.getMessage());
            return false;
        }
    }

    public static boolean agregarMiembroAlGrupo(int idUsuario, int idGrupo) {
        String sql = "INSERT INTO MIEMBROS_GRUPO (codMiembrosGrupo, id_Usuario, id_Grupo) VALUES (?, ?, ?)";
        try (Connection conn = SQLDataAccess.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, CodeGenerator.generarCodigo(conn, "MIEMBROS_GRUPO", "codMiembrosGrupo", "MGR"));
            ps.setInt(2, idUsuario);
            ps.setInt(3, idGrupo);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar miembro: " + e.getMessage());
            return false;
        }
    }

    public static List<grupo> getGruposPorUsuario(int idUsuario) {
        List<grupo> listaGrupos = new LinkedList<>();
        String sql = "SELECT g.* FROM GRUPO g " +
                "JOIN MIEMBROS_GRUPO mg ON g.Id_Grupo = mg.id_Grupo " +
                "WHERE mg.id_Usuario = ?";

        try (Connection con = SQLDataAccess.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    grupo g = new grupo(
                            rs.getInt("Id_Grupo"),
                            rs.getString("codGrupo"),
                            rs.getString("Titulo"),
                            rs.getString("Descripcion"),
                            rs.getString("Moneda"),
                            rs.getDate("Fecha_creacion").toLocalDate()
                    );
                    listaGrupos.add(g);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar grupos del usuario: " + e.getMessage());
        }
        return listaGrupos;
    }

    public static boolean insertarGrupo(grupo g) {

        String sql = """
        INSERT INTO GRUPO (codGrupo, Titulo, Descripcion, Moneda, Id_Estado)
        VALUES (?,?,?,?,?)
    """;

        try (Connection con = SQLDataAccess.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, g.getCodGrupo());
            statement.setString(2, g.getTitulo());
            statement.setString(3, g.getDescripcion());
            statement.setString(4, g.getMoneda());

            // 🔥 AQUÍ ESTÁ EL ERROR
            statement.setInt(5, g.getId_estado());

            return statement.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteGrupo(int idGrupo) {
        String sql = "DELETE FROM GRUPO WHERE Id_Grupo = ?";
        try (Connection con = SQLDataAccess.getConnection();
             PreparedStatement stat = con.prepareStatement(sql)) {

            stat.setInt(1, idGrupo);
            return stat.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar grupo: " + e.getMessage());
            return false;
        }
    }
    }




