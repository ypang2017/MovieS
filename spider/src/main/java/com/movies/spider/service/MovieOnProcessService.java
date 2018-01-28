package com.movies.spider.service;

import com.movies.spider.entity.Page;
import com.movies.spider.service.impl.IProcessService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * The showing movie content parse class
 */
public class MovieOnProcessService implements IProcessService {

  public void process(Page page) {

    Document doc = Jsoup.parse(page.getContext());

    //Selector, select feature information
    Elements nameElement = doc.select("#content > h1:nth-child(2) > span:nth-child(1)");// name information
    Elements scoreElement = doc.select("strong.ll");// score information
    Elements numberElement = doc.select(".rating_people > span:nth-child(1)");// scorenumber information

    //parse the movie information from the page context
    String name = nameElement.text();
    float score = Float.parseFloat(scoreElement.text());
    int number = Integer.parseInt(numberElement.text());

    //set the movie information name,score,scorenumber
    page.setMovieName(name);
    page.setMovieScore(score);
    page.setScoreNum(number);
  }
}
