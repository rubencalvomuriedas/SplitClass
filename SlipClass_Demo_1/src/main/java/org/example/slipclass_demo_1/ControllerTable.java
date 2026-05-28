package org.example.slipclass_demo_1;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.slipclass_demo_1.model.SQLModelGrupo;
import org.example.slipclass_demo_1.model.SQLModelUsuario;
import org.example.slipclass_demo_1.model.grupo;
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


    @FXML private TableView<grupo> TablaGrupos;

    @FXML private TableColumn<grupo, String> tablaGruposCod;
    @FXML private TableColumn<grupo, String> tablaGrupoTitulo;
    @FXML private TableColumn<grupo, String> tablaGrupoDescrip;
    @FXML private TableColumn<grupo, String> tablaGrupoMon;
    @FXML private TableColumn<grupo, LocalDate> tablaGrupoCreacion;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        codUsuarios.setCellValueFactory(new PropertyValueFactory<>("codUsuario"));

        nombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        emailUsuario.setCellValueFactory(new PropertyValueFactory<>("email"));
        telUsuario.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        fechaUsuario.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));

        tablaUsuarios.setItems(FXCollections.observableArrayList(SQLModelUsuario.getAllUsuarios()));


    }




    public void onEditarButtonUsAction(ActionEvent event) {
    }

    public void onEliminarButtonUsAction(ActionEvent event) {
    }


    public void onRegistroGrupoClick(ActionEvent event) {
    }

    public void onEliminarButtonClick(ActionEvent event) {
    }

    public void irAMenuGrupo(ActionEvent event) {
    }
}
