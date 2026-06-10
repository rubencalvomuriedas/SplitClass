package org.example.slipclass_demo_1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.slipclass_demo_1.model.SQLModelGrupo;
import org.example.slipclass_demo_1.model.grupo;
import org.example.slipclass_demo_1.model.utils.Alertas;
import org.example.slipclass_demo_1.model.utils.Navigator;

import java.io.IOException;
import java.time.LocalDate;

public class RegistroGrupoController {

    @FXML private TextField txtGastoConcepto;
    @FXML private TextField txtGastoMonto;

    @FXML
    private void onGuardarGrupo(ActionEvent event) {

        grupo nuevoGrupo = new grupo(
                "",
                txtGastoConcepto.getText(),
                txtGastoMonto.getText(),
                "EUR",
                LocalDate.now(),
                null,
                0
        );

        if (SQLModelGrupo.insertarGrupo(nuevoGrupo)) {
            Alertas.showAlert("Éxito", "Grupo creado correctamente", null);
            txtGastoConcepto.clear();
            txtGastoMonto.clear();
        } else {
            Alertas.showAlert("Error", "No se pudo crear el grupo", null);
        }
    }

    @FXML
    private void onCancelar(ActionEvent event) {
        txtGastoConcepto.clear();
        txtGastoMonto.clear();
    }

    public void onVolverrrClick() {
        try {

            String fxml = "menuGrupo.fxml";

            String titulo = "Gestionar usuarios";

            Navigator.arbrirVentanaSecundaria(fxml, titulo, getClass());


        } catch (IOException e) {
            Alertas.showAlert("a", "a", Alert.AlertType.ERROR);
        }
    }

}