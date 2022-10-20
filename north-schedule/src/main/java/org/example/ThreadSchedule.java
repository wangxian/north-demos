package org.example;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadSchedule {
    public static void main(String[] args) {
        final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);

        // 5秒后执行一次性任务
        scheduledExecutorService.schedule(() -> {
            System.out.println("5秒后执行一次性任务");
            // scheduledExecutorService.shutdown();
        }, 5, TimeUnit.SECONDS);


        // 2秒后开始执行定时任务，每3秒执行
        // FixedRate是指任务总是以固定时间间隔触发，不管任务执行多长时间
        // 在FixedRate模式下，假设每秒触发，如果某次任务执行时间超过1秒，后续任务会：立即执行
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("scheduleAtFixedRate::2秒后开始执行定时任务，每3秒执行");
        }, 2, 3, TimeUnit.SECONDS);


        // 2秒后开始执行定时任务，每3秒执行
        // 而FixedDelay是指，上一次任务执行完毕后，等待固定的时间间隔，再执行下一次任务
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            System.out.println("scheduleWithFixedDelay::2秒后开始执行定时任务，每3秒执行");
        }, 2, 3, TimeUnit.SECONDS);

        System.out.println("- OVER -");

        // --------------------------------- 不推荐使用了
        // Java标准库还提供了一个java.util.Timer类，这个类也可以定期执行任务，但是，一个Timer会对应一个Thread，
        // 所以，一个Timer只能定期执行一个任务，多个定时任务必须启动多个Timer，而一个ScheduledThreadPool就可以调度多个定时任务，
        // 所以，我们完全可以用ScheduledThreadPool取代旧的Timer。

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Task performed on: " + new Date() + "\n" +
                                           "Thread's name: " + Thread.currentThread().getName());
            }
        }, 3000);
    }
}
