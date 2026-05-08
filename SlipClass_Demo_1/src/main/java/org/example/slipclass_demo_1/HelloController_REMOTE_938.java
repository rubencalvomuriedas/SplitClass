package org.example.slipclass_demo_1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class HelloController {

    @FXML
    public void clickDescarga() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Próximamente");
        alerta.setHeaderText(null);
        alerta.setContentText("¡La descarga estará disponible próximamente!");
        alerta.show();
    }

    @FXML
    public void clickSobreNosotros() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Sección en construcción");
        alerta.setHeaderText(null);
        alerta.setContentText("Próximamente...");
        alerta.show();
    }

    @FXML
    protected void onRegistrarseClick(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("registro.fxml"));


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            Scene scene = new Scene(root);


            stage.setScene(scene);

        } catch (IOException e) {
            System.err.println("No se pudo cargar el formulario: " + e.getMessage());
        }
    }
}