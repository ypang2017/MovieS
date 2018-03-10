package com.movies.spider.service.impl;

import com.movies.spider.entity.Page;
import com.movies.spider.service.IDownLoadService;
import com.movies.spider.utils.PageDownLoadUtil;

/**
 * HttpClient page downloads implementation class
 */
public class HttpClientDownLoadService implements IDownLoadService{

  public Page downLoad(String url) {
    Page page = new Page();
    page.setContent(PageDownLoadUtil.getPageContent(url));
    page.setUrl(url);
    return page;
  }
}
