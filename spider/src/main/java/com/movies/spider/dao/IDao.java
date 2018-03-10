package com.movies.spider.dao;

import com.movies.spider.entity.Page;

import java.sql.Date;
import java.util.List;

public interface IDao {
  /**
   * Store the movie page info into DB
   *
   * @param page
   */
  public void store(Page page);

  /**
   * Search all movies records in DB
   *
   * @return
   */
  public List<Page> searchAll();

  /**
   * Search one movie record in DB
   *
   * @param sql
   * @return
   */
  public Page searchOneRecord(String sql);

  /**
   * Search one movie record in DB by id
   *
   * @param sql
   * @param id
   * @return
   */
  public int searchOneValue(String sql, String id);

  /**
   * Search one movie record in DB by date
   *
   * @param sql
   * @param excuteDay
   * @return
   */
  public String searchOneValue(String sql, Date excuteDay);

  /**
   * Is movie exist or not,dudged by moiveId
   *
   * @param id
   * @return
   */
  public boolean isExist(String id);

  /**
   * Is movies infor exist or not,dudged by excuteDay
   *
   * @param date
   * @return
   */
  public boolean isExist(Date date);
}
