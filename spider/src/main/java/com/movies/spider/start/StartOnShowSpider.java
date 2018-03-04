package com.movies.spider.start;

import com.movies.spider.entity.Page;
import com.movies.spider.service.*;
import com.movies.spider.service.impl.IRepositoryService;
import com.movies.spider.service.impl.IStoreService;
import com.movies.spider.utils.LoadPropertyUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is an entrance of on show movies on Home Page
 */
public class StartOnShowSpider {
  private HttpClientDownLoadService downLoadService;
  private MovieOnProcessService processService;
  private IStoreService storeService;
  private Queue<String> urlQueue = new ConcurrentLinkedQueue<String>();
  private ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(Integer.parseInt(LoadPropertyUtil.getCommon("threadNum")));
  private IRepositoryService repositoryService;

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

  public Queue<String> getUrlQueue() {
    return urlQueue;
  }

  public void setUrlQueue(Queue<String> urlQueue) {
    this.urlQueue = urlQueue;
  }

  public IRepositoryService getiRepositoryService() {
    return repositoryService;
  }

  public void setiRepositoryService(IRepositoryService iRepositoryService) {
    this.repositoryService = iRepositoryService;
  }

  /**
   * Page download
   *
   * @param url
   * @return
   */
  public Page downloadPage(String url) {
    return this.downLoadService.downLoad(url);
  }

  /**
   * Page parse
   *
   * @param page
   */
  public void processPage(Page page) {
    this.processService.process(page);
  }

  /**
   * Page info store
   *
   * @return
   */
  public void storePageInfo(Page page) {
    this.storeService.store(page);
  }

  public static void main(String[] args) {
    String tableName = "onshowmovie";
    StartOnShowSpider start = new StartOnShowSpider();
    start.setDownLoadService(new HttpClientDownLoadService());
    start.setProcessService(new MovieOnProcessService());
//    start.storeService = new ConsoleStoreService();
//    start.storeService = new HBaseStoreService();
    start.storeService = new MysqlStoreService(tableName);
    start.repositoryService = new RedisRepositoryService();

    String url = "https://movie.douban.com/";
//    start.urlQueue.add(url);
    start.repositoryService.addHighLevel(url);
    start.startSpider();
  }

  /**
   * Start a entrance for spider
   */
  public void startSpider() {
    while (true) {
//      //Poll a url from urlQueue to parse
//      final String url = urlQueue.poll();

      //Poll a url from repository to parse
      final String url = repositoryService.poll();

      if (StringUtils.isNotBlank(url)) {
        newFixedThreadPool.execute(new Runnable() {
          @Override
          public void run() {
            System.out.println("Current excuting thread is:" + Thread.currentThread().getId());
            //Page download
            Page page = StartOnShowSpider.this.downloadPage(url);
            //Page parse
            StartOnShowSpider.this.processPage(page);
            if (url.equals("https://movie.douban.com/")) {
              //Add the on show movies's urls to the urlQueue
              Set<String> onShowUrls = page.getUrlSet();
              for (String eachUrl : onShowUrls) {
//                urlQueue.add(eachUrl);
                repositoryService.addLowLevel(eachUrl);
              }
            } else {
              //Store the on show movies's information
              if (url.endsWith("from=showing")) {
                //Page store
                StartOnShowSpider.this.storePageInfo(page);
                try {
                  Thread.currentThread().sleep(Long.parseLong(LoadPropertyUtil.getCommon("million_5")));
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }
            }
          }
        });
      } else {
        System.out.println("All URLs in the queue is parse, please wait");
        try {
          Thread.currentThread().sleep(Long.parseLong(LoadPropertyUtil.getCommon("million_10")));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
