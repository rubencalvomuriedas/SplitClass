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

/**
 * Controlador provisional para la gestión de formularios de gastos.
 * Permite tanto la creación de nuevos gastos como la edición de los existentes,
 * vinculando categorías, grupos y usuarios pagadores.
 *
 * @author TuNombre
 * @version 1.0
 */
public class controllerProvisional implements Initializable {

    private gasto newGasto;
    private boolean isNewGasto = true;
    private int idGastoEnEdicion;

    @FXML private ComboBox<categoria> comboCategoria;
    @FXML private ComboBox<grupo> comboGrupo;
    @FXML private ComboBox<usuario> comboUsuario;
    @FXML private TextField txtGastoConcepto, txtGastoMonto;
    @FXML private DatePicker dpGastoFecha;

    /**
     * Inicializa los componentes, carga los datos necesarios en los ComboBoxes
     * y configura los listeners para la selección dinámica de miembros por grupo.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            cargarCategorias();
            cargarGrupos();

            // Listener para actualizar usuarios disponibles según el grupo seleccionado
            comboGrupo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, nuevoGrupo) -> {
                if (nuevoGrupo != null) {
                    List<usuario> miembros = SQLModelUsuario.getUsuariosPorGrupo(nuevoGrupo.getId_grupo());
                    comboUsuario.setItems(FXCollections.observableArrayList(miembros));

                    // Auto-selecciona al usuario logueado si es parte del grupo
                    usuario usuarioLogueado = SessionManager.getCurrentUser();
                    if (usuarioLogueado != null) {
                        miembros.stream()
                                .filter(u -> u.getId_usuario() == usuarioLogueado.getId_usuario())
                                .findFirst()
                                .ifPresent(comboUsuario::setValue);
                    }
                } else {
                    comboUsuario.setItems(FXCollections.observableArrayList());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Configuración de visualización para ComboBoxes
        configurarRenderizadoComboBoxes();
    }

    private void configurarRenderizadoComboBoxes() {
        // Lógica de renderizado para usuarios y categorías...
    }

    private void cargarCategorias() {
        comboCategoria.setItems(FXCollections.observableArrayList(SQLModelGasto.getAllCategorias()));
    }

    private void cargarGrupos() {
        usuario usuarioLogueado = SessionManager.getCurrentUser();
        if (usuarioLogueado != null) {
            comboGrupo.setItems(FXCollections.observableArrayList(SQLModelGrupo.getGruposPorUsuario(usuarioLogueado.getId_usuario())));
        }
    }

    /**
     * Procesa la confirmación del formulario, creando o actualizando un gasto.
     */
    public void onConfirmarGasto(ActionEvent actionEvent) {
        if (txtGastoConcepto.getText().isEmpty() || txtGastoMonto.getText().isEmpty() || dpGastoFecha.getValue() == null) {
            mostrarAlerta("Campos incompletos", "Por favor, complete todos los campos.");
            return;
        }

        int idCat = comboCategoria.getValue().getId_categoria();
        int idGrp = comboGrupo.getValue().getId_grupo();
        int idUsr = comboUsuario.getValue().getId_usuario();

        if (isNewGasto) {
            this.newGasto = new gasto(txtGastoConcepto.getText(), Double.parseDouble(txtGastoMonto.getText()),
                    dpGastoFecha.getValue(), idCat, idGrp, idUsr);
            if (SQLModelGasto.createGasto(this.newGasto)) {
                mostrarAlerta("Éxito", "Gasto creado correctamente.");
                limpiarCampos();
            }
        } else {
            gasto gastoEditado = new gasto(this.idGastoEnEdicion, "", txtGastoConcepto.getText(),
                    Double.parseDouble(txtGastoMonto.getText()), dpGastoFecha.getValue(),
                    idGrp, idCat, idUsr);
            if (SQLModelGasto.updateGasto(gastoEditado)) {
                mostrarAlerta("Éxito", "Gasto actualizado.");
                isNewGasto = true;
                limpiarCampos();
            }
        }
    }

    /**
     * Prepara el formulario para editar un gasto existente.
     * @param g El gasto a editar.
     */
    public void cargarGastoParaEditar(gasto g) {
        this.isNewGasto = false;
        this.idGastoEnEdicion = g.getId_gasto();
        txtGastoConcepto.setText(g.getConcepto());
        txtGastoMonto.setText(String.valueOf(g.getMonto_total()));
        dpGastoFecha.setValue(g.getFecha());
        // Lógica de selección de valores en combos...
    }

    private void limpiarCampos() {
        txtGastoConcepto.clear();
        txtGastoMonto.clear();
        dpGastoFecha.setValue(null);
        comboCategoria.getSelectionModel().clearSelection();
        comboGrupo.getSelectionModel().clearSelection();
        comboUsuario.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String msj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(msj);
        alert.show();
    }

    public void onVolverRegisterAction(ActionEvent event) {
        // Lógica de navegación...
    }
}