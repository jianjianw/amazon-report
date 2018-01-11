package com.nhsoft.report.provider;

import com.nhsoft.module.report.api.ReportApi;
import com.nhsoft.module.report.api.dto.*;
import com.nhsoft.module.report.dao.PosOrderDao;
import com.nhsoft.module.report.dto.BranchDTO;
import com.nhsoft.module.report.dto.ShipDetailDTO;
import com.nhsoft.module.report.dto.ShipOrderSummary;
import com.nhsoft.module.report.rpc.*;
import com.nhsoft.module.report.service.PosOrderService;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSpringBoot {

    @Autowired
    private AdjustmentOrderRpc adjustmentOrderRpc;
    @Autowired
    private BranchRpc branchRpc;
    @Autowired
    private PosOrderRpc posOrderRpc;
    @Autowired
    private ShipOrderRpc shipOrderRpc;
    @Autowired
    private ReportApi reportApi;

    @Test
    public void testShipMoney(){
        List<ShipOrderSummary> shipMoneyByCompanies = shipOrderRpc.findCarriageMoneyByCompanies(systemBookCode, null, null, null, null);
        System.out.println();
    }
    @Test
    public void testShipDeatil(){
        List<ShipDetailDTO> shipDetailByCompanies = shipOrderRpc.findDetails(systemBookCode, null, null, null, null);
        System.out.println();
    }



    /*Date dateFrom = null;
    Date dateTo = null;*/
   // List<Integer> branchNums = null;
    //String systemBookCode = "4020";
    String branchStr = "[";

    //@Before
    public void date(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateFrom = sdf.parse("2017-10-01");
            dateTo = sdf.parse("2017-10-02");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
        branchNums = new ArrayList<Integer>();
        for (BranchDTO b : all) {
            Integer branchNum = b.getBranchNum();
            branchNums.add(branchNum);
        }

        for (int i = 0; i <branchNums.size() ; i++) {
            if(i == branchNums.size()-1){
                branchStr += branchNums.get(i) + "]";
            }else{
                branchStr += branchNums.get(i) + ",";
            }
        }
    }

    @Test
    public void testApiByStore(){           //API：com.nhsoft.module.report.api.ReportApi.byBranch耗时：2350ms
        List<OperationStoreDTO> test = reportApi.findSaleMoneyByBranch(systemBookCode, null, "2017-10");
        System.out.println();
    }

    @Test
    public void testApiByRegion(){          //API：com.nhsoft.module.report.api.ReportApi.byRegion耗时：3119ms
        List<OperationRegionDTO> list = reportApi.findSaleMoneyByRegion(systemBookCode, null, "2017-10-30|2017-11-05");
        System.out.println();
    }


    @Test
    public void byBizday(){     //API：com.nhsoft.module.report.api.ReportApi.byBizday耗时：201ms


        List<TrendDailyDTO> list = reportApi.byBizday(systemBookCode, "122|好的", "2017-10");

        System.out.println();

    }

    @Test
    public void byBizmonth(){   //API：com.nhsoft.module.report.api.ReportApi.byBizmonth耗时：363ms
        List<TrendMonthlyDTO> trendMonthlies = reportApi.byBizmonth(systemBookCode, "|", "2017");
        System.out.println();
    }


    @Test
    public void testBranchTop(){        //API：com.nhsoft.module.report.api.ReportApi.findMoneyFinishRateBranchTop耗时：1948ms
        List<SaleFinishMoneyTopDTO> moneyFinishRateBranchTop = reportApi.findMoneyFinishRateBranchTop("4020", null, "2017-10-10");
        System.out.println();
    }


    @Test
    public void testRegionTop(){        //API：com.nhsoft.module.report.api.ReportApi.findMoneyFinishRateRegionTop耗时：961ms
        List<SaleFinishMoneyTopDTO> moneyFinishRateRegionTop = reportApi.findMoneyFinishRateRegionTop("4020", null, "2017-10-11");
        System.out.println();
    }


    @Test
    public void test1(){            //API：com.nhsoft.module.report.api.ReportApi.findSaleAnalysisByMonth耗时：331ms
        List<SaleMoneyMonthDTO> saleAnalysisByMonth = reportApi.findSaleAnalysisByMonth("4020","|","2017");
        System.out.println();
    }

    @Autowired
    private PosOrderDao posOrderDao;
    @Autowired
    private PosOrderService posOrderService;


    Date dateFrom = null;
    Date dateTo = null;
    String systemBookCode= "4344";
    List<Integer> branchNums = null;
    @Before
    public void testDate() throws Exception{
        List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
        branchNums = new ArrayList<Integer>();
        for (BranchDTO b : all) {
            Integer branchNum = b.getBranchNum();
            branchNums.add(branchNum);
        }
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateFrom = sdf.parse("2017-10-01");
        dateTo = sdf.parse("2017-10-31");
    }
    @Test
    public void test2(){
        CardReportQuery cardReportQuery = new CardReportQuery();
        cardReportQuery.setSystemBookCode("4344");
        cardReportQuery.setBranchNum(99);
        cardReportQuery.setDateFrom(dateFrom);
        cardReportQuery.setDateTo(dateTo);
        //cardReportQuery.setQueryDate(true);
        //List<Object[]> summaryByBizday = posOrderDao.findSummaryByBizday(cardReportQuery);
        List<Object[]> summaryByBizday = posOrderService.findSummaryByBizday(cardReportQuery);
        System.out.println();
    }
    @Test
    public void test3(){
        List<Integer> list = new ArrayList<>();
        list.add(99);
        List<Object[]> customReportByBizday = posOrderService.findCustomReportByBizday("4344", list, dateFrom, dateTo);
        System.out.println();
    }

    @Test
    public void test4(){///pos_order_detail    inner
        //含inner
        CardReportQuery cardReportQuery = new CardReportQuery();
        cardReportQuery.setQueryDetail(true);
        //cardReportQuery.setQueryPayment(true);
        cardReportQuery.setSystemBookCode("4344");
        cardReportQuery.setBranchNum(99);
        cardReportQuery.setDateFrom(dateFrom);
        cardReportQuery.setDateTo(dateTo);
        List<Object[]> summaryByBranch = posOrderService.findSummaryByBranch(cardReportQuery);
        System.out.println();
        //posOrderService.createByCardReportQuery();
    }

    @Test
    public void test5(){//pos_order_detail   and pos_order_kit_detail     ok

        List<Integer> items = new ArrayList<>();
        items.add(434400126);
        List<Object[]> itemSum = posOrderService.findItemSum(systemBookCode,branchNums,dateFrom,dateTo,items,true);
        System.out.println();

    }

    @Test
    public void test6(){ // pos_order   查不到数据会报NullPointerException    (含case when)                     ok
        List<Object[]> customReportByBizday = posOrderService.findCustomReportByBizday(systemBookCode,branchNums,dateFrom,dateTo);
        System.out.println();
    }

    @Test
    public void test7(){
        CardReportQuery cardReportQuery = new CardReportQuery();
        cardReportQuery.setQueryDetail(true);
        cardReportQuery.setSystemBookCode("4344");
        cardReportQuery.setBranchNum(99);
        List<Object[]> summaryByBizday = posOrderService.findSummaryByBizday(cardReportQuery);
        System.out.println();
    }


   /* public List<ItemDailyDetail> findItemDailyDetailSummary(String systemBookCode, Date dateFrom, Date dateTo) {
        List<Object[]> objects = posOrderService.findItemDailyDetailSummary(systemBookCode, dateFrom, dateTo);
        List<ItemDailyDetail> list = new ArrayList<>();
        if (objects.isEmpty()) {
            return list;
        }
        Map<String, ItemDailyDetail> map = new HashMap<>();
        for (int i = 0; i < objects.size(); i++) {
            Object[] object = objects.get(i);
            //向map添加数据
            StringBuilder sb = new StringBuilder();
            StringBuilder append = sb.append((Integer) object[0]).append((String) object[1]).append((String) object[3]).append((Integer) object[4]);
            String key = append.toString();
            ItemDailyDetail itemDailyDetail = map.get(key);
            if (itemDailyDetail != null) {
                String itemPeriod = itemDailyDetail.getItemPeriod();
                String hour = itemPeriod.substring(0, 2);
                Integer intHour = Integer.valueOf(itemPeriod.substring(0, 2));
                String min = itemPeriod.substring(2, 4);
                Integer intMin = Integer.valueOf(itemPeriod.substring(2, 4));

                if (intMin >= 0 && intMin <= 30) {
                    itemDailyDetail.setItemMoney(itemDailyDetail.getItemMoney().add((BigDecimal) object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]));
                    itemDailyDetail.setItemAmout(itemDailyDetail.getItemAmout() + ((Integer) object[6] == null ? 0 : (Integer) object[6]));
                    itemDailyDetail.setItemPeriod(itemPeriod);
                } else {
                    itemDailyDetail.setItemMoney(itemDailyDetail.getItemMoney().add((BigDecimal) object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]));
                    itemDailyDetail.setItemAmout(itemDailyDetail.getItemAmout() + ((Integer) object[6] == null ? 0 : (Integer) object[6]));
                    int hourCount = intHour + 1;
                    StringBuilder stringBuilder = new StringBuilder();
                    StringBuilder append1 = stringBuilder.append(hourCount).append(intMin);
                    itemDailyDetail.setItemPeriod(append1.toString());
                }
            } else {
                ItemDailyDetail dailyDetail = new ItemDailyDetail();
                dailyDetail.setSystemBookCode(systemBookCode);
                dailyDetail.setBranchNum((Integer) object[0]);
                dailyDetail.setShiftTableBizday((String) object[1]);
                dailyDetail.setItemPeriod((String) object[2]);
                dailyDetail.setItemSource((String) object[3]);
                dailyDetail.setItemNum((Integer) object[4]);
                dailyDetail.setItemMoney((BigDecimal) object[5]);
                dailyDetail.setItemAmout((Integer) object[6]);
                dailyDetail.setShiftTableDate(DateUtil.getDateStr(dailyDetail.getShiftTableBizday()));
                map.put(key, dailyDetail);
            }

        }
        List<ItemDailyDetail> resultList = new ArrayList<>();
        Set<String> keys = map.keySet();
        for (String mapKey : keys) {
            ItemDailyDetail itemDailyDetail1 = map.get(mapKey);
            resultList.add(itemDailyDetail1);
        }

        return resultList;
    }*/



}
