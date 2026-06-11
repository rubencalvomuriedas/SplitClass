package org.example.slipclass_demo_1.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa a un usuario registrado en el sistema SplitClass.
 * Contiene la información personal, credenciales de acceso y preferencias del usuario.
 *
 * @author TuNombre
 * @version 1.0
 */
public class usuario {

    private int id_usuario;
    private String codUsuario;
    private String nombre;
    private String email;
    private String password;
    private String telefono;
    private int idIdioma;
    private String alias;
    private String iban;
    private LocalDateTime fecha_creacion;
    private LocalDate fecha_nacimiento;
    private boolean verificacionActividad;

    /**
     * Constructor completo para usuarios existentes recuperados de la base de datos.
     *
     * @param id_usuario ID único del usuario.
     * @param codUsuario Código identificativo.
     * @param nombre Nombre completo.
     * @param email Correo electrónico.
     * @param password Contraseña cifrada.
     * @param telefono Número de teléfono.
     * @param idioma ID del idioma seleccionado.
     * @param alias Apodo del usuario.
     * @param iban Cuenta bancaria.
     * @param fecha_creacion Fecha y hora de registro.
     * @param fecha_nacimiento Fecha de nacimiento.
     */
    public usuario(int id_usuario, String codUsuario, String nombre, String email, String password, String telefono, int idioma, String alias, String iban, LocalDateTime fecha_creacion, LocalDate fecha_nacimiento) {
        this.id_usuario = id_usuario;
        this.codUsuario = codUsuario;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.idIdioma = idioma;
        this.alias = alias;
        this.iban = iban;
        this.fecha_creacion = fecha_creacion;
        this.fecha_nacimiento = fecha_nacimiento;
        this.verificacionActividad = false;
    }

    /**
     * Constructor para nuevos usuarios con selección de idioma.
     * @param nombre Nombre.
     * @param email Email.
     * @param password Password.
     * @param telefono Teléfono.
     * @param fecha_nacimiento Fecha de nacimiento.
     * @param idIdioma ID del idioma preferido.
     */
    public usuario(String nombre, String email, String password, String telefono, LocalDate fecha_nacimiento, int idIdioma) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.idIdioma = idIdioma;
        this.verificacionActividad = true;
    }

    /**
     * Constructor simplificado para nuevos usuarios.
     * @param nombre Nombre.
     * @param email Email.
     * @param password Password.
     * @param telefono Teléfono.
     * @param fecha_nacimiento Fecha de nacimiento.
     */
    public usuario(String nombre, String email, String password, String telefono, LocalDate fecha_nacimiento) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.verificacionActividad = true;
    }

    /** @return Código único del usuario. */
    public String getCodUsuario() { return codUsuario; }

    /** @return true si el usuario ha verificado su cuenta. */
    public boolean isVerificacionActividad() { return verificacionActividad; }

    /** @return ID único. */
    public int getId_usuario() { return id_usuario; }

    /** @return Nombre del usuario. */
    public String getNombre() { return nombre; }

    /** @return Email. */
    public String getEmail() { return email; }

    /** @return Contraseña. */
    public String getPassword() { return password; }

    /** @return Teléfono. */
    public String getTelefono() { return telefono; }

    /** @return ID del idioma. */
    public int getIdIdioma() { return idIdioma; }

    /** @return Alias. */
    public String getAlias() { return alias; }

    /** @return IBAN. */
    public String getIban() { return iban; }

    /** @return Fecha de registro. */
    public LocalDateTime getFecha_creacion() { return fecha_creacion; }

    /** @return Fecha de nacimiento. */
    public LocalDate getFecha_nacimiento() { return fecha_nacimiento; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setAlias(String alias) { this.alias = alias; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setFecha_creacion(LocalDateTime fecha_creacion) { this.fecha_creacion = fecha_creacion; }
    public void setFecha_nacimiento(LocalDate fecha_nacimiento) { this.fecha_nacimiento = fecha_nacimiento; }
    public void setIdIdioma(int idIdioma) { this.idIdioma = idIdioma; }

    /**
     * Devuelve una representación visual formateada de los datos del usuario.
     * @return String con los detalles del usuario.
     */
    @Override
    public String toString() {
        return """
            ╔══════════════════════════════╗
                    DATOS DEL USUARIO
            ╠══════════════════════════════╣
            ║ ID                : %d
            ║ Nombre            : %s
            ║ Email             : %s
            ║ Password          : %s
            ║ Teléfono          : %s
            ║ Idioma            : %s
            ║ Alias             : %s
            ║ IBAN              : %s
            ║ Fecha creación    : %s
            ║ Fecha nacimiento  : %s
            ╚══════════════════════════════╝
            """.formatted(
                id_usuario, nombre, email, password != null ? "********" : "Vacío",
                telefono, idIdioma, alias, iban, fecha_creacion,
                fecha_nacimiento != null ? fecha_nacimiento : "Vacío"
        );
    }
}