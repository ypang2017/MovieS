package com.movies.spider.start.Quartz;

import com.movies.spider.view.ExcelUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Db2ExcelJob implements Job {
  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    ExcelUtil excelUtil = new ExcelUtil();
    excelUtil.Data2Excel();
  }
}
