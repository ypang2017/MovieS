package com.movies.spider.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestLoadPropertyUtil {
  Map<String,String> map = new HashMap<String, String>();

  @Test
  public void testLoadProperty(){
    map.put("nameElement", "#content > h1:nth-child(2) > span:nth-child(1)");
    map.put("scoreElement", "strong.ll");
    map.put("numberElement", ".rating_people > span:nth-child(1)");
    map.put("nextUrlElement", "a.btn-next:nth-child(1)");
    Assert.assertEquals(map.get("nameElement"), LoadPropertyUtil.getOnShow("nameElement"));
    Assert.assertEquals(map.get("scoreElement"), LoadPropertyUtil.getOnShow("scoreElement"));
    Assert.assertEquals(map.get("numberElement"), LoadPropertyUtil.getOnShow("numberElement"));
    Assert.assertEquals(map.get("nextUrlElement"), LoadPropertyUtil.getOnShow("nextUrlElement"));
  }
}
