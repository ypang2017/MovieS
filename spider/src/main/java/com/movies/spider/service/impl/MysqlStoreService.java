package com.movies.spider.service.impl;

import com.movies.spider.dao.IDao;
import com.movies.spider.entity.Page;
import com.movies.spider.service.IStoreService;
import com.movies.spider.utils.MysqlUtil;

import java.sql.Date;
import java.util.List;

/**
 * Use mysql to store the movies information
 */
public class MysqlStoreService implements IStoreService {
  private MysqlUtil mysqlUtil;
  private IDao mysqlDao;

  public void setMysqlDao(IDao mysqlDao) {
    this.mysqlDao = mysqlDao;
  }

  public void setMysqlUtil(MysqlUtil mysqlUtil) {
    this.mysqlUtil = mysqlUtil;
  }

  @Override
  public void store(Page page) {
    mysqlDao.store(page);
  }

  /**
   * Search all movies records in mysql
   *
   * @return
   */
  public List<Page> searchAll() {
    return mysqlDao.searchAll();
  }

  /**
   * Search one movie record in mysql
   *
   * @param sql
   * @return
   */
  public Page searchOneRecord(String sql) {
    return mysqlDao.searchOneRecord(sql);
  }

  public int searchOneValue(String sql, String id) {
    return mysqlDao.searchOneValue(sql, id);
  }

  public String searchOneValue(String sql, Date excuteDay) {
    return mysqlDao.searchOneValue(sql, excuteDay);
  }

  /**
   * Is movie exist or not,dudged by moiveId
   *
   * @param id
   * @return
   */
  public boolean isExist(String id) {
    return mysqlDao.isExist(id);
  }

  /**
   * Is movies infor exist or not,dudged by excuteDay
   *
   * @param date
   * @return
   */
  public boolean isExist(Date date) {
    return mysqlDao.isExist(date);
  }
}