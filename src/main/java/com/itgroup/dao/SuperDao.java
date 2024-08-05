package com.itgroup.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class SuperDao{
    private String driver ;
    private String url = null; // 데이터 베이스 출처
    private String id = null; // 사용자 아이디
    private String password = null; // 사용자 비밀번호

    public SuperDao() {
        this.driver = "oracle.jdbc.driver.OracleDriver";
        this.url = "jdbc:oracle:thin:@localhost:1521:xe" ;
        this.id = "oraman" ;
        this.password = "oracle" ;
        try{
            Class.forName(driver); // 동적 객체 생성
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    protected Connection getConnection() {
        Connection conn = null ;
        try{

        } catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                conn = DriverManager.getConnection(url, id, password);
                if(conn != null){
//                    System.out.println("접속 성공");
                } else {
                    System.out.println("접속 실패");
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return conn ;
    }
}
