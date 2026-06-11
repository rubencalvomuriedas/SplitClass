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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.slipclass_demo_1.model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador encargado de la gestión de grupos en la aplicación SplitClass.
 * Maneja la visualización de grupos mediante una tabla, la creación de nuevos grupos,
 * la eliminación y la vinculación de usuarios a grupos específicos.
 *
 * @author TuNombre
 * @version 1.0
 */
public class TableGrupos implements Initializable {

    @FXML private TableView<grupo> TablaGrupos;
    @FXML private TableColumn<grupo, String> tablaGrupoTitulo;
    @FXML private TableColumn<grupo, String> tablaGrupoDescrip;
    @FXML private TableColumn<grupo, String> tablaGrupoMon;
    @FXML private TableColumn<grupo, LocalDate> tablaGrupoCreacion;

    @FXML private TableView<grupo> TablaGrupos1;
    @FXML private TableColumn<grupo, String> tablaGrupoTitulo1;
    @FXML private TableColumn<grupo, String> tablaGrupoDescrip1;
    @FXML private TableColumn<grupo, String> tablaGrupoMon1;
    @FXML private TableColumn<grupo, LocalDate> tablaGrupoCreacion1;

    @FXML private TextField txtGrupoTItulo, txtGrupoDescrip;
    @FXML private ComboBox<String> comboMoneda;
    @FXML private ComboBox<usuario> comboUsuarioSelect;
    @FXML private ComboBox<grupo> comboGrupoSelect;

    /**
     * Inicializa el controlador, configurando las celdas de la tabla y cargando
     * los grupos pertenecientes al usuario activo en la sesión.
     *
     * @param url La ubicación relativa del archivo FXML.
     * @param resourceBundle Los recursos de localización.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablaGrupoTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tablaGrupoDescrip.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tablaGrupoMon.setCellValueFactory(new PropertyValueFactory<>("moneda"));
        tablaGrupoCreacion.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));

        usuario u = SessionManager.getCurrentUser();
        if (u != null) {
            List<grupo> lista = SQLModelGrupo.getGruposPorUsuario(u.getId_usuario());
            TablaGrupos.setItems(FXCollections.observableArrayList(lista));
        }
    }

    /**
     * Actualiza la lista de grupos en la tabla según el usuario logueado.
     */
    private void cargarTabla() {
        usuario u = SessionManager.getCurrentUser();
        if (u != null) {
            List<grupo> lista = SQLModelGrupo.getGruposPorUsuario(u.getId_usuario());
            TablaGrupos.setItems(FXCollections.observableArrayList(lista));
        }
    }

    /**
     * Prepara el formulario de edición cargando los datos del grupo seleccionado.
     * @param actionEvent El evento de clic.
     */
    public void onModificarGrupoClick(ActionEvent actionEvent) {
        grupo seleccionado = TablaGrupos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterGrupos.fxml"));
            Parent root = loader.load();
            HelloController formularioController = loader.getController();
            formularioController.prepararEdicion(seleccionado);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Navega de vuelta al menú de gestión de grupos.
     * @param event El evento de acción.
     */
    public void irAMenuGrupo(ActionEvent event) {
        cambiarPantalla(event, "MenuGrupo.fxml");
    }

    /**
     * Registra un nuevo grupo en la base de datos con los datos ingresados.
     * @param event El evento de confirmación.
     */
    public void onConfirmarGrupo(ActionEvent event) {
        if (txtGrupoTItulo.getText().isEmpty()) {
            mostrarAlerta("Error", "El título es obligatorio.");
            return;
        }

        grupo nuevoGrupo = new grupo("", txtGrupoTItulo.getText(), txtGrupoDescrip.getText(), "EUR", null, null, 0);
        int idUsuarioLogueado = 3; // Nota: Considera usar SessionManager para obtener este ID

        if (SQLModelGrupo.createGrupoConCreador(nuevoGrupo, idUsuarioLogueado)) {
            mostrarAlerta("Éxito", "Grupo creado correctamente.");
            txtGrupoTItulo.clear();
            txtGrupoDescrip.clear();
        } else {
            mostrarAlerta("Error", "No se pudo crear el grupo.");
        }
    }

    /**
     * Vincula un usuario seleccionado a un grupo seleccionado.
     * @param event El evento de confirmación.
     */
    public void onConfirmarGrupoMiembro(ActionEvent event) {
        grupo grupoSeleccionado = comboGrupoSelect.getValue();
        usuario usuarioSeleccionado = comboUsuarioSelect.getValue();

        if (grupoSeleccionado == null || usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Debes seleccionar grupo y usuario.");
            return;
        }

        if (SQLModelGrupo.agregarMiembroAlGrupo(usuarioSeleccionado.getId_usuario(), grupoSeleccionado.getId_grupo())) {
            mostrarAlerta("Éxito", "Usuario añadido al grupo.");
        } else {
            mostrarAlerta("Error", "No se pudo añadir al usuario.");
        }
    }

    /**
     * Elimina el grupo seleccionado tras solicitar confirmación al usuario.
     * @param event El evento de eliminación.
     */
    @FXML
    public void onEliminarButtonClick(ActionEvent event) {
        grupo seleccionado = TablaGrupos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Aviso", "Selecciona un grupo primero.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setContentText("¿Seguro que quieres borrar el grupo '" + seleccionado.getTitulo() + "'?");

        confirm.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                int idUsuario = SessionManager.getCurrentUser().getId_usuario();
                if (SQLModelGrupo.eliminarGrupo(seleccionado.getId_grupo(), idUsuario)) {
                    mostrarAlerta("Éxito", "Grupo eliminado.");
                    cargarTabla();
                }
            }
        });
    }

    private void mostrarAlerta(String titulo, String msj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msj);
        alert.show();
    }

    private void cambiarPantalla(ActionEvent event, String archivoFXML) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(archivoFXML));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}