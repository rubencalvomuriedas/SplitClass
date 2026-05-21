package org.example.slipclass_demo_1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.slipclass_demo_1.model.SQLModelGasto;
import org.example.slipclass_demo_1.model.categoria;
import org.example.slipclass_demo_1.model.gasto;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class newController implements Initializable {

//    @FXML private TextField txtGastoConcepto, txtGastoMonto, dpGastoFecha;

    @FXML private ComboBox<categoria> comboCategoria;
    @FXML private TableView<gasto> tablaGastos;
    @FXML private TableColumn<gasto, LocalDate> colFecha;
    @FXML private TableColumn<gasto, String> colConcepto;
    @FXML private TableColumn<gasto, Double> colMonto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colConcepto.setCellValueFactory(new PropertyValueFactory<>("concepto"));
        colMonto.setCellValueFactory(new PropertyValueFactory<>("monto_total"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        tablaGastos.setItems(FXCollections.observableArrayList(SQLModelGasto.getAllGastos()));

//        cargarCategorias();
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


}
