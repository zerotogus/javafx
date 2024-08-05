package com.itgroup.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class CaptionRotateController implements Initializable {
    @FXML private TextArea textArea ;
    @FXML private Button myButton01, myButton02, myButton03, topButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void btnAction(ActionEvent event) {
        Button btn = (Button)event.getSource();
        if(btn == myButton01 || btn == myButton02 || btn == myButton03) {
            textArea.appendText(btn.getText() + "\n");

        }else if(btn == topButton){
            textArea.setText(""); // 글자 지우기

            // 버튼들의 캡션 변경하기
            String temp = myButton01.getText();
            myButton01.setText(myButton02.getText());
            myButton02.setText(myButton03.getText());
            myButton03.setText(temp);

        }else{
            System.out.println("b");
        }
    }
}
