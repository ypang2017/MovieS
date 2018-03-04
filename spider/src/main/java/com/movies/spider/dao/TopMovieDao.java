package com.movies.spider.dao;

import com.movies.spider.dao.impl.IDao;
import com.movies.spider.entity.Page;

import java.sql.Date;
import java.util.List;

public class TopMovieDao implements IDao {
  @Override
  public void store(Page page) {

  }

  @Override
  public List<Page> searchAll() {
    return null;
  }

  @Override
  public Page searchOneRecord(String sql) {
    return null;
  }

  @Override
  public int searchOneValue(String sql, String id) {
    return 0;
  }

  @Override
  public String searchOneValue(String sql, Date excuteDay) {
    return null;
  }

  @Override
  public boolean isExist(String id) {
    return false;
  }

  @Override
  public boolean isExist(Date date) {
    return false;
  }
}
