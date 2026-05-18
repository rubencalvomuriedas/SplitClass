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
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HelloController implements Initializable {


    @FXML private TextField txtUsuario, txtEmail, txtTelefono;
    @FXML private PasswordField txtPass, txtPassConfirm;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private TextField loginUsuario;
    @FXML private PasswordField loginPass;
    @FXML private TextField txtGrupoTitulo;
    @FXML private TextArea txtGrupoDescripcion;
    @FXML private TextField txtGrupoMoneda;
    @FXML private TextField txtGastoConcepto;
    @FXML private TextField txtGastoMonto;
    @FXML private DatePicker dpGastoFecha;
    @FXML private TextField txtGastoCategoria;
    @FXML private TextField txtLiqMonto;
    @FXML private TextField txtLiqEmisor;
    @FXML private TextField txtLiqReceptor;
    @FXML private TextField txtLiqConcepto;
    @FXML private DatePicker dpLiqFecha;


    private int idUsuarioLogueado = 1;
    private int idGrupoActivo = 1;


    @FXML
    public void onConfirmarGrupo(ActionEvent event) {
        if (txtGrupoTitulo.getText().isEmpty()) {
            mostrarAlerta("Campos vacíos", "El título del grupo es obligatorio.", Alert.AlertType.WARNING);
            return;
        }

        String sqlGrupo = "INSERT INTO GRUPO (Titulo, Descripcion, Moneda) VALUES (?, ?, ?)";
        String sqlMiembro = "INSERT INTO MIEMBROS_GRUPO (Id_Usuario, Id_Grupo) VALUES (?, LAST_INSERT_ID())";

        try (Connection conn = SQLDataAccess.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psG = conn.prepareStatement(sqlGrupo);
                 PreparedStatement psM = conn.prepareStatement(sqlMiembro)) {

                psG.setString(1, txtGrupoTitulo.getText());
                psG.setString(2, txtGrupoDescripcion.getText());
                psG.setString(3, txtGrupoMoneda.getText().isEmpty() ? "EUR" : txtGrupoMoneda.getText());
                psG.executeUpdate();

                psM.setInt(1, idUsuarioLogueado);
                psM.executeUpdate();

                conn.commit();
                mostrarAlerta("Éxito", "Grupo creado de forma correcta.", Alert.AlertType.INFORMATION);
                onCancelarGrupo();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        } catch (SQLException ex) {
            mostrarAlerta("Error SQL", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onCancelarGrupo() {
        txtGrupoTitulo.clear();
        txtGrupoDescripcion.clear();
        txtGrupoMoneda.clear();
    }

    @FXML
    public void onConfirmarGasto(ActionEvent event) {
        if (txtGastoConcepto.getText().isEmpty() || txtGastoMonto.getText().isEmpty()) {
            mostrarAlerta("Campos vacíos", "Concepto y Monto son requeridos.", Alert.AlertType.WARNING);
            return;
        }

        String sqlGasto = "INSERT INTO GASTO (Concepto, Monto_total, Fecha, Id_Grupo, Id_Categoria, Id_Usuario_Pagador) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = SQLDataAccess.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlGasto)) {

            ps.setString(1, txtGastoConcepto.getText());
            ps.setDouble(2, Double.parseDouble(txtGastoMonto.getText()));
            ps.setDate(3, dpGastoFecha.getValue() != null ? Date.valueOf(dpGastoFecha.getValue()) : Date.valueOf(LocalDate.now()));
            ps.setInt(4, idGrupoActivo);

            if (txtGastoCategoria.getText().isEmpty()) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, Integer.parseInt(txtGastoCategoria.getText()));
            }

            ps.setInt(6, idUsuarioLogueado);
            ps.executeUpdate();

            mostrarAlerta("Gasto Registrado", "El gasto se guardó y calculó con éxito.", Alert.AlertType.INFORMATION);
            onCancelarGasto();
        } catch (Exception ex) {
            mostrarAlerta("Error", "Error al guardar el gasto: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onCancelarGasto() {
        txtGastoConcepto.clear();
        txtGastoMonto.clear();
        dpGastoFecha.setValue(null);
        txtGastoCategoria.clear();
    }

    @FXML
    public void onConfirmarLiquidacion(ActionEvent event) {
        if (txtLiqMonto.getText().isEmpty() || txtLiqEmisor.getText().isEmpty() || txtLiqReceptor.getText().isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor completa los importes y usuarios involucrados.", Alert.AlertType.WARNING);
            return;
        }

        String sql = "INSERT INTO LIQUIDACIÓN (Monto, Fecha, Concepto, Id_Emisor, Id_Receptor, Id_Grupo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = SQLDataAccess.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, Double.parseDouble(txtLiqMonto.getText()));
            ps.setDate(2, dpLiqFecha.getValue() != null ? Date.valueOf(dpLiqFecha.getValue()) : Date.valueOf(LocalDate.now()));
            ps.setString(3, txtLiqConcepto.getText().isEmpty() ? "Liquidación" : txtLiqConcepto.getText());
            ps.setInt(4, Integer.parseInt(txtLiqEmisor.getText()));
            ps.setInt(5, Integer.parseInt(txtLiqReceptor.getText()));
            ps.setInt(6, idGrupoActivo);

            ps.executeUpdate();
            mostrarAlerta("Éxito", "Liquidación registrada.", Alert.AlertType.INFORMATION);
            onCancelarLiquidacion();
        } catch (Exception ex) {
            mostrarAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onCancelarLiquidacion() {
        txtLiqMonto.clear();
        txtLiqEmisor.clear();
        txtLiqReceptor.clear();
        txtLiqConcepto.clear();
        dpLiqFecha.setValue(null);
    }


    @FXML public void clickDescarga() { mostrarAlerta("Próximamente", "¡La descarga estará disponible próximamente!", Alert.AlertType.INFORMATION); }
    @FXML public void clickSobreNosotros() { mostrarAlerta("Creadores", "Diego Gómez\nCamilo Arone\nRubén Calvo", Alert.AlertType.INFORMATION); }
    @FXML public void clickSobreBuscar() {}
    @FXML public void clickSobreListar() {}
    @FXML public void clickSobreInsertar() {}
    @FXML public void clickSobreResgistrar() {}
    @FXML public void clickSobreMostrar() {}
    @FXML public void clickSobreEliminar() {}

    @FXML
    private void onRegistrarseClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registro.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
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

    @FXML
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
            mostrarAlerta("Error", "No hay archivo de usuarios.", Alert.AlertType.WARNING);
            return;
        }

        if (encontrado) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("interfaz_Cliente.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
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
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarEnArchivo(String user, String mail, String tel, String password) {
        try (FileWriter fw = new FileWriter("usuarios.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(user + "," + mail + "," + tel + "," + password);
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo");
        }
    }

    @FXML private void onClickButtonCasa(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("interfaz_Cliente.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void onSolicitudesClick(ActionEvent event) {}
    @FXML public void onInsertarButtonClick(ActionEvent actionEvent) {}
    @FXML public void onListadoButtonClick(ActionEvent actionEvent) {}
    @FXML public void onEliminarButtonClick(ActionEvent actionEvent) {}
    @FXML public void onBuscarButtonClick(ActionEvent actionEvent) {}
    @FXML public void onRegistroButtonClick(ActionEvent actionEvent) {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}