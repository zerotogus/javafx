package com.itgroup.jdbc;

import com.itgroup.bean.Product;
import com.itgroup.dao.ProductDao;

import java.util.Scanner;

public class DeleteMain {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("삭제할 상품 번호 : ");
        int pnum = scan.nextInt();

        ProductDao dao = new ProductDao();
        int cnt = -1 ;
        cnt = dao.deleteDate(pnum);

        if (cnt == -1){
            System.out.println("상품 삭제에 실패했습니다.");
        }else {
            System.out.println("상품 삭제에 성공했습니다.");
        }
    }
}
