package com.itgroup.dao;

import com.itgroup.bean.Product;
import com.itgroup.utility.Paging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/*
String sql = " select pnum, name, company, image01, image02, image03, stock, price,";
sql += " category, contents, point, inputdate from (";
sql += " select pnum, name, company, image01, image02, image03, stock, price,";
sql += " category, contents, point, inputdate, rank() over(order by pnum desc)as ranking";
sql += " from products) where ranking between ? and ? " ;

 */
public class ProductDao extends SuperDao {
    public Product selectByPK(int pnum) {
        Connection conn = null;

        String sql = " select * from products ";
        sql += " where pnum = ? ";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Product bean = null;
        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pnum);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = this.makeBean(rs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return bean;
    }

    public List<Product> selectByCategory(String category) {
        Connection conn = null;

        String sql = " select * from products ";

        boolean bool = category == null || category.equals("all");
        if (!bool) {
            sql += " where category = ? ";
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Product> allData = new ArrayList<>();
        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            if (!bool) {
                pstmt.setString(1, category);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Product bean = this.makeBean(rs);
                allData.add(bean);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return allData;
    }


    public ProductDao() {
        super();
    }

    public List<Product> selectAll() { // 모든 상품 조회하기
        Connection conn = null;
        String sql = "select * from products order by pnum desc ";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Product> allData = new ArrayList<>();
        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Product bean = this.makeBean(rs);
                allData.add(bean);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return allData;
    }

    private Product makeBean(ResultSet rs) {
        // bean 데이터를 만들어 주는 메소드입니다.
        // 여러 군데서 호출이 되므로 별도로 만들었습니다.
        Product bean = new Product();
        try {
            bean.setPnum(rs.getInt("pnum"));
            bean.setName(rs.getString("name"));
            bean.setCompany(rs.getString("company"));
            bean.setImage01(rs.getString("image01"));
            bean.setImage02(rs.getString("image02"));
            bean.setImage03(rs.getString("image03"));
            bean.setStock(rs.getInt("stock"));
            bean.setPrice(rs.getInt("price"));
            bean.setCategory(rs.getString("category"));
            bean.setContents(rs.getString("contents"));
            bean.setPoint(rs.getInt("point"));
            bean.setInputdate(String.valueOf(rs.getDate("inputdate")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bean;
    }

    public int updateData(Product bean) {
        System.out.println(bean);
        int cnt = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = " update products set name = ?, company = ?, image01 = ?, image02 = ?, image03 = ? ,stock = ?, price = ?, category = ?, contents = ?, point = ?, inputdate = ? ";

        sql += " where pnum = ? ";


        try {
            conn = super.getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bean.getName());
            pstmt.setString(2, bean.getCompany());
            pstmt.setString(3, bean.getImage01());
            pstmt.setString(4, bean.getImage02());
            pstmt.setString(5, bean.getImage03());
            pstmt.setInt(6, bean.getStock());
            pstmt.setInt(7, bean.getPrice());
            pstmt.setString(8, bean.getCategory());
            pstmt.setString(9, bean.getContents());
            pstmt.setInt(10, bean.getPoint());
            pstmt.setString(11, bean.getInputdate());
            pstmt.setInt(12, bean.getPnum());


            cnt = pstmt.executeUpdate();

            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                ;
                if (conn != null) {
                    conn.close();
                }
                ;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return cnt;
    }


    public int intsertData(Product bean) {
        System.out.println(bean);
        int cnt = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = " insert into products(pnum, name, company, image01, image02, image03 ,stock, price, category, contents, point, inputdate) ";

        sql += " values(seqproduct.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";


        try {
            conn = super.getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bean.getName());
            pstmt.setString(2, bean.getCompany());
            pstmt.setString(3, bean.getImage01());
            pstmt.setString(4, bean.getImage02());
            pstmt.setString(5, bean.getImage03());
            pstmt.setInt(6, bean.getStock());
            pstmt.setInt(7, bean.getPrice());
            pstmt.setString(8, bean.getCategory());
            pstmt.setString(9, bean.getContents());


            pstmt.setInt(10, bean.getPoint());
            pstmt.setString(11, bean.getInputdate());

            cnt = pstmt.executeUpdate();

            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                ;
                if (conn != null) {
                    conn.close();
                }
                ;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return cnt;
    }

    public int deleteDate(int pnum) {
        System.out.println("기본 키 = " + pnum);
        int cnt = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = " delete from products ";
        sql += " where pnum = ? ";


        try {
            conn = super.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, pnum);


            cnt = pstmt.executeUpdate();

            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                ;
                if (conn != null) {
                    conn.close();
                }
                ;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return cnt;
    }

    public int getTotalCount(String category) {
        int totalCount = 0;

        String sql = "select count(*) as mycnt from products";

        boolean bool = category == null || category.equals("all");
        if (!bool) {
            sql += " where category = ?";
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Product bean = null;
        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);

            if (!bool) {
                pstmt.setString(1, category);
            }

            rs = pstmt.executeQuery();

            if (rs.next()) {
                totalCount = rs.getInt("mycnt");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return totalCount;
    }

    public List<Product> getPaginationData(Paging pageInfo) {
        Connection conn = null;
        String sql = " select pnum, name, company, image01, image02, image03, stock,price,category,contents,point,inputdate ";
        sql += " from (";
        sql += " select pnum, name, company, image01, image02, image03, stock,price,category,contents,point,inputdate, ";
        sql += " rank() over(order by pnum desc) as ranking ";
        sql += " from products ";

        // mode가 'all'이 아니면 where 절이 추가로 필요합니다.
        String mode = pageInfo.getMode();
        boolean bool = mode.equals(null) || mode.equals("null") || mode.equals("") || mode.equals("all");

        if (!bool) {
            sql += " where category = ? ";
        }

        sql += " ) ";
        sql += " where ranking between ? and ? ";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Product> allData = new ArrayList<>();
        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);

            if (!bool) {
                pstmt.setString(1, mode);
                pstmt.setInt(2, pageInfo.getBeginRow());
                pstmt.setInt(3, pageInfo.getEndRow());
            } else {
                pstmt.setInt(1, pageInfo.getBeginRow());
                pstmt.setInt(2, pageInfo.getEndRow());
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Product bean = this.makeBean(rs);
                allData.add(bean);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return allData;
    }
}




