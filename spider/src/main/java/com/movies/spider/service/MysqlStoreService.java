package com.movies.spider.service;

import com.movies.spider.entity.Page;
import com.movies.spider.service.impl.IStoreService;
import com.movies.spider.utils.MysqlUtil;

/**
 *  Use mysql to store the movies information
 */
public class MysqlStoreService implements IStoreService {
  MysqlUtil mysqlUtil = new MysqlUtil();
  
  @Override
  public void store(Page page) {
    String sql="insert into movieinfo (movieName,movieScore,scoreNum," +
      "increaseNum,excuteTime,excuteDay,url,movieId) values(?,?,?,?,?,?,?,?)";
    String[] str=new String[]{page.getMovieName(), String.valueOf(page.getMovieScore()), 
                              String.valueOf(page.getScoreNum()), String.valueOf(page.getIncreaseNum()), 
                              String.valueOf(page.getExcuteTime()), String.valueOf(page.getExcuteDay()),
                              page.getUrl(), String.valueOf(page.getMovieId())};
    mysqlUtil.addU(sql, str);
  }
}
