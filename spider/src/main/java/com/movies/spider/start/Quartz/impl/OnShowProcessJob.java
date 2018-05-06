package com.movies.spider.start.Quartz.impl;

import com.movies.spider.service.impl.*;
import com.movies.spider.start.Quartz.IProcessJob;
import com.movies.spider.start.StartOnShowSpider;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class OnShowProcessJob implements IProcessJob {
  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    ApplicationContext context = new ClassPathXmlApplicationContext("onShowBeans.xml");
    StartOnShowSpider start = new StartOnShowSpider();
    start.setDownLoadService((HttpClientDownLoadService) context.getBean("httpClientDownLoadService"));
    start.setProcessService((MovieOnProcessService) context.getBean("movieOnProcessService"));
//    start.storeService = (ConsoleStoreService)context.getBean("consoleStoreService");
//    start.storeService = (HBaseStoreService) context.getBean("hBaseStoreService");
    start.setStoreService((MysqlStoreService) context.getBean("mysqlStoreService"));
    start.setiRepositoryService((QueueRepositoryService)context.getBean("queueRepositoryService"));
//    start.repositoryService = (RedisRepositoryService) context.getBean("redisRepositoryService");

    String url = "https://movie.douban.com/";
    start.getUrlQueue().add(url);
    start.getiRepositoryService().addHighLevel(url);
    start.startSpider();
  }
}
