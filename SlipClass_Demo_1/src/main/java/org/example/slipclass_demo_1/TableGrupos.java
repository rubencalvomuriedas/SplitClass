package org.example.slipclass_demo_1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.slipclass_demo_1.model.*;
import org.example.slipclass_demo_1.model.utils.Alertas;
import org.example.slipclass_demo_1.model.utils.Navigator;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class TableGrupos implements Initializable {
    
    public Node root;
    public TextField txtGastoMonto;
    public TextField txtGastoConcepto;
    public Button guardar;

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
    
    }


    public void onModificarGrupoClick(ActionEvent actionEvent) {
        grupo seleccionado = TablaGrupos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            System.out.println("Por favor, selecciona un gasto de la tabla para editar.");
            return;
        }

//        try {
//            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("registroGrupo.fxml"));
//            javafx.scene.Parent root = loader.load();
//
//            controllerProvisional formularioController = loader. getController();
//
//            //formularioController.cargarGrupoParaEditar(seleccionado);
//
//            javafx.scene.Scene scene = new javafx.scene.Scene(root);
//            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
//            stage.setScene(scene);
//            stage.show();
//
//        } catch (Exception e) {
//            System.err.println("Error al cambiar a la pantalla de edición:");
//            e.printStackTrace();
//        }
        try {

            String fxml = "registroGrupo.fxml";

            String titulo = "Modificar grupo";

            Navigator.arbrirVentanaSecundaria(fxml, titulo, getClass());


        } catch (IOException e) {
            Alertas.showAlert("No se puede crear grupos", String.valueOf(e), Alert.AlertType.ERROR);
        }
    }

    public void onEliminarButtonClick(ActionEvent event) {
    }

    @FXML
    private void abrirVentanaCrearGrupo() {
        try {

            String fxml = "registroGrupo.fxml";

            String titulo = "Crear grupo";

            Navigator.arbrirVentanaSecundaria(fxml, titulo, getClass());


        } catch (IOException e) {
            Alertas.showAlert("No se puede crear grupos", String.valueOf(e), Alert.AlertType.ERROR);
        }
    }

    public void onVolverRegisterAction(ActionEvent event) {
        try {

            String fxml = "menuPrincipal.fxml";

            String titulo = "SplitClass";

            Navigator.arbrirVentanaSecundaria(fxml, titulo, getClass());


        } catch (IOException e) {
            Alertas.showAlert("No se puede crear grupos", String.valueOf(e), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onCancelarrrClick(ActionEvent event) {
        txtGastoConcepto.clear();
        txtGastoMonto.clear();
        
        Alertas.showAlert("Cancelado", "Registro de Grupo cancelado.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void onVolverrrClick(ActionEvent event) { // ¡Ojo! 3 'r's - Vuelve al menú de grupos
        try {

            String fxml = "menuGrupo.fxml";

            String titulo = "Menu grupos";

            Navigator.arbrirVentanaSecundaria(fxml, titulo, getClass());


        } catch (IOException e) {
            Alertas.showAlert("No se puede crear grupos", String.valueOf(e), Alert.AlertType.ERROR);
        }
    }

    public void onGuardarGrupo(ActionEvent event) {
        String moneda = "EUR";

        grupo nuevoGrupo = new grupo(
                "",
                txtGastoConcepto.getText(),
                txtGastoMonto.getText(),
                moneda,
                LocalDate.now(), null, 0
        );

        if (SQLModelGrupo.insertarGrupo(nuevoGrupo)) {
            mostrarAlerta("Éxito", "Grupo creado y vinculado correctamente.");
            txtGastoConcepto.clear();
            txtGastoMonto.clear();
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

    @FXML
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




}
