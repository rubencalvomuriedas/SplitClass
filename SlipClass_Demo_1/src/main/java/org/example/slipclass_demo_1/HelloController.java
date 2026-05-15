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
import org.example.slipclass_demo_1.model.SQLModelUsuario;
import org.example.slipclass_demo_1.model.usuario;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private usuario us;
    private boolean isNewUser = true;
    private ObservableList<usuario> usuariosObservableList = FXCollections.observableArrayList();

    @FXML
    private ListView<usuario> listViewUsuarios;

    @FXML private TextField txtUsuario, txtEmail, txtTelefono;
    @FXML private PasswordField txtPass, txtPassConfirm;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private TextField loginUsuario;
    @FXML private PasswordField loginPass;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Solo ejecutamos esto si el FXML cargado tiene la lista (hello-view.fxml)
        if (this.listViewUsuarios != null) {
            this.listViewUsuarios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                this.us = newValue;
                if (newValue != null) {
                    System.out.println("Usuario seleccionado: " + newValue.getNombre());
                }
            });

            loadUsuariosList();
        }
    }

    public void loadUsuariosList(){
        try {
            this.usuariosObservableList.clear();

            List<usuario> misUsuarios = SQLModelUsuario.getAllUsuarios();

            if (misUsuarios != null){
                this.usuariosObservableList.addAll(misUsuarios);
                this.listViewUsuarios.setItems(this.usuariosObservableList);
            }


        } catch (Exception e) {
            System.out.println("Error al cargar la lista de usuarios: " + e.getMessage());
        }

    }


    @FXML
    public void clickDescarga() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Próximamente");
        alerta.setHeaderText(null);
        alerta.setContentText("¡La descarga estará disponible próximamente!");
        alerta.show();
    }

    @FXML
    public void clickSobreNosotros() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Sección en construcción");
        alerta.setHeaderText(null);
        alerta.setContentText("Próximamente...");
        alerta.show();
    }

    @FXML
    public void clickSobreBuscar() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Esto es lo que se va a mostar");
        alerta.setHeaderText(null);
        alerta.setContentText("Esto es lo que se va a mostar");
        alerta.show();
    }

    @FXML
    public void clickSobreListar() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Esto es lo que se va a mostar");
        alerta.setHeaderText(null);
        alerta.setContentText("Esto es lo que se va a mostar");
        alerta.show();
    }

    @FXML
    public void clickSobreInsertar() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Esto es lo que se va a mostar");
        alerta.setHeaderText(null);
        alerta.setContentText("Esto es lo que se va a mostar");
        alerta.show();
    }

    @FXML
    public void clickSobreResgistrar() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Esto es lo que se va a mostar");
        alerta.setHeaderText(null);
        alerta.setContentText("Esto es lo que se va a mostar");
        alerta.show();
    }

    @FXML
    public void clickSobreMostrar() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Esto es lo que se va a mostar");
        alerta.setHeaderText(null);
        alerta.setContentText("Esto es lo que se va a mostar");
        alerta.show();
    }

    @FXML
    public void clickSobreEliminar() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Esto es lo que se va a mostar");
        alerta.setHeaderText(null);
        alerta.setContentText("Esto es lo que se va a mostar");
        alerta.show();

    }

    public void onRegistrarseClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("registro.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cambiar de pantalla: " + e.getMessage());
        }
    }

    public void onInsertarButtonClick(ActionEvent event) {
    }

    public void onListadoButtonClick(ActionEvent event) {
    }

    public void onEliminarButtonClick(ActionEvent event) {
    }

    public void onBuscarButtonClick(ActionEvent event) {
    }

    public void onRegistroButtonClick(ActionEvent event) {


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

    @FXML
    private void onVolverClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        txtUsuario.clear();
        txtEmail.clear();
        txtPass.clear();
        txtPassConfirm.clear();
        txtTelefono.clear();
        dpFechaNacimiento.setValue(null);
    }
    private void mostrarAlerta(String titulo, String msj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(msj);
        alert.show();
    }

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
            System.out.println("Login correcto: Bienvenido " + usuarioLogueado.getNombre());

            irAPantallaPrincipal(event);
        } else {
            mostrarAlerta("Error de acceso", "Usuario o contraseña incorrectos.");
        }
    }

    private void irAPantallaPrincipal(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ListaUsuarios.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cambiar de pantalla: " + e.getMessage());
        }
    }

    public void onIrALoginClick(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                System.err.println("Error al cambiar de pantalla: " + e.getMessage());
            }
    }
}


