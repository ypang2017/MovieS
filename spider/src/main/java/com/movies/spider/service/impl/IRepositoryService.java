package com.movies.spider.service.impl;

/**
 * Url store repository interface
 */
public interface IRepositoryService {

  public String poll();

  public void addHighLevel(String url);

  public void addLowLevel(String url);
}