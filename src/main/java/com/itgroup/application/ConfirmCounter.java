package com.itgroup.application;

import com.itgroup.utility.Utility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ConfirmCounter extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = Utility.FXMl_PATH + "ConfirmCounter.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));


        Parent container = fxmlLoader.load(); // 승급
        Scene scene = new Scene(container);
        stage.setTitle("악플 달지 맙시다.");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
      launch(args);
    }
}
