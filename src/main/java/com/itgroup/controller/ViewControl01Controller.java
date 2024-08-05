package com.itgroup.controller;

import com.itgroup.bean.MyObject;
import com.itgroup.utility.Utility;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jdk.jshell.execution.Util;

import java.net.URL;
import java.util.ResourceBundle;


public class ViewControl01Controller implements Initializable {
    @FXML
    private ListView listView;
    @FXML
    private TableView<MyObject> tableView;
    @FXML
    private ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> dataList
                = FXCollections.observableArrayList("어린이", "음료수", "빵");
        listView.setItems(dataList);

        // 테이블 뷰의 각 컬럼의 값을 MyObject 객체와 연결되도록 property를 셋팅해주어야 합니다.
        // 0번째 컬럼은 name 변수이고, 1번째 컬럼은 image입니다.
        TableColumn tcName = tableView.getColumns().get(0);
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn tcImage = tableView.getColumns().get(1);
        tcImage.setCellValueFactory(new PropertyValueFactory<>("image"));

        ChangeListener<Number> listListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                String message = "oldValue : " + oldValue + ", newValue: " + newValue;
                System.out.println(message);

                int menu = newValue.intValue();
                System.out.println("선택한 인덱스: " + menu);

                // 테이블 뷰에 넣을 데이터 정보
                ObservableList tableData = null;

                switch (menu) {
                    case 0: // 어린이
                        tableData = FXCollections.observableArrayList(
                                new MyObject("빨간어린이", "child04.jpg"),
                                new MyObject("노란어린이", "child03.jpg"),
                                new MyObject("폼잡는 어린이", "child01.jpg"),
                                new MyObject("말 안듣는 어린이", "child02.jpg")
                        );
                        break;
                    case 1: // 음료수
                        tableData = FXCollections.observableArrayList(
                                new MyObject("아메리카노", "americano01.png"),
                                new MyObject("카푸치노", "cappuccino03.png"),
                                new MyObject("카페라떼", "vanilla_latte_02.png"),
                                new MyObject("콜드브루", "americano02.png")
                        );
                        break;
                    case 2: // 빵
                        tableData = FXCollections.observableArrayList(
                                new MyObject("굿모닝롤", "brioche_01.png"),
                                new MyObject("맘모스빵", "brioche_02.png"),
                                new MyObject("바게트", "french_baguette_03.png"),
                                new MyObject("치아버터", "ciabatta_01.png")
                        );
                        break;
                } // end switch

                if (tableData != null) {
                    tableView.setItems(tableData);
                }
            }
        };

        // 리스트 뷰의 색인 정보가 변경(change)되었을 때 listListener가 동작하도록 합니다.
        listView.getSelectionModel().selectedIndexProperty().addListener(listListener);

        ChangeListener<MyObject> tableListener = new ChangeListener<MyObject>() {
            @Override
            public void changed(ObservableValue<? extends MyObject> observableValue, MyObject oldValue, MyObject newValue) {
                if (newValue != null) {
                    String imageFile = Utility.IMAGE_PATH + newValue.getImage();
                    System.out.println("이미지 파일");
                    System.out.println(imageFile);

                    Image someImage = new Image(getClass().getResource(imageFile).toString());
                    imageView.setImage(someImage);
                }
            }
        };

        tableView.getSelectionModel().selectedItemProperty().addListener(tableListener);

    }

    public void handleBtnOkAction(ActionEvent actionEvent) {
        Object item = listView.getSelectionModel().getSelectedItem();
        if (item==null){
            System.out.println("리스트 선택 필수");
        }else {
            System.out.println("리스트 뷰 선택된 항목 : " + item);
        }


        MyObject bean = tableView.getSelectionModel().getSelectedItem();

        if (bean == null) {
            String[] message = {"테이블 선택 여부", "항목 미체크", "테이블 뷰에서 항목을 선택해 주세요."};
            Utility.showAlert(Alert.AlertType.INFORMATION, message);
        } else {
            System.out.println("선택된 품목 : " + bean.getName());
            System.out.println("선택된 이미지 : " + bean.getImage());
        }
    }


    public void handleBtnCancelAction(ActionEvent actionEvent) {
        System.out.println("종료합니다.");
        Platform.exit();
    }
}
