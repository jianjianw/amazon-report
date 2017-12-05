package com.nhsoft.module.azure.listener;

import com.nhsoft.module.azure.timer.AzureTask;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Timer;

@Component
public class ReportListener implements ApplicationListener<ContextRefreshedEvent> {

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Timer timer = new Timer();
        timer.schedule(new AzureTask(),1000,1000*60*30);
        System.out.println("所有bean初始化完成");
        /*ThreadPoolTaskExecutor thread = new ThreadPoolTaskExecutor();
        thread.execute(new AzureTask());
        thread.shutdown();*/
    }
}
