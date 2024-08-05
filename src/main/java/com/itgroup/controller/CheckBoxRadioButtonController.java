package com.itgroup.controller;

import com.itgroup.utility.Utility;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CheckBoxRadioButtonController implements Initializable {
    @FXML private CheckBox changeImage01 ;
    @FXML private CheckBox changeImage02 ;
    @FXML private ImageView checkImageView;
    @FXML private ToggleGroup breadGroup ;
//    @FXML private RadioButton breadRadio01, breadRadio02, breadRadio03 ;
    @FXML private ImageView radioImageView ;
    @FXML private Button fxmlButtonExit ;
    @FXML private Slider mySlider ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 슬라이더의 값이 변경되면 글자의 크기를 변경합니다.
        ChangeListener<Number> sliderListenner = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldnumber, Number newNumber) {
                // observableValue: 값 변경이 된 객체
                // oldnumber : 이전 값, Number newNumber : 새로운 값
                // %d는 int 타입에서만 적용됨.
                String message = "이전 값 : %.3f, 새로운 값: %.3f\n";
                System.out.printf(message, oldnumber, newNumber);

                fxmlButtonExit.setFont(new Font(newNumber.doubleValue()));
            }
        };
        mySlider.valueProperty().addListener(sliderListenner);

        // 라디오 버튼이 토글되었을 때, 반응하는 리스너입니다.
        ChangeListener<Toggle> radioListener = new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {
                // newValue : 내가 선택한 라디오 버튼 객체
                if (newValue != null){
                    // fxml 파일의 userData 속성을 참조하세요.
                    String imageFile = Utility.IMAGE_PATH + newValue.getUserData().toString();
                    System.out.println("이미지 파일 이름");
                    System.out.println(imageFile);

                    String newImage = getClass().getResource(imageFile).toString();
                    Image tatgetImage = new Image(newImage);
                    radioImageView.setImage(tatgetImage);
                }
            }
        };

        breadGroup.selectedToggleProperty().addListener(radioListener);
    }

    public void handleChkAction(ActionEvent actionEvent) {
        // 체크 박스의 선택 여부에 따라서 다른 이미지로 교체됩니다.
        String name = "";

        if(changeImage01.isSelected() && changeImage02.isSelected()){
            name = Utility.IMAGE_PATH + "geek-glasses-hair.gif";
        } else if (changeImage01.isSelected()) {
            name = Utility.IMAGE_PATH + "geek-glasses.gif";
        } else if (changeImage02.isSelected()) {
            name = Utility.IMAGE_PATH + "geek-hair.gif";
        } else {
            name = Utility.IMAGE_PATH + "geek.gif";
        }

        System.out.println("이미지 파일 이름 : \n");
        System.out.println(name);

        String url = getClass().getResource(name).toString();
        Image image = new Image(url);
        checkImageView.setImage(image);
    }

    public void handleButtonExit(ActionEvent actionEvent) {
        // 종료버튼 클릭시 종료 여부를 확인 받고 종료합니다.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("종료 확인하기");
        alert.setHeaderText("지금 종료하려고 하시는군요.");
        alert.setContentText("프로그램을 종료하시겠습니까?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get()==ButtonType.OK){
            System.out.println("OK 버튼을 눌러서 종료하도록 합니다.");
            Platform.exit();
        } else {
            System.out.println("Cancel 버튼을 누르셨군요.");
        }

    }
}
