package com.movies.spider.utils;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LoadPropertyUtil {

  //Read the configuration of on show movies
  public static String getOnShow(String key){
    return getValue("onshow",key);
  }
  //Read the common configuration
  public static String getCommon(String key){
    return getValue("common",key);
  }
  //Read mysql configuration
  public static String getMysql(String key) {
    return getValue("mysql",key);
  }
  //Get the configuration value through baseName and key
  public static String getValue(String baseName, String key) {
    String value = "";
    Locale locale = Locale.getDefault();
    try {
      ResourceBundle localResource = ResourceBundle.getBundle(baseName,
        locale);
      value = localResource.getString(key);
    } catch (MissingResourceException mre) {
      value = "";
    }
    return value;
  }
  
  public static void main(String[] args) {
//    System.out.println(getOnShow("nameElement"));
//    System.out.println(getOnShow("scoreElement"));
//    System.out.println(getOnShow("numberElement"));
//    System.out.println(getCommon("threadNum"));
//    System.out.println(getMysql("driverClass"));
  }
}