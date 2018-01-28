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

  public String getPageContent(String url){
    HttpClientBuilder builder = HttpClientBuilder.create();
    CloseableHttpClient client = builder.build();
    HttpGet request = new HttpGet(url);
    String content = null;
    try {
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
    String url = "https://movie.douban.com/";
    PageDownLoadUtil util = new PageDownLoadUtil();
    System.out.println(util.getPageContent(url));
  }
}
