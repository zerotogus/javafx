package com.itgroup.controller;

import com.itgroup.bean.Product;
import com.itgroup.dao.ProductDao;
import com.itgroup.utility.Paging;
import com.itgroup.utility.Utility;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CoffeeExamController implements Initializable {
    private ProductDao dao = null;

    @FXML private ImageView imageView;

    private ObservableList<Product> dataList = null;
    private String mode = null; // 필드 검색을 위한 mode 변수

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dao = new ProductDao();
        setTableClumns();
        setPagination(0);
    }

    @FXML private Pagination pagination;

    private void setPagination(int pageIndex) {
        // 페이징 관련 설정을 합니다.
        pagination.setPageFactory(this::createPage);
        pagination.setCurrentPageIndex(pageIndex);


        //화면 갱신 시 이미지 뷰 정보도 같이 없애기
        imageView.setImage(null); // 디폴트 이미지(noimage도 고려)로 변경

        // 테이블 뷰의 1행을 클릭하면, 우측에 이미지를 보여줍니다.
        ChangeListener<Product> tableListener = new ChangeListener<Product>() {
            @Override
            public void changed(ObservableValue<? extends Product> observableValue, Product oldValue, Product newValue) {
                if (newValue != null){
                    System.out.println("상품 정보");
                    System.out.println(newValue);

                    String imageFile = ""; // 해당 이미지의 fullPath + 이미지 이름
                    if (newValue.getImage01() != null){
                        imageFile = Utility.IMAGE_PATH + newValue.getImage01().trim();
                    }else {
                        imageFile = Utility.IMAGE_PATH + "no image.png";
                    }

                    Image someImage = null; // 이미지 객체
                    if (getClass().getResource(imageFile) == null){
                        imageView.setImage(null);
                    }else {
                        someImage = new Image(getClass().getResource(imageFile).toString());
                        imageView.setImage(someImage);
                    }
                }
            }
        };

        productTable.getSelectionModel().selectedItemProperty().addListener(tableListener);

        setContextMenu();
    }

    private void setContextMenu() {
        // 테이블 뷰에 대하여 컨텍스트 메뉴를 구성합니다.
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem01 = new MenuItem("단가 수직 막대");
        MenuItem menuItem02 = new MenuItem("단가 파이 그래프");
        MenuItem menuItem03 = new MenuItem("단가/재고 막대");

        // 자바에서... 은 가변 매개 변수입니다.
        // 즉, 매개 변수를 무제한 개수로 넣을 수 있는데, addAll() 메소드가 가변 매개 변수 형태로 입력이 가능합니다.
        contextMenu.getItems().addAll(menuItem01, menuItem02, menuItem03);

        productTable.setContextMenu(contextMenu);

        menuItem01.setOnAction((event)->{
            try {
                makeBarChart();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });

        menuItem02.setOnAction((event)->{
            try {
                makePieChart();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });

        menuItem03.setOnAction((event)->{
            try {
                makeBarChartAll();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }

    private void makeBarChartAll() throws Exception{
        System.out.println("단가/재고 막대 그래프 그리기");
        FXMLLoader fxmlLoader = this.getFxmlLoader("BarChart.fxml");
        Parent parent = fxmlLoader.load();
        BarChartController controller = fxmlLoader.getController();
        controller.makeChartAll(productTable.getItems());
        this.showModal(parent);
    }

    private void makePieChart() throws Exception{
        System.out.println("단가 파이 막대 그래프 그리기");
        FXMLLoader fxmlLoader = this.getFxmlLoader("PieChart.fxml");
        Parent parent = fxmlLoader.load();
        PieChartController controller = fxmlLoader.getController();
        controller.makePieChart(productTable.getItems());

        this.showModal(parent);
    }

    private void makeBarChart() throws Exception{
        System.out.println("단가 수직 막대 그래프 그리기");
        FXMLLoader fxmlLoader = this.getFxmlLoader("BarChart.fxml");
        Parent parent = fxmlLoader.load();

        // 주의) 해당 컨트롤러는 반드시 load() 메소드 이후에 호출해야합니다.
        BarChartController controller = fxmlLoader.getController();

        // 해당 담당자 컨트롤러에게 그리고자 하는 정보를 넘겨줍니다.
        controller.makeChart(productTable.getItems());

        this.showModal(parent);
    }

    private void showModal(Parent parent) {
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private FXMLLoader getFxmlLoader(String fxmlName) throws Exception{
        // 입력된 fxml 파일 이름을 이용하여, FXML의 최상위 컨테이너를 반환해줍니다.
        Parent parent = null;

        String fileName = Utility.FXMl_PATH + fxmlName ;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fileName));
        
        return fxmlLoader;
    }

    private Node createPage(Integer pageIndex) {
        // 각 페이지의 Pagination을 동적으로 생성해주는 공장(Factory) 역할을 합니다.
        // mode 변수는 필드 검색시 사용하는 변수입니다.

        int totalCount = 0;
        totalCount = dao.getTotalCount(this.mode);

        Paging pageInfo = new Paging(String.valueOf(pageIndex+1),"10",totalCount,null, this.mode,null);

        pagination.setPageCount(pageInfo.getTotalPage());

        fillTableData(pageInfo);

        VBox vbox = new VBox(productTable);

        return vbox;
    }
    @FXML private Label pageStatus;

    private void fillTableData(Paging pageInfo) {
        // 테이블 뷰에 목록을 채워 줍니다.
        List<Product> productList =dao.getPaginationData(pageInfo);
        dataList = FXCollections.observableArrayList(productList);
        productTable.setItems(dataList);
        pageStatus.setText(pageInfo.getPagingStatus());
    }

    @FXML private TableView<Product> productTable; // 테이블 목록을 보여주는 뷰

    private void setTableClumns() {
        // Product 빈 클래스에서 보여 주고자 하는 컬럼 정보들을 연결합니다.
        // 열거 되지 않은 목록들은 할목의 상세 보기 페이지에서 구현할 수 있습니다.
        String[] fields = {"pnum","name","company","category","inputdate"};
        String[] colNames = {"상품번호","이름","제조회사","카테고리","입고일자"};

        TableColumn tableColumn = null;
        System.out.println(productTable);
        for (int i = 0; i < fields.length; i++) {
            tableColumn = productTable.getColumns().get(i);
            tableColumn.setText(colNames[i]); // 컬럼을 한글 이름으로 변경

            // Product 빈 클래스의 인스턴스 변수 이름을 셋팅하면 데이터가 자동으로 바인딩됩니다.
            tableColumn.setCellValueFactory(new PropertyValueFactory<>(fields[i]));

            tableColumn.setStyle("-fx-alignment:center"); // 모든 셀 데이터를 가운데 정렬하기
        }

    }

    public void onInsert(ActionEvent event) throws Exception {
        // 상품을 등록합니다.
        // fxml 파일 로딩
        String fxmlFile = Utility.FXMl_PATH + "ProductInsert.fxml";

        // import java.net.URL ;
        URL url = getClass().getResource(fxmlFile);
        FXMLLoader fxmlLoader =  new FXMLLoader(url);

        Parent container = fxmlLoader.load(); // fxml의 최상위 컨테이너 객체

        Scene scene = new Scene(container); // 씬에 담기
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); // 씬을 무대에 담기
        stage.showAndWait(); // 창 띄우고 대기
        stage.setResizable(false);
        stage.setTitle("상품 등록하기");
        setPagination(0); // 화면 갱신
    }

    public void onUpdate(ActionEvent event) throws Exception{
        // 선택된 항목(Product Bean)에 대한 수정 작업을 합니다.
        int idx = productTable.getSelectionModel().getSelectedIndex() ;
        System.out.println(idx);

        if (idx >= 0){
            System.out.println("선택된 색인 번호 : " + idx);

            // fxml 파일 로딩
            String fxmlFile = Utility.FXMl_PATH + "ProductUpdate.fxml";
            URL url = getClass().getResource(fxmlFile);
            FXMLLoader fxmlLoader =  new FXMLLoader(url);
            Parent container = fxmlLoader.load(); // fxml의 최상위 컨테이너 객체

            // update 기능에서 추가된 내용 시작
            // 현재 내가 선택한 상품(Product) 정보와 색인 정보(idx)를 해당 컨트롤러에게 메소드를 통하여 전달해줍니다.
            Product bean = productTable.getSelectionModel().getSelectedItem() ;

            ProductUpdateController controller = fxmlLoader.getController();

            controller.setBean(bean);

            // update 기능에서 추가된 내용 끝

            Scene scene = new Scene(container); // 씬에 담기
            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene); // 씬을 무대에 담기
            stage.showAndWait(); // 창 띄우고 대기
            stage.setResizable(false);
            stage.setTitle("상품 수정하기");
            setPagination(0); // 화면 갱신
        }else {
            String[] message = new String[]{"상품 선택 확인", "상품 미선택", "수정하고자 하는 상품을 선택해 주세요."};
            Utility.showAlert(Alert.AlertType.ERROR, message);
        }
    }

    public void onDelete(ActionEvent event) {
        // 특정한 항목에 대하여 요소를 삭제합니다.
        int idx = productTable.getSelectionModel().getSelectedIndex();

        if (idx >= 0){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("삭제 확인 메세지");
            alert.setHeaderText("삭제 항목 대화 상자");
            alert.setContentText("이 항목을 정말로 삭제하겠습니까?");

            Optional<ButtonType> response = alert.showAndWait();

            if (response.get() == ButtonType.OK){
                Product bean = productTable.getSelectionModel().getSelectedItem() ;
                int pnum = bean.getPnum();
                int cnt = -1 ;
                cnt = dao.deleteDate(pnum);
                if (cnt != -1){
                    System.out.println("삭제 성공");
                    setPagination(0);
                } else {
                    System.out.println("삭제 실패");
                }
            }else {
                System.out.println("삭제를 취소하였습니다.");
            }

        }else {
            String[] message = new String[]{"삭제할 목록 확인", "삭제할 대상 미선택", "삭제할 행을 선택해주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
        }

    }

    public void onSaveFile(ActionEvent event) {
        // 현재 페이지에 보이는 테이블 뷰 목록을 텍스트 형식으로 저장합니다.
        FileChooser chooser = new FileChooser() ;
        Button myBtn = (Button) event.getSource();
        Window window = myBtn.getScene().getWindow();
        File savedFile = chooser.showSaveDialog(window);

        if (savedFile != null){
            System.out.println("yes");
            FileWriter fw = null;
            BufferedWriter bw = null;

                try {
                    fw = new FileWriter(savedFile);
                    bw = new BufferedWriter(fw); // 승급

                    for (Product bean : dataList){
                        bw.write(bean.toString());
                        bw.newLine(); // 엔터키
                    }

                    System.out.println("파일 저장이 완료되었습니다.");
                }catch (Exception ex){
                    ex.printStackTrace();
                }finally {
                    try {
                        if (bw != null){bw.close();}
                        if (fw != null){fw.close();}
                    }catch (Exception ex2){
                        ex2.printStackTrace();
                    }
                }

        }else {
            System.out.println("파일 저장이 취소되었습니다.");
        }

    }

    public void onClosing(ActionEvent event) {
        System.out.println("프로그램 종료");
        Platform.exit();
    }
    @FXML private ComboBox<String> fieldSearch ; // 필드 검색을 위한 콤보 박스


    public void choiceSelect(ActionEvent event) {
        // 특정 카테고리에 대하여 필터링합니다.
        String category = fieldSearch.getSelectionModel().getSelectedItem();
        System.out.println("검색 카테고리 : [" + category + "]");

        this.mode = Utility.getCategoryName(category, "value");
        System.out.println("필드 검색 모드 : [" + mode + "]");

        setPagination(0); // 화면 정보 갱신
    }
}
