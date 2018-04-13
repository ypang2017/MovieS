package com.movies.spider.service.impl;

import com.movies.spider.entity.Page;
import com.movies.spider.service.IProcessService;
import com.movies.spider.utils.LoadPropertyUtil;
import com.movies.spider.utils.RegexUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Date;
import java.util.regex.Pattern;

/**
 * The top250 movies content parse class
 */
public class MovieTop250ProcessService implements IProcessService {

  public void process(Page page) {

    Document doc = Jsoup.parse(page.getContent());

    if (page.getUrl().startsWith("https://movie.douban.com/top250")) {
      Elements links = doc.select("a[href]");

      for (Element link : links) {
        Pattern pattern = Pattern.compile(LoadPropertyUtil.getTopMovie("urlRegex"));
        page.addUrl(RegexUtil.getPageInfoByRegex(link.toString(), pattern, 0));
      }
    } else {
      parseDetail(page, doc);
    }
  }

  /**
   * Parse the detail moive page information
   */
  public static void parseDetail(Page page, Document doc) {
    String tableName = "topmovie";
    MysqlStoreService iStoreService = null;
    //Selector, select feature information
    Elements nameElement = doc.select(LoadPropertyUtil.getTopMovie("nameElement"));// name information
    Elements scoreElement = doc.select(LoadPropertyUtil.getTopMovie("scoreElement"));// score information
    Elements numberElement = doc.select(LoadPropertyUtil.getTopMovie("numberElement"));// scorenumber information
    Elements rankElement = doc.select(LoadPropertyUtil.getTopMovie("rankElement"));//movierank information
    //parse the movie information from the page context
    String name = nameElement.text();
    float score;
    int number;
    int rank = Integer.parseInt(RegexUtil.getPageInfoByRegex(rankElement.text(), Pattern.compile("[\\d]+"), 0));
//    int rank = Integer.parseInt("1");
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
    page.setMovieRank(rank);

    //set the movie id
    Pattern pattern = Pattern.compile(LoadPropertyUtil.getTopMovie("idRegex"));
    String movieId = "Top250_" + RegexUtil.getPageInfoByRegex(page.getContent(), pattern, 1);
    page.setMovieId(movieId);

    //set the increase number，
    iStoreService = new MysqlStoreService();
    if (iStoreService.isExist(movieId)) {
      String sql = LoadPropertyUtil.getTopMovie("maxScoreNumSql");
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
