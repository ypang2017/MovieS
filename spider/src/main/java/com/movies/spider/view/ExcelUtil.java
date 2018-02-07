package com.movies.spider.view;

import com.movies.spider.entity.Page;
import com.movies.spider.service.MysqlStoreService;
import com.movies.spider.utils.MysqlUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class ExcelUtil {
  
  //Parse movies information from db to Excel
  public void Data2Excel(){
    MysqlStoreService mysqlStoreService = new MysqlStoreService();
    try {
      WritableWorkbook wwb = null;

      // Create a Excel workbook
      String fileName = "/home/hadoop/work/mycel/Mycel/mysql.xls";
      File file = new File(fileName);
      if (!file.exists()) {
        file.createNewFile();
      }
      // Create Workbook with fileName
      wwb = Workbook.createWorkbook(file);
      
      // Create a work sheet
      WritableSheet ws = wwb.createSheet("Movieshow1", 1);

      // Query all data from the database
      List<Page> list = (List<Page>) mysqlStoreService.searchAll();
      // Use the defaut number "0" as the first column in Excel
      Label Moviename = new Label(0, 0, "Moviename)");
      Label Moviescore = new Label(1, 0, "Moviescore");
      Label Scorenum = new Label(2, 0, "Scorenum");
      Label DayIncrease = new Label(3, 0, "DayIncrease");
      Label Excuteday = new Label(4, 0, "Excuteday");

      ws.addCell(Moviename);
      ws.addCell(Moviescore);
      ws.addCell(Scorenum);
      ws.addCell(DayIncrease);
      ws.addCell(Excuteday);

      for (int i = 0; i < list.size(); i++) {
        Label Moviename_i = new Label(0, i + 1, list.get(i).getMovieName() + "");
        Label Moviescore_i = new Label(1, i + 1, list.get(i).getMovieScore() + "");
        Label Scorenum_i = new Label(2, i + 1, list.get(i).getScoreNum() + "");
        Label DayIncrease_i = new Label(3, i + 1, list.get(i).getIncreaseNum() + "");
        Label Excuteday_i = new Label(4, i + 1, list.get(i).getExcuteDay() + "");
        ws.addCell(Moviename_i);
        ws.addCell(Moviescore_i);
        ws.addCell(Scorenum_i);
        ws.addCell(DayIncrease_i);
        ws.addCell(Excuteday_i);
      }

      // Write to Excel 
      wwb.write();
      // Close Excel object
      wwb.close();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}