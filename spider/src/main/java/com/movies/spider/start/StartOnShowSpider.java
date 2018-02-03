package com.movies.spider.start;

import com.movies.spider.entity.Page;
import com.movies.spider.service.ConsoleStoreService;
import com.movies.spider.service.HBaseStoreService;
import com.movies.spider.service.HttpClientDownLoadService;
import com.movies.spider.service.MovieOnProcessService;
import com.movies.spider.service.impl.IStoreService;

/**
 * This is an entrance of on show movies on Home Page
 */
public class StartOnShowSpider {
  private HttpClientDownLoadService downLoadService;
  private MovieOnProcessService processService;
  private IStoreService storeService;

  public HttpClientDownLoadService getDownLoadService() {
    return downLoadService;
  }

  public void setDownLoadService(HttpClientDownLoadService downLoadService) {
    this.downLoadService = downLoadService;
  }

  public MovieOnProcessService getProcessService() {
    return processService;
  }

  public void setProcessService(MovieOnProcessService processService) {
    this.processService = processService;
  }

  public IStoreService getStoreService() {
    return storeService;
  }

  public void setStoreService(IStoreService storeService) {
    this.storeService = storeService;
  }

  /**
   * Page download
   * @param url
   * @return
   */
  public Page downloadPage(String url){
    return this.downLoadService.downLoad(url);
  }

  /**
   * Page parse
   * @param page
   */
  public void processPage(Page page){
    this.processService.process(page);
  }

  /**
   * Page info store
   * @return
   */
  public void storePageInfo(Page page){
    this.storeService.store(page);
  }

  public static void main(String[] args) {
    StartOnShowSpider start = new StartOnShowSpider();
    start.setDownLoadService(new HttpClientDownLoadService());
    start.setProcessService(new MovieOnProcessService());
    start.storeService = new ConsoleStoreService();
//    start.storeService = new HBaseStoreService();

    String url = "https://movie.douban.com/subject/26942674/?from=showing";


    //Page download
    Page page = start.downloadPage(url);
    //Page parse
    start.processPage(page);
    //Page store
    start.storePageInfo(page);
  }
}
