package org.example;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {
    public static void main(String[] args) throws SchedulerException {
        // 1. 定时器对象
        final Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2. 工作对象
        final JobDetail jobDetail = JobBuilder.newJob(HelloWorld.class).withIdentity("job1", "group1").build();

        // 3. 触发对像
        final CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                                                .withSchedule(CronScheduleBuilder.cronSchedule("0/3 * * * * ?"))
                                                .build();

        // 4. 定时任务关联工作对象和触发对象
        defaultScheduler.scheduleJob(jobDetail, cronTrigger);


        // ----
        // 增加一个新的任务
        final JobDetail jobDetail2 = JobBuilder.newJob(HelloWorld.class).withIdentity("job2", "group2").build();
        final CronTrigger cronTrigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2", "group2")
                                                      .withSchedule(CronScheduleBuilder.cronSchedule("0/8 * * * * ?"))
                                                      .build();
        defaultScheduler.scheduleJob(jobDetail2, cronTrigger2);


        // 启动
        defaultScheduler.start();
    }
}
