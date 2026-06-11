package org.example.slipclass_demo_1.model;

import org.example.slipclass_demo_1.configuration.SQLDataAccess;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase de acceso a datos (DAO) para la gestión de usuarios.
 * Proporciona métodos para el registro, actualización, autenticación y
 * consulta de usuarios dentro de la base de datos.
 *
 * @author TuNombre
 * @version 1.0
 */
public class SQLModelUsuario {

    /**
     * Recupera todos los usuarios activos registrados en el sistema.
     * @return Una lista de objetos {@link usuario}.
     */
    public static List<usuario> getAllUsuarios() {
        List<usuario> usuarios = new LinkedList<>();
        String sql = "SELECT * FROM USUARIO WHERE verificacionActividad = true";

        try (Connection connection = SQLDataAccess.getConnection();
             Statement stat = connection.createStatement();
             ResultSet resultSet = stat.executeQuery(sql)) {

            while (resultSet.next()) {
                usuarios.add(mapResultSetToUsuario(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("SQLException en getAllUsuarios: " + e.getMessage());
        }
        return usuarios;
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     * @param us Objeto {@link usuario} con los datos del nuevo usuario.
     * @return true si el usuario se creó correctamente, false en caso contrario.
     */
    public static boolean createUsuario(usuario us) {
        String sql = "INSERT INTO USUARIO (codUsuario, Nombre, Email, Password, Telefono, id_idioma, Alias, IBAN, Fecha_Nacimiento, verificacionActividad) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = SQLDataAccess.getConnection();
            PreparedStatement stat = connection.prepareStatement(sql)) {

            String nuevoCodigo = CodeGenerator.generarCodigo(connection, "USUARIO", "codUsuario", "USR");
            stat.setString(1, nuevoCodigo);
            stat.setString(2, us.getNombre());
            stat.setString(3, us.getEmail());
            stat.setString(4, us.getPassword());
            stat.setString(5, us.getTelefono());
            stat.setInt(6, (us.getIdIdioma() <= 0) ? 1 : us.getIdIdioma());
            stat.setString(7, us.getAlias());
            stat.setString(8, us.getIban());

            if (us.getFecha_nacimiento() != null) {
                stat.setDate(9, Date.valueOf(us.getFecha_nacimiento()));
            } else {
                stat.setNull(9, Types.DATE);
            }
            stat.setBoolean(10, true);

            return stat.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza la información básica de un usuario existente.
     * @param us Objeto {@link usuario} con los datos actualizados.
     * @return true si la actualización fue exitosa.
     */
    public static boolean updateUsuario(usuario us) {
        String sql = "UPDATE usuario SET nombre = ?, email = ?, password = ?, fecha_nacimiento = ?, telefono = ? WHERE id_usuario = ?";
        try (Connection conn = SQLDataAccess.getConnection();
             PreparedStatement stat = conn.prepareStatement(sql)){

            stat.setString(1, us.getNombre());
            stat.setString(2, us.getEmail());
            stat.setString(3, us.getPassword());
            stat.setDate(4, Date.valueOf(us.getFecha_nacimiento()));
            stat.setString(5, us.getTelefono());
            stat.setInt(6, us.getId_usuario());

            return stat.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    /**
     * Valida las credenciales de un usuario.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return El objeto {@link usuario} si el login es correcto, null en caso contrario.
     */
    public static usuario login(String email, String password){
        String sql = "SELECT * FROM usuario WHERE email = ? AND password = ?";
        try (Connection con = SQLDataAccess.getConnection();
             PreparedStatement stat = con.prepareStatement(sql)) {

            stat.setString(1, email);
            stat.setString(2, password);

            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) return mapResultSetToUsuario(rs);
            }
        } catch (SQLException e) {
            System.err.println("SQLException en login: " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtiene los usuarios miembros de un grupo específico.
     * @param idGrupo ID del grupo a consultar.
     * @return Lista de objetos {@link usuario}.
     */
    public static List<usuario> getUsuariosPorGrupo(int idGrupo) {
        List<usuario> usuarios = new LinkedList<>();
        String sql = "SELECT u.* FROM USUARIO u INNER JOIN MIEMBROS_GRUPO mg ON u.id_Usuario = mg.id_Usuario WHERE mg.id_Grupo = ? AND u.verificacionActividad = true";

        try (Connection con = SQLDataAccess.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idGrupo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(mapResultSetToUsuario(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en getUsuariosPorGrupo: " + e.getMessage());
        }
        return usuarios;
    }

    /**
     * Método auxiliar para mapear un ResultSet a un objeto Usuario.
     */
    private static usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        LocalDate fecha_nac = (rs.getDate("Fecha_Nacimiento") != null) ? rs.getDate("Fecha_Nacimiento").toLocalDate() : null;
        return new usuario(
                rs.getInt("id_Usuario"), rs.getString("codUsuario"), rs.getString("Nombre"),
                rs.getString("Email"), rs.getString("Password"), rs.getString("Telefono"),
                rs.getInt("id_idioma"), rs.getString("Alias"), rs.getString("IBAN"),
                rs.getTimestamp("Fecha_Creacion").toLocalDateTime(), fecha_nac
        );
    }
}