package org.example.slipclass_demo_1;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.slipclass_demo_1.model.SQLModelUsuario;
import org.example.slipclass_demo_1.model.grupo;
import org.example.slipclass_demo_1.model.usuario;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Controlador encargado de gestionar la visualización de listas de usuarios y grupos
 * en componentes {@link TableView}.
 *
 * @author TuNombre
 * @version 1.0
 */
public class ControllerTable implements Initializable {

    // Columnas para la tabla de usuarios
    @FXML private TableView<usuario> tablaUsuarios;
    @FXML private TableColumn<usuario, String> codUsuarios;
    @FXML private TableColumn<usuario, String> nombreUsuario;
    @FXML private TableColumn<usuario, String> emailUsuario;
    @FXML private TableColumn<usuario, String> telUsuario;
    @FXML private TableColumn<usuario, LocalDate> fechaUsuario;

    // Columnas para la tabla de grupos
    @FXML private TableView<grupo> TablaGrupos;
    @FXML private TableColumn<grupo, String> tablaGrupoTitulo;
    @FXML private TableColumn<grupo, String> tablaGrupoDescrip;
    @FXML private TableColumn<grupo, String> tablaGrupoMon;
    @FXML private TableColumn<grupo, LocalDate> tablaGrupoCreacion;

    /**
     * Configura las factorías de celdas para los modelos de datos y carga
     * la información inicial desde la base de datos.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configuración de la tabla de usuarios
        codUsuarios.setCellValueFactory(new PropertyValueFactory<>("codUsuario"));
        nombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        emailUsuario.setCellValueFactory(new PropertyValueFactory<>("email"));
        telUsuario.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        fechaUsuario.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));

        // Carga de datos
        tablaUsuarios.setItems(FXCollections.observableArrayList(SQLModelUsuario.getAllUsuarios()));
    }

    public void onEditarButtonUsAction(ActionEvent event) {
        // Lógica de edición de usuario
    }

    public void onEliminarButtonUsAction(ActionEvent event) {
        // Lógica de eliminación de usuario
    }

    public void onRegistroGrupoClick(ActionEvent event) {
        // Lógica para navegar al registro de grupo
    }

    public void onEliminarButtonClick(ActionEvent event) {
        // Lógica para eliminar grupo
    }

    public void irAMenuGrupo(ActionEvent event) {
        // Lógica para navegación al menú de grupos
    }
}