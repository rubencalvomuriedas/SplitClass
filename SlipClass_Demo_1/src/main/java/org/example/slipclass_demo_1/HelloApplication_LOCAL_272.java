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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1620, 880);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        SQLDataAccess dataAccess = new SQLDataAccess();
        Connection conn = SQLDataAccess.getConnection();
        if (conn != null) {
            System.out.println("Conexión exitosa a la base de datos");
            launch(args);
        } else {
            System.out.println("Error al conectar a la base de datos");
        }


    }


}