package org.example.slipclass_demo_1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.slipclass_demo_1.configuration.SQLDataAccess;

import java.io.IOException;
import java.sql.Connection;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
<<<<<<< HEAD
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menuPrincipal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
=======
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Grupos_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
>>>>>>> d8eb80be925a3e2b5c6f68d4cf58ade44b2117d8
        stage.setTitle("SplitClass");
        stage.setScene(scene);
        stage.show();
    }
}