package org.example.slipclass_demo_1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.example.slipclass_demo_1.model.SQLModelUsuario;
import org.example.slipclass_demo_1.model.usuario;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private usuario usuarioSeleccionado;
    private ObservableList<usuario> usuariosObservableList = FXCollections.observableArrayList();

    @FXML
    private ListView<usuario> listViewUsuarios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.listViewUsuarios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.usuarioSeleccionado = newValue;
            if (newValue != null) {
                System.out.println("Usuario seleccionado: " + newValue.getNombre());
            }
        });

        loadUsuariosList();
    }

    public void loadUsuariosList(){
        try {
            this.usuariosObservableList.clear();

            List<usuario> misUsuarios = SQLModelUsuario.getAllUsuarios();

            if (misUsuarios != null){
                this.usuariosObservableList.addAll(misUsuarios);
                this.listViewUsuarios.setItems(this.usuariosObservableList);
            }


        } catch (Exception e) {
            System.out.println("Error al cargar la lista de usuarios: " + e.getMessage());
        }

    }




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
/*
    protected void onRegistrarseClick(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("registro.fxml"));


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            Scene scene = new Scene(root);


            stage.setScene(scene);

        } catch (IOException e) {
            System.err.println("No se pudo cargar el formulario: " + e.getMessage());
        }
*/
    }

    public void onRegistrarseClick(ActionEvent event) {
    }

    public void onInsertarButtonClick(ActionEvent event) {
    }

    public void onListadoButtonClick(ActionEvent event) {
    }

    public void onEliminarButtonClick(ActionEvent event) {
    }

    public void onBuscarButtonClick(ActionEvent event) {
    }

    public void onRegistroButtonClick(ActionEvent event) {
    }
}


