package org.example.slipclass_demo_1;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class HelloController {

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
        // El mensaje que me has pedido
        alerta.setContentText("Próximamente...");
        alerta.show();
    }
}