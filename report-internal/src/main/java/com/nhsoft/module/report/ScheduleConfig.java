package com.nhsoft.module.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskExecutor());
    }
    @Bean
    public Executor taskExecutor() {
        logger.info("初始化Executor");
        return Executors.newScheduledThreadPool(100);
    }
}
