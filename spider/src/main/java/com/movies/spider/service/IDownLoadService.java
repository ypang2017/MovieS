package com.movies.spider.service;

import com.movies.spider.entity.Page;

/**
 * Download the url page information
 */
public interface IDownLoadService {
  public Page downLoad(String url);
}
