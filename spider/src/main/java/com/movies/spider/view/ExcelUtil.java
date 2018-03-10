package com.movies.spider.view;

import com.movies.spider.entity.Page;
import com.movies.spider.service.impl.MysqlStoreService;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.util.List;

public class ExcelUtil {

  //Parse movies information from db to Excel
  public void Data2Excel() {
    String tableName = "onshowmovie";
    MysqlStoreService mysqlStoreService = new MysqlStoreService(tableName);

    // Query all data from the database
    List<Page> list = (List<Page>) mysqlStoreService.searchAll();
    try{
      WritableWorkbook wwb = null;
      Workbook wb = null;
      WritableSheet ws = null;

      // Create a Excel workbook
//      String fileName = "/home/hadoop/work/mycel/Mycel/mysql.xls";
      String fileName = "D:\\学习\\java\\MovieS\\mysql.xls";
      File file = new File(fileName);
      if (!file.exists()) {
        file.createNewFile();

        // Create Workbook with fileName
        wwb = Workbook.createWorkbook(file);

        // Create a work sheet
        ws = wwb.createSheet("Movieshow1", 1);

        // Use the defaut number "0" as the first column in Excel
        Label Moviename = new Label(0, 0, "Moviename");
        Label Moviescore = new Label(1, 0, "Moviescore");
        Label Scorenum = new Label(2, 0, "Scorenum");
        Label DayIncrease = new Label(3, 0, "DayIncrease");
        Label Excuteday = new Label(4, 0, "Excuteday");

        //Add title
        ws.addCell(Moviename);
        ws.addCell(Moviescore);
        ws.addCell(Scorenum);
        ws.addCell(DayIncrease);
        ws.addCell(Excuteday);

      } else {
        wb = Workbook.getWorkbook(file);
        wwb = Workbook.createWorkbook(file, wb);
        ws = wwb.getSheet("Movieshow1");
      }

    cellAdd(list, ws);

      // Write to Excel
      wwb.write();
      // Close Excel object
      wwb.close();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private static void cellAdd(List<Page> list, WritableSheet ws) {
    for (int i = 0; i < list.size(); i++) {
      Label Moviename_i = new Label(0, i + 1, list.get(i).getMovieName() + "");
      Label Moviescore_i = new Label(1, i + 1, list.get(i).getMovieScore() + "");
      Label Scorenum_i = new Label(2, i + 1, list.get(i).getScoreNum() + "");
      Label DayIncrease_i = new Label(3, i + 1, list.get(i).getIncreaseNum() + "");
      Label Excuteday_i = new Label(4, i + 1, list.get(i).getExcuteDay() + "");
      try {
        //Add detail data from db to Excel
        ws.addCell(Moviename_i);
        ws.addCell(Moviescore_i);
        ws.addCell(Scorenum_i);
        ws.addCell(DayIncrease_i);
        ws.addCell(Excuteday_i);
      } catch (WriteException e) {
        e.printStackTrace();
      }
    }
  }
}