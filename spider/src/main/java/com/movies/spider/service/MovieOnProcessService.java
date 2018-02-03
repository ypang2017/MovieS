package com.movies.spider.service;

import com.movies.spider.entity.Page;
import com.movies.spider.service.impl.IProcessService;
import com.movies.spider.utils.LoadPropertyUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The showing movie content parse class
 */
public class MovieOnProcessService implements IProcessService {

  public void process(Page page) {

    Document doc = Jsoup.parse(page.getContent());

    if (page.getUrl().equals("https://movie.douban.com/")) {
      Elements divNode = doc.getElementsByClass("ui-slide-item");
      Elements liTags = divNode.select("li[class]");
      for (Element liTag : liTags) {
        Elements tag = liTag.getElementsByClass("title");
        for (Element urls : tag) {
          Pattern pattern = Pattern.compile(LoadPropertyUtil.getOnShow("urlRegex"));
          Matcher matcher = pattern.matcher(tag.toString());
          if (matcher.find()) {
            page.addUrl(matcher.group(0).trim() + "/?from=showing");
          }
        }
//        String test = tag.toString();
//        page.addUrl(test);
      }
    } else {
      parseDetail(page, doc);
    }
  }

  /**
   * Parse the detail moive page information
   */
  public static void parseDetail(Page page, Document doc) {
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
    page.setIncreaseNum(0);

    // set the movie id
    Pattern pattern = Pattern.compile(LoadPropertyUtil.getOnShow("idRegex"));
    Matcher matcher = pattern.matcher(page.getContent());
    if (matcher.find()) {
      page.setMovieId("OnShow_" + matcher.group(1).trim());
    } else {
      page.setMovieId("");
    }
  }
}
