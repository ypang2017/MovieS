package com.movies.spider.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class Page {
  private String content;//the url context about the movie;
  private String movieName;
  private float movieScore;
  private int scoreNum;
  private int increaseNum;//everyday increase score number
  private long excuteTime;//spider excute time
  private Date excuteDay;//spider excute day
  private String url;
  private String movieId;
  private int movieRank;
  private Set<String> urlSet = new HashSet<String>();

  public Page(String content, String movieName, float movieScore, int scoreNum, int increaseNum, 
              long excuteTime, Date excuteDay, String url, String movieId, int movieRank, Set<String> urlSet) {
    this.content = content;
    this.movieName = movieName;
    this.movieScore = movieScore;
    this.scoreNum = scoreNum;
    this.increaseNum = increaseNum;
    this.excuteTime = excuteTime;
    this.excuteDay = excuteDay;
    this.url = url;
    this.movieId = movieId;
    this.movieRank = movieRank;
    this.urlSet = urlSet;
  }

  public Page(String movieName, float movieScore, int scoreNum, int increaseNum, long excuteTime, Date excuteDay, String url, String movieId) {
    this(null, movieName, movieScore, scoreNum, increaseNum, excuteTime, excuteDay, url, movieId, 0, null);
  }

  public Page(String movieName, float movieScore, int scoreNum, int increaseNum, long excuteTime, Date excuteDay, String url, String movieId, int movieRank) {
    this(null, movieName, movieScore, scoreNum, increaseNum, excuteTime, excuteDay, url, movieId, movieRank, null);
  }

  public Page() {
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getMovieName() {
    return movieName;
  }

  public void setMovieName(String movieName) {
    this.movieName = movieName;
  }

  public float getMovieScore() {
    return movieScore;
  }

  public void setMovieScore(float movieScore) {
    this.movieScore = movieScore;
  }

  public int getScoreNum() {
    return scoreNum;
  }

  public void setScoreNum(int scoreNum) {
    this.scoreNum = scoreNum;
  }

  public int getIncreaseNum() {
    return increaseNum;
  }

  public void setIncreaseNum(int increaseNum) {
    this.increaseNum = increaseNum;
  }

  public long getExcuteTime() {
    return excuteTime;
  }

  public void setExcuteTime(long excuteTime) {
    this.excuteTime = excuteTime;
  }

  public Date getExcuteDay() {
    return excuteDay;
  }

  public void setExcuteDay(Date excuteDay) {
    this.excuteDay = excuteDay;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getMovieId() {
    return movieId;
  }

  public void setMovieId(String movieId) {
    this.movieId = movieId;
  }

  public int getMovieRank() {
    return movieRank;
  }

  public void setMovieRank(int movieRank) {
    this.movieRank = movieRank;
  }

  public Set<String> getUrlSet() {
    return urlSet;
  }

  public void addUrl(String url) {
    this.urlSet.add(url);
  }
}
