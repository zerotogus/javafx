package com.itgroup.controller;

import com.itgroup.bean.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BarChartController implements Initializable {
    @FXML private BarChart<String, Integer> barChart;

    public void makeChartAll(ObservableList<Product> dataList) {
        for (Product bean : dataList){
            List<XYChart.Data<String, Integer>> lists = new ArrayList<>();

            // 단가와 재고의 수치 정보가 너무 큰 차이가 나면 로그 함수를 고려해 보자.
            lists.add(new XYChart.Data<String, Integer>("단가", bean.getPrice()));
            lists.add(new XYChart.Data<String, Integer>("재고", bean.getStock()));

            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName(bean.getName());
            series.setData(FXCollections.observableArrayList(lists));
            barChart.getData().add(series);
        }
        barChart.setTitle("단가/재고 막대");
    }

    public void makeChart(ObservableList<Product> dataList) {
        // 단가에 대한 막대 그래프를 그려줍니다.

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("단가");

        for (Product bean : dataList){
            series.getData().add(new XYChart.Data<>(bean.getName(), bean.getPrice()));
        }
        barChart.getData().add(series);
        barChart.setTitle("단가 막대 그래프");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("막대 그래프를 그리려고 하시군요");

    }



}
