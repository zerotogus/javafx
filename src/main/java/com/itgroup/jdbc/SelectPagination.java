package com.itgroup.jdbc;

import com.itgroup.bean.Product;
import com.itgroup.dao.ProductDao;
import com.itgroup.utility.Paging;

import java.util.List;
import java.util.Scanner;

public class SelectPagination {
    public static void main(String[] args) {
        // 검색 모드와 페이지 네이션 기능을 구현합니다.
        Scanner scan = new Scanner(System.in);
        System.out.print("몇 페이지 볼거니? ");
        String pageNumber = scan.next();

        System.out.print("페이지 당 몇 건씩 볼거니? ");
        String pageSize = scan.next();

        System.out.print("all, beverage, bread, macaron, cake 중 1개 입력 : ");
        String mode = scan.next(); // 검색 모드(무엇을 검색할 것인가?)

        ProductDao dao = new ProductDao();
        int totalcount = dao.getTotalCount(mode);

        String url = "prList.jsp";
        String keyword = "";
        Paging pageInfo = new Paging(pageNumber, pageSize, totalcount, url, mode, keyword);
        pageInfo.displayInformation();

        List<Product> productList = dao.getPaginationData(pageInfo);

        for(Product bean : productList){
            ShowData.printBean(bean);
        }
    }
}
