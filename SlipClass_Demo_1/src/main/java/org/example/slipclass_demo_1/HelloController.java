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

    private void limpiarCampos() {
        txtUsuario.clear();
        txtEmail.clear();
        txtPass.clear();
        txtPassConfirm.clear();
        txtTelefono.clear();
        dpFechaNacimiento.setValue(null);
    }


    @FXML
    private void onVolver(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- MENÚ GRUPOS ---
    @FXML
    public void onRegistroGrupoClick(ActionEvent event) {
        cambiarPantalla(event, "registroGrupo.fxml");
    }
    @FXML
    public void onTablaGrupoClick(ActionEvent event) {
        mostrarAlerta("Tabla Grupos", "Abriendo vista de Tabla/Listado de Grupos...");
    }

    @FXML
    private void onVolverrClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menuPrincipal.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al cargar menuPrincipal.fxml");
            e.printStackTrace();
        }
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

    @FXML public void onIniciarSesionClick(ActionEvent event) {
        String nombre = loginUsuario.getText();
        String pass = loginPass.getText();

        if (nombre.isEmpty() || pass.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor, rellena todos los campos.");
            return;
        }

        usuario usuarioLogueado = SQLModelUsuario.login(nombre, pass);

        if (usuarioLogueado != null) {
            System.out.println("Login correcto: Bienvenido " + usuarioLogueado.getNombre());

            irAPantallaPrincipal(event);
        } else {
            mostrarAlerta("Error de acceso", "Usuario o contraseña incorrectos.");
        } }

    private void irAPantallaPrincipal(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("menuUsuario.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cambiar de pantalla: " + e.getMessage());
        }
    }


    @FXML
    public void onVolverClick(ActionEvent event) {
        cambiarPantalla(event, "menuUsuario.fxml");
    }

    @FXML
    public void onTablaGrupooClick(ActionEvent event) {
        cambiarPantalla(event, "Grupos_view.fxml");
    }

    @FXML
    public void onnIrALoginClick(ActionEvent event) {

        cambiarPantalla(event, "login.fxml");
    }
    public void clickSobreNosotros(ActionEvent actionEvent) {
    }

    public void clickDescarga(ActionEvent actionEvent) {
    }

    public void onRegistrarseClick(ActionEvent event) {
        cambiarPantalla(event, "registroUsuario.fxml");
    }


    //UTILIDAD MENU PRINCIPAL
    @FXML
    private void handleGrupos(ActionEvent event) {
        cambiarEscena(event, "menuGrupo.fxml");
    }

    @FXML
    private void handleGastos(ActionEvent event) {
        cambiarEscena(event, "ListaGastos.fxml");
    }

    @FXML
    private void handleSalir(ActionEvent event) {
        cambiarEscena(event, "hello-view.fxml");
    }

    @FXML
    private void handlePerfil(ActionEvent event) {
        cambiarEscena(event, "Perfil.fxml");
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

    //Utilidad Nuevo Grupo
    @FXML
    private TextField txtGastoConcepto;
    @FXML
    private TextField txtGastoMonto;

    @FXML
    private void onVolverrrClick(ActionEvent event) {
        cambiarEscena(event, "menuGrupo.fxml");
    }

    @FXML
    private void onCancelarrrClick(ActionEvent event) {
        txtGastoConcepto.clear();
        txtGastoMonto.clear();
        mostrarAlerta(Alert.AlertType.INFORMATION, "Cancelado", "Registro de Grupo cancelado.");
    }

    @FXML
    private void onGuardarrrClick(ActionEvent event) {
        String titulo = txtGastoConcepto.getText().trim();
        String descripcion = txtGastoMonto.getText().trim();

        if (titulo.isEmpty() || descripcion.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Hay que rellenar todos los campos");
        } else {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Nuevo Grupo creado correctamente.");
            txtGastoConcepto.clear();
            txtGastoMonto.clear();
        }
    }
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}
