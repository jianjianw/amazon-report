package com.nhsoft.module.azure.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class TestSchedule {

//    @Scheduled(cron="0 */1 * * * *")        //删除2天以前的数据
//    public void test01(){
//        System.out.println("我是01");
//    }
//
//    @Scheduled(cron="0 */1 * * * *")        //删除2天以前的数据
//    public void test02(){
//        System.out.println("我是02");
//    }
//
//    @Scheduled(fixedDelay = 5000)
//    public void test03(){
//        System.out.println("我是03");
//    }

    @Scheduled(cron="30 * * * * *")
    public void test04()throws Exception{
        System.out.println("我是04");
    }
    @Scheduled(cron="30 * * * * *")
    public void test05()throws Exception{
        Thread.sleep(5000);
        System.out.println("我是05");
    }

}
