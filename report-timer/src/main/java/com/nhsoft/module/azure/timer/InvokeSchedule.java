package com.nhsoft.module.azure.timer;

import com.nhsoft.module.azure.invoke.Invoke;
import com.nhsoft.module.azure.service.AzureService;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//@Component
public class InvokeSchedule implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(InvokeSchedule.class);
    @Autowired
    private Invoke invoke;
    @Autowired
    private AzureService azureService;

    @Value("${datasource.names}")
    private String str;

    String[] systemBookCode;

    public void afterPropertiesSet() throws Exception {
        systemBookCode = str.split(",");
        //移除测试账套12345
        systemBookCode = ArrayUtils.remove(systemBookCode, systemBookCode.length - 1);
    }

    @Scheduled(cron = "0 * * * * *")
    public void test(){
        for (int i = 0; i <systemBookCode.length ; i++) {
            String str = systemBookCode[i];
            System.out.println(str);
        }
    }

    @Scheduled(cron="0 */30 * * * *")
    public void saveBranchDailyMin(){     //分店销售汇总(每30分钟执行一次)  Scheduled(cron="0 */30 * * * *")
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        int length = systemBookCode.length;
        for (int i = 0; i <length ; i++) {
            invoke.saveBranchDailys(systemBookCode[i],date,date);
        }
    }
    @Scheduled(cron="0 0 2-3 * * *")////更新历史1
    public void saveBranchDailyHour(){     //分店销售汇总(每天凌晨2点执行,更新前七天的数据)
        Calendar calendar = Calendar.getInstance();
        Date dateTo = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-7);
        Date dateFrom = calendar.getTime();
        int length = systemBookCode.length;
        for (int i = 0; i <length ; i++) {
            invoke.saveBranchDailys(systemBookCode[i],dateFrom,dateTo);
        }
    }


    @Scheduled(cron="0 */30 * * * *")       //每30分钟更新一次当天的额数据
    public void saveBranchDailyDirectMin(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        int length = systemBookCode.length;
        for (int i = 0; i <length ; i++) {
            invoke.saveBranchDailyDirects(systemBookCode[i],date,date);
        }
    }
    @Scheduled(cron="0 0 2-3 * * *")     ////更新历史2
    public void saveBranchDailyDirectHour(){    //每天凌晨更新前2天的数据
        Calendar calendar = Calendar.getInstance();
        Date dateTo = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        Date dateFrom = calendar.getTime();
        int length = systemBookCode.length;
        for (int i = 0; i < length; i++) {
            invoke.saveBranchDailyDirects(systemBookCode[i],dateFrom,dateTo);
        }
    }
    @Scheduled(cron="0 0 2-3 * * *")
    public void deleteBranchDailyDirect(){      //保留两天数据,删除两天前的数据
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        Date date = calendar.getTime();
        int length = systemBookCode.length;
        for (int i = 0; i < length; i++) {
            azureService.batchDeleteBranchDailyDirects(systemBookCode[i],date,date);
        }
    }


    @Scheduled(cron="0 */30 * * * *")       //    @Scheduled(cron="0 0,30 * * * *")
    public void saveItemDailyDetailMinute(){        //商品日时段销售汇总(从凌晨开始，每个小时的0分和30分执行一次)
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        int length = systemBookCode.length;
        for (int i = 0; i <length ; i++) {
            invoke.saveItemDailyDeatils(systemBookCode[i],date,date);
        }
    }
    @Scheduled(cron="0 0 2-3 * * *")
    public void saveItemDailyDetailHour(){        //商品日时段销售汇总(汇总昨天的数据)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date date = calendar.getTime();
        int length = systemBookCode.length;
        for (int i = 0; i < length ; i++) {
            invoke.saveItemDailyDeatils(systemBookCode[i],date,date);
        }
    }
    @Scheduled(cron="0 0 2-3 * * *")
    public void deleteItemDetailDaily(){        //删除2天前的数据（保存三天的数据）
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        Date date = calendar.getTime();
        int length = systemBookCode.length;
        for (int i = 0; i < length ; i++) {
            azureService.batchDeleteItemDetailDailies(systemBookCode[i],date,date);
        }
    }

    @Scheduled(cron="0 0 2-3 * * *")
    public void saveBranch(){                 //每天更新分店
        int length = systemBookCode.length;
        for (int i = 0; i <length ; i++) {
            invoke.saveBranch(systemBookCode[i]);
        }
    }

    @Scheduled(cron="0 0 2-3 * * *")    //每天更新商品资料
    public void saveItem(){
        int length = systemBookCode.length;
        for (int i = 0; i <length ; i++) {
            invoke.saveItems(systemBookCode[i]);
        }
    }

    @Scheduled(cron="0 10,20 0 * * *") //每天凌晨定时更新日期表
    public void saveBizday(){
        int length = systemBookCode.length;
        for (int i = 0; i <length ; i++) {
            invoke.saveBranch(systemBookCode[i]);
        }
    }

    //新加的表
    @Scheduled(cron="0 0 2-3 * * *") //每天凌晨2-3点更新前两天的数据     商品日销售汇总  (不需要今天的数据)
    public void saveItemSaleDaily(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date dateTo = calendar.getTime();   //昨天
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date dateFrom = calendar.getTime(); //前天
        int length = systemBookCode.length;
        for (int i = 0; i <length ; i++) {
            invoke.saveItemSaleDailys(systemBookCode[i],dateFrom,dateTo);
        }
    }
    @Scheduled(cron="0 0 2-3 * * *")        //每天凌晨2-3点删除四个月前的数据     商品日销售汇总
    public void deleteItemSaleDaily(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-4);
        Date time = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String format = sdf.format(time);
        calendar.add(Calendar.DAY_OF_MONTH,-Integer.valueOf(format)+1);
        Date date = calendar.getTime();

        int length = systemBookCode.length;
        for (int i = 0; i <length ; i++) {
            azureService.batchDeleteItemSaleDailies(systemBookCode[i],date,date);
        }
    }


    @Scheduled(cron="0 0 2-3 * * *")    //每天凌晨2-3点更新一次前两天的数据   商品日报损汇总
    public void saveItemLossDaily(){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date dateTo = calendar.getTime();   //昨天
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date dateFrom = calendar.getTime(); //前天
        int length = systemBookCode.length;
        for (int i = 0; i <length ; i++) {
            invoke.saveItemLossDailys(systemBookCode[i],dateFrom,dateTo);
        }
    }
    @Scheduled(cron="0 0 2-3 * * *")    //每天凌晨2-3点删除四个月前的数据   商品日报损汇总
    public void deleteItemLossDaily(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-4);
        Date time = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String format = sdf.format(time);
        calendar.add(Calendar.DAY_OF_MONTH,-Integer.valueOf(format)+1);
        Date date = calendar.getTime();

        int length = systemBookCode.length;
        for (int i = 0; i < length ; i++) {
            azureService.batchDeleteItemLossDailies(systemBookCode[i],date,date);
        }
    }

    @Scheduled(cron="0 0 2-3 * * *") //每天凌晨2-3点更新前两天的数据         会员统计
    public void saveCardDailyHour(){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date yesterday = calendar.getTime();   //昨天
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date beforeYesterday = calendar.getTime(); //前天
        int length = systemBookCode.length;
        for (int i = 0; i <length ; i++) {
            invoke.saveCardDailys(systemBookCode[i],yesterday,yesterday);//更新昨天数据
            invoke.saveCardDailys(systemBookCode[i],beforeYesterday,beforeYesterday);//更新前天数据
        }
    }
    @Scheduled(cron="0 0 2-3 * * *")    //每天凌晨2-3点删除三个月前的数据   会员统计
    public void deleteCardDaily(){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-4);
        Date time = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String format = sdf.format(time);
        calendar.add(Calendar.DAY_OF_MONTH,-Integer.valueOf(format)+1);
        Date date = calendar.getTime();
        int length = systemBookCode.length;
        for (int i = 0; i <length ; i++) {
            azureService.batchDeleteCardDailies(systemBookCode[i],date,date);
        }

    }


}
