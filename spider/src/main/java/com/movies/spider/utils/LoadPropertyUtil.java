package com.movies.spider.utils;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LoadPropertyUtil {

  //read the configuration of on show movies
  public static String getOnShow(String key){
    String value = "";
    Locale locale = Locale.getDefault();
    try {
      ResourceBundle localResource = ResourceBundle.getBundle("onshow",
              locale);
      value = localResource.getString(key);
    } catch (MissingResourceException mre) {
      value = "";
    }
    return value;
  }

  public static String getCommon(String key){
    String value = "";
    Locale locale = Locale.getDefault();
    try {
      ResourceBundle localResource = ResourceBundle.getBundle("common",
              locale);
      value = localResource.getString(key);
    } catch (MissingResourceException mre) {
      value = "";
    }
    return value;
  }
//  public static void main(String[] args) {
//    System.out.println(getOnShow("nameElement"));
//    System.out.println(getOnShow("scoreElement"));
//    System.out.println(getOnShow("numberElement"));
//    System.out.println(getCommon("threadNum"));
//  }
}