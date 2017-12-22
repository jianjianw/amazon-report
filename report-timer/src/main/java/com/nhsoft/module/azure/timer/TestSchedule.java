package com.nhsoft.module.azure.timer;

import com.nhsoft.module.azure.model.ItemSaleDaily;
import com.nhsoft.module.report.dto.ItemSaleDailyDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//@Component
public class TestSchedule {

//    @Scheduled(cron = "0 */1 * * * *")        //删除2天以前的数据
//    public void test01() {
//        System.out.println("我是01");
//    }
//
//    @Scheduled(cron = "0 */1 * * * *")        //删除2天以前的数据
//    public void test02() {
//        System.out.println("我是02");
//    }
//
//    @Scheduled(fixedDelay = 5000)
//    public void test03() {
//        System.out.println("我是03");
//    }

    @Scheduled(cron = "0 53 * * * *")
    public void test04() throws Exception {
        System.out.println("我是04");
        Thread.sleep(80000);
    }

    @Scheduled(cron = "0 53 * * * *")
    public void test05() throws Exception {
        System.out.println("我是05");
        Thread.sleep(80000);
    }

    @Scheduled(cron="0 0,59 * 22 12 ?")       //每30分钟，更新一次当天数据    商品日销售汇总
    public void saveItemSaleDailyMin(){
        System.out.println("12月22日的定时器执行了");
    }

}
