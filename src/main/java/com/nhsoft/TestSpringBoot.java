package com.nhsoft;

import com.nhsoft.report.dao.impl.TransferOutMoney;
import com.nhsoft.report.dto.*;
import com.nhsoft.report.model.Branch;
import com.nhsoft.report.model.BranchRegion;
import com.nhsoft.report.rpc.ReportRpc;
import com.nhsoft.report.service.CardDepositService;
import com.nhsoft.report.util.AppConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSpringBoot {

    @Autowired
    private ReportRpc reportRpc;
    @Autowired
    private CardDepositService cardDepositService;


    Date dateFrom = null;
    Date dateTo = null;
    List<Integer> branchNums = null;
    String systemBookCode = "4020";
    @Before
    public void date(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            dateFrom = sdf.parse("2017-10-03");
            dateTo = sdf.parse("2017-10-14");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Branch> all = reportRpc.findAll(systemBookCode);
        branchNums = new ArrayList<Integer>();
        for (Branch b : all) {
            Integer branchNum = b.getId().getBranchNum();
            branchNums.add(branchNum);
        }
    }

    @Test
    public void findBranch(){
        List<Branch> all = reportRpc.findAll(systemBookCode);
        System.out.println();
    }


    @Test
    public void testRpcMoney() {

        String queryBy = AppConstants.BUSINESS_TREND_PAYMENT;
        List<BranchMoneyReport> moneyByBranch = reportRpc.findMoneyByBranch(systemBookCode, branchNums, queryBy, dateFrom, dateTo, false);
        System.out.println();
    }


    @Test
    public void testBranch(){
        List<Branch> all = reportRpc.findAll(systemBookCode);
        System.out.println();

    }

    @Test
    public void testDeposit(){
        List<BranchDepositReport> depositByBranch = reportRpc.findDepositByBranch(systemBookCode, branchNums, dateFrom, dateTo);
        System.out.println();
    }

    @Test
    public void testConsume(){

        List<BranchConsumeReport> consumeByBranch = reportRpc.findConsumeByBranch(systemBookCode, null, dateFrom, null);
        System.out.println();
    }

    @Test
    public void testRegion(){
        List<BranchRegion> branchRegion = reportRpc.findBranchRegion(systemBookCode);
        System.out.println();
    }

    @Test
    public void testRegionBranch(){
        List<Branch> branchByBranchRegionNum = reportRpc.findBranchByBranchRegionNum(systemBookCode, 402000002);

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i<branchByBranchRegionNum.size(); i++){
            Integer branchNum = branchByBranchRegionNum.get(i).getId().getBranchNum();
            list.add(branchNum);
        }
        System.out.println();
    }


    @Test
    public void testLossMoney(){
        List<LossMoneyReport> lossMoneyByBranch = reportRpc.findLossMoneyByBranch(systemBookCode, branchNums,dateFrom, dateTo);
        System.out.println();
    }

    @Test
    public void testDiffMoney(){
        List<DifferenceMoney> differenceMoneyByBranch = reportRpc.findDifferenceMoneyByBranch(systemBookCode, branchNums, dateFrom, dateTo);
        System.out.println();
    }

    @Test
    public void testCardUserCount(){
        List<CardUserCount> cardUserCountByBranch = reportRpc.findCardUserCountByBranch(systemBookCode, branchNums, dateFrom, dateTo);
        System.out.println();
    }

    @Test
    public void testCheckMoney(){
        List<CheckMoney> checkMoneyByBranch = reportRpc.findCheckMoneyByBranch(systemBookCode, branchNums, dateFrom, dateTo);
        System.out.println();
    }

    @Test
    public void testSaleMoney(){
        List<SaleMoneyGoals> saleMoneyGoalsByBranch = reportRpc.findSaleMoneyGoalsByBranch(systemBookCode, branchNums, dateFrom, dateTo,AppConstants.BUSINESS_DATE_SOME_MONTH);
        System.out.println();
    }

    @Test
    public void testBranchArea(){
        List<BranchArea> branchArea = reportRpc.findBranchArea(systemBookCode, branchNums);
        System.out.println();
    }

    @Test
    public void testCauseMoney(){
        List<AdjustmentCauseMoney> adjustmentCauseMoneyByBranch = reportRpc.findAdjustmentCauseMoneyByBranch(systemBookCode, branchNums, null, null);
        System.out.println();
    }

    @Test
    public void testOutMoney(){

        List<TransferOutMoney> transferOutMoneyByBranch = reportRpc.findTransferOutMoneyByBranch(systemBookCode, branchNums, dateFrom, dateTo);
        System.out.println();
    }


}
