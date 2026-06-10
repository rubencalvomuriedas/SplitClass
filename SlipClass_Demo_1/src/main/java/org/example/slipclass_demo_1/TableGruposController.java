package org.example.slipclass_demo_1;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.slipclass_demo_1.model.SQLModelGrupo;
import org.example.slipclass_demo_1.model.grupo;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TableGruposController implements Initializable {

    @FXML private TableView<grupo> TablaGrupos;

    @FXML private TableColumn<grupo, String> tablaGrupoTitulo;
    @FXML private TableColumn<grupo, String> tablaGrupoDescrip;
    @FXML private TableColumn<grupo, String> tablaGrupoMon;
    @FXML private TableColumn<grupo, LocalDate> tablaGrupoCreacion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tablaGrupoTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tablaGrupoDescrip.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tablaGrupoMon.setCellValueFactory(new PropertyValueFactory<>("moneda"));
        tablaGrupoCreacion.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));

        TablaGrupos.setItems(
                FXCollections.observableArrayList(
                        SQLModelGrupo.getGruposPorUsuario(3)
                )
        );
    }
}