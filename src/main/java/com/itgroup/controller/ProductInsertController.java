package com.itgroup.controller;

import com.itgroup.bean.Product;
import com.itgroup.dao.ProductDao;
import com.itgroup.utility.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jdk.jshell.execution.Util;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProductInsertController implements Initializable {
    // fxml 파일 내에서 "fxml + 변수이름" 으로 명명했습니다.
    @FXML private TextField fxmlName;
    @FXML private TextField fxmlCompany;
    @FXML private TextField fxmlImage01;
    @FXML private TextField fxmlImage02;
    @FXML private TextField fxmlImage03;
    @FXML private TextField fxmlStock;
    @FXML private TextField fxmlPrice;
    @FXML private ComboBox<String> fxmlCategory;
    @FXML private TextField fxmlContents;
    @FXML private TextField fxmlPoint;
    @FXML private DatePicker fxmlInputdate;

    ProductDao dao = null;
    Product bean = null; // 상품 1개를 의미하는 빈 클래스

    public void onProductInsert(ActionEvent event) {
        // 기입한 상품 목록을 데이터 베이스에 추가합니다.
        // event 객체는 해당 이벤트를 발생시킨 객체입니다.
        System.out.println(event);
        boolean bool = validationCheck();
        if (bool == true){
            int cnt = insertDatabase();
            if(cnt ==1 ){ // 인서트 성공시
                Node source = (Node) event.getSource(); // 강등
                Stage stage = (Stage) source.getScene().getWindow() ; // 강등
                stage.close(); // 현재 창을 닫습니다.
            }
        }else {
            System.out.println("등록실패");
        }
    }

    private int insertDatabase() {
        // 1건의 데이터인 bean을 dao를 사용하여 데이터 베이스에 추가.
        int cnt = -1 ; // 작업실패
        cnt = dao.intsertData(this.bean);
        if (cnt == -1){
            String[] message = new String[]{"상품 등록", "길이 제한 위배", "상품 등록을 실패했습니다.."};
            Utility.showAlert(Alert.AlertType.ERROR, message);
        }else {

        }
        return cnt;
    }

    private boolean validationCheck() {
        // 유효성 검사를 통화하면 true 가 됩니다.
        String[] message = null;

        String name = fxmlName.getText().trim();
        if (name.length() <= 2 || name.length() >= 11){
            message = new String[]{"유효성 검사 : 이름", "길이 제한 위배", "이름은 3글자 이상 9글자 이하만 가능합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        String company = fxmlCompany.getText().trim();
        if (company.length() <= 2 || company.length() >= 16){
            message = new String[]{"유효성 검사 : 제조 회사", "길이 제한 위배", "이름은 3글자 이상 10글자 이하만 가능합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        boolean bool = false;
        String image01 = fxmlImage01.getText().trim();

        if(image01 == null || image01.length() < 5){
            message = new String[]{"유효성 검사 : 이미지01", "필수 입력 체크", "1번 이미지는 필수 입력 사항입니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        // startWith()와 endsWith()
        bool = image01.endsWith(".jpg") || image01.endsWith(".png") ;
        if (!bool){
            message = new String[]{"유효성 검사 : 이미지01", "잘못된 확장자", "이미지의 확장자는 .jpg 또는 .png만 가능합니다. ."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        String image02 = fxmlImage02.getText().trim();

        if (image02.length()==0){ // 사용자가 2번 이미지를 입력하지 않은 경우
            image02 = null;
        }

        if (image02 != null){
            bool = image02.endsWith(".jpg") || image02.endsWith(".png") ;
            if (!bool){
                message = new String[]{"유효성 검사 : 이미지02", "잘못된 확장자", "이미지의 확장자는 .jpg 또는 .png만 가능합니다. ."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }
        }

        String image03 = fxmlImage03.getText().trim();
        if (image03.length()==0){ // 사용자가 3번 이미지를 입력하지 않은 경우
            image03 = null;
        }

        if (image03 != null){
            bool = image03.endsWith(".jpg") || image03.endsWith(".png") ;
            if (!bool){
                message = new String[]{"유효성 검사 : 이미지03", "잘못된 확장자","이미지의 확장자는 .jpg 또는 .png만 가능합니다. ."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }
        }

        int stock = 0 ;
        try{
            String _stock = fxmlStock.getText().trim();
            stock = Integer.valueOf(_stock);

            if (stock < 10 || stock > 100){
                message = new String[]{"유효성 검사 : 재고", "허용 숫자 위반", "재고는 10개 이상 100개 이하로 입력해 주세요."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }

        }catch (NumberFormatException ex){
            ex.printStackTrace();

            message = new String[]{"유효성 검사 : 재고", "무효한 숫자 형식", "올바른 숫자 형식을 입력해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }
        int price = 0 ;
        try{
            String _price = fxmlPrice.getText().trim();
            price = Integer.valueOf(_price);

            if (price < 1000 || price > 10000){
                message = new String[]{"유효성 검사 : 단가", "허용 숫자 위반", "가격은 1000원 이상 10000원 이하로 입력해 주세요."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }

        }catch (NumberFormatException ex){
            ex.printStackTrace();

            message = new String[]{"유효성 검사 : 단가", "무효한 숫자 형식", "올바른 숫자 형식을 입력해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        int selectedIndex = fxmlCategory.getSelectionModel().getSelectedIndex();
        String category = null;
        if (selectedIndex == 0){
            message = new String[]{"유효성 검사 : 카테고리", "카테고리 미선택", "원하는 카테고리를 반드시 선택해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        } else {
            category = fxmlCategory.getSelectionModel().getSelectedItem();
            System.out.println("선택된 항목");
            System.out.println(category);
        }


        String contents = fxmlContents.getText().trim();
        if (contents.length() <= 4 || contents.length() >= 31){
            message = new String[]{"유효성 검사 : 상품 설명", "허용 숫자 위반", "상품설명은 4글자 이상 30포인트 이하로 입력해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;

        }
        int point = 0;
        try{
            String _point = fxmlPoint.getText().trim();
            point = Integer.valueOf(_point);

            if (point < 3 || point > 5){
                message = new String[]{"유효성 검사 : 포인트", "허용 숫자 위반", "포인트는 3포인트 이상 5포인트 이하로 입력해 주세요."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }

        }catch (NumberFormatException ex){
            ex.printStackTrace();

            message = new String[]{"유효성 검사 : 포인트", "무효한 숫자 형식", "올바른 숫자 형식을 입력해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        String inputDate = null;
        LocalDate _inputDate = fxmlInputdate.getValue();
        if (_inputDate == null){
            message = new String[]{"유효성 검사 : 입고일자", "무효한 날짜 형식", "올바른 입고 일자 형식을 입력해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }else {
            inputDate = _inputDate.toString() ;
                    inputDate = inputDate.replace("-", "/");
        }

        // 유효성 검사가 통과되면 비로소 객체 생성합니다.
        this.bean= new Product();
        // bean.setPnum();
        bean.setName(name);
        bean.setCompany(company);
        bean.setImage01(image01);
        bean.setImage02(image02);
        bean.setImage03(image03);
        bean.setStock(stock);
        bean.setPrice(price);

        // 사용자가 입력한 key인 한글 카테고리 이름을 value인 영문으로 변환시켜 셋팅합니다.
        bean.setCategory(Utility.getCategoryName(category, "value"));
        bean.setContents(contents);
        bean.setPoint(point);
        bean.setInputdate(inputDate);


        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dao = new ProductDao();

        // 최초 시작시 콤보 박스의 0번째 항목 선택하기
        fxmlCategory.getSelectionModel().select(0);

    }


}
