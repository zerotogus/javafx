package com.itgroup.application;

import com.itgroup.utility.Utility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ButtonEvent extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = Utility.FXMl_PATH + "ButtonEvent.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));


        Parent container = fxmlLoader.load(); // 승급
        Scene scene = new Scene(container);
        stage.setTitle("버튼 이벤트 프로그래밍");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
      launch(args);
    }
}
