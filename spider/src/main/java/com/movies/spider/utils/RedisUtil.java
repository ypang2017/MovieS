package com.movies.spider.utils;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * The util to use redis
 */
public class RedisUtil {

  //The keys in redis set
  public static String highkey = "spider.highlevel";
  public static String lowkey = "spider.lowlevel";

  public static String starturl = "start.url";
  JedisPool jedisPool = null;

  public RedisUtil() {
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxIdle(10);
    poolConfig.setMaxTotal(100);
    poolConfig.setMaxWaitMillis(10000);
    poolConfig.setTestOnBorrow(true);
    jedisPool = new JedisPool(poolConfig, "192.168.23.128", 6379);
  }

  /**
   * Search
   *
   * @param key
   * @param start
   * @param end
   * @return
   */
  public List<String> lrange(String key, int start, int end) {
    Jedis resource = jedisPool.getResource();

    List<String> list = resource.lrange(key, start, end);
    jedisPool.returnResourceObject(resource);
    return list;

  }

  /**
   * Add list
   *
   * @param Key
   * @param url
   */
  public void add(String Key, String url) {
    Jedis resource = jedisPool.getResource();
    resource.lpush(Key, url);
    jedisPool.returnResourceObject(resource);
  }


  /**
   * Get value
   *
   * @param key
   * @return
   */
  public String poll(String key) {
    Jedis resource = jedisPool.getResource();
    String result = resource.rpop(key);
    jedisPool.returnResourceObject(resource);
    return result;
  }

  /**
   * Add set
   *
   * @param Key
   * @param value
   */
  public void addSet(String Key, String value) {
    Jedis resource = jedisPool.getResource();
    resource.sadd(Key, value);
    jedisPool.returnResourceObject(resource);
  }

  /**
   * Random get set value
   *
   * @param key
   */
  public String getSet(String key) {
    Jedis resource = jedisPool.getResource();
    String value = resource.srandmember(key);
    jedisPool.returnResourceObject(resource);
    return value;
  }

  /**
   * Delete Set
   *
   * @param key
   * @param value
   */
  public void deleteSet(String key, String value) {
    Jedis resource = jedisPool.getResource();
    resource.srem(key, value);
    jedisPool.returnResourceObject(resource);
  }

//  /**
//   * Test
//   * @param args
//   */
//  public static void main(String[] args) {
//    RedisUtil redisUtil = new RedisUtil();
//    String url = "https://movie.douban.com/";
//    redisUtil.add(highkey, url);
//  }
}