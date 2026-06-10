package org.example.slipclass_demo_1;

import org.example.slipclass_demo_1.model.usuario;

public class SessionManager {
    private static usuario currentUser;

    public static void setCurrentUser(usuario user) {
        currentUser = user;
    }

    public static usuario getCurrentUser() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
    }
}