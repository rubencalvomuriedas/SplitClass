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

    public static List <grupo> getAllGrupos() {
        List<grupo> listaGrupos = new LinkedList<>();
        String sql = "SELECT Id_Grupo, codGrupo,Titulo, Descripcion, Moneda, Fecha_creacion, fecha_eliminacion  FROM GRUPO";

        try (Connection con = SQLDataAccess.getConnection();
             Statement stat = con.createStatement();
             ResultSet rs = stat.executeQuery(sql)) {

            while(rs.next()) {
                grupo g = new grupo(
                       rs
                )

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaGrupos;
    }

}
