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

  

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Grupos_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);

=======

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menuPrincipal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
>>>>>>> adef906beecdae2b9ec148f587a468269785d1cf
        stage.setTitle("SplitClass");
        stage.setScene(scene);
        stage.show();
    }
}