package com.movies.spider.service;

import com.movies.spider.entity.Page;
import com.movies.spider.service.impl.HttpClientDownLoadService;
import com.movies.spider.service.impl.MovieOnProcessService;
import org.junit.Test;

public class TestMovieOnProcessService {

  @Test
  public void testProcess(){
    HttpClientDownLoadService downLoadService = new HttpClientDownLoadService();
    Page page = downLoadService.downLoad("https://movie.douban.com/");
    MovieOnProcessService movieOnProcessService = new MovieOnProcessService();
    movieOnProcessService.process(page);
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
