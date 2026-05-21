package org.example.slipclass_demo_1.model;

import org.example.slipclass_demo_1.configuration.SQLDataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        String sql = "SELECT Id_Grupo, codGrupo, Titulo, Descripcion, Moneda, Fecha_creacion, fecha_eliminacion, Id_Estado  FROM GRUPO";

        try (Connection con = SQLDataAccess.getConnection();
             Statement stat = con.createStatement();
             ResultSet rs = stat.executeQuery(sql)) {

//            while(rs.next()) {
//                grupo g = new grupo(
//                            rs.getInt("Id_grupo"),
//                            rs.getString("codGrupo"),
//                            rs.getString("Titulo"),
//                            rs.getString("Descripcion"),
//                            rs.getString("Moneda"),
//                            rs.getDate("Fecha_creacion"),
//                            rs.getDate("fecha_eliminacion"),
//                            rs.getInt("Id_Estado")
//                        );
//                listaGrupos.add(g);
//            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los grupos: " + e.getMessage());
        }
        return listaGrupos;
    }

}
