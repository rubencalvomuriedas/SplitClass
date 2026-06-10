package org.example.slipclass_demo_1;

import javafx.application.Platform;
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

    // =========================================================================
    // 1. VARIABLES DE ESTADO VARIABLES GLOBALES
    // =========================================================================
    private usuario us;
    private boolean isNewUser = true;
    private ObservableList<usuario> usuariosObservableList = FXCollections.observableArrayList();


    // =========================================================================
    // 2. INYCCIONES FXML (@FXML) - AGRUPADAS POR PANTALLA
    // =========================================================================

    // --- [PANTALLA: LOGIN (login.fxml)] ---
    @FXML private TextField loginUsuario;
    @FXML private PasswordField loginPass;

    // --- [PANTALLA: REGISTRO USUARIO (registroUsuario.fxml)] ---
    @FXML private ListView<usuario> listViewUsuarios; // (También usado como panel de gestión)
    @FXML private TextField txtUsuario, txtEmail, txtTelefono;
    @FXML private PasswordField txtPass, txtPassConfirm;
    @FXML private DatePicker dpFechaNacimiento;

    // --- [PANTALLA: NUEVO GRUPO / GASTOS (registroGrupo.fxml)] ---
    @FXML private TextField txtGastoConcepto;
    @FXML private TextField txtGastoMonto;


    // =========================================================================
    // 3. INICIALIZACIÓN (MÉTODOS DE CARGA)
    // =========================================================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.listViewUsuarios != null) {
            this.listViewUsuarios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                this.us = newValue;
            });
            loadUsuariosList();
        }

        System.out.println("HELLO CONTROLLER");
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


    // =========================================================================
    // 4. LÓGICA POR PANTALLA (EVENTOS ON ACTION)
    // =========================================================================

    // -------------------------------------------------------------------------
    // --- PANTALLA: BIENVENIDA / INICIO (hello-view.fxml)
    // -------------------------------------------------------------------------
    @FXML
    public void onnIrALoginClick(ActionEvent event) {
        cambiarPantalla(event, "login.fxml");
    }

    @FXML
    public void onRegistrarseClick(ActionEvent event) {
        cambiarPantalla(event, "registroUsuario.fxml");
    }

    @FXML
    public void clickSobreNosotros(ActionEvent actionEvent) {
        mostrarAlerta("Próximamente", "En desarrollo... PROXIMAMENTE");
    }

    @FXML
    public void clickDescarga(ActionEvent actionEvent) {
        mostrarAlerta("Próximamente", "En desarrollo... PROXIMAMENTE");
    }

    // -------------------------------------------------------------------------
    // --- PANTALLA: LOGIN (login.fxml)
    // -------------------------------------------------------------------------
    @FXML
    public void onIniciarSesionClick(ActionEvent event) {
        String nombre = loginUsuario.getText();
        String pass = loginPass.getText();

        if (nombre.isEmpty() || pass.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor, rellena todos los campos.");
            return;
        }

        usuario usuarioLogueado = SQLModelUsuario.login(nombre, pass);

        if (usuarioLogueado != null) {
            SessionManager.setCurrentUser(usuarioLogueado);

            System.out.println("Login correcto: Bienvenido " + usuarioLogueado.getNombre());
            irAPantallaPrincipal(event);
        } else {
            mostrarAlerta("Error de acceso", "Usuario o contraseña incorrectos.");
        }
    }

    // -------------------------------------------------------------------------
    // --- PANTALLA: REGISTRO USUARIO (registroUsuario.fxml)
    // -------------------------------------------------------------------------

    @FXML
    public void onFinalizarRegistroClick(ActionEvent event) {
        if (txtUsuario.getText().isEmpty() || txtEmail.getText().isEmpty() || txtPass.getText().isEmpty() || txtTelefono.getText().isEmpty() || dpFechaNacimiento.getValue() == null) {
            return;
        }

        if (!txtPass.getText().equals(txtPassConfirm.getText())){
            mostrarAlerta("Error", "Las contraseñas no coinciden");
            return;
        }

        if (isNewUser) {
            this.us = new usuario(
                    txtUsuario.getText(),
                    txtEmail.getText(),
                    txtPass.getText(),
                    txtTelefono.getText(),
                    dpFechaNacimiento.getValue()
            );

            if (SQLModelUsuario.createUsuario(this.us)) {
                mostrarAlerta("Registro exitoso", "Usuario creado exitosamente");
                limpiarCampos();
            }
        } else {
            this.us.setNombre(txtUsuario.getText());
            this.us.setEmail(txtEmail.getText());
            this.us.setPassword(txtPass.getText());
            this.us.setTelefono(txtTelefono.getText());
            this.us.setFecha_nacimiento(dpFechaNacimiento.getValue());

            if (SQLModelUsuario.updateUsuario(this.us)) {
                mostrarAlerta("Exito", "Usuario actualizado exitosamente");
            }
        }
        System.out.println("Usuario registrado.");
    }

    @FXML
    private void onVolver(ActionEvent event) {
        cambiarPantalla(event, "hello-view.fxml");
    }

    private void limpiarCampos() {
        txtUsuario.clear();
        txtEmail.clear();
        txtPass.clear();
        txtPassConfirm.clear();
        txtTelefono.clear();
        dpFechaNacimiento.setValue(null);
    }


    // -------------------------------------------------------------------------
    // --- PANTALLAS: MENÚ USUARIO / MENÚ PRINCIPAL (menuUsuario.fxml / menuPrincipal.fxml)
    // -------------------------------------------------------------------------
    @FXML
    public void onVolverClick(ActionEvent event) {
        cambiarPantalla(event, "hello-view.fxml");
    }

    @FXML
    public void onTablaGrupooClick(ActionEvent event) {
        cambiarPantalla(event, "Grupos_view.fxml");
    }

    @FXML
    private void handleGrupos(ActionEvent event) {
        cambiarEscena(event, "menuGrupo.fxml");
    }

    @FXML
    private void handleGastos(ActionEvent event) {
        cambiarEscena(event, "ListaGastos.fxml");
    }

    @FXML
    private void salir(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void onVolverrClick(ActionEvent event) { // ¡Ojo! 2 'r's - Carga menú principal
        cambiarEscena(event, "menuPrincipal.fxml");
    }


    // -------------------------------------------------------------------------
    // --- PANTALLA: MENÚ GRUPOS (menuGrupo.fxml)
    // -------------------------------------------------------------------------
    @FXML
    public void onRegistroGrupoClick(ActionEvent event) {
        cambiarPantalla(event, "registroGrupo.fxml");
    }


    // -------------------------------------------------------------------------
    // --- PANTALLA: REGISTRO GRUPO / DETALLE (registroGrupo.fxml)
    // -------------------------------------------------------------------------
    @FXML
    private void onVolverrrClick(ActionEvent event) { // ¡Ojo! 3 'r's - Vuelve al menú de grupos
        cambiarEscena(event, "menuGrupo.fxml");
    }

    @FXML
    private void onCancelarrrClick(ActionEvent event) {
        txtGastoConcepto.clear();
        txtGastoMonto.clear();
        mostrarAlerta(Alert.AlertType.INFORMATION, "Cancelado", "Registro de Grupo cancelado.");
    }

    @FXML
    public void onGuardarrrClick(ActionEvent event) {
        // 1. Validar campos
        if (txtGastoConcepto.getText().isEmpty() || txtGastoMonto.getText().isEmpty()) {
            mostrarAlerta("Error", "Debes completar título y descripción");
            return;
        }

        // 2. Obtener usuario de la sesión (Esto evita errores de ID nulo)
        usuario userActual = SessionManager.getCurrentUser();
        if (userActual == null) {
            mostrarAlerta("Error", "Sesión expirada. Por favor, inicia sesión de nuevo.");
            return;
        }

        // 3. Crear grupo
        grupo g = new grupo();
        g.setTitulo(txtGastoConcepto.getText());
        g.setDescripcion(txtGastoMonto.getText());
        g.setMoneda("EUR"); // Asegúrate de asignar este valor

        // 4. Guardar usando la BD
        if (SQLModelGrupo.createGrupoConCreador(g, userActual.getId_usuario())) {
            mostrarAlerta("Éxito", "Grupo creado exitosamente.");
            limpiarCampos();
        } else {
            mostrarAlerta("Error", "No se pudo guardar en la base de datos.");
        }
    }


    // =========================================================================
    // 5. NAVEGACIÓN Y UTILIDADES GLOBALES
    // =========================================================================
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

    private void cambiarEscena(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla: " + fxmlFile);
            e.printStackTrace();
        }
    }

    private void irAPantallaPrincipal(ActionEvent event) {
        cambiarPantalla(event, "menuPrincipal.fxml");
    }

    private void mostrarAlerta(String titulo, String msj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msj);
        alert.show();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}