package com.itgroup.controller;

import com.itgroup.bean.Item;
import com.itgroup.utility.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class GetSelectedItemController implements Initializable {
    @FXML private ImageView targetImage;
    @FXML
    private ComboBox leftCombo, rightCombo;

    // 품목들을 저장할 리스트 컬렉션
    private List<Item> comboList = new ArrayList<>() ;

    // 카테고리 목록을 저장할 Set 컬렉션
    private Set<String> categorySet = new HashSet<String>();

    // 상품 이름과 이미지 정보를 저장하고 있는 Map 컬렉션
    private Map<String, String> imagaMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillDataFromFile();

        ObservableList<String> dataList = FXCollections.observableArrayList(categorySet);
        leftCombo.setItems(dataList);
    }

    private void fillDataFromFile() {
        // 특정 텍스트 파일에서 관련 정보들을 읽어 들입니다.
        String pathName = System.getProperty("user.dir");
        pathName += Utility.DATA_PATH;

        System.out.println(pathName);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(pathName, "list.txt")));

            String oneline = null;
            while((oneline=br.readLine()) != null){
                System.out.println(oneline);
                String[] myItem = oneline.split("/");
                comboList.add(new Item(myItem[0], myItem[1], myItem[2]));
                categorySet.add(myItem[0]);
            }
            System.out.println("카테고리 품목 : ");
            System.out.println(categorySet);

            System.out.println("리스트 품목 : ");
            System.out.println(comboList);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void selectLeft(ActionEvent actionEvent) {
        // 좌측 콤보를 선택하였습니다.
        // 선택된 카테고리의 하위 품목들을 오른쪽 콤보 박스에 추가합니다.
        String selectedCategory = (String)leftCombo.getSelectionModel().getSelectedItem();
        System.out.println("선택된 카테고리 : " + selectedCategory);

        // 여러 개의 리스트들에서 category 가 selectedCategory 와 동일한 품목만 추출합니다.
        List<Item> filterItems = comboList.stream().filter(item->selectedCategory.equals(item.getCategory())).collect(Collectors.toList());

        System.out.println(filterItems);

        // 우측 콤보 박스 rightCombo 에 항목들을 추가합니다.
        rightCombo.getItems().clear(); // 일단 비우고
        for (Item one : filterItems){
            rightCombo.getItems().add(one.getName());

            // 해당 상품 이름에 해당하는 이미지 정보 추가
            imagaMap.put(one.getName(), one.getImage());
        }

        // 우측 콤보 박스에서 사용할 이벤트 입니다.
        rightCombo.setOnAction(event -> {
            String selectedItem = (String)rightCombo.getSelectionModel().getSelectedItem();
            String imageName = imagaMap.get(selectedItem);

            System.out.println("Selected Item : " + selectedItem );
            System.out.println("Image : " + imageName);

            if(imageName != null) {
                // 해당 이미지를 보여주세요.
                String imageFile = Utility.IMAGE_PATH + imageName;
                String myUrl = getClass().getResource(imageFile).toString();
                Image image =new Image(myUrl);
                targetImage.setImage(image);
            } else {
                targetImage.setImage(null);
            }
        });
    }
}
