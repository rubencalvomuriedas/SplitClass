package org.example.slipclass_demo_1.model;

import org.example.slipclass_demo_1.configuration.SQLDataAccess;

import java.sql.*;
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
                int id = resultSet.getInt("Id_Usuario");
                String nombre = resultSet.getString("Nombre");
                String email = resultSet.getString("Email");
                String password = resultSet.getString("Password");
                String telefono = resultSet.getString("Telefono");
                String idioma = resultSet.getString("Idioma");
                String alias = resultSet.getString("Alias");
                String iban = resultSet.getString("IBAN");

                LocalDateTime fecha_creacion = resultSet.getTimestamp("Fecha_Creacion").toLocalDateTime();
                LocalDate fecha_nac = null;
                Date sqlDate = resultSet.getDate("Fecha_Nacimiento");
                if (sqlDate != null) {
                    fecha_nac = sqlDate.toLocalDate();
                }
                usuario us = new usuario(id, nombre, email, password, telefono, idioma, alias, iban, fecha_creacion, fecha_nac);


                usuarios.add(us);}
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }

            return usuarios;
    }

    public static List<usuario> insertUsuario(usuario us) {
        List<usuario> usuarios = new LinkedList<>();

        String sql = "INSERT INTO usuario (Nombre, Email, Password, Telefono) VALUES (?, ?, ?, ?)";

        try(Connection connection = SQLDataAccess.getConnection();
            PreparedStatement stat = connection.prepareStatement(sql)) {

            stat.setString(1, us.getNombre());
            stat.setString(2, us.getEmail());
            stat.setString(3, us.getPassword());
            stat.setString(4, us.getTelefono());

            int rowsAffected = stat.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Usuario insertado correctamente.");
            } else {
                System.out.println("No se pudo insertar el usuario.");
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }

        return usuarios;
    }

}
