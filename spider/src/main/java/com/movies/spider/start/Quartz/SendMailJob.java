package com.movies.spider.start.Quartz;

import com.movies.spider.view.impl.QQEmailSender;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SendMailJob implements Job {
  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    QQEmailSender sender = new QQEmailSender();
    sender.send();
  }
}
