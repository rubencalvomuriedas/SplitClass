package org.example.slipclass_demo_1.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class usuario {

    private int id_usuario;
    private String codUsuario;
    private String nombre;
    private String email;
    private String password;
    private String telefono;
    private int idIdioma; // seleccionadoIdioma
    private String alias;
    private String iban;
    private LocalDateTime fecha_creacion;
    private LocalDate fecha_nacimiento;
    private boolean verificacionActividad;

    public usuario(int id_usuario, String codUsuario, String nombre, String email, String password, String telefono, int idioma, String alias, String iban, LocalDateTime fecha_creacion, LocalDate fecha_nacimiento) {
        this.id_usuario = id_usuario;
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
    public usuario(String nombre, String email, String password, String telefono, LocalDate fecha_nacimiento){
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.verificacionActividad = false;
    }


    public int getId_usuario() {
        return id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getIdIdioma() {
        return idIdioma;
    }

    public String getAlias() {
        return alias;
    }

    public String getIban() {
        return iban;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFecha_creacion(LocalDateTime fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

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
                id_usuario,
                nombre,
                email,
                password != null ? "********" : "Vacío",
                telefono,
                idIdioma,
                alias,
                iban,
                fecha_creacion,
                fecha_nacimiento != null ? fecha_nacimiento : "Vacío"
        );
    }
}
