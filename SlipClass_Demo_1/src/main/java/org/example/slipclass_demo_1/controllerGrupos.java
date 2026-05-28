package org.example.slipclass_demo_1;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.slipclass_demo_1.model.SQLModelGrupo;
import org.example.slipclass_demo_1.model.grupo;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class controllerGrupos implements Initializable {


    @FXML
    private TableView<grupo> TablaGrupos;

    @FXML private TableColumn<grupo, String> tablaGruposCod;
    @FXML private TableColumn<grupo, String> tablaGrupoTitulo;
    @FXML private TableColumn<grupo, String> tablaGrupoDescrip;
    @FXML private TableColumn<grupo, String> tablaGrupoMon;
    @FXML private TableColumn<grupo, LocalDate> tablaGrupoCreacion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tablaGruposCod.setCellValueFactory(new PropertyValueFactory<>("codGrupo"));
        tablaGrupoTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tablaGrupoDescrip.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tablaGrupoMon.setCellValueFactory(new PropertyValueFactory<>("moneda"));
        tablaGrupoCreacion.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));

        TablaGrupos.setItems(FXCollections.observableArrayList(SQLModelGrupo.getAllGruposTabla()));
    
    
    }


    public void onRegistroGrupoClick(ActionEvent event) {
    }

    public void onEliminarButtonClick(ActionEvent event) {
    }

    public void irAMenuGrupo(ActionEvent event) {
    }
}
