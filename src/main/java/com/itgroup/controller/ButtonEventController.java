package com.itgroup.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ButtonEventController implements Initializable {
    @FXML private TextArea textArea;
    @FXML private Button btnOk;
    @FXML private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnOKAction (ActionEvent event){
        String value = btnOk.getText();
//        textArea.setText(value); // 기존내용 덮어쓰기
        textArea.appendText(value + "\n"); // 기존 내용에 추가하기
    }

    public void btnCancelAction (ActionEvent event){
        String value = btnCancel.getText();
        textArea.appendText(value + "\n"); // 기존 내용에 추가하기

    }
}
