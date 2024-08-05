package com.itgroup.controller;

import com.itgroup.bean.Person;
import com.itgroup.dao.PersonDao;
import com.itgroup.utility.Paging;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PaginationTestController implements Initializable {
    @FXML Label pageStatus;
    @FXML VBox vbox;
    @FXML TableView<Person> tableView;
    @FXML Pagination pagination;

    PersonDao dao = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dao = new PersonDao();

        setTableColumms();
        setPagination(0);
    }



    private void setPagination(int pageIndex) {
        System.out.println("pageIndex : " + pageIndex);
        // 주의) 0 base
        pagination.setCurrentPageIndex(pageIndex);

        // createPage 메소드를 사용하여 페이지 네이션을 만들어 줍니다.
        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(int pageNumber) {
        int totalCount = dao.getTotalCount();

        Paging pageInfo = new Paging(String.valueOf(pageNumber+1),"10", totalCount, null,null,null);

        pageInfo.displayInformation();

        pagination.setPageCount(pageInfo.getTotalPage());

        fillTableData(pageInfo);
        vbox = new VBox(tableView);

        return vbox;
    }

    ObservableList<Person> dataList = null;

    private void fillTableData(Paging pageInfo) {
        // tableView에 데이터를 채워 줍니다.
        List<Person> personList = dao.getAllData(pageInfo.getBeginRow()-1,pageInfo.getEndRow());

        dataList = FXCollections.observableArrayList(personList);
        tableView.setItems(dataList);
        pageStatus.setText(pageInfo.getPagingStatus());


    }

    private void setTableColumms() {
        // 테이블 내 컬럼 작업
        // tableView 객체가 Person 타입의 정보를 가지고 있으므로
        // 다음 배열 요소의 값은 Person 클래스의 변수 이름과 동일해야 합니다.
        String[] field = {"num", "firstName", "lastName"};
        TableColumn tcol = null;
        for (int i = 0; i < field.length; i++) {
            tcol = tableView.getColumns().get(i);
            tcol.setCellValueFactory(new PropertyValueFactory<>(field[i]));
            tcol.setStyle("-fx-alignment:center;");

        }
    }
}
