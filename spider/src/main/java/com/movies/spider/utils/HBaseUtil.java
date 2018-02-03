package com.movies.spider.utils;

import com.movies.spider.entity.Page;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


/**
 * HBase util
 */
public class HBaseUtil {

  /**
   * HBASE tablename
   */
  public static final String TABLE_NAME = "moviecount";
  /**
   * Cloumnfamily1
   */
  public static final String COLUMNFAMILY_1 = "movieinfo";
  /**
   * The cloumn in cloumnfamily1
   */
  public static final String COLUMNFAMILY_1_MOVIENAME = "moviename";
  public static final String COLUMNFAMILY_1_URL = "url";
  public static final String COLUMNFAMILY_1_SCORE = "score";
  public static final String COLUMNFAMILY_1_SCORENUM = "scorenumber";
  public static final String COLUMNFAMILY_1_INCREASENUM_ = "increasenumber";
  public static final String COLUMNFAMILY_1_EXCUTETIME = "excutetime";
  public static final String COLUMNFAMILY_1_EXCUTEDATE = "excutedate";

  HBaseAdmin admin = null;
  Configuration conf = null;

  /**
   * Construtor load configuration
   */
  public HBaseUtil() {
    conf = new Configuration();
    conf.set("hbase.zookeeper.quorum", "192.168.23.128:2181");
    conf.set("hbase.rootdir", "hdfs://192.168.23.128:9000/hbase");
    try {
      admin = new HBaseAdmin(conf);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws Exception {
    HBaseUtil hbase = new HBaseUtil();
    // create a table
    //hbase.createTable(TABLE_NAME, COLUMNFAMILY_1);
    // query all tables
    hbase.getALLTable();
  }

  /**
   * Use rowFilter
   *
   * @param tableName
   * @param reg
   * @throws Exception
   */
  public void getRowFilter(String tableName, String reg) throws Exception {
    HTable hTable = new HTable(conf, tableName);
    Scan scan = new Scan();
    // Filter
    RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.NOT_EQUAL,
            new RegexStringComparator(reg));
    scan.setFilter(rowFilter);
    ResultScanner scanner = hTable.getScanner(scan);
    for (Result result : scanner) {
      System.out.println(new String(result.getRow()));
    }
  }

  /**
   * Scan information
   *
   * @param tableName
   * @param family
   * @param qualifier
   * @throws Exception
   */
  public void getScanData(String tableName, String family, String qualifier)
          throws Exception {
    HTable hTable = new HTable(conf, tableName);
    Scan scan = new Scan();
    scan.addColumn(family.getBytes(), qualifier.getBytes());
    ResultScanner scanner = hTable.getScanner(scan);
    for (Result result : scanner) {
      if (result.raw().length == 0) {
        System.out.println(tableName + " table info is null！");
      } else {
        for (KeyValue kv : result.raw()) {
          System.out.println(new String(kv.getKey()) + "\t"
                  + new String(kv.getValue()));
        }
      }
    }
  }

  /**
   * Delete table
   *
   * @param tableName
   */
  private void deleteTable(String tableName) {
    try {
      if (admin.tableExists(tableName)) {
        admin.disableTable(tableName);
        admin.deleteTable(tableName);
        System.out.println(tableName + "delete table success！");
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println(tableName + "delete table fail！");
    }

  }

  /**
   * Delete a record
   *
   * @param tableName
   * @param rowKey
   */
  public void deleteOneRecord(String tableName, String rowKey) {
    HTablePool hTablePool = new HTablePool(conf, 1000);
    HTableInterface table = hTablePool.getTable(tableName);
    Delete delete = new Delete(rowKey.getBytes());
    try {
      table.delete(delete);
      System.out.println(rowKey + "delete record success！");
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println(rowKey + "delete record failed！");
    }
  }

  /**
   * Get all info in table
   *
   * @param tableName
   */
  public void getALLData(String tableName) {
    try {
      HTable hTable = new HTable(conf, tableName);
      Scan scan = new Scan();
      ResultScanner scanner = hTable.getScanner(scan);
      for (Result result : scanner) {
        if (result.raw().length == 0) {
          System.out.println(tableName + " table is null！");
        } else {
          for (KeyValue kv : result.raw()) {
            System.out.println(new String(kv.getKey()) + "\t"
                    + new String(kv.getValue()));
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Add a record
   *
   * @param tableName
   * @param row
   * @param columnFamily
   * @param column
   * @param data
   * @throws IOException
   */
  public void put(String tableName, String row, String columnFamily,
                  String column, String data) throws IOException {
    HTablePool hTablePool = new HTablePool(conf, 1000);
    HTableInterface table = hTablePool.getTable(tableName);
    Put p1 = new Put(Bytes.toBytes(row));
    p1.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column),
            Bytes.toBytes(data));
    table.put(p1);
    System.out.println("put'" + row + "'," + columnFamily + ":" + column
            + "','" + data + "'");
  }

  /**
   * Query all tables
   *
   * @return
   * @throws Exception
   */
  public List<String> getALLTable() throws Exception {
    ArrayList<String> tables = new ArrayList<String>();
    if (admin != null) {
      HTableDescriptor[] listTables = admin.listTables();
      if (listTables.length > 0) {
        for (HTableDescriptor tableDesc : listTables) {
          tables.add(tableDesc.getNameAsString());
          System.out.println(tableDesc.getNameAsString());
        }
      }
    }
    return tables;
  }

  /**
   * Create a table
   *
   * @param tableName
   * @param column
   * @throws Exception
   */
  public void createTable(String tableName, String column) throws Exception {
    if (admin.tableExists(tableName)) {
      System.out.println(tableName + "table is exist！");
    } else {
      HTableDescriptor tableDesc = new HTableDescriptor(tableName);
      tableDesc.addFamily(new HColumnDescriptor(column.getBytes()));
      admin.createTable(tableDesc);
      System.out.println(tableName + "create table success！");
    }
  }

  /**
   * Get the detail info about the movie
   *
   * @param tableName
   * @param row
   * @return
   */
  @SuppressWarnings({"deprecation", "resource"})
  public Page get(String tableName, String row) {
    HTablePool hTablePool = new HTablePool(conf, 1000);
    HTableInterface table = hTablePool.getTable(tableName);
    Get get = new Get(row.getBytes());
    Page page = null;
    try {
      Result result = table.get(get);
      KeyValue[] raw = result.raw();
      if (raw.length == 7) {
        page = new Page();
        page.setMovieId(row);
        page.setMovieName(new String(raw[0].getValue()));
        page.setMovieScore(Float.parseFloat(new String(raw[1].getValue())));
        page.setScoreNum(Integer.parseInt(new String(raw[2].getValue())));
        page.setIncreaseNum(Integer.parseInt(new String(raw[3].getValue())));
        page.setExcuteTime(Long.parseLong(new String(raw[4].getValue())));
        page.setExcuteDay(Date.valueOf(new String(raw[5].getValue())));
        page.setUrl(new String(raw[6].getValue()));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return page;
  }
}