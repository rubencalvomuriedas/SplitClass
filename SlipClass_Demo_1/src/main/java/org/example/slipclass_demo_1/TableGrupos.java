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

public class TableGrupos implements Initializable {


    @FXML
    private TableView<grupo> TablaGrupos;

    @FXML private TableColumn<grupo, String> tablaGruposCod;
    @FXML private TableColumn<grupo, String> tablaGrupoTitulo;
    @FXML private TableColumn<grupo, String> tablaGrupoDescrip;
    @FXML private TableColumn<grupo, String> tablaGrupoMon;
    @FXML private TableColumn<grupo, LocalDate> tablaGrupoCreacion;


    @FXML
    private TableView<grupo> TablaGrupos1;

    @FXML private TableColumn<grupo, String> tablaGrupoTitulo1;
    @FXML private TableColumn<grupo, String> tablaGrupoDescrip1;
    @FXML private TableColumn<grupo, String> tablaGrupoMon1;
    @FXML private TableColumn<grupo, LocalDate> tablaGrupoCreacion1;


    @FXML private TextField txtGrupoTItulo, txtGrupoDescrip;
    @FXML private ComboBox<String> comboMoneda;

    @FXML private ComboBox<usuario> comboUsuarioSelect;
    @FXML private ComboBox<grupo> comboGrupoSelect;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tablaGrupoTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tablaGrupoDescrip.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tablaGrupoMon.setCellValueFactory(new PropertyValueFactory<>("moneda"));
        tablaGrupoCreacion.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));

        TablaGrupos.setItems(FXCollections.observableArrayList(SQLModelGrupo.getGruposPorUsuario(3)));

//        cargarUsuarios();
//        cargarGrupos();

        System.out.println("AAAAAA");
    
    }


    public void onModificarGrupoClick(ActionEvent actionEvent) {
        grupo seleccionado = TablaGrupos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            System.out.println("Por favor, selecciona un gasto de la tabla para editar.");
            return;
        }

        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("registroGrupo.fxml"));
            javafx.scene.Parent root = loader.load();

            controllerProvisional formularioController = loader. getController();

            //formularioController.cargarGrupoParaEditar(seleccionado);

            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.err.println("Error al cambiar a la pantalla de edición:");
            e.printStackTrace();
        }
    }

    public void onEliminarButtonClick(ActionEvent event) {
    }


    public void irAMenuGrupo(ActionEvent event) {
        cambiarPantalla(event, "menuGrupo.fxml");
        System.out.println("Volviendo al menú de grupos...");
    }

    public void onCancelarGrupo(ActionEvent event) {
    }

    public void onConfirmarGrupo(ActionEvent event) {
        if (txtGrupoTItulo.getText().isEmpty()) {
            mostrarAlerta("Error", "El título es obligatorio.");
            return;
        }

        String moneda = "EUR";

        grupo nuevoGrupo = new grupo(
                "",
                txtGrupoTItulo.getText(),
                txtGrupoDescrip.getText(),
                moneda,
                null, null, 0
        );

        int idUsuarioLogueado = 3;

        if (SQLModelGrupo.createGrupoConCreador(nuevoGrupo, idUsuarioLogueado)) {
            mostrarAlerta("Éxito", "Grupo creado y vinculado correctamente.");
            txtGrupoTItulo.clear();
            txtGrupoDescrip.clear();
        } else {
            mostrarAlerta("Error", "No se pudo crear el grupo.");
        }
    }

    public void pruebaAgregarUsuario(int idUsuario, int idGrupo) {
        if (SQLModelGrupo.agregarMiembroAlGrupo(idUsuario, idGrupo)) {
            System.out.println("¡Éxito! Usuario agregado al grupo.");
        } else {
            System.out.println("Error: No se pudo agregar (quizás el usuario ya es miembro).");
        }
    }

    private void cargarGrupos() {
        List<grupo> lista = SQLModelGrupo.getAllGrupos();
        ObservableList<grupo> observableLista = FXCollections.observableArrayList(lista);
        comboGrupoSelect.setItems(observableLista);

        comboGrupoSelect.setCellFactory(lv -> new ListCell<grupo>() {
            @Override
            protected void updateItem(grupo item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getTitulo());
            }
        });
        comboGrupoSelect.setButtonCell(new ListCell<grupo>() {
            @Override
            protected void updateItem(grupo item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getTitulo());
            }
        });
    }

    private void cargarUsuarios() {
        List<usuario> lista = SQLModelUsuario.getAllUsuarios();
        ObservableList<usuario> observableList = FXCollections.observableArrayList(lista);
        comboUsuarioSelect.setItems(observableList);

        comboUsuarioSelect.setCellFactory(lv -> new ListCell<usuario>() {
            @Override
            protected void updateItem(usuario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre());
            }
        });
        comboUsuarioSelect.setButtonCell(new ListCell<usuario>() {
            @Override
            protected void updateItem(usuario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre());
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


    public void onConfirmarGrupoMiembro(ActionEvent event) {
        grupo grupoSeleccionado = comboGrupoSelect.getValue();
        usuario usuarioSeleccionado = comboUsuarioSelect.getValue();

        if (grupoSeleccionado == null || usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Debes seleccionar tanto un grupo como un usuario.");
            return;
        }

        int idGrupo = grupoSeleccionado.getId_grupo();
        int idUsuario = usuarioSeleccionado.getId_usuario();

        if (SQLModelGrupo.agregarMiembroAlGrupo(idUsuario, idGrupo)) {
            mostrarAlerta("Éxito", "Usuario '" + usuarioSeleccionado.getNombre() +
                    "' añadido al grupo '" + grupoSeleccionado.getTitulo() + "'.");

            comboGrupoSelect.getSelectionModel().clearSelection();
            comboUsuarioSelect.getSelectionModel().clearSelection();
        } else {
            mostrarAlerta("Error", "No se pudo añadir al usuario. ¿Quizás ya pertenece al grupo?");
        }
    }

    public void onCargarGruposFiltradoClick(ActionEvent event) {

    }

    private void cambiarPantalla(ActionEvent event, String archivoFXML) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(archivoFXML));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar " + archivoFXML + ": " + e.getMessage());
            mostrarAlerta("Error de Navegación", "No se encontró el archivo FXML: " + archivoFXML);
        }
    }
}
