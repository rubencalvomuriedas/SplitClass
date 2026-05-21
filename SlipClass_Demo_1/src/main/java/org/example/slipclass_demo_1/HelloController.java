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
import javafx.stage.Stage;
import org.example.slipclass_demo_1.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private usuario us;
    private boolean isNewUser = true;
    private ObservableList<usuario> usuariosObservableList = FXCollections.observableArrayList();

    @FXML private ListView<usuario> listViewUsuarios;

    // Campos de texto para los registros (conservados por si los necesitas)
    @FXML private TextField txtUsuario, txtEmail, txtTelefono, loginUsuario;
    @FXML private PasswordField txtPass, txtPassConfirm, loginPass;
    @FXML private DatePicker dpFechaNacimiento;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.listViewUsuarios != null) {
            this.listViewUsuarios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                this.us = newValue;
            });
            loadUsuariosList();
        }
    }

    public void loadUsuariosList() {
        try {
            this.usuariosObservableList.clear();
            List<usuario> misUsuarios = SQLModelUsuario.getAllUsuarios();
            if (misUsuarios != null) {
                this.usuariosObservableList.addAll(misUsuarios);
                this.listViewUsuarios.setItems(this.usuariosObservableList);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar la lista: " + e.getMessage());
        }
    }

    // ========================================================
    // 1. NAVEGACIÓN ENTRE LOS 3 MENÚS (Flecha Siguiente Menú)
    // ========================================================
    @FXML
    public void irAMenuUsuario(ActionEvent event) {
        cambiarPantalla(event, "menuUsuario.fxml");
    }

    @FXML
    public void irAMenuGrupo(ActionEvent event) {
        cambiarPantalla(event, "menuGrupo.fxml");
    }

    @FXML
    public void irAMenuGasto(ActionEvent event) {
        cambiarPantalla(event, "menuGasto.fxml");
    }

    // ========================================================
    // 2. BOTONES INTERNOS DE CADA MENÚ (Registro y Tablas)
    // ========================================================

    // --- MENÚ USUARIO ---
    @FXML
    public void onRegistroUsuarioClick(ActionEvent event) {
        cambiarPantalla(event, "registro.fxml"); // Tu pantalla de registrar usuario
    }

    // --- MENÚ GRUPOS ---
    @FXML
    public void onRegistroGrupoClick(ActionEvent event) {
        cambiarPantalla(event, "formularioGrupo.fxml"); // Tu formulario de nuevo grupo
    }
    @FXML
    public void onTablaGrupoClick(ActionEvent event) {
        mostrarAlerta("Tabla Grupos", "Abriendo vista de Tabla/Listado de Grupos...");
        // cambiarPantalla(event, "tablaGrupos.fxml");
    }

    // --- MENÚ GASTOS ---
//    @FXML
//    public void onRegistroGastoClick(ActionEvent event) {
//        cambiarPantalla(event, "formularioGasto.fxml"); // Tu formulario de nuevo gasto
//    }
//    @FXML
//    public void onTablaGastoClick(ActionEvent event) {
//        mostrarAlerta("Tabla Gastos", "Abriendo vista de Tabla/Listado de Gastos...");
//        // cambiarPantalla(event, "tablaGastos.fxml");
//    }

    // ========================================================
    // 3. MÉTODOS AUXILIARES
    // ========================================================
    @FXML
    public void onVolverAMenuPrincipal(ActionEvent event) {
        cambiarPantalla(event, "menuUsuario.fxml"); // Define cuál quieres que sea tu menú "Home" por defecto
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

    private void mostrarAlerta(String titulo, String msj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msj);
        alert.show();
    }

    // Métodos heredados obligatorios de tu código original
    @FXML public void onIniciarSesionClick(ActionEvent event) { cambiarPantalla(event, "menuUsuario.fxml"); }
    @FXML public void onFinalizarRegistroClick(ActionEvent event) { cambiarPantalla(event, "menuUsuario.fxml"); }

    @FXML
    public void onTablaUsuarioClick(ActionEvent event) {
        // Al hacer clic, carga y cambia la escena actual a la tabla de usuarios
        cambiarPantalla(event, "ListaUsuarios.fxml");
    }

    @FXML
    public void onVolverClick(ActionEvent event) {
        // Redirige de vuelta al menú de usuarios
        cambiarPantalla(event, "menuUsuario.fxml");
    }

    @FXML
    public void onTablaGrupooClick(ActionEvent event) {
        // Cambia "ListaGrupos.fxml" por el nombre exacto de tu archivo si se llama distinto
        cambiarPantalla(event, "Grupos_view.fxml");
    }

    public void onEliminarButtonClick(ActionEvent actionEvent) {
    }

    // ==========================================
    // NAVEGACIÓN DESDE EL MENÚ GASTOS
    // ==========================================

    // 1. Lleva al formulario para añadir un nuevo gasto
    @FXML
    public void onRegistroGastoClick(ActionEvent event) {
        // Cambia "formularioGasto.fxml" por el nombre exacto de tu pantalla de formulario de gastos
        cambiarPantalla(event, "registerGasto.fxml");
    }

    // 2. Lleva al listado/tabla de gastos
    @FXML
    public void onTablaGastoClick(ActionEvent event) {
        // Cambia "Gastos_view.fxml" por el nombre de tu fxml de tabla de gastos cuando lo crees
        cambiarPantalla(event, "Gastos_view.fxml");
    }

    @FXML
    public void onCancelarGasto(ActionEvent event) {
        // Cancela la operación y te devuelve al menú de gastos
        cambiarPantalla(event, "menuGasto.fxml");
    }

    @FXML
    public void onTablaGastoooClick(ActionEvent event) {
        // Asegúrate de que el nombre del archivo coincida exactamente con tu archivo .fxml
        cambiarPantalla(event, "ListaGastos.fxml");
    }

    @FXML
    public void onVolverAMenuGasto(ActionEvent event) {
        // Esto te llevará de vuelta al menú de gastos
        cambiarPantalla(event, "menuGasto.fxml");
    }
    // ==========================================
    // NAVEGACIÓN DESDE EL MENÚ GRUPOS
    // ==========================================

    // 1. Lleva al formulario para crear un nuevo grupo
//    @FXML
//    public void onRegistroGrupoClick(ActionEvent event) {
//        cambiarPantalla(event, "formularioGrupo.fxml");
//    }
//
//    // 2. Llevará a la tabla de grupos (cuando la crees)
//    @FXML
//    public void onTablaGrupoClick(ActionEvent event) {
//        // Si aún no tienes el FXML de la tabla, dejamos una alerta temporal
//        mostrarAlerta("Próximamente", "Aquí cargaremos la tabla de Grupos.");
//        // cambiarPantalla(event, "ListaGrupos.fxml");
//    }
//
//    // 3. La flecha que avanza al siguiente menú principal
//    @FXML
//    public void irAMenuGasto(ActionEvent event) {
//        cambiarPantalla(event, "menuGasto.fxml");
//    }
}