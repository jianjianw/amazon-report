package com.nhsoft.module.azure.rest;

import com.nhsoft.module.azure.model.*;
import com.nhsoft.module.azure.service.AzureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_UP;

@RequestMapping(value="importData")
@RestController
public class ImportDataApi {


    @Autowired
     AzureService azureService;

    public String getDateStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    String systemBookCode = "12345";
    @RequestMapping(method = RequestMethod.GET,value = "/branchDaily")
    public String branchDaily(){//按分店和营业日统计的都是100条数据
        List<BranchDaily> list = new ArrayList<BranchDaily>();

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String shiftTableBizday = getDateStr(date);

        Integer branchNum;      //1-100
        BigDecimal dailyMoney;          //营业额   5000-20000
        Integer dailyQty;         //客单量     100-500
        BigDecimal dailyPrice;      //客单价      50-100
        BigDecimal targetMoney;     //营业额目标  10000-15000
        Integer dailyCount;     //客单购买数   200-800
        BranchDaily branchDaily;

        Random random = new Random();
        BigDecimal big = new BigDecimal(1);

        for (int i = 1; i <= 100; i++) {
            branchNum = i;

            double money = random.nextDouble()+1;
            BigDecimal money_ = new BigDecimal(money * 20000 - money * 5000);
            dailyMoney = money_.divide(big, 2, ROUND_HALF_UP);      //营业额

            dailyQty = random.nextInt(500);
            if(dailyQty<100){
                dailyQty+=100;
            }

            double price = random.nextDouble();
            BigDecimal price_ = new BigDecimal(price * 100 - price * 50);
            dailyPrice = price_.divide(big, 2, ROUND_HALF_UP);//客单价

            double target = random.nextDouble()+1;
            BigDecimal target_ = new BigDecimal(target * 15000 - target * 5000);
            targetMoney = target_.divide(big, 2, ROUND_HALF_UP);    //营业额目标

            dailyCount = random.nextInt(800) + 200; //客单购买数
            if(dailyCount<200){
                dailyCount+=200;
            }

            branchDaily = new BranchDaily();
            branchDaily.setSystemBookCode(systemBookCode);
            branchDaily.setBranchNum(branchNum);
            branchDaily.setShiftTableBizday(shiftTableBizday);
            branchDaily.setShiftTableDate(date);
            branchDaily.setDailyMoney(dailyMoney);
            branchDaily.setDailyQty(dailyQty);
            branchDaily.setDailyPrice(dailyPrice);
            branchDaily.setTargetMoney(targetMoney);
            branchDaily.setDailyCount(dailyCount);

            list.add(branchDaily);
        }
        azureService.batchSaveBranchDailies(systemBookCode,list,date,date);
       return "success";
    }

    @RequestMapping(method = RequestMethod.GET,value = "/cardDaily")
    public String cardDaily(){//按分店和营业日统计的都是100条数据

        List<CardDaily> list = new ArrayList<CardDaily>();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        String systemBookCode = "12345";
        Integer branchNum;      //1-100
        String shiftTableBizday = getDateStr(date);

        Integer cardDeliverCount;       //新发卡数          20-50
        Integer cardReturnCount;        //退卡数            0-5
        Integer cardDeliverTarget;      //发卡目标          10-30
        BigDecimal cardDepositCash;     //付款金额          1500-2500

        BigDecimal cardDepositMoney;    //存款金额          500-2000
        BigDecimal cardDepositTarget;   //存款目标          1000-1500
        BigDecimal cardConsumeMoney;    //消费金额          1500-2500
        BigDecimal big = new BigDecimal(1);
        CardDaily cardDaily;
        Random random = new Random();
        for (int i = 1; i <= 100 ; i++) {
            branchNum = i;//分店号
            cardDeliverCount = random.nextInt(50) + 20;     //新发卡数
            if(cardDeliverCount<20){
                cardDeliverCount+=20;
            }
            cardReturnCount = random.nextInt(6);            //退卡数

            cardDeliverTarget = random.nextInt(30) + 10;    //发卡目标

            if(cardDeliverTarget<10){
                cardDeliverTarget+=10;
            }

            double cash = random.nextDouble() + 1;
            BigDecimal cash_ = new BigDecimal(cash * 2500 - cash * 1000);
            cardDepositCash = cash_.divide(big, 2, ROUND_HALF_UP); //付款金额

            double depositMoney = random.nextDouble() + 1;
            BigDecimal depositMoney_ = new BigDecimal(depositMoney * 2000 - depositMoney * 500);
            cardDepositMoney = depositMoney_.divide(big, 2, ROUND_HALF_UP); //存款金额

            double depositTarget = random.nextDouble() + 1;
            BigDecimal depositTarget_ = new BigDecimal(depositTarget * 1500 - depositTarget * 500);
            cardDepositTarget = depositTarget_.divide(big, 2, ROUND_HALF_UP); //存款目标

            double consumeMoney = random.nextDouble() + 1;
            BigDecimal consumeMoney_ = new BigDecimal(consumeMoney * 2500 - consumeMoney * 1000);
            cardConsumeMoney = consumeMoney_.divide(big, 2, ROUND_HALF_UP); //消费金额

            cardDaily = new CardDaily();
            cardDaily.setSystemBookCode(systemBookCode);
            cardDaily.setBranchNum(branchNum);
            cardDaily.setShiftTableBizday(shiftTableBizday);
            cardDaily.setShiftTableDate(date);
            cardDaily.setCardDeliverCount(cardDeliverCount);        //新发卡数
            cardDaily.setCardReturnCount(cardReturnCount);         //退卡数
            cardDaily.setCardDeliverTarget(cardDeliverTarget);       //发卡目标
            cardDaily.setCardDepositCash(cardDepositCash);         //付款金额
            cardDaily.setCardDepositMoney(cardDepositMoney);        //存款金额
            cardDaily.setCardDepositTarget(cardDepositTarget);       //存款目标
            cardDaily.setCardConsumeMoney(cardConsumeMoney);        //消费金额
            list.add(cardDaily);
        }

        azureService.batchSaveCardDailies(systemBookCode,list,date,date);
        return "success";

    }

    @RequestMapping(method = RequestMethod.GET,value = "/itemLossDaily")
    public String itemLossDaily(){        //每天大概150条记录

        List<ItemLossDaily> list = new ArrayList<ItemLossDaily>();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        String shiftTableBizday = getDateStr(date);
        Integer branchNum;          //1-100
        Integer itemNum;            //123450001 - 123450100      itemCode  10001 - 10100
        String[] itemLossReason = {"水果现切","报损"} ;      //报损原因      水果现切      报损
        BigDecimal itemMoney;       //报损金额   0-1000
        BigDecimal itemAmount;      //报损数量  1-10

        //循环的size
        Random random = new Random();

        int size = random.nextInt(200);
        if(size<100){
            size += 100;
        }
        BigDecimal big = new BigDecimal(1);
        ItemLossDaily itemLossDaily;
        for (int i = 1; i <= size ; i++) {

            branchNum = random.nextInt(100) + 1;
            itemNum = 123450000 + (random.nextInt(100) + 1);    //123450001 - 123450100

            int value1 = random.nextInt(1000);
            int value2 = random.nextInt(100);
            BigDecimal money_ = new BigDecimal(value1+"."+value2);
            itemMoney = money_.divide(big, 2, ROUND_HALF_UP);   //报损金额   0-1000

            int value3 = random.nextInt(10)+1;
            int value4 = random.nextInt(100);
            BigDecimal amount = new BigDecimal(value3+"."+value4);
            itemAmount = amount.divide(big,2,ROUND_HALF_UP);

            itemLossDaily = new ItemLossDaily();
            itemLossDaily.setSystemBookCode(systemBookCode);
            itemLossDaily.setBranchNum(branchNum);
            itemLossDaily.setShiftTableBizday(shiftTableBizday);
            itemLossDaily.setShiftTableDate(date);
            itemLossDaily.setItemNum(itemNum);
            itemLossDaily.setItemLossReason(itemLossReason[i%2]);
            itemLossDaily.setItemMoney(itemMoney);
            itemLossDaily.setItemAmount(itemAmount);
            list.add(itemLossDaily);
        }

        azureService.batchSaveItemLossDailies(systemBookCode,list,date,date);
        return "success";

    }

