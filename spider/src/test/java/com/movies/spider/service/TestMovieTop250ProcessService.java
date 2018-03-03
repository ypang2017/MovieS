package com.movies.spider.service;

import com.movies.spider.entity.Page;
import org.junit.Test;

public class TestMovieTop250ProcessService {

  @Test
  public void testProcess(){
    HttpClientDownLoadService downLoadService = new HttpClientDownLoadService();
    Page page = downLoadService.downLoad("https://movie.douban.com/top250");
    MovieTop250ProcessService movieTop250ProcessService = new MovieTop250ProcessService();
    movieTop250ProcessService.process(page);
    System.out.println(page.getMovieName());
    System.out.println(page.getMovieScore());
    System.out.println(page.getScoreNum());
    System.out.println(page.getExcuteTime());
    System.out.println(page.getExcuteDay());
    System.out.println(page.getUrlSet().toString());
    System.out.println(page.getUrlSet().size());
//    System.out.println(page.getContent());
  }
}
