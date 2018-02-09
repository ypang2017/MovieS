package com.movies.spider.service;

import com.movies.spider.entity.Page;
import com.movies.spider.service.impl.IStoreService;
import com.movies.spider.utils.MysqlUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Use mysql to store the movies information
 */
public class MysqlStoreService implements IStoreService {
  MysqlUtil mysqlUtil = new MysqlUtil();

  @Override
  public void store(Page page) {
    String sql = "insert into movieinfo (movieName,movieScore,scoreNum," +
            "increaseNum,excuteTime,excuteDay,url,movieId) values(?,?,?,?,?,?,?,?)";
    String[] str = new String[]{page.getMovieName(), String.valueOf(page.getMovieScore()),
            String.valueOf(page.getScoreNum()), String.valueOf(page.getIncreaseNum()),
            String.valueOf(page.getExcuteTime()), String.valueOf(page.getExcuteDay()),
            page.getUrl(), String.valueOf(page.getMovieId())};
    mysqlUtil.addU(sql, str);
  }

  /**
   * Search all movies records in mysql
   *
   * @return
   */
  public List<Page> searchAll() {
    String sql = "select * from movieinfo";
    List<Page> list = new ArrayList<Page>();

    ResultSet res = mysqlUtil.search(sql, null);
    try {
      while (res.next()) {
        String movieName = res.getString("movieName");
        float movieScore = res.getFloat("movieScore");
        int soreNum = res.getInt("scoreNum");
        int increaseNum = res.getInt("increaseNum");
        long excuteTime = res.getLong("excuteTime");
        Date excuteDay = res.getDate("excuteDay");
        String url = res.getString("url");
        String movieId = res.getString("movieId");
        list.add(new Page(movieName, movieScore, soreNum, increaseNum, excuteTime, excuteDay, url, movieId));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  /**
   * Search one movie record in mysql
   * @param sql
   * @return
   */
  public Page searchOneRecord(String sql) {
    Page page = null;
    ResultSet res = mysqlUtil.search(sql, null);
    try {
      while (res.next()) {
        String movieName = res.getString("movieName");
        float movieScore = res.getFloat("movieScore");
        int soreNum = res.getInt("scoreNum");
        int increaseNum = res.getInt("increaseNum");
        long excuteTime = res.getLong("excuteTime");
        Date excuteDay = res.getDate("excuteDay");
        String url = res.getString("url");
        String movieId = res.getString("movieId");
        page = new Page(movieName, movieScore, soreNum, increaseNum, excuteTime, excuteDay, url, movieId);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return page;
  }

  public int searchOneValue(String sql, String id) {
    int result = 0;
    ResultSet res = mysqlUtil.search(sql, new String[]{id});
    try {
      while (res.next()) {
        result = res.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Is movie exist or not
   *
   * @param id
   * @return
   */
  public boolean isExist(String id) {
    String sql = "select * from movieinfo where movieId=?";
    try {
      ResultSet rs = mysqlUtil.search(sql, new String[]{id});
      if (rs.next()) {
        return true;
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return false;
  }
}
