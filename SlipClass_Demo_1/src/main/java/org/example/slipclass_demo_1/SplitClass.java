package org.example.slipclass_demo_1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación SplitClass.
 * Esta clase se encarga de iniciar el ciclo de vida de la aplicación JavaFX,
 * cargando la interfaz inicial desde el archivo FXML correspondiente.
 * * @author TuNombre
 * @version 1.0
 */
public class SplitClass extends Application {

    /**
     * Punto de entrada principal para el inicio de la interfaz gráfica.
     * Carga el archivo "Inicio.fxml", configura el título de la ventana
     * y muestra el escenario (Stage) principal.
     *
     * @param stage El escenario principal de la aplicación donde se mostrará la escena.
     * @throws IOException Si el archivo FXML no puede ser cargado o encontrado.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SplitClass.class.getResource("Inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("SplitClass");
        stage.setScene(scene);
        stage.show();
    }
}