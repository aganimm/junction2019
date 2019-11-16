package com.junction.vk.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

@Configuration
public class ThreadPoolConfiguration {
    public static final String TASK_SCHEDULER = "Task Scheduler";

    public static final String DEFAULT_EXECUTOR = "defaultExecutor";
    public static final String USER_EXECUTOR = "feedExecutor";

    public static final String DEFAULT_SCHEDULER = "defaultScheduler";
    public static final String FEED_SCHEDULER = "feedScheduler";

    @Bean(TASK_SCHEDULER)
    public TaskScheduler getThreadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix(TASK_SCHEDULER);
        return threadPoolTaskScheduler;
    }


    @Bean
    @Qualifier(DEFAULT_EXECUTOR)
    public ExecutorService getDefaultExecutorService(@Value("${executor.default.poolSize}") int poolSize) {
        return createExecutorService(poolSize, DEFAULT_EXECUTOR);
    }

    @Bean
    @Qualifier(USER_EXECUTOR)
    public ExecutorService getBookUpdateExecutorService(@Value("${executor.feed.poolSize}") int poolSize) {
        return createExecutorService(poolSize, USER_EXECUTOR);
    }

    @Bean
    @Primary
    @Qualifier(DEFAULT_SCHEDULER)
    public ScheduledExecutorService getDefaultScheduledExecutorService(@Value("${scheduled.default.poolSize}") int poolSize) {
        return createScheduledExecutorService(poolSize, DEFAULT_SCHEDULER);
    }

    @Bean
    @Qualifier(FEED_SCHEDULER)
    public ScheduledExecutorService getScheduledBookUpdateExecutorService(@Value("${scheduled.feed.poolSize}") int poolSize) {
        return createScheduledExecutorService(poolSize, FEED_SCHEDULER);
    }

    private static ScheduledExecutorService createScheduledExecutorService(int poolSize, String threadNameTemplate) {
        return Executors.newScheduledThreadPool(poolSize, getDaemonThreadFactory(threadNameTemplate));
    }

    private static ExecutorService createExecutorService(int poolSize, String threadNameTemplate) {
        return Executors.newFixedThreadPool(poolSize, getDaemonThreadFactory(threadNameTemplate));
    }

    private static ThreadFactory getDaemonThreadFactory(String threadNameTemplate) {
        return new ThreadFactoryBuilder().setNameFormat(threadNameTemplate + "-%d")
                .setDaemon(true).build();
    }
}