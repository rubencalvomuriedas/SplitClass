package org.example.slipclass_demo_1.model;

import org.example.slipclass_demo_1.configuration.SQLDataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class SQLModelUsuario {

    public static List<usuario> getAllUsuarios() {
        List<usuario> usuarios = new LinkedList<>();

        String sql = "SELECT * FROM usuario";

        try(Connection connection = SQLDataAccess.getConnection();
            Statement stat = connection.createStatement();
            ResultSet resultSet = stat.executeQuery(sql)) {

            while (resultSet.next()) {
                int id_usuario = resultSet.getInt("1");
                String nombre = resultSet.getString("2");
                String email = resultSet.getString("3");
                String password = resultSet.getString("4");
                String telefono = resultSet.getString("5");
                String idioma = resultSet.getString("6");
                String alias = resultSet.getString("7");
                String iban = resultSet.getString("8");

                LocalDateTime fecha_creaacion = resultSet.getTimestamp("9").toLocalDateTime();
                LocalDate fecha_nacimiento = resultSet.getTimestamp("10").toLocalDateTime().toLocalDate();

                usuario us = new usuario(nombre, email, password, telefono, idioma, alias, iban, fecha_creaacion, fecha_nacimiento);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }

            return usuarios;
    }



}
