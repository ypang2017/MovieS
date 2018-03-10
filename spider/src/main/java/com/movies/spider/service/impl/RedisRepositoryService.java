package com.movies.spider.service.impl;

import com.movies.spider.service.IRepositoryService;
import com.movies.spider.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;

/**
 * Redis url repository implement
 *
 */
public class RedisRepositoryService implements IRepositoryService {
  RedisUtil redisUtil = new RedisUtil();

  public String poll() {
    // TODO Auto-generated method stub
    String url = redisUtil.poll(RedisUtil.highkey);
    if (StringUtils.isBlank(url)) {
      url = redisUtil.poll(RedisUtil.lowkey);
    }
    return url;
  }

  public void addHighLevel(String url) {
    // TODO Auto-generated method stub
    redisUtil.add(RedisUtil.highkey, url);
  }

  public void addLowLevel(String url) {
    // TODO Auto-generated method stub
    redisUtil.add(RedisUtil.lowkey, url);
  }

}