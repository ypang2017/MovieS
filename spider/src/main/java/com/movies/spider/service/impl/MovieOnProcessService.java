package com.movies.spider.service.impl;

import com.movies.spider.entity.Page;
import com.movies.spider.service.IProcessService;
import com.movies.spider.utils.LoadPropertyUtil;
import com.movies.spider.utils.RegexUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Date;
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
          page.addUrl(RegexUtil.getPageInfoByRegex(urls.toString(), pattern, 0));
        }
      }
    } else {
      parseDetail(page, doc);
    }
  }

  /**
   * Parse the detail moive page information
   */
  public static void parseDetail(Page page, Document doc) {
    MysqlStoreService iStoreService = null;

    //Selector, select feature information
    Elements nameElement = doc.select(LoadPropertyUtil.getOnShow("nameElement"));// name information
    Elements scoreElement = doc.select(LoadPropertyUtil.getOnShow("scoreElement"));// score information
    Elements numberElement = doc.select(LoadPropertyUtil.getOnShow("numberElement"));// scorenumber information
    //parse the movie information from the page context
    String name = nameElement.text();
    float score;
    int number;
    // Judge the movie score is null or not
    if (scoreElement.hasText()) {
      score = Float.parseFloat(scoreElement.text());
      number = Integer.parseInt(numberElement.text());
    } else {
      score = 0.0f;
      number = 0;
    }

    //set the movie information name,score,scorenumber,time
    long processTime = System.currentTimeMillis();
    Date processDate = new Date(processTime);
    page.setMovieName(name);
    page.setMovieScore(score);
    page.setScoreNum(number);
    page.setExcuteTime(processTime);
    page.setExcuteDay(processDate);
//    page.setIncreaseNum(0);

    //set the movie id
    Pattern pattern = Pattern.compile(LoadPropertyUtil.getOnShow("idRegex"));
    String movieId = "OnShow_" + RegexUtil.getPageInfoByRegex(page.getContent(), pattern, 1);
    page.setMovieId(movieId);

    //set the increase number，
    ApplicationContext context = new ClassPathXmlApplicationContext("onShowBeans.xml");
    iStoreService = (MysqlStoreService)context.getBean("mysqlStoreService");
    if (iStoreService.isExist(movieId)) {
      String sql = LoadPropertyUtil.getOnShow("maxScoreNumSql");
      int recentlyRecord = iStoreService.searchOneValue(sql, movieId);

      //get the up-to-date page info from mysql,
      // the increase number is number subtract the recently biggest scoreNum record
      page.setIncreaseNum(number - recentlyRecord);
    } else {
      //the moiveId is not exist in DB,set "0" as the first increase number
      page.setIncreaseNum(0);
    }
  }
}
