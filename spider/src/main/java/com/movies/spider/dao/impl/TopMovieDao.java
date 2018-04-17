package com.movies.spider.dao.impl;

import com.movies.spider.dao.IDao;
import com.movies.spider.entity.Page;
import com.movies.spider.utils.LoadPropertyUtil;
import com.movies.spider.utils.MysqlUtil;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component(value = "topMovieDao")
public class TopMovieDao implements IDao {
  MysqlUtil mysqlUtil = new MysqlUtil();

  public void setMysqlUtil(MysqlUtil mysqlUtil) {
    this.mysqlUtil = mysqlUtil;
  }

  @Override
  public void store(Page page) {
    String sql = LoadPropertyUtil.getTopMovie("storeSql");
    String[] str = new String[]{page.getMovieName(), String.valueOf(page.getMovieScore()),
            String.valueOf(page.getScoreNum()), String.valueOf(page.getIncreaseNum()),
            String.valueOf(page.getExcuteTime()), String.valueOf(page.getExcuteDay()),
            page.getUrl(), String.valueOf(page.getMovieId()), String.valueOf(page.getMovieRank())};
    mysqlUtil.addU(sql, str);
  }

  @Override
  public List<Page> searchAll() {
    String sql = LoadPropertyUtil.getTopMovie("searchAllSql");
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
        int movieRank = res.getInt("movieRank");
        list.add(new Page(movieName, movieScore, soreNum, increaseNum, excuteTime, excuteDay, url, movieId, movieRank));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  @Override
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
        int movieRank = res.getInt("movieRank");
        page = new Page(movieName, movieScore, soreNum, increaseNum, excuteTime, excuteDay, url, movieId, movieRank);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return page;
  }

  @Override
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

  @Override
  public String searchOneValue(String sql, Date excuteDay) {
    String result = null;
    ResultSet res = mysqlUtil.search(sql, new String[]{excuteDay.toString()});
    try {
      while (res.next()) {
        result = res.getString(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public boolean isExist(String id) {
    String sql = LoadPropertyUtil.getTopMovie("idExistSql");
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

  @Override
  public boolean isExist(Date date) {
    String sql = LoadPropertyUtil.getTopMovie("excuteDayExistSql");
    try {
      ResultSet rs = mysqlUtil.search(sql, new String[]{date.toString()});
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
