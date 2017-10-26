package com.nhsoft.module.report;


import com.nhsoft.module.report.api.ReportApi;
import com.nhsoft.module.report.api.dto.*;
import com.nhsoft.module.report.dto.BranchDTO;
import com.nhsoft.module.report.dto.ShipDetailDTO;
import com.nhsoft.module.report.dto.ShipOrderSummary;
import com.nhsoft.module.report.rpc.AdjustmentOrderRpc;
import com.nhsoft.module.report.rpc.BranchRpc;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import com.nhsoft.module.report.rpc.ShipOrderRpc;
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
    public void testApiByStore(){
        List<OperationStoreDTO> test = reportApi.byBranch(systemBookCode, null, "2017-10-04");
        System.out.println();
    }

    @Test
    public void testApiByRegion(){
        List<OperationRegionDTO> list = reportApi.byRegion(systemBookCode, null, "2017-03");
        System.out.println();
    }


    @Test
    public void byBizday(){

        List<TrendDailyDTO> list = reportApi.byBizday(systemBookCode, "122|好的", "2017-10");

        System.out.println();

    }

    @Test
    public void byBizmonth(){
        List<TrendMonthlyDTO> trendMonthlies = reportApi.byBizmonth(systemBookCode, "", "2017");
        System.out.println();
    }

    @Test
    public void testAll(){
        List<BranchDTO> all = branchRpc.findAll("4020");
        System.out.println();
    }


    @Test
    public void testBranchTop(){
        List<SaleFinishMoneyTopDTO> moneyFinishRateBranchTop = reportApi.findMoneyFinishRateBranchTop("4020", "", null);
        System.out.println();
    }


    @Test
    public void testRegionTop(){
        reportApi.findMoneyFinishRateRegionTop("4020","123|haha",null);
    }

    @Test
    public void testCarriage(){
        List<ShipOrderSummary> carriageMoneyByCompanies = shipOrderRpc.findCarriageMoneyByCompanies("4020",null,new Date(),new Date(),null);
        System.out.println();
    }

    @Test
    public void testFindDetails(){
        List<ShipDetailDTO> details = shipOrderRpc.findDetails("4020", null, new Date(), new Date(), null);
        System.out.println();
    }














}
