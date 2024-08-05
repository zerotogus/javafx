package com.itgroup.controller;

import com.itgroup.bean.MyObject;
import com.itgroup.dao.ArticleDao;
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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewControl02Controller implements Initializable {
    @FXML
    private ListView listView;
    @FXML
    private TableView<MyObject> tableView;
    @FXML
    private ImageView imageView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArticleDao dao = new ArticleDao();
        List<String> categories = dao.selectDistinctCategory();

        ObservableList<String> dataList = FXCollections.observableArrayList(categories);
        listView.setItems(dataList);

        // 테이블 뷰의 각 컬럼의 값을 MyObject 객체와 연결되도록 property를 세팅해줘야 합니다.
        // 0번째 컬럼은 name 변수이고, 1번째 컬럼은 image입니다.
        TableColumn tcName = tableView.getColumns().get(0);
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn tcImage = tableView.getColumns().get(1);
        tcImage.setCellValueFactory(new PropertyValueFactory<>("image"));


        ChangeListener<String> listListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                String message = "oldValue:" + oldValue + ", newValue:" + newValue;
                System.out.println(message);

                ObservableList tableData = FXCollections.observableArrayList(dao.selectDataByCategory(newValue));
                System.out.println(tableData);

                if (tableData!=null){
                    tableView.setItems(tableData);
                }
            }
        };
        // 리스트 뷰의 아이템  정보가 변경(Change) 되었을 때 listListener 동작하도록 합니다.
        listView.getSelectionModel().selectedItemProperty().addListener(listListener);

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
        Object item = listView.getSelectionModel().getSelectedItems();
        if (item == null) {
            System.out.println("리스트 제발 선택해 주세요.");
        } else {
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


