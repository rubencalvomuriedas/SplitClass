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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Clase controladora principal que gestiona la lógica de interfaz (GUI) para la aplicación.
 * Implementa {@link Initializable} para configurar los componentes al cargar la vista.
 * Maneja el flujo de autenticación, gestión de registros, navegación entre escenas
 * y operaciones sobre grupos.
 * * @author TuNombre
 * @version 1.0
 */
public class HelloController implements Initializable {

    /** Usuario actualmente seleccionado en la lista o en edición. */
    private usuario us;

    /** Flag booleano que indica si se está creando un nuevo registro (true) o editando uno existente (false). */
    private boolean isNewUser = true;

    /** Lista observable de usuarios para sincronización automática con la vista {@link ListView}. */
    private ObservableList<usuario> usuariosObservableList = FXCollections.observableArrayList();

    /** Campo de texto para el email/nombre de usuario en el login. */
    @FXML private TextField loginUsuario;
    /** Campo de contraseña para el inicio de sesión. */
    @FXML private PasswordField loginPass;
    /** Campo de texto para el título del gasto. */
    @FXML private TextField txtGastoTitulo;
    /** Campo de texto para la descripción del gasto. */
    @FXML private TextField txtGastoDescript;
    /** Lista visual de usuarios registrados. */
    @FXML private ListView<usuario> listViewUsuarios;
    /** Campo de entrada para nombre de usuario en registro. */
    @FXML private TextField txtUsuario;
    /** Campo de entrada para email en registro. */
    @FXML private TextField txtEmail;
    /** Campo de entrada para teléfono en registro. */
    @FXML private TextField txtTelefono;
    /** Campo de contraseña en registro. */
    @FXML private PasswordField txtPass;
    /** Campo de confirmación de contraseña en registro. */
    @FXML private PasswordField txtPassConfirm;
    /** Selector de fecha de nacimiento. */
    @FXML private DatePicker dpFechaNacimiento;

    /**
     * Inicializa los componentes de la interfaz.
     * Configura los listeners de selección para el {@link ListView}.
     * * @param url La ubicación relativa de los archivos FXML.
     * @param resourceBundle Los recursos localizados.
     */
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

    /**
     * Carga la lista completa de usuarios desde la base de datos y actualiza la interfaz.
     */
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

    /**
     * Navega a la pantalla de Login.
     * @param event El evento de acción.
     */
    @FXML public void onnIrALoginClick(ActionEvent event) { cambiarPantalla(event, "Login.fxml"); }

    /**
     * Navega a la pantalla de Registro.
     * @param event El evento de acción.
     */
    @FXML public void onRegistrarseClick(ActionEvent event) { cambiarPantalla(event, "Register.fxml"); }

    /** Muestra alerta informativa de función en desarrollo. */
    @FXML public void clickSobreNosotros(ActionEvent actionEvent) { mostrarAlerta("Próximamente", "En desarrollo... PROXIMAMENTE"); }

    /** Muestra alerta informativa de función en desarrollo. */
    @FXML public void clickDescarga(ActionEvent actionEvent) { mostrarAlerta("Próximamente", "En desarrollo... PROXIMAMENTE"); }

    /**
     * Valida las credenciales introducidas y maneja el acceso al sistema.
     * @param event El evento de acción.
     */
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

    /**
     * Procesa el formulario de registro. Valida datos y contraseñas antes de persistir en DB.
     * @param event El evento de acción.
     */
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
            this.us = new usuario(txtUsuario.getText(), txtEmail.getText(), txtPass.getText(), txtTelefono.getText(), dpFechaNacimiento.getValue());
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

    /** Navega a Inicio.fxml. */
    @FXML private void onVolver(ActionEvent event) { cambiarPantalla(event, "Inicio.fxml"); }

    /** Limpia los campos del formulario de usuario. */
    private void limpiarCampos() {
        txtUsuario.clear();
        txtEmail.clear();
        txtPass.clear();
        txtPassConfirm.clear();
        txtTelefono.clear();
        dpFechaNacimiento.setValue(null);
    }

    /** Limpia los campos del formulario de grupo. */
    private void limpiarCamposGrupo() {
        txtGastoTitulo.clear();
        txtGastoDescript.clear();
    }

    /** Navega a Inicio.fxml. */
    @FXML public void onVolverClick(ActionEvent event) { cambiarPantalla(event, "Inicio.fxml"); }

    /** Navega a la tabla de grupos. */
    @FXML public void onTablaGrupooClick(ActionEvent event) { cambiarPantalla(event, "TableViewGrupos.fxml"); }

    /** Navega al menú de grupos. */
    @FXML private void handleGrupos(ActionEvent event) { cambiarEscena(event, "MenuGrupo.fxml"); }

