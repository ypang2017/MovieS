package com.movies.spider.start;

import com.movies.spider.entity.Page;
import com.movies.spider.service.IRepositoryService;
import com.movies.spider.service.IStoreService;
import com.movies.spider.service.impl.*;
import com.movies.spider.utils.LoadPropertyUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is an entrance of Top250 movies on Top250 Page
 */
public class StartTopMovieSpider {
  @Resource(name = "httpClientDownLoadService")
  private HttpClientDownLoadService downLoadService;

  @Resource(name = "movieTop250ProcessService")
  private MovieTop250ProcessService processService;

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

  public MovieTop250ProcessService getProcessService() {
    return processService;
  }

  public void setProcessService(MovieTop250ProcessService processService) {
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
    ApplicationContext context = new ClassPathXmlApplicationContext("topMovieBean.xml");
    StartTopMovieSpider start = new StartTopMovieSpider();
    start.setDownLoadService((HttpClientDownLoadService) context.getBean("httpClientDownLoadService"));
    start.setProcessService((MovieTop250ProcessService) context.getBean("movieTop250ProcessService"));
//    start.storeService = new ConsoleStoreService();
//    start.storeService = new HBaseStoreService();
    start.storeService = (MysqlStoreService) context.getBean("mysqlStoreService");
//    start.repositoryService = new RedisRepositoryService();
    start.repositoryService = (QueueRepositoryService) context.getBean("queueRepositoryService");

    String url = null;
    for (int i = 0; i < 10; i++) {
      url = "https://movie.douban.com/top250" + "?start=" + i*25 + "&filter=";
      //    start.urlQueue.add(url);
      start.repositoryService.addHighLevel(url);
    }

    start.startSpider();
  }

  /**
   * Start a entrance for spider
   */
  public void startSpider() {
    while (true) {
////      Poll a url from urlQueue to parse
//      final String url = urlQueue.poll();

      //Poll a url from repository to parse
      final String url = repositoryService.poll();

      if (StringUtils.isNotBlank(url)) {
        newFixedThreadPool.execute(new Runnable() {
          @Override
          public void run() {
            System.out.println("Current excuting thread is:" + Thread.currentThread().getId());
            //Page download
            Page page = StartTopMovieSpider.this.downloadPage(url);
            //Page parse
            StartTopMovieSpider.this.processPage(page);
            if (url.startsWith("https://movie.douban.com/top250")) {
              Set<String> topMovieUrls = page.getUrlSet();
              for (String eachUrl : topMovieUrls) {
//                urlQueue.add(eachUrl);
                repositoryService.addLowLevel(eachUrl);
              }
            } else {
              //Store the top250 movies's information
              //Page store
              StartTopMovieSpider.this.storePageInfo(page);
              try {
                Thread.currentThread().sleep(Long.parseLong(LoadPropertyUtil.getCommon("million_5")));
              } catch (InterruptedException e) {
                e.printStackTrace();
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
