package org.example.slipclass_demo_1;

import org.example.slipclass_demo_1.model.usuario;

/**
 * Gestiona el estado de la sesión del usuario actual en la aplicación.
 * Permite almacenar, recuperar y finalizar la sesión del usuario logueado de manera global.
 * * @author TuNombre
 * @version 1.0
 */
public class SessionManager {

    /** Instancia del usuario que ha iniciado sesión actualmente. */
    private static usuario currentUser;

    /**
     * Establece el usuario que ha iniciado sesión.
     * * @param user El objeto {@link usuario} que representa al usuario autenticado.
     */
    public static void setCurrentUser(usuario user) {
        currentUser = user;
    }

    /**
     * Obtiene el usuario que actualmente tiene la sesión iniciada.
     * * @return El objeto {@link usuario} actual, o {@code null} si no hay ninguna sesión activa.
     */
    public static usuario getCurrentUser() {
        return currentUser;
    }

    /**
     * Finaliza la sesión actual estableciendo el usuario a {@code null}.
     */
    public static void logout() {
        currentUser = null;
    }
}