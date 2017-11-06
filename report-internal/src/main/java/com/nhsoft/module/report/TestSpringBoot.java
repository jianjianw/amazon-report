package com.nhsoft.module.report;

import com.nhsoft.module.report.api.ReportApi;
import com.nhsoft.module.report.api.dto.*;
import com.nhsoft.module.report.dto.BranchDTO;
import com.nhsoft.module.report.dto.ShipDetailDTO;
import com.nhsoft.module.report.dto.ShipOrderSummary;
import com.nhsoft.module.report.rpc.*;
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




    Date dateFrom = null;
    Date dateTo = null;
    List<Integer> branchNums = null;
    String systemBookCode = "4020";
    String branchStr = "[";

    @Before
    public void date(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateFrom = sdf.parse("2017-10-01");
            dateTo = sdf.parse("2017-10-02");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<BranchDTO> all = branchRpc.findAll(systemBookCode);
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
        List<OperationStoreDTO> test = reportApi.byBranch(systemBookCode, null, "2017-10-30|2017-11-05");
        System.out.println();
    }

    @Test
    public void testApiByRegion(){          //API：com.nhsoft.module.report.api.ReportApi.byRegion耗时：3119ms
        List<OperationRegionDTO> list = reportApi.byRegion(systemBookCode, null, "2017-10-30|2017-11-05");
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
    public void testAll(){
        List<BranchDTO> all = branchRpc.findAll("4020");
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



}
