package com.itgroup.jdbc;

import com.itgroup.bean.Product;
import com.itgroup.dao.ProductDao;

import java.util.Scanner;

public class UpdataMain {
    public static void main(String[] args) {
        // 특정 상품에 대한 정보를 수정합니다.
        ProductDao dao = new ProductDao();
        Product bean = new Product();

        Scanner scan = new Scanner(System.in);
        System.out.print("상품 번호 : ");
        int pnum = scan.nextInt();

        System.out.print("상품 이름 : ");
        String name = scan.next();

        bean.setPnum(pnum);
        bean.setName(name);
        bean.setCompany("AB 식품");
        bean.setImage01("aa.png");
        bean.setImage02("bb.png");
        bean.setImage03("cc.png");
        bean.setStock(9999);
        bean.setPrice(1111);
        bean.setCategory("bread");
        bean.setContents("별로임");
        bean.setPoint(15); // 포인트는 기본 값 사용 예정
        bean.setInputdate("2024/07/17"); // 입고 날자도 기본 값 사용 예정


        int cnt = -1 ; // -1을 실패한 경우라고 가정합니다.
        cnt = dao.updateData(bean);

        if(cnt==-1){
            System.out.println("상품 수정에 실패했습니다.");
        } else {
            System.out.println("상품 수정 성공했습니다.");
        }
    }

}
