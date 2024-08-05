package com.itgroup.jdbc;

import com.itgroup.bean.Product;

public class ShowData {

    public static void printBean(Product bean) {
        int pnum = bean.getPnum();
        String name = bean.getName();
        String company = bean.getCompany();
        String image01 = bean.getImage01();
        String image02 = bean.getImage02();
        String image03 = bean.getImage03();
        int stock = bean.getStock();
        int price = bean.getPrice();
        String category = bean.getCategory();
        String contents = bean.getContents();
        int point = bean.getPoint();
        String inputdate = bean.getInputdate();

        System.out.println("상품번호 : " + pnum);
        System.out.println("상품명 : " + name);
        System.out.println("제조 회사 : " + company);
        System.out.println("이미지01 : " + image01);
        System.out.println("이미지02 : " + image02);
        System.out.println("이미지03 : " + image03);
        System.out.println("재고 : " + stock);
        System.out.println("단가 : " + price);
        System.out.println("카테고리 : " + category);
        System.out.println("상품 설명 : " + contents);
        System.out.println("포인트 : " + point);
        System.out.println("입고 일자" + inputdate);


    }
}
