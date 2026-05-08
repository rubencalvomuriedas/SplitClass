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
    public void clickSobreBuscar() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Esto es lo que se va a mostar");
        alerta.setHeaderText(null);
        alerta.setContentText("Esto es lo que se va a mostar");
        alerta.show();
    }

    @FXML
    public void clickSobreListar() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Esto es lo que se va a mostar");
        alerta.setHeaderText(null);
        alerta.setContentText("Esto es lo que se va a mostar");
        alerta.show();
    }

    @FXML
    public void clickSobreInsertar() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Esto es lo que se va a mostar");
        alerta.setHeaderText(null);
        alerta.setContentText("Esto es lo que se va a mostar");
        alerta.show();
    }

    @FXML
    public void clickSobreResgistrar() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Esto es lo que se va a mostar");
        alerta.setHeaderText(null);
        alerta.setContentText("Esto es lo que se va a mostar");
        alerta.show();
    }

    @FXML
    public void clickSobreMostrar() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Esto es lo que se va a mostar");
        alerta.setHeaderText(null);
        alerta.setContentText("Esto es lo que se va a mostar");
        alerta.show();
    }

    @FXML
    public void clickSobreEliminar() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Esto es lo que se va a mostar");
        alerta.setHeaderText(null);
        alerta.setContentText("Esto es lo que se va a mostar");
        alerta.show();
    }

//    @FXML
//    protected void onRegistrarseClick(ActionEvent event) {
//        try {
//
//            Parent root = FXMLLoader.load(getClass().getResource("formulario_ingreso.fxml"));
//
//
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
//
//        } catch (IOException e) {
//            System.err.println("¡Error! No se encuentra el archivo registro.fxml");
//            e.printStackTrace();
//        }
//    }

    public void onInsertarButtonClick(javafx.event.ActionEvent actionEvent) {
    }

    public void onListadoButtonClick(javafx.event.ActionEvent actionEvent) {
    }

    public void onEliminarButtonClick(javafx.event.ActionEvent actionEvent) {
    }

    public void onBuscarButtonClick(javafx.event.ActionEvent actionEvent) {
    }

    public void onRegistroButtonClick(javafx.event.ActionEvent actionEvent) {
    }

    public void onRegistrarseClick(javafx.event.ActionEvent actionEvent) {
    }
}