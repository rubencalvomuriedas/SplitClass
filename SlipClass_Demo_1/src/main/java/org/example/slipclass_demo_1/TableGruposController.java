package org.example.slipclass_demo_1;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.slipclass_demo_1.model.SQLModelGasto;
import org.example.slipclass_demo_1.model.SQLModelGrupo;
import org.example.slipclass_demo_1.model.gasto;
import org.example.slipclass_demo_1.model.grupo;
import org.example.slipclass_demo_1.model.utils.Alertas;
import org.example.slipclass_demo_1.model.utils.Navigator;

import java.io.IOException;
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
    
    @FXML
    public void onVolverClick(){
        try {

            String fxml = "menuGrupo.fxml";

            String titulo = "Menu grupo";

            Navigator.arbrirVentanaSecundaria(fxml, titulo, getClass());


        } catch (IOException e) {
            Alertas.showAlert("a", "a", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onEliminarClick(){
        grupo seleccionado = TablaGrupos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            System.out.println("Por favor, selecciona un grupo de la tabla para eliminar.");
            return;
        }

        // Ejecutar eliminación en base de datos
        if (SQLModelGrupo.deleteGrupo(seleccionado.getId_grupo())) {
            // Remover directamente de la tabla de la interfaz
            TablaGrupos.getItems().remove(seleccionado);
            System.out.println("Gasto eliminado exitosamente.");
        } else {
            System.out.println("Error al intentar eliminar el gasto de la base de datos.");
        }

    }

    @FXML
    public void onEditarClick(){ //HAY QUE CREAR OTRO FXML PARA EDITAR EL GRUPO
        try {

            String fxml = "registroGrupo.fxml";

            String titulo = "Gestionar usuarios";

            Navigator.arbrirVentanaSecundaria(fxml, titulo, getClass());


        } catch (IOException e) {
            Alertas.showAlert("a", "a", Alert.AlertType.ERROR);
        }
    }
}