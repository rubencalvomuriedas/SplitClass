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
        String sql = "SELECT Id_Grupo, codGrupo, Titulo, Descripcion, Moneda, Fecha_creacion, fecha_eliminacion, Id_Estado  FROM GRUPO";

        try (Connection con = SQLDataAccess.getConnection();
             Statement stat = con.createStatement();
             ResultSet rs = stat.executeQuery(sql)) {

            while(rs.next()) {
                grupo g = new grupo(
                            rs.getInt("Id_grupo"),
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
        return listaGrupos;
    }



//    public static void crearGrupo (grupo g) {
//        String sql = "SELECT GRUPO SET Id_Grupo = ?, codGrupo = ?, Titulo = ?, Descripcion = ?, Moneda = ?, Fecha_creacion = ?, fecha_eliminacion = ?, Id_Estado = ? WHERE Id_Grupo = ?";
//        try(Connection con = SQLDataAccess.getConnection();
//            PreparedStatement stat = con.prepareStatement(sql)) {
//            stat.setInt(1, g.getId_grupo());
//            stat.setString(2, g.getCodGrupo());
//            stat.setString(3, g.getTitulo());
//            stat.setString(4, g.getDescripcion());
//            stat.setString(5, g.getMoneda());
//            stat.setDate(Date.valueOf(g.getFecha_creacion()));
//            stat.setDate(Date.valueOf(g.getFecha_eliminacion()));
//            stat.setInt(6, g.getId_Estado());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

//
//    public static boolean editarGrupo(grupo g) {
//        String sql = "UPDATE GRUPO SET Id_Grupo = ?, codGrupo = ?, Titulo = ?, Descripcion = ?, Moneda = ?, Fecha_creacion = ?, fecha_eliminacion = ?, Id_Estado = ? WHERE Id_Grupo = ?";
//        try (Connection con = SQLDataAccess.getConnection();
//             PreparedStatement stat = con.prepareStatement(sql)) {
//            stat.setString(1, g.getId_grupo());
//            stat.setString(2, g.getCodGrupo());
//            stat.setString(3, g.getTitulo());
//            stat.setString(4, g.getDescripcion());
//            stat.setDouble(5, g.getMoneda());
//            stat.setDate(6, Date.valueOf(g.getFecha_creacion()));
//            stat.setDate(7, Date.valueOf(g.getFecha_eliminacion()));
//            stat.setString(8, g.getId_Estado());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }


