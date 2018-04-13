package com.movies.spider.service;

import com.movies.spider.entity.Page;
import com.movies.spider.service.impl.HttpClientDownLoadService;
import com.movies.spider.service.impl.MovieTop250ProcessService;
import org.junit.Test;

public class TestMovieTop250ProcessService {

  @Test
  public void testProcess(){
    HttpClientDownLoadService downLoadService = new HttpClientDownLoadService();
    Page page = downLoadService.downLoad("https://movie.douban.com/top250");
//    Page page = downLoadService.downLoad("https://movie.douban.com/subject/1291546/");//《霸王别姬》
//    Page page = downLoadService.downLoad("https://movie.douban.com/subject/5912992/");//《熔炉》
    MovieTop250ProcessService movieTop250ProcessService = new MovieTop250ProcessService();
    movieTop250ProcessService.process(page);
    System.out.println(page.getMovieName());
    System.out.println(page.getMovieScore());
    System.out.println(page.getScoreNum());
    System.out.println(page.getExcuteTime());
    System.out.println(page.getExcuteDay());
    System.out.println(page.getUrlSet().toString());
    System.out.println(page.getMovieRank());
    System.out.println(page.getUrlSet().size());
//    System.out.println(page.getContent());
  }
}
