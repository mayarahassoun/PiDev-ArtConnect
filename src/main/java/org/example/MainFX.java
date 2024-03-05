package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/dashbord.fxml"));
        Scene scene =new Scene(fxmlLoader.load());
                                                           //EventMangement
                                                             //AfficherEvent

        // scene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        primaryStage.setTitle("HELLO");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}
