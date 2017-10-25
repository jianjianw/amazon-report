package com.nhsoft.module.report;


import com.nhsoft.module.report.api.TestApi;
import com.nhsoft.module.report.api.dto.OperationRegionDTO;
import com.nhsoft.module.report.api.dto.OperationStoreDTO;
import com.nhsoft.module.report.api.dto.TrendDaily;
import com.nhsoft.module.report.api.dto.TrendMonthly;
import com.nhsoft.module.report.dto.BranchBizRevenueSummary;
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
    private TestApi testApi;

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
        List<OperationStoreDTO> test = testApi.byBranch(systemBookCode, null, "2017-10-04");
        System.out.println();
    }

    @Test
    public void testApiByRegion(){
        List<OperationRegionDTO> list = testApi.byRegion(systemBookCode, null, "2017-03");
        System.out.println();
    }


    @Test
    public void byBizday(){

        List<TrendDaily> list = testApi.byBizday(systemBookCode, "", "2017-10");

        System.out.println();

    }

    @Test
    public void byBizmonth(){
        List<TrendMonthly> trendMonthlies = testApi.byBizmonth(systemBookCode, "", "2017");
        System.out.println();
    }

    @Test
    public void testAll(){
        List<BranchDTO> all = branchRpc.findAll("4020");
        System.out.println();
    }


}
