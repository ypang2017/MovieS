package com.movies.spider.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Utils to download the URL page content
 */
public class PageDownLoadUtil {
  private final static String USER_AGENT="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";

  public static String getPageContent(String url){
    HttpClientBuilder builder = HttpClientBuilder.create();
    CloseableHttpClient client = builder.build();
    HttpGet request = new HttpGet(url);
    String content = null;
    try {
      request.setHeader("User-Agent",USER_AGENT);
      HttpResponse response = client.execute(request);
      HttpEntity entity = response.getEntity();
      content = EntityUtils.toString(entity);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return content;
  }

  /**
   * test to download a page context
   * @param args
   */
  public static void main(String[] args) {
//    String url = "https://movie.douban.com/subject/5912992/";
    String url = "https://movie.douban.com/top250?start=225&filter=";
    PageDownLoadUtil util = new PageDownLoadUtil();
    System.out.println(util.getPageContent(url));
  }
}
