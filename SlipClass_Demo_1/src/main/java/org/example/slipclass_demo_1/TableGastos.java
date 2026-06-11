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
import org.example.slipclass_demo_1.model.SQLModelGasto;
import org.example.slipclass_demo_1.model.categoria;
import org.example.slipclass_demo_1.model.gasto;
import org.example.slipclass_demo_1.model.grupo;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador encargado de la gestión de la tabla de gastos.
 * Permite visualizar, eliminar, editar y navegar hacia la creación de nuevos gastos
 * dentro del sistema SplitClass.
 * * @author TuNombre
 * @version 1.0
 */
public class TableGastos implements Initializable {

    @FXML private ComboBox<categoria> comboCategoria;
    @FXML private TableView<gasto> tablaGastos;
    @FXML private TableColumn<gasto, LocalDate> colFecha;
    @FXML private TableColumn<gasto, String> colConcepto;
    @FXML private TableColumn<gasto, Double> colMonto;

    @FXML private TableView<grupo> listado_grupos_view;
    @FXML private TableColumn<grupo, String> columnId;
    @FXML private TableColumn<grupo, String> columnNombre;
    @FXML private TableColumn<grupo, String> columnNºUsuarios;
    @FXML private TableColumn<grupo, Double> columnMoneda;
    @FXML private TableColumn<grupo, LocalDate> columnCreacion;
    @FXML private TableColumn<grupo, LocalDate> columnEliminacion;

    /**
     * Inicializa el controlador configurando las columnas de la tabla y cargando
     * los gastos asociados al usuario que ha iniciado sesión.
     * * @param location La ubicación relativa del archivo FXML.
     * @param resources Los recursos específicos de la localización.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colConcepto.setCellValueFactory(new PropertyValueFactory<>("concepto"));
        colMonto.setCellValueFactory(new PropertyValueFactory<>("monto_total"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        org.example.slipclass_demo_1.model.usuario u = org.example.slipclass_demo_1.SessionManager.getCurrentUser();

        if (u != null) {
            List<gasto> gastosFiltrados = SQLModelGasto.getGastosPorUsuario(u.getId_usuario());
            tablaGastos.setItems(FXCollections.observableArrayList(gastosFiltrados));
        } else {
            System.out.println("No hay ningún usuario activo en sesión.");
        }
    }

    /**
     * Carga todas las categorías disponibles desde la base de datos en el ComboBox.
     */
    private void cargarCategorias(){
        List<categoria> lista = SQLModelGasto.getAllCategorias();
        ObservableList<categoria> observableLista = FXCollections.observableArrayList(lista);
        comboCategoria.setItems(observableLista);
    }

    public void onCancelarGasto(ActionEvent actionEvent) {}

    public void onConfirmarGasto(ActionEvent actionEvent) {
        categoria seleccionada = comboCategoria.getValue();
        if (seleccionada != null) {
            System.out.println("Categoría seleccionada: " + seleccionada.getNombre());
        }
    }

    /**
     * Navega a la vista de registro de un nuevo gasto.
     * @param actionEvent El evento de acción que dispara la navegación.
     */
    public void onNuevoGastoAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterGasto.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina el gasto seleccionado de la tabla y de la base de datos tras confirmación.
     * @param actionEvent El evento de acción.
     */
    public void onEliminarGastoAction(ActionEvent actionEvent) {
        gasto seleccionado = tablaGastos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Aviso", "Selecciona un gasto de la tabla primero.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar borrado");
        confirm.setContentText("¿Seguro que quieres borrar el gasto '" + seleccionado.getConcepto() + "'?");

        confirm.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                if (SQLModelGasto.deleteGasto(seleccionado.getId_gasto())) {
                    mostrarAlerta("Éxito", "Gasto eliminado correctamente.");
                    tablaGastos.getItems().remove(seleccionado);
                } else {
                    mostrarAlerta("Error", "No se pudo eliminar el gasto.");
                }
            }
        });
    }

    /**
     * Abre el formulario de edición cargando los datos del gasto seleccionado.
     * @param actionEvent El evento de acción.
     */
    public void onModificarGastoAction(ActionEvent actionEvent) {
        gasto seleccionado = tablaGastos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterGasto.fxml"));
            Parent root = loader.load();
            controllerProvisional formularioController = loader.getController();
            formularioController.cargarGastoParaEditar(seleccionado);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Navega de regreso al menú principal.
     * @param event El evento de acción.
     */
    public void onVolverAMenuGasto(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MenuPrincipal.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra un mensaje de alerta informativo al usuario.
     * @param titulo Título de la ventana.
     * @param msj Mensaje a mostrar.
     */
    private void mostrarAlerta(String titulo, String msj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msj);
        alert.show();
    }
}