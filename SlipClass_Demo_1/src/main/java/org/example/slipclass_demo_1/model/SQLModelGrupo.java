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

<<<<<<< HEAD
//    public static List <grupo> getAllGruposTabla() {
//        List<grupo> listaGrupos = new LinkedList<>();
//        String sql = "SELECT Id_Grupo, codGrupo, Titulo, Descripcion, Moneda, Fecha_creacion, fecha_eliminacion, Id_Estado  FROM GRUPO";
//
//        try (Connection con = SQLDataAccess.getConnection();
//             Statement stat = con.createStatement();
//             ResultSet rs = stat.executeQuery(sql)) {
//
//            while(rs.next()) {
//                grupo g = new grupo(
//                            rs.getInt("Id_grupo"),
//                            rs.getString("codGrupo"),
//                            rs.getString("Titulo"),
//                            rs.getString("Descripcion"),
//                            rs.getString("Moneda"),
//                            rs.getDate("Fecha_creacion"),
//                            rs.getDate("fecha_eliminacion"),
//                            rs.getString("Id_Es" +
//                                    "tado")
//                        );
//                listaGrupos.add(g);
//            }
//        } catch (SQLException e) {
//            System.err.println("Error al obtener los grupos: " + e.getMessage());
//        }
//        return listaGrupos;
//    }
    public static List <grupo> getAllGruposTabla() {

            List<grupo> listaGrupos = new LinkedList<>();
            String sql = "SELECT Id_Grupo, codGrupo, Titulo, Descripcion, Moneda, Fecha_creacion, fecha_eliminacion, Id_Estado FROM GRUPO";

            try (Connection con = SQLDataAccess.getConnection();
                 Statement stat = con.createStatement();
                 ResultSet rs = stat.executeQuery(sql)) {
=======

    public static List<grupo> getAllGruposTabla() {
        List<grupo> listaGrupos = new LinkedList<>();
        String sql = "SELECT Id_Grupo, codGrupo, Titulo, Descripcion, Moneda, Fecha_creacion, fecha_eliminacion, Id_Estado FROM GRUPO";
>>>>>>> 5fb847712056079c9d0deb5aa7d355ee9761ffa9

                while (rs.next()) {
                    grupo g = new grupo(
                            rs.getInt("Id_Grupo"),
                            rs.getString("codGrupo"),
                            rs.getString("Titulo"),
                            rs.getString("Descripcion"),
                            rs.getString("Moneda"),
                            rs.getDate("Fecha_creacion"),
                            rs.getDate("fecha_eliminacion"),
                            rs.getInt("Id_Estado")
                    );
                    listaGrupos.add(g);
                }
            } catch (SQLException e) {
                System.err.println("Error al obtener los grupos: " + e.getMessage());
            }
<<<<<<< HEAD
            return listaGrupos;
=======
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

>>>>>>> 5fb847712056079c9d0deb5aa7d355ee9761ffa9



    }
}