    /** Navega a la tabla de gastos. */
    @FXML private void handleGastos(ActionEvent event) { cambiarEscena(event, "TableViewGastos.fxml"); }

    /** Finaliza la aplicación. */
    @FXML private void salir(ActionEvent event) { Platform.exit(); }

    /** Navega al menú principal. */
    @FXML private void onVolverrClick(ActionEvent event) { cambiarEscena(event, "MenuPrincipal.fxml"); }

    /** Navega a la pantalla de registro de grupos. */
    @FXML public void onRegistroGrupoClick(ActionEvent event) { cambiarPantalla(event, "RegisterGrupos.fxml"); }

    /** Navega al menú de grupos. */
    @FXML private void onVolverrrClick(ActionEvent event) { cambiarEscena(event, "MenuGrupo.fxml"); }

    /** Limpia el formulario de grupos. */
    @FXML private void onCancelarrrClick(ActionEvent event) { limpiarCamposGrupo(); }

    /**
     * Maneja la lógica de guardado/actualización de un grupo en la base de datos.
     * @param event El evento de acción.
     */
    @FXML
    public void onGuardarrrClick(ActionEvent event) {
        if (txtGastoTitulo.getText().isEmpty() || txtGastoDescript.getText().isEmpty()) {
            mostrarAlerta("Error", "Debes completar título y descripción");
            return;
        }
        usuario userActual = SessionManager.getCurrentUser();
        if (userActual == null) {
            mostrarAlerta("Error", "Sesión expirada.");
            return;
        }

        if (this.grupoEnEdicion != null) {
            this.grupoEnEdicion.setTitulo(txtGastoTitulo.getText());
            this.grupoEnEdicion.setDescripcion(txtGastoDescript.getText());
            if (SQLModelGrupo.updateGrupo(this.grupoEnEdicion, userActual.getId_usuario())) {
                mostrarAlerta("Éxito", "Grupo actualizado exitosamente.");
                this.grupoEnEdicion = null;
                limpiarCamposGrupo();
            } else {
                mostrarAlerta("Error", "No se pudo actualizar el grupo.");
            }
        } else {
            grupo g = new grupo();
            g.setTitulo(txtGastoTitulo.getText());
            g.setDescripcion(txtGastoDescript.getText());
            g.setMoneda("EUR");
            if (SQLModelGrupo.createGrupoConCreador(g, userActual.getId_usuario())) {
                mostrarAlerta("Éxito", "Grupo creado exitosamente.");
                txtGastoTitulo.clear();
                txtGastoDescript.clear();
            } else {
                mostrarAlerta("Error", "No se pudo guardar.");
            }
        }
    }

    /**
     * Carga un archivo FXML dado y cambia la escena actual.
     * @param event El evento que dispara la acción.
     * @param archivoFXML Nombre del archivo.
     */
    private void cambiarPantalla(ActionEvent event, String archivoFXML) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(archivoFXML));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Error de Navegación", "No se encontró: " + archivoFXML);
        }
    }

    /**
     * Cambia la escena usando un FXMLLoader explícito.
     * @param event El evento.
     * @param fxmlFile Nombre del archivo.
     */
    private void cambiarEscena(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Navega al menú principal. */
    private void irAPantallaPrincipal(ActionEvent event) { cambiarPantalla(event, "MenuPrincipal.fxml"); }

    /** Muestra un aviso simple al usuario. */
    private void mostrarAlerta(String titulo, String msj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msj);
        alert.show();
    }

    /** Muestra una alerta con un tipo definido. */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /** Referencia al grupo que está siendo editado actualmente. */
    private grupo grupoEnEdicion = null;

    /**
     * Carga datos de un grupo existente en el formulario para su edición.
     * @param g Grupo a editar.
     */
    public void prepararEdicion(grupo g) {
        this.grupoEnEdicion = g;
        if (txtGastoTitulo != null) txtGastoTitulo.setText(g.getTitulo());
        if (txtGastoDescript != null) txtGastoDescript.setText(g.getDescripcion());
    }

    /** Cierra sesión y vuelve al Inicio. */
    @FXML private void cerrarSesion(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Inicio.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Finaliza el proceso de la aplicación. */
    public void apagarapp(ActionEvent Event) { Platform.exit(); }

    /** Muestra diálogo para introducir código de acceso. */
    @FXML void onAnadirGrupoClick(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Validación");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(codigo -> { /* Lógica de validación */ });
    }

    /** Muestra diálogo para unirse a un grupo mediante código. */
    @FXML void onAnadirGrupooClick(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(codigo -> { /* Lógica de unión */ });
    }

    /** Verifica si un código de grupo es válido. */
    private boolean verificarYUnirAGrupo(String codigo) { return false; }

    /** Muestra mensaje informativo al usuario. */
    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}