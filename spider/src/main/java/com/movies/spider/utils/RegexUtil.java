package com.movies.spider.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Pattern regex util
 */
public class RegexUtil {

  public static String getPageInfoByRegex(String content, Pattern pattern, int groupNo) {
    Matcher matcher = pattern.matcher(content);
    if (matcher.find()) {
      return matcher.group(groupNo).trim();
    }
    return "";
  }
}
