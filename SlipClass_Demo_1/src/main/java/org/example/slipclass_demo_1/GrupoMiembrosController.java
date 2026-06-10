package org.example.slipclass_demo_1;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.Alert;
import org.example.slipclass_demo_1.model.SQLModelGrupo;
import org.example.slipclass_demo_1.model.SQLModelUsuario;
import org.example.slipclass_demo_1.model.grupo;
import org.example.slipclass_demo_1.model.usuario;

import java.util.List;

public class GrupoMiembrosController {

    @FXML private ComboBox<grupo> comboGrupoSelect;
    @FXML private ComboBox<usuario> comboUsuarioSelect;

    @FXML
    private void initialize() {
        cargarGrupos();
        cargarUsuarios();
    }

    private void cargarGrupos() {
        List<grupo> lista = SQLModelGrupo.getAllGrupos();

        comboGrupoSelect.setItems(FXCollections.observableArrayList(lista));

        comboGrupoSelect.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(grupo item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getTitulo());
            }
        });
    }

    private void cargarUsuarios() {
        List<usuario> lista = SQLModelUsuario.getAllUsuarios();

        comboUsuarioSelect.setItems(FXCollections.observableArrayList(lista));

        comboUsuarioSelect.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(usuario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre());
            }
        });
    }

    @FXML
    private void onConfirmarGrupoMiembro() {

        grupo g = comboGrupoSelect.getValue();
        usuario u = comboUsuarioSelect.getValue();

        if (g == null || u == null) {
            mostrar("Error", "Selecciona grupo y usuario");
            return;
        }

        if (SQLModelGrupo.agregarMiembroAlGrupo(u.getId_usuario(), g.getId_grupo())) {
            mostrar("OK", "Usuario añadido");
        } else {
            mostrar("Error", "No se pudo añadir");
        }
    }

    private void mostrar(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setContentText(m);
        a.show();
    }
}