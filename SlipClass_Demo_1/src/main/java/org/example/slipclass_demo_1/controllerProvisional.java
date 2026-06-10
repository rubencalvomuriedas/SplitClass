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
import org.example.slipclass_demo_1.model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class controllerProvisional implements Initializable {

    private gasto newGasto;
    private boolean isNewGasto = true;
    private ObservableList<gasto> gastosObservableList = FXCollections.observableArrayList();

    @FXML private ComboBox<categoria> comboCategoria;
    @FXML private ComboBox<grupo> comboGrupo;
    @FXML private ComboBox<usuario> comboUsuario;

    @FXML private TextField txtGastoConcepto, txtGastoMonto;
    @FXML private DatePicker dpGastoFecha;

    private int idGastoEnEdicion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            cargarCategorias();
            cargarGrupos(); // Primero cargamos los grupos disponibles

            // Agregamos el Listener al ComboBox de Grupos
            comboGrupo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, nuevoGrupo) -> {
                if (nuevoGrupo != null) {
                    // 1. Buscamos los usuarios que pertenecen al grupo seleccionado
                    List<usuario> miembros = SQLModelUsuario.getUsuariosPorGrupo(nuevoGrupo.getId_grupo());
                    comboUsuario.setItems(FXCollections.observableArrayList(miembros));

                    // 2. Intentamos preseleccionar por defecto al usuario logueado si es miembro
                    usuario usuarioLogueado = SessionManager.getCurrentUser();
                    if (usuarioLogueado != null) {
                        for (usuario usr : miembros) {
                            if (usr.getId_usuario() == usuarioLogueado.getId_usuario()) {
                                comboUsuario.setValue(usr);
                                break;
                            }
                        }
                    }
                } else {
                    // Si deseleccionan el grupo, limpiamos el combo de usuarios
                    comboUsuario.setItems(FXCollections.observableArrayList());
                }
            });

        } catch (Exception e) {
            System.err.println("¡ERROR crítico en el inicio del controlador!");
            e.printStackTrace();
        }

        // Configuración estética de las celdas del comboUsuario (Mantenemos el formato de nombres)
        comboUsuario.setCellFactory(lv -> new ListCell<usuario>() {
            @Override
            protected void updateItem(usuario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre());
            }
        });
        comboUsuario.setButtonCell(new ListCell<usuario>() {
            @Override
            protected void updateItem(usuario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre());
            }
        });

        System.out.println("CONTROLLER PROVISIONAL CONFIGURADO");
    }

    private void cargarCategorias() {
        List<categoria> lista = SQLModelGasto.getAllCategorias();
        ObservableList<categoria> observableLista = FXCollections.observableArrayList(lista);
        comboCategoria.setItems(observableLista);

        comboCategoria.setCellFactory(lv -> new ListCell<categoria>() {
            @Override
            protected void updateItem(categoria item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre());
            }
        });
        comboCategoria.setButtonCell(new ListCell<categoria>() {
            @Override
            protected void updateItem(categoria item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre());
            }
        });
    }

    private void cargarUsuarios() {
        List<usuario> lista = SQLModelUsuario.getAllUsuarios();
        ObservableList<usuario> observableList = FXCollections.observableArrayList(lista);
        comboUsuario.setItems(observableList);

        comboUsuario.setCellFactory(lv -> new ListCell<usuario>() {
            @Override
            protected void updateItem(usuario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre());
            }
        });
        comboUsuario.setButtonCell(new ListCell<usuario>() {
            @Override
            protected void updateItem(usuario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre());
            }
        });
    }

    private void cargarGrupos() {
        usuario usuarioLogueado = SessionManager.getCurrentUser();

        if (usuarioLogueado != null) {
            List<grupo> lista = SQLModelGrupo.getGruposPorUsuario(usuarioLogueado.getId_usuario());

            ObservableList<grupo> observableLista = FXCollections.observableArrayList(lista);
            comboGrupo.setItems(observableLista);
        } else {
            System.err.println("Advertencia: No hay ningún usuario logueado en SessionManager.");
            comboGrupo.setItems(FXCollections.observableArrayList());
        }

        // Mantienes tus celdas personalizadas intactas para mostrar el título
        comboGrupo.setCellFactory(lv -> new ListCell<grupo>() {
            @Override
            protected void updateItem(grupo item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getTitulo());
            }
        });
        comboGrupo.setButtonCell(new ListCell<grupo>() {
            @Override
            protected void updateItem(grupo item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getTitulo());
            }
        });
    }

    public void onCancelarGasto(ActionEvent actionEvent) {
        limpiarCampos();
    }

    public void onConfirmarGasto(ActionEvent actionEvent) {
        if (txtGastoConcepto.getText().isEmpty() || txtGastoMonto.getText().isEmpty() || dpGastoFecha.getValue() == null) {
            mostrarAlerta("Campos incompletos", "Por favor, complete todos los campos.");
            return;
        }

        categoria catSeleccionada = comboCategoria.getValue();
        grupo grupoSeleccionado = comboGrupo.getValue();
        usuario usuarioSeleccionado = comboUsuario.getValue();

        if (catSeleccionada == null || grupoSeleccionado == null || usuarioSeleccionado == null) {
            mostrarAlerta("Error", "Debes seleccionar una categoría, un grupo y un pagador.");
            return;
        }

        int idCat = catSeleccionada.getId_categoria();
        int idGrp = grupoSeleccionado.getId_grupo();
        int idUsr = usuarioSeleccionado.getId_usuario();

        if (isNewGasto) {
            this.newGasto = new gasto(
                    txtGastoConcepto.getText(),
                    Double.parseDouble(txtGastoMonto.getText()),
                    dpGastoFecha.getValue(),
                    idCat,
                    idGrp,
                    idUsr
            );

            if (SQLModelGasto.createGasto(this.newGasto)) {
                mostrarAlerta("Éxito", "Gasto creado correctamente.");
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo crear el gasto.");
            }
        } else {

            gasto gastoEditado = new gasto(
                    this.idGastoEnEdicion,
                    "",
                    txtGastoConcepto.getText(),
                    Double.parseDouble(txtGastoMonto.getText()),
                    dpGastoFecha.getValue(),
                    idGrp,
                    idCat,
                    idUsr
            );

            if (SQLModelGasto.updateGasto(gastoEditado)) {
                mostrarAlerta("Éxito", "Gasto actualizado correctamente.");
                isNewGasto = true;
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo actualizar el gasto.");
            }
        }
    }

    private void mostrarAlerta(String titulo, String msj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msj);
        alert.show();
    }

    private void limpiarCampos() {
        txtGastoConcepto.clear();
        txtGastoMonto.clear();
        dpGastoFecha.setValue(null);
        comboCategoria.getSelectionModel().clearSelection();
        comboGrupo.getSelectionModel().clearSelection();
        comboUsuario.getSelectionModel().clearSelection();
    }

    public void cargarGastoParaEditar(gasto g) {
        this.isNewGasto = false;
        this.idGastoEnEdicion = g.getId_gasto();

        txtGastoConcepto.setText(g.getConcepto());
        txtGastoMonto.setText(String.valueOf(g.getMonto_total()));
        dpGastoFecha.setValue(g.getFecha());

        for (categoria cat : comboCategoria.getItems()) {
            if (cat.getId_categoria() == g.getId_categoria()) {
                comboCategoria.setValue(cat);
                break;
            }
        }
        for (grupo grp : comboGrupo.getItems()) {
            if (grp.getId_grupo() == g.getId_grupo()) {
                comboGrupo.setValue(grp);
                break;
            }
        }
        for (usuario usr : comboUsuario.getItems()) {
            if (usr.getId_usuario() == g.getId_usuarioPagador()) {
                comboUsuario.setValue(usr);
                break;
            }
        }
    }


    public void onVolverRegisterAction(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("ListaGastos.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}