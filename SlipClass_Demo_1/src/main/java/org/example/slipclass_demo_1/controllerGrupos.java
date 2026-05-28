package org.example.slipclass_demo_1;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.slipclass_demo_1.model.SQLModelGrupo;
import org.example.slipclass_demo_1.model.grupo;
import org.w3c.dom.Text;

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

    @FXML private TextField txtTitulo, txtDescripcion;
    @FXML private ComboBox<String> comboMoneda;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        tablaGruposCod.setCellValueFactory(new PropertyValueFactory<>("codGrupo"));
//        tablaGrupoTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
//        tablaGrupoDescrip.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
//        tablaGrupoMon.setCellValueFactory(new PropertyValueFactory<>("moneda"));
//        tablaGrupoCreacion.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));
//
//        TablaGrupos.setItems(FXCollections.observableArrayList(SQLModelGrupo.getAllGruposTabla()));
    
    
    }


    public void onRegistroGrupoClick(ActionEvent event) {
    }

    public void onEliminarButtonClick(ActionEvent event) {
    }

    public void irAMenuGrupo(ActionEvent event) {
    }

    public void onVolverRegisterAction(ActionEvent event) {
    }

    public void onCancelarGrupo(ActionEvent event) {
    }

    public void onConfirmarGrupo(ActionEvent event) {
        if (txtTitulo.getText().isEmpty()) {
            mostrarAlerta("Error", "El título es obligatorio.");
            return;
        }

        // Si la moneda es nula o vacía, le asignamos "EUR" por defecto
        String moneda = "EUR";

        // Creamos el grupo usando el constructor que acepta la moneda
        grupo nuevoGrupo = new grupo(
                "", // codGrupo se genera solo
                txtTitulo.getText(),
                txtDescripcion.getText(),
                moneda,             // Pasamos "EUR"
                null, null, 0       // Valores dummy para los campos extra del constructor
        );

        int idUsuarioLogueado = 1;

        if (SQLModelGrupo.createGrupoConCreador(nuevoGrupo, idUsuarioLogueado)) {
            mostrarAlerta("Éxito", "Grupo creado y vinculado correctamente.");
            // Opcional: limpiar campos
            txtTitulo.clear();
            txtDescripcion.clear();
        } else {
            mostrarAlerta("Error", "No se pudo crear el grupo.");
        }
    }

    private void mostrarAlerta(String titulo, String msj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msj);
        alert.show();
    }

}
