package com.movies.spider.service;

import com.movies.spider.entity.Page;
import com.movies.spider.service.impl.IProcessService;
import com.movies.spider.utils.LoadPropertyUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.sql.Date;

/**
 * The showing movie content parse class
 */
public class MovieOnProcessService implements IProcessService {

  public void process(Page page) {

    Document doc = Jsoup.parse(page.getContext());
    LoadPropertyUtil elementUtil = new LoadPropertyUtil();

    //Selector, select feature information
    Elements nameElement = doc.select(LoadPropertyUtil.getOnShow("nameElement"));// name information
    Elements scoreElement = doc.select(LoadPropertyUtil.getOnShow("scoreElement"));// score information
    Elements numberElement = doc.select(LoadPropertyUtil.getOnShow("numberElement"));// scorenumber information


    //parse the movie information from the page context
    String name = nameElement.text();
    float score = Float.parseFloat(scoreElement.text());
    int number = Integer.parseInt(numberElement.text());

    //set the movie information name,score,scorenumber,time
    long processTime = System.currentTimeMillis();
    Date processDate = new Date(processTime);
    page.setMovieName(name);
    page.setMovieScore(score);
    page.setScoreNum(number);
    page.setExcuteTime(processTime);
    page.setExcuteDay(processDate);

    System.out.println("movie name:" + page.getMovieName());
    System.out.println("movie score:" + page.getMovieScore());
    System.out.println("score number:" + page.getScoreNum());
    System.out.println("excute time:" + page.getExcuteTime());
    System.out.println("excute date:" + page.getExcuteDay());
  }
}
