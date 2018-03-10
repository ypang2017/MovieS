package com.movies.spider.service.impl;

import com.movies.spider.service.IRepositoryService;
import org.apache.commons.lang.StringUtils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class QueueRepositoryService implements IRepositoryService {
  //High level
  private Queue<String> highLevelQueue = new ConcurrentLinkedDeque<String>();
  //Low level
  private Queue<String> lowLevelQueue = new ConcurrentLinkedDeque<String>();

  public String poll() {
    // TODO Auto-generated method stub
    //Parse hihlevelqueue first
    String url = highLevelQueue.poll();
    if (StringUtils.isBlank(url)) {
      //Parse lowlevelqueue behind hihlevelqueue
      url = lowLevelQueue.poll();
    }
    return url;
  }

  public void addHighLevel(String url) {
    // TODO Auto-generated method stub
    this.highLevelQueue.add(url);
  }

  public void addLowLevel(String url) {
    // TODO Auto-generated method stub
    this.lowLevelQueue.add(url);
  }
}
