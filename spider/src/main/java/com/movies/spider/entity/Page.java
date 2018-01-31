package com.movies.spider.entity;

import java.sql.Date;

public class Page {
  private String context;//the url context about the movie;
  private String movieName;
  private float movieScore;
  private int scoreNum;
  private int increaseNum;//everyday increase score number
  private long excuteTime;//spider excute time
  private Date excuteDay;//spider excute day
  private String url;


  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
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
}
