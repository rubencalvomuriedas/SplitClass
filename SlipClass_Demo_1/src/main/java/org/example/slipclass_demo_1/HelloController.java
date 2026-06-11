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

    // --- [PANTALLA: LOGIN (Login.fxml)] ---
    @FXML private TextField loginUsuario;
    @FXML private PasswordField loginPass;
    @FXML private TextField txtGastoTitulo;
    @FXML private TextField txtGastoDescript;
    // --- [PANTALLA: REGISTRO USUARIO (registroUsuario.fxml)] ---
    @FXML private ListView<usuario> listViewUsuarios; // (También usado como panel de gestión)
    @FXML private TextField txtUsuario, txtEmail, txtTelefono;
    @FXML private PasswordField txtPass, txtPassConfirm;
    @FXML private DatePicker dpFechaNacimiento;

    // --- [PANTALLA: NUEVO GRUPO / GASTOS (RegisterGrupos.fxml)] ---


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
    // --- PANTALLA: BIENVENIDA / INICIO (Inicio.fxml)
    // -------------------------------------------------------------------------
    @FXML
    public void onnIrALoginClick(ActionEvent event) {
        cambiarPantalla(event, "Login.fxml");
    }

    @FXML
    public void onRegistrarseClick(ActionEvent event) {
        cambiarPantalla(event, "Register.fxml");
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
    // --- PANTALLA: LOGIN (Login.fxml)
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
        cambiarPantalla(event, "Inicio.fxml");
    }

    private void limpiarCampos() {
        txtUsuario.clear();
        txtEmail.clear();
        txtPass.clear();
        txtPassConfirm.clear();
        txtTelefono.clear();
        dpFechaNacimiento.setValue(null);
    }

    private void limpiarCamposGrupo() {
        txtGastoTitulo.clear();
        txtGastoDescript.clear();
    }


    // -------------------------------------------------------------------------
    // --- PANTALLAS: MENÚ USUARIO / MENÚ PRINCIPAL (menuUsuario.fxml / MenuPrincipal.fxml)
    // -------------------------------------------------------------------------
    @FXML
    public void onVolverClick(ActionEvent event) {
        cambiarPantalla(event, "Inicio.fxml");
    }

    @FXML
    public void onTablaGrupooClick(ActionEvent event) {
        cambiarPantalla(event, "TableViewGrupos.fxml");
    }

    @FXML
    private void handleGrupos(ActionEvent event) {
        cambiarEscena(event, "MenuGrupo.fxml");
    }

    @FXML
    private void handleGastos(ActionEvent event) {
        cambiarEscena(event, "TableViewGastos.fxml");
    }

    @FXML
    private void salir(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void onVolverrClick(ActionEvent event) { // ¡Ojo! 2 'r's - Carga menú principal
        cambiarEscena(event, "MenuPrincipal.fxml");
    }


    // -------------------------------------------------------------------------
    // --- PANTALLA: MENÚ GRUPOS (MenuGrupo.fxml)
    // -------------------------------------------------------------------------
    @FXML
    public void onRegistroGrupoClick(ActionEvent event) {
        cambiarPantalla(event, "RegisterGrupos.fxml");
    }


    // -------------------------------------------------------------------------
    // --- PANTALLA: REGISTRO GRUPO / DETALLE (RegisterGrupos.fxml)
    // -------------------------------------------------------------------------
    @FXML
    private void onVolverrrClick(ActionEvent event) { // ¡Ojo! 3 'r's - Vuelve al menú de grupos
        cambiarEscena(event, "MenuGrupo.fxml");
    }

    @FXML
    private void onCancelarrrClick(ActionEvent event) {
        limpiarCamposGrupo();
    }

    @FXML
    public void onGuardarrrClick(ActionEvent event) {
        // 1. Validar campos
        if (txtGastoTitulo.getText().isEmpty() || txtGastoDescript.getText().isEmpty()) {
            mostrarAlerta("Error", "Debes completar título y descripción");
            return;
        }

        usuario userActual = SessionManager.getCurrentUser();
        if (userActual == null) {
            mostrarAlerta("Error", "Sesión expirada.");
            return;
        }

        // 2. Lógica de Edición vs Creación
        if (this.grupoEnEdicion != null) {
            // --- MODO EDICIÓN ---
            this.grupoEnEdicion.setTitulo(txtGastoTitulo.getText());
            this.grupoEnEdicion.setDescripcion(txtGastoDescript.getText());

            if (SQLModelGrupo.updateGrupo(this.grupoEnEdicion, userActual.getId_usuario())) {
                mostrarAlerta("Éxito", "Grupo actualizado exitosamente.");
                // Limpiamos la variable para que la próxima vez sea una creación
                this.grupoEnEdicion = null;
                limpiarCamposGrupo();
            } else {
                mostrarAlerta("Error", "No se pudo actualizar el grupo.");
            }
        } else {
            // --- MODO CREACIÓN (Tu código original) ---
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
        cambiarPantalla(event, "MenuPrincipal.fxml");
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


    private grupo grupoEnEdicion = null;


    // Asegúrate de que los IDs aquí coincidan exactamente con el RegisterGrupos.fxml
    public void prepararEdicion(grupo g) {
        this.grupoEnEdicion = g; // Guardamos el grupo para saber que estamos editando

        // Rellenamos los campos con los datos del grupo seleccionado
        if (txtGastoTitulo != null) {
            txtGastoTitulo.setText(g.getTitulo());
        }
        if (txtGastoDescript != null) {
            txtGastoDescript.setText(g.getDescripcion());
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        try {
            // 1. Cargamos el archivo FXML de la vista inicial
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
            Parent root = loader.load();

            // 2. Obtenemos el Stage actual (la ventana que se está mostrando)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 3. Creamos una nueva escena con el contenido cargado
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al cerrar sesión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void apagarapp(ActionEvent Event) {
        Platform.exit();
    }

    @FXML
    void onAnadirGrupoClick(ActionEvent event) {
        // Creamos el diálogo de entrada de texto
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Validación requerida");
        dialog.setHeaderText("Introduce el código de acceso");
        dialog.setContentText("Código:");

        // Mostramos el diálogo y esperamos la respuesta
        Optional<String> result = dialog.showAndWait();

        // Comprobamos si el usuario pulsó OK y si escribió algo
        result.ifPresent(codigo -> {
            if (codigo.equals("TU_CODIGO_SECRETO")) { // Cambia esto por tu lógica de validación
                System.out.println("Código correcto. Accediendo...");
                // Aquí puedes llamar a otra ventana o realizar la acción
            } else {
                System.out.println("Código incorrecto.");
                // Opcional: mostrar un Alert de error
            }
        });
    }

    @FXML
    void onAnadirGrupooClick(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Unirse a un grupo");
        dialog.setHeaderText("Introduce el código de invitación");
        dialog.setContentText("Código del grupo:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(codigoIngresado -> {
            // Llamada a un método que haga la consulta a la BD
            boolean esValido = verificarYUnirAGrupo(codigoIngresado);

            if (esValido) {
                mostrarMensaje("Éxito", "Te has unido al grupo correctamente.");
            } else {
                mostrarMensaje("Error", "Código inválido o no existente.");
            }
        });
    }

    // Método de ejemplo para tu lógica de BD
    private boolean verificarYUnirAGrupo(String codigo) {
        // 1. Conecta a tu BD
        // 2. Ejecuta un SELECT para ver si el código existe en la tabla de grupos
        // 3. Si existe, ejecuta un INSERT en la tabla intermedia 'usuario_grupo'
        //    (o la lógica que tengas para relacionarlos)

        // Ejemplo ficticio:
        // String consulta = "SELECT id_grupo FROM grupos WHERE codigo_invitacion = ?";
        // return baseDeDatos.ejecutarInsercion(consulta, codigo);

        return false; // Cambia esto por tu lógica real
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}