package org.example.slipclass_demo_1.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class usuario {

    private int id_usuario;
<<<<<<< HEAD
    private String CodUsuario;
=======
    private String codUsuario;
>>>>>>> 6252e25364fc04059d339fdbb07b54c4802d83ab
    private String nombre;
    private String email;
    private String password;
    private String telefono;
    private String idioma; // seleccionadoIdioma
    private String alias;
    private String iban;
    private LocalDateTime fecha_creacion;
    private LocalDate fecha_nacimiento;

    public usuario(int id_usuario, String nombre, String email, String password, String telefono, String idioma, String alias, String iban, LocalDateTime fecha_creacion, LocalDate fecha_nacimiento) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.idioma = idioma;
        this.alias = alias;
        this.iban = iban;
        this.fecha_creacion = fecha_creacion;
        this.fecha_nacimiento = fecha_nacimiento;
    }
    public usuario(String nombre, String email, String password, String telefono, LocalDate fecha_nacimiento){
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.fecha_nacimiento = fecha_nacimiento;
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

    public String getIdioma() {
        return idioma;
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
                idioma,
                alias,
                iban,
                fecha_creacion,
                fecha_nacimiento != null ? fecha_nacimiento : "Vacío"
        );
    }
}
