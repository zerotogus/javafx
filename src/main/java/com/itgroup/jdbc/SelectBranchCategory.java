package com.itgroup.jdbc;

import com.itgroup.bean.Product;
import com.itgroup.dao.ProductDao;

import java.util.List;
import java.util.Scanner;

public class SelectBranchCategory {
    public static void main(String[] args) {
        // 모든 상품 똔느 특정 카테고리만 조회하기
        Scanner scan = new Scanner(System.in);
        System.out.print("all, beverage, bread, macaron, cake 중 1개 입력 : ");

        String category = scan.next();

        // 모든 상품 조회하기
        ProductDao dao = new ProductDao();
        List<Product> allProduct = dao.selectByCategory(category);
        System.out.println("상품 개수 : " + allProduct.size());

        for (Product bean : allProduct){
            ShowData.printBean(bean);
        }
    }
}
