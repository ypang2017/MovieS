package com.movies.spider.start.Quartz;

import com.movies.spider.service.impl.HttpClientDownLoadService;
import com.movies.spider.service.impl.MovieOnProcessService;
import com.movies.spider.service.impl.MysqlStoreService;
import com.movies.spider.service.impl.RedisRepositoryService;
import com.movies.spider.start.StartOnShowSpider;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ProcessJob implements Job {
  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    StartOnShowSpider start = new StartOnShowSpider();
    start.setDownLoadService(new HttpClientDownLoadService());
    start.setProcessService(new MovieOnProcessService());
//    start.storeService = new ConsoleStoreService();
//    start.storeService = new HBaseStoreService();
    String tableName = "onshowmovie";
    start.setStoreService(new MysqlStoreService(tableName));
    start.setiRepositoryService(new RedisRepositoryService());

    String url = "https://movie.douban.com/";
//    start.getUrlQueue().add(url);
    start.getiRepositoryService().addHighLevel(url);
    start.startSpider();
  }
}
