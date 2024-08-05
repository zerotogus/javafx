package com.itgroup.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloJavaFX02 extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/com/itgroup/fxml/" + "HelloJavaFX02.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));


        Parent container = fxmlLoader.load();
        Scene scene = new Scene(container);
        stage.setScene(scene);
        stage.setTitle("AppMain");
        stage.show();
    }

    public static void main(String[] args) {
      launch(args);
    }
}
