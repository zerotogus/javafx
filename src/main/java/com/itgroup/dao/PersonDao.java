package com.itgroup.dao;

import com.itgroup.bean.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDao extends SuperDao{
    // Person 목록을 저장할 리스트 컬렉션입니다.
    // 실제 프로그램에서는 Database 에서 읽어 들여야 합니다.
    private List<Person> personList = null ;
    private int totalCount = 0; // 컬렉션 전체 개수

    public PersonDao() {
        personList = new ArrayList<Person>();

        this.fillData();
    }



    public int getTotalCount() {
        return totalCount;
    }
    private void fillData() {
        // 원칙은 데이터 베이스로부터 읽어 와야합니다.
        for (int i = 1; i <= 12; i++) {
            personList.add(new Person(""+(3*i-2), "철수"+i, "김"));
            personList.add(new Person(""+(3*i-1), "영희"+i, "박"));
            personList.add(new Person(""+(3*i-0), "유식"+i, "최"));
        }
        totalCount = personList.size();
        System.out.println("totalCount : " + totalCount);
    }

    public List<Person> getAllData(int beginRow, int endRow) {
        return personList.subList(beginRow, endRow);
    }
}