    @RequestMapping(method = RequestMethod.GET,value = "/itemSaleDaily")
    public String itemSaleDaily(){

        List<ItemSaleDaily> list = new ArrayList<ItemSaleDaily>();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String shiftTableBizday = getDateStr(date);

        Integer branchNum;
        Integer itemNum;
        String[] itemSource = {"线下门店","蜂巢微商城","美团"};      //单据来源
        String[] itemMemberTag = {"会员","非会员"};  //会员标记
        BigDecimal itemMoney;   //销售金额  //10-500
        BigDecimal itemAmount;  //销售数量  //1-50
        Integer itemCount;      //销售次数  //1-20

        BigDecimal big = new BigDecimal(1);
        ItemSaleDaily itemSaleDaily;
        //循环size
        Random random = new Random();
        int size = random.nextInt(17000);
        if(size < 10000){
            size+=10000;
        }
        for (int i = 0; i <size ; i++) {

            branchNum  = random.nextInt(100)+1;
            itemNum = 123450000 + random.nextInt(100)+1;

            //单据来源下标
            int sourceIndex = random.nextInt(3);
            //会员标记下标
            int memberIndex = random.nextInt(2);

            int value3 = random.nextInt(490)+10;
            int value4 = random.nextInt(100);
            BigDecimal money = new BigDecimal(value3 + "." + value4);
            itemMoney = money.divide(big, 2, ROUND_HALF_UP);   //   销售金额

            int value5 = random.nextInt(50) + 1;
            int value6 = random.nextInt(100);
            BigDecimal amount = new BigDecimal(value5 + "." + value6);
            itemAmount = amount.divide(big, 2, ROUND_HALF_UP);

            itemCount = random.nextInt(20) + 1;

            itemSaleDaily = new ItemSaleDaily();
            itemSaleDaily.setSystemBookCode(systemBookCode);
            itemSaleDaily.setShiftTableBizday(shiftTableBizday);
            itemSaleDaily.setShiftTableDate(date);
            itemSaleDaily.setBranchNum(branchNum);
            itemSaleDaily.setItemNum(itemNum);
            itemSaleDaily.setItemSource(itemSource[sourceIndex]);
            itemSaleDaily.setItemMemberTag(itemMemberTag[memberIndex]);
            itemSaleDaily.setItemMoney(itemMoney);
            itemSaleDaily.setItemAmount(itemAmount);
            itemSaleDaily.setItemCount(itemCount);
            list.add(itemSaleDaily);
        }
        int size1 = list.size();
        //解决主键冲突
        Map<String,ItemSaleDaily> map = new HashMap<String,ItemSaleDaily>();
        for (int i = 0; i <size1 ; i++) {
            ItemSaleDaily sale = list.get(i);
            Integer branchNum1 = sale.getBranchNum();
            Integer itemNum1 = sale.getItemNum();
            String itemMemberTag1 = sale.getItemMemberTag();
            String itemSource1 = sale.getItemSource();
            StringBuilder stringBuilder = new StringBuilder();
            String key = stringBuilder.append(branchNum1).append(itemNum1).append(itemMemberTag1).append(itemSource1).toString();
            ItemSaleDaily saleDaily = map.get(key);
            if(saleDaily == null ){
                map.put(key,sale);
            }
        }
        List<ItemSaleDaily> returnList = new ArrayList<ItemSaleDaily>(map.values());
        azureService.batchSaveItemSaleDailies(systemBookCode,returnList,date,date);
        return "success";
    }
    @RequestMapping(method = RequestMethod.GET,value = "/itemDailyDetail")
    //@Test
    public String itemDailyDetail(){

        List<ItemDailyDetail> list = new ArrayList<ItemDailyDetail>();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String shiftTableBizday = getDateStr(date);
        String[] itemSource = {"线下门店", "蜂巢微商城", "美团"};      //销售来源

        Integer branchNum;
        Integer itemNum;
        String[] itemPeriod  = new String[40];      //时段

        BigDecimal itemPeriodAmout;    //当前时段销售数量
        BigDecimal itemPeriodMoney;    //当前时段销售金额

        ItemDailyDetail itemDailyDetail;
        Random random = new Random();

        BigDecimal big  = new BigDecimal(1);
        for (int i = 0; i < 300; i++) {

            BigDecimal itemAmout = BigDecimal.ZERO;   //销售数量时段汇总
            BigDecimal itemMoney = BigDecimal.ZERO;   //销售金额时段汇总

            branchNum  = random.nextInt(100)+1;                 //id
            itemNum = 123450000 + random.nextInt(100)+1;        //id
            //单据来源下标
            int sourceIndex = random.nextInt(3);

            for (int j = 0; j <40 ; j++) {

                if(j == 0){
                    itemPeriod[j] = "00:30";
                }else if(j % 2 == 0){
                    if(j<19){
                        itemPeriod[j] = "0"+ (j/2) +":30";  //偶数
                    }else{
                        itemPeriod[j] = (j/2) +":30";
                    }
                }else if(j % 2 != 0){      //基数
                    if(j<19){
                        itemPeriod[j] = "0" + ((j+1)/2) + ":00";
                    }else{
                        itemPeriod[j] = ((j+1)/2) + ":00";
                    }
                }

                itemDailyDetail  = new ItemDailyDetail();
                itemDailyDetail.setSystemBookCode(systemBookCode);                 //id
                itemDailyDetail.setBranchNum(branchNum);                            //id
                itemDailyDetail.setItemNum(itemNum);                                //id
                itemDailyDetail.setShiftTableBizday(shiftTableBizday);              //id
                itemDailyDetail.setItemSource(itemSource[sourceIndex]);             //id
                itemDailyDetail.setItemPeriod(itemPeriod[j]);                       //id
                itemDailyDetail.setShiftTableDate(date);

                int value1 = random.nextInt(100);
                int value2 = random.nextInt(100);
                BigDecimal periodAmout = new BigDecimal(value1+"."+value2);
                itemPeriodAmout = periodAmout.divide(big, 2, ROUND_HALF_UP);

                itemDailyDetail.setItemPeriodAmout(itemPeriodAmout);

                int value3 = random.nextInt(100);
                int value4 = random.nextInt(100);
                BigDecimal periodMoney = new BigDecimal(value3+"."+value4);
                itemPeriodMoney = periodMoney.divide(big, 2, ROUND_HALF_UP);

                itemDailyDetail.setItemPeriodMoney(itemPeriodMoney);

                itemAmout = itemAmout.add(itemPeriodAmout) ;
                itemMoney = itemMoney.add(itemPeriodMoney);
                itemDailyDetail.setItemAmout(itemAmout);
                itemDailyDetail.setItemMoney(itemMoney);

                list.add(itemDailyDetail);
            }
        }

        Map<String,ItemDailyDetail> map = new HashMap<String, ItemDailyDetail>();
        int size1 = list.size();
        for (int i = 0; i <size1 ; i++) {

            ItemDailyDetail detail = list.get(i);
            Integer branchNum1 = detail.getBranchNum();
            Integer itemNum1 = detail.getItemNum();
            String period = detail.getItemPeriod();
            String itemSource1 = detail.getItemSource();
            StringBuilder stringBuilder = new StringBuilder();
            String key = stringBuilder.append(branchNum1).append(itemNum1).append(period).append(itemSource1).toString();
            ItemDailyDetail itemDailyDetail1 = map.get(key);
            if(itemDailyDetail1 == null){
                map.put(key,detail);
            }
        }
        List<ItemDailyDetail> returnList = new ArrayList<ItemDailyDetail>(map.values());
        azureService.batchSaveItemDailyDetails(systemBookCode,returnList,date,date);
        return "success";

    }

  
}
