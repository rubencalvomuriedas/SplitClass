package org.example.slipclass_demo_1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.slipclass_demo_1.configuration.SQLDataAccess;
import org.example.slipclass_demo_1.model.utils.Alertas;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        if(SQLDataAccess.getConnection() == null){
            return;

        }else{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menuPrincipal.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 500);
            stage.setTitle("SplitClass");

            if(stage.isFocused()){
                stage.setAlwaysOnTop(true);
            }else{
                stage.setAlwaysOnTop(false);
            }

            stage.setScene(scene);
            stage.show();
        }

    }

    private boolean launchTest() {
        boolean invalid = false;
        try {
            Connection x = SQLDataAccess.getConnection();
            x.close();

        } catch (SQLException e) {
            Alertas.showAlert("getConnection (Syntax SQL)", String.valueOf(e), Alert.AlertType.WARNING);
            invalid = true;
        }
        if (invalid) {
            exitProgram();
            return true;
        }
        return false;
    }
    
    private void exitProgram() {
        Platform.exit();
    }

    public static void main(String[] args) {
        launch();
    }

}