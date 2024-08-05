package com.itgroup.controller;

import com.itgroup.bean.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PieChartController implements Initializable {
    @FXML private PieChart pieChart;

    public void makePieChart(ObservableList<Product> dataList) {
        List<PieChart.Data> pieChartData = new ArrayList<>();
        for (Product bean : dataList){
            pieChartData.add(new PieChart.Data(bean.getName(), bean.getPrice()));
        }
        pieChart.setData(FXCollections.observableArrayList(pieChartData));
        pieChart.setTitle("단가에 대한 파이 그래프");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("파이 그래프를 그리려고 하시군요");

    }


}
