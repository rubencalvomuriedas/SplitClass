package org.example.slipclass_demo_1;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.slipclass_demo_1.model.SQLModelUsuario;
import org.example.slipclass_demo_1.model.usuario;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerTable implements Initializable {

    @FXML private TableView<usuario> tablaUsuarios;

    @FXML private TableColumn<usuario, String> codUsuarios;
    @FXML private TableColumn<usuario, String> nombreUsuario;
    @FXML private TableColumn<usuario, String> emailUsuario;
    @FXML private TableColumn<usuario, String>  telUsuario;
    @FXML private TableColumn<usuario, LocalDate> fechaUsuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        codUsuarios.setCellValueFactory(new PropertyValueFactory<>("Codigo"));
        nombreUsuario.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        emailUsuario.setCellValueFactory(new PropertyValueFactory<>("Email"));
        telUsuario.setCellValueFactory(new PropertyValueFactory<>("Telefono"));
        fechaUsuario.setCellValueFactory(new PropertyValueFactory<>("FechaNacimiento"));

        tablaUsuarios.setItems(FXCollections.observableArrayList(SQLModelUsuario.getAllUsuarios()));
    }




    public void onEditarButtonUsAction(ActionEvent event) {
    }

    public void onEliminarButtonUsAction(ActionEvent event) {
    }


}
