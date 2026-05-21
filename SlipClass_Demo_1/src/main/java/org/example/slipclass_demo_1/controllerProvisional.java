package org.example.slipclass_demo_1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.slipclass_demo_1.model.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class controllerProvisional implements Initializable {

    private gasto newGasto;
    private boolean isNewGasto = true;
    private ObservableList <gasto> gastosObservableList = FXCollections.observableArrayList();

    @FXML private ComboBox<categoria> comboCategoria;

    @FXML private ComboBox<grupo> comboGrupo;

    @FXML private ComboBox<usuario> comboUsuario;

    @FXML private TextField txtGastoConcepto, txtGastoMonto;

    @FXML private DatePicker dpGastoFecha;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cargarCategorias();

    }

    private void cargarCategorias(){
        List<categoria> lista = SQLModelGasto.getAllCategorias();

        ObservableList<categoria> observableLista = FXCollections.observableArrayList(lista);

        comboCategoria.setItems(observableLista);
    }

    public void onCancelarGasto(ActionEvent actionEvent) {
    }

    public void onConfirmarGasto(ActionEvent actionEvent) {

        if (txtGastoConcepto.getText().isEmpty() || txtGastoMonto.getText().isEmpty() || dpGastoFecha.getValue() == null) {
            System.out.println("Por favor, complete todos los campos antes de confirmar el gasto.");
            return;
        }

        categoria seleccionada = comboCategoria.getValue();

        if (seleccionada != null) {
            System.out.println("Categoría seleccionada: " + seleccionada.getNombre());
            System.out.println("ID a enviar a la FK de Gastos: " + seleccionada.getId_categoria());
        } else {
            System.out.println("No se ha seleccionado ninguna categoría.");
        }

        if (isNewGasto) {
            this.newGasto = new gasto(
                    txtGastoConcepto.getText(),
                    Double.parseDouble(txtGastoMonto.getText()),
                    dpGastoFecha.getValue(),
                    seleccionada != null ? seleccionada.getId_categoria() : 0
            );

            if (SQLModelGasto.createGasto(this.newGasto)) {
                mostrarAlerta("Éxito", "Gasto creado correctamente.");
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo crear el gasto. Por favor, inténtelo de nuevo.");
            }

            System.out.println("Gasto creado.");
        }

    }
    private void mostrarAlerta(String titulo, String msj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(msj);
        alert.show();
    }
    private void limpiarCampos() {
        txtGastoConcepto.clear();
        txtGastoMonto.clear();
        dpGastoFecha.setValue(null);
        comboCategoria.getSelectionModel().clearSelection();
    }


}
