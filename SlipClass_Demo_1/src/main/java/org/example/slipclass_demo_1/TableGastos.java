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

public class TableGastos implements Initializable {

//    @FXML private TextField txtGastoConcepto, txtGastoMonto, dpGastoFecha;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colConcepto.setCellValueFactory(new PropertyValueFactory<>("concepto"));
        colMonto.setCellValueFactory(new PropertyValueFactory<>("monto_total"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        // Recuperamos el usuario logueado de la sesión
        org.example.slipclass_demo_1.model.usuario u = org.example.slipclass_demo_1.SessionManager.getCurrentUser();

        if (u != null) {
            // Cargamos únicamente sus gastos relacionados
            List<gasto> gastosFiltrados = SQLModelGasto.getGastosPorUsuario(u.getId_usuario());
            tablaGastos.setItems(FXCollections.observableArrayList(gastosFiltrados));
            System.out.println("Gastos cargados para el usuario actual: " + gastosFiltrados.size());
        } else {
            System.out.println("No hay ningún usuario activo en sesión.");
        }
    }




    private void cargarCategorias(){
        List<categoria> lista = SQLModelGasto.getAllCategorias();

        ObservableList<categoria> observableLista = FXCollections.observableArrayList(lista);

        comboCategoria.setItems(observableLista);
    }

    public void onCancelarGasto(ActionEvent actionEvent) {
    }

    public void onConfirmarGasto(ActionEvent actionEvent) {

        categoria seleccionada = comboCategoria.getValue();

        if (seleccionada != null) {
            System.out.println("Categoría seleccionada: " + seleccionada.getNombre());
            System.out.println("ID a enviar a la FK de Gastos: " + seleccionada.getId_categoria());
        } else {
            System.out.println("No se ha seleccionado ninguna categoría.");
        }

    }


    public void onNuevoGastoAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registerGasto.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Error al cargar la vista registerGasto.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void onEliminarGastoAction(ActionEvent actionEvent) {

        gasto seleccionado = tablaGastos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Aviso", "Selecciona un gasto de la tabla primero.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar borrado");
        confirm.setHeaderText(null);
        confirm.setContentText(
                "¿Seguro que quieres borrar el gasto '" +
                        seleccionado.getConcepto() + "'?"
        );

        confirm.showAndWait().ifPresent(respuesta -> {

            if (respuesta == ButtonType.OK) {

                if (SQLModelGasto.deleteGasto(seleccionado.getId_gasto())) {

                    mostrarAlerta("Éxito", "Gasto eliminado correctamente.");

                    // Opción 1: eliminar directamente de la tabla
                    tablaGastos.getItems().remove(seleccionado);

                    // Opción 2 (más recomendable):
                    // cargarTabla();

                } else {
                    mostrarAlerta("Error", "No se pudo eliminar el gasto.");
                }
            }
        });
    }

    public void onModificarGastoAction(ActionEvent actionEvent) {
        gasto seleccionado = tablaGastos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            System.out.println("Por favor, selecciona un gasto de la tabla para editar.");
            return;
        }

        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("registerGasto.fxml"));
            javafx.scene.Parent root = loader.load();

            controllerProvisional formularioController = loader.getController();

            formularioController.cargarGastoParaEditar(seleccionado);

            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.err.println("Error al cambiar a la pantalla de edición:");
            e.printStackTrace();
        }
    }

    public void onVolverAMenuGasto(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("menuPrincipal.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void mostrarAlerta(String titulo, String msj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msj);
        alert.show();
    }
}
