package org.example.slipclass_demo_1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.slipclass_demo_1.configuration.SQLDataAccess;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private TextField txtUsuario, txtEmail, txtTelefono;
    @FXML
    private PasswordField txtPass, txtPassConfirm;
    @FXML
    private DatePicker dpFechaNacimiento;
    @FXML
    private TextField loginUsuario;
    @FXML
    private PasswordField loginPass;

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
        alerta.setContentText("Bienvenido a SplitClass, somos una aplicacion que gestionara tus gastos y el de tus amigos.\nEstos son nuestros creadores:\nDiego Gomez\nCamilo Arone\nRubén Calvo");
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

    @FXML
    private void onRegistrarseClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registro.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Error: No se pudo encontrar el archivo registro.fxml");
            e.printStackTrace();
        }
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

    @FXML
    protected void onFinalizarRegistroClick(ActionEvent event) {
        String usuario = txtUsuario.getText();
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();
        String pass = txtPass.getText();
        String passConfirm = txtPassConfirm.getText();
        LocalDate fecha = dpFechaNacimiento.getValue();

        String error = "";

        if (usuario.isEmpty() || email.isEmpty() || telefono.isEmpty() || pass.isEmpty() || fecha == null) {
            error = "Por favor, rellena todos los campos.";
        } else if (!pass.equals(passConfirm)) {
            error = "Las contraseñas no coinciden.";
        } else if (telefono.length() != 9) {
            error = "El teléfono debe tener 9 números.";
        }

        if (!error.isEmpty()) {
            mostrarAlerta("Error de Registro", error, Alert.AlertType.ERROR);
        } else {
            guardarEnArchivo(usuario, email, telefono, pass);

            mostrarAlerta("¡Bienvenido!", "Usuario " + usuario + " registrado con éxito.", Alert.AlertType.INFORMATION);

            onVolverClick(event);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


    @FXML
    protected void onIniciarSesionClick(ActionEvent event) {
        String userIn = loginUsuario.getText();
        String passIn = loginPass.getText();
        boolean encontrado = false;

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("usuarios.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos[0].equals(userIn) && datos[3].equals(passIn)) {
                    encontrado = true;
                    break;
                }
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No hay usuarios registrados todavía o el archivo no existe.", Alert.AlertType.WARNING);
            return;
        }

        if (encontrado) {
            mostrarAlerta("Éxito", "¡Bienvenido de nuevo, " + userIn + "!", Alert.AlertType.INFORMATION);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("interfaz_Cliente.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                System.err.println("Error: No se pudo cargar interfaz_Cliente.fxml");
                e.printStackTrace();
                mostrarAlerta("Error de Carga", "No se pudo abrir la pantalla principal del cliente.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Usuario o contraseña incorrectos.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onIrALoginClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Error: No se pudo encontrar el archivo login.fxml");
            e.printStackTrace();
        }
    }

    private void guardarEnArchivo(String user, String mail, String tel, String password) {
        try (FileWriter fw = new FileWriter("usuarios.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(user + "," + mail + "," + tel + "," + password);
            System.out.println("Usuario guardado en el TXT: " + user);

        } catch (IOException e) {
            System.err.println("Error al escribir en usuarios.txt: " + e.getMessage());
        }
    }

    @FXML
    private void onClickButtonCasa(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("interfaz-Cliente.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSolicitudesClick(ActionEvent event) {

    }

    public void onInsertarButtonClick(javafx.event.ActionEvent actionEvent) {
    }

    public void onListadoButtonClick(javafx.event.ActionEvent actionEvent) {
    }

    public void onEliminarButtonClick(javafx.event.ActionEvent actionEvent) {
    }

    public void onBuscarButtonClick(javafx.event.ActionEvent actionEvent) {
    }

    public void onRegistroButtonClick(javafx.event.ActionEvent actionEvent) {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Grupo> grupos = SQLDataAccess.getGrupos();

        TreeItem<String> root = new TreeItem<String>("");
        root.setExpanded(true);

        for(Grupo g : grupos) {
            TreeItem<String> gupoItem<>(g.getid_grupo());

            grupoItem.expandedProperty
        }
    }
}