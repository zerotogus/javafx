package com.itgroup.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelloJavaFx extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        VBox container = new VBox();
        container.setPrefWidth(350);
        container.setPrefHeight(150);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);

        Label label = new Label();
        label.setText("Hello! Java FX");
        label.setFont(new Font(50));

        Button button = new Button();
        button.setText("확인");

// 버튼에 람다식을 이용한 이벤트 처리
        button.setOnAction((event)->{
            // event 객체 : 이벤트를 발생시킨 객체
            System.out.println(event.toString());
            String text = label.getText();
            System.out.println(text + "호호호");
            Platform.exit(); // 어플리케이션 종료
        });

        container.getChildren().add(label);
        container.getChildren().add(button);

        Scene scene = new Scene(container); //승급
        stage.setScene(scene);
        stage.setTitle("첫번째 어플리케이션");
        stage.show();
    }

    public static void main(String[] args) {
        // launch 메소드 호출시 자동으로 start 메소드를 실행시켜줍니다.
        launch(args);
    }
}
