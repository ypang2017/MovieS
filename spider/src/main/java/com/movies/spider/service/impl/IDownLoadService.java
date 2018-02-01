package com.movies.spider.service.impl;

import com.movies.spider.entity.Page;

/**
 * Download the url page information
 */
public interface IDownLoadService {
  public Page downLoad(String url);
}
