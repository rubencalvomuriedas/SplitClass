package org.example.slipclass_demo_1.model;

import com.google.protobuf.StructOrBuilder;
import org.example.slipclass_demo_1.configuration.SQLDataAccess;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class SQLModelUsuario {

    public static List<usuario> getAllUsuarios() {
        List<usuario> usuarios = new LinkedList<>();

        String sql = "SELECT * FROM USUARIO WHERE verificacionActividad = true";

        try (Connection connection = SQLDataAccess.getConnection();
             Statement stat = connection.createStatement();
             ResultSet resultSet = stat.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id_Usuario");
                String codUsuario = resultSet.getString("codUsuario");
                String nombre = resultSet.getString("Nombre");
                String email = resultSet.getString("Email");
                String password = resultSet.getString("Password");
                String telefono = resultSet.getString("Telefono");
                int idIdioma = resultSet.getInt("id_idioma");
                String alias = resultSet.getString("Alias");
                String iban = resultSet.getString("IBAN");

                LocalDateTime fecha_creacion = resultSet.getTimestamp("Fecha_Creacion").toLocalDateTime();

                LocalDate fecha_nac = null;
                Date sqlDate = resultSet.getDate("Fecha_Nacimiento");
                if (sqlDate != null) {
                    fecha_nac = sqlDate.toLocalDate();
                }

                usuario us = new usuario(id, codUsuario, nombre, email, password, telefono, idIdioma, alias, iban, fecha_creacion, fecha_nac);
                usuarios.add(us);
            }
        } catch (SQLException e) {
            System.err.println("SQLException en getAllUsuarios: " + e.getMessage());
        }

        return usuarios;
    }
    public static boolean createUsuario(usuario us) {
        boolean result = false;


        String sql = "INSERT INTO USUARIO (codUsuario, Nombre, Email, Password, Telefono, id_idioma, Alias, IBAN, Fecha_Nacimiento, verificacionActividad) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = SQLDataAccess.getConnection();
            PreparedStatement stat = connection.prepareStatement(sql)) {

            String nuevoCodigo = CodeGenerator.generarCodigo(connection, "USUARIO", "codUsuario", "USR");

            stat.setString(1, nuevoCodigo);
            stat.setString(2, us.getNombre());
            stat.setString(3, us.getEmail());
            stat.setString(4, us.getPassword());
            stat.setString(5, us.getTelefono());
            int idiomaAInsertar = (us.getIdIdioma() <= 0) ? 1 : us.getIdIdioma();
            stat.setInt(6, idiomaAInsertar);
            stat.setString(7, us.getAlias());
            stat.setString(8, us.getIban());

            if (us.getFecha_nacimiento() != null) {
                stat.setDate(9, Date.valueOf(us.getFecha_nacimiento()));
            } else {
                stat.setNull(9, Types.DATE);
            }
            stat.setBoolean(10, true);

            stat.executeUpdate();
            result = true;

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }

        return result;
    }

    public static boolean updateUsuario(usuario us) {
        boolean result = false;
        String sql = "UPDATE usuario SET nombre = ?, email = ?, " +
                "password = ?, fecha_nacimiento = ?, telefono = ? WHERE id_usuario = ?";

        try (Connection conn = SQLDataAccess.getConnection();
             PreparedStatement stat = conn.prepareStatement(sql)){

            stat.setString(1, us.getNombre());
            stat.setString(2, us.getEmail());
            stat.setString(3, us.getPassword());
            stat.setDate(4, Date.valueOf(us.getFecha_nacimiento()));
            stat.setString(5, us.getTelefono());

            stat.setInt(6, us.getId_usuario());

            int filasAfectadas = stat.executeUpdate();
            result = (filasAfectadas > 0);

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }


        return result;
    }

    public static usuario login(String email, String password){
        usuario user = null;

        String sql = "SELECT * FROM usuario WHERE email = ? AND password = ?";

        try (Connection con = SQLDataAccess.getConnection();
             PreparedStatement stat = con.prepareStatement(sql)) {

            stat.setString(1, email);
            stat.setString(2, password);

            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    // 1. Extraemos el ID y todos los campos requeridos por el constructor completo
                    int id = rs.getInt("id_Usuario");
                    String codUsuario = rs.getString("codUsuario");
                    String nombre = rs.getString("Nombre");
                    String mail = rs.getString("Email");
                    String pass = rs.getString("Password");
                    String telefono = rs.getString("Telefono");
                    int idIdioma = rs.getInt("id_idioma");
                    String alias = rs.getString("Alias");
                    String iban = rs.getString("IBAN");

                    LocalDateTime fecha_creacion = rs.getTimestamp("Fecha_Creacion").toLocalDateTime();

                    LocalDate fecha_nac = null;
                    Date sqlDate = rs.getDate("Fecha_Nacimiento");
                    if (sqlDate != null) {
                        fecha_nac = sqlDate.toLocalDate();
                    }

                    // 2. Usamos el constructor que incluye el 'id' para que no se quede en 0
                    user = new usuario(id, codUsuario, nombre, mail, pass, telefono, idIdioma, alias, iban, fecha_creacion, fecha_nac);
                }
            }

        } catch (SQLException e) {
            System.err.println("SQLException en login: " + e.getMessage());
        }

        return user;
    }


}
