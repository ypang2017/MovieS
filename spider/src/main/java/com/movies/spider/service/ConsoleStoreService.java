package com.movies.spider.service;

import com.movies.spider.entity.Page;
import com.movies.spider.service.impl.IStoreService;

/**
 * Use the console to show the storage information
 */
public class ConsoleStoreService implements IStoreService {
  public void store(Page page) {
    System.out.println(page.getMovieName());
    System.out.println(page.getMovieScore());
    System.out.println(page.getScoreNum());
    System.out.println(page.getIncreaseNum());
    System.out.println(page.getExcuteTime());
    System.out.println(page.getExcuteDay());
  }
}
