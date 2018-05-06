package com.movies.spider.start.Quartz;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import com.movies.spider.start.Quartz.impl.OnShowDb2ExcelJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * CronTrigger cycle start spider task day by day
 */
public class CronTriggerStart {

  public void run() throws Exception {
    Logger log = LoggerFactory.getLogger(CronTriggerStart.class);

    log.info("------- Initializing -------------------");

    // First we must get a reference to a scheduler
    SchedulerFactory sf = new StdSchedulerFactory();
    Scheduler sched = sf.getScheduler();

    log.info("------- Initialization Complete --------");

    log.info("------- Scheduling Jobs ----------------");

    //Crawler task will run everyday at 22:00,parse moives information
    JobDetail job = newJob(IProcessJob.class).withIdentity("processJob", "group1").build();
    //Excute at 12:00 everyday
    CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1").withSchedule(cronSchedule("0 0 22 * * ?"))
            .build();

    Date ft = sched.scheduleJob(job, trigger);
    log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: "
            + trigger.getCronExpression());

    // DB data download to Excel task will run everyday at 22:20
    job = newJob(IDb2ExcelJob.class).withIdentity("Db2ExcelJob", "group1").build();
    //Excute at 12:20 everyday
    trigger = newTrigger().withIdentity("trigger2", "group1").withSchedule(cronSchedule("0 20 22 * * ?")).build();

    ft = sched.scheduleJob(job, trigger);

    log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: "
            + trigger.getCronExpression());

    // Everyday movies trend info will send an e-mail,at 22:30 everyday
    job = newJob(ISendMailJob.class).withIdentity("sendMailJob", "group1").build();
    // Excute at 12:30 everyday
    trigger = newTrigger().withIdentity("trigger3", "group1").withSchedule(cronSchedule("0 30 22 * * ?")).build();

    ft = sched.scheduleJob(job, trigger);

    log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: "
            + trigger.getCronExpression());

    // All of the jobs have been added to the scheduler, but none of the
    // jobs
    // will run until the scheduler has been started
    sched.start();

    log.info("------- Started Scheduler -----------------");
  }

  public static void main(String[] args) throws Exception {

    CronTriggerStart example = new CronTriggerStart();
    example.run();
  }
}