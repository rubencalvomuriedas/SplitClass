package org.example.slipclass_demo_1.model.utils;

import javafx.scene.control.Alert;

public class Alertas {
    public static void showAlert(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}