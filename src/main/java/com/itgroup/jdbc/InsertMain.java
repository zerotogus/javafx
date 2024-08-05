package com.itgroup.jdbc;

import com.itgroup.bean.Product;
import com.itgroup.dao.ProductDao;

import java.util.Scanner;

public class InsertMain {
    public static void main(String[] args) {
        // 관리자가 상품 1개를 등록합니다.
        ProductDao dao = new ProductDao();
        Product bean = new Product();

        Scanner scan = new Scanner(System.in);
        System.out.print("상품 이름 : ");
        String name = scan.next();

        //bean.setPnum(0); // 시퀀스로 대체
        bean.setName(name);
        bean.setCompany("AB 식품");
        bean.setImage01("xx.png");
        bean.setImage02("yy.png");
        bean.setImage03("zz.png");
        bean.setStock(1234);
        bean.setPrice(5678);
        bean.setCategory("bread");
        bean.setContents("엄청 맛나요");
        //bean.setPoint(0); // 포인트는 기본 값 사용 예정
        //bean.setInputdate(null); // 입고 날자도 기본 값 사용 예정


        int cnt = -1 ; // -1을 실패한 경우라고 가정합니다.
        cnt = dao.intsertData(bean);

        if(cnt==-1){
            System.out.println("상품 등록에 실패했습니다.");
        } else {
            System.out.println("상품 등록에 성공했습니다.");
        }
    }

}
