package com.movies.spider.service.impl;

import com.movies.spider.entity.Page;
import com.movies.spider.service.IStoreService;
import com.movies.spider.utils.HBaseUtil;

import java.io.IOException;

/**
 * Use hbase to store the movies information
 */
public class HBaseStoreService implements IStoreService {
  HBaseUtil hbaseUtil = new HBaseUtil();

  public void store(Page page) {
    // TODO Auto-generated method stub
    String movieId = page.getMovieId();
    try {
      hbaseUtil.put(hbaseUtil.TABLE_NAME, movieId, hbaseUtil.COLUMNFAMILY_1, hbaseUtil.COLUMNFAMILY_1_URL, page.getUrl());
      hbaseUtil.put(hbaseUtil.TABLE_NAME, movieId, hbaseUtil.COLUMNFAMILY_1, hbaseUtil.COLUMNFAMILY_1_SCORE, page.getMovieScore() + "");
      hbaseUtil.put(hbaseUtil.TABLE_NAME, movieId, hbaseUtil.COLUMNFAMILY_1, hbaseUtil.COLUMNFAMILY_1_SCORENUM, page.getScoreNum() + "");
      hbaseUtil.put(hbaseUtil.TABLE_NAME, movieId, hbaseUtil.COLUMNFAMILY_1, hbaseUtil.COLUMNFAMILY_1_INCREASENUM_, page.getIncreaseNum() + "");
      hbaseUtil.put(hbaseUtil.TABLE_NAME, movieId, hbaseUtil.COLUMNFAMILY_1, hbaseUtil.COLUMNFAMILY_1_EXCUTETIME, page.getExcuteTime() + "");
      hbaseUtil.put(hbaseUtil.TABLE_NAME, movieId, hbaseUtil.COLUMNFAMILY_1, hbaseUtil.COLUMNFAMILY_1_EXCUTEDATE, page.getExcuteDay() + "");
      hbaseUtil.put(hbaseUtil.TABLE_NAME, movieId, hbaseUtil.COLUMNFAMILY_1, hbaseUtil.COLUMNFAMILY_1_MOVIENAME, page.getMovieName());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

//  public void deleteOneRecord(Page page) {
//    hbaseUtil.deleteOneRecord(hbaseUtil.TABLE_NAME, "1");
//  }
}