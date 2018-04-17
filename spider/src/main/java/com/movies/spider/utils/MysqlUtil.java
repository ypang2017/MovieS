package com.movies.spider.utils;

import org.springframework.stereotype.Component;

import java.sql.*;

@Component(value = "mysqlUtil")
public class MysqlUtil {
  static Connection con=null;
  static PreparedStatement pstmt=null;
  static ResultSet res=null;

  //Get the mysql connection
  public static Connection getConnection() {
    try {
      Class.forName(LoadPropertyUtil.getMysql("driverClass"));
      con = DriverManager.getConnection(LoadPropertyUtil.getMysql("url"),
        LoadPropertyUtil.getMysql("username"), LoadPropertyUtil.getMysql("password"));
    } catch (ClassNotFoundException e) {
      System.err.println("Load JDBC/ODBC driver failed");
      e.printStackTrace();
    } catch (SQLException e) {
      System.err.println("Cann't connect the database");
      e.printStackTrace();
    }
    return con;
  }

  //Close all sources
  public static void closeAll(ResultSet rs, Statement stmt, Connection conn) {
    try {
      if (rs != null)
        rs.close();
      if (stmt != null)
        stmt.close();
      if (conn != null)
        conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ResultSet search(String sql, String[] str) {
    con = MysqlUtil.getConnection();
    try {
      pstmt = con.prepareStatement(sql);
      if (str != null) {
        for (int i =0; i < str.length; i++) {
          pstmt.setString(i+1, str[i]);
        }
      }
      res = pstmt.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
//    }finally{
//      DBUtil.closeAll(res, pstmt, con);
    }
    return res;
  }

  public int addU(String sql, String[] str) {
    con = MysqlUtil.getConnection();
    int a = 0;
    try {
      PreparedStatement pst = con.prepareStatement(sql);
      if (str != null) {
        for (int i = 0; i < str.length; i++) {
          pst.setString(i + 1, str[i]);
        }
      }
      a = pst.executeUpdate();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return a;
  }
}
