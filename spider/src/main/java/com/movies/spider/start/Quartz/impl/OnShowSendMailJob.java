package com.movies.spider.start.Quartz.impl;

import com.movies.spider.start.Quartz.ISendMailJob;
import com.movies.spider.view.impl.QQEmailSender;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class OnShowSendMailJob implements ISendMailJob {
  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    QQEmailSender sender = new QQEmailSender();
    sender.send();
  }
}
