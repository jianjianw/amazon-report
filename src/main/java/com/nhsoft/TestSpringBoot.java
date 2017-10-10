package com.nhsoft;

import com.nhsoft.report.dto.BranchConsumeReport;
import com.nhsoft.report.dto.BranchDepositReport;
import com.nhsoft.report.dto.BranchMoneyReport;
import com.nhsoft.report.dto.RegionMoneyReport;
import com.nhsoft.report.model.Branch;
import com.nhsoft.report.model.BranchRegion;
import com.nhsoft.report.rpc.ReportRpc;
import com.nhsoft.report.service.CardDepositService;
import com.nhsoft.report.util.AppConstants;
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

    @Test
    public void testRpcMoney() {
        /**
         * /**
         * 按分店查询营业额
         * @param systemBookCode
         * @param branchNums 分店号
         * @param queryBy 统计类型 按营业额 or 按储值额 or 按发卡量
         * @param dateFrom 时间起
         * @param dateTo 时间止
         * @return
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = sdf.parse("2017-02-10");
            dateTo = sdf.parse("2017-02-11");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String systemBookCode = "2048";
        String queryBy = AppConstants.BUSINESS_TREND_PAYMENT;
        List<Branch> all = reportRpc.findAll(systemBookCode);
        List<Integer> branchNum = new ArrayList<Integer>();
        for (Branch b : all) {
            branchNum.add(b.getId().getBranchNum());
        }
        List<BranchMoneyReport> moneyByBranch = reportRpc.findMoneyByBranch(systemBookCode, branchNum, queryBy, dateFrom, dateTo, false);


        System.out.println();
    }

    @Test
    public void testRegion1(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = sdf.parse("2017-02-10");
            dateTo = sdf.parse("2017-02-11");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String systemBookCode = "4344";
        String queryBy = AppConstants.BUSINESS_TREND_PAYMENT;
        List<BranchRegion> branchRegion = reportRpc.findBranchRegion(systemBookCode);
        List<Integer> list = new ArrayList<>();
        for (BranchRegion b :  branchRegion){
            Integer branchRegionNum = b.getBranchRegionNum();
            list.add(branchRegionNum);
        }
        List<RegionMoneyReport> moneyByRegion = reportRpc.findMoneyByRegion(systemBookCode, list,queryBy, dateFrom, dateTo, false);
        System.out.println();

    }


    @Test
    public void testBranch(){
        List<Branch> all = reportRpc.findAll("11001");
        System.out.println();

    }

    @Test
    public void testDeposit(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = sdf.parse("2013-06-19 09:50:56");
            dateTo = sdf.parse("2013-06-20 09:50:56");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String systemBookCode = "4020";
        List<Branch> all = reportRpc.findAll(systemBookCode);
        List<Integer> branchNums = new ArrayList<Integer>();
        for (Branch b : all) {
            Integer branchNum = b.getId().getBranchNum();
            branchNums.add(branchNum);
        }
        //List<Object[]> branchSum = cardDepositService.findBranchSum(systemBookCode, branchNums, dateFrom, dateTo);
        List<BranchDepositReport> depositByBranch = reportRpc.findDepositByBranch(systemBookCode, branchNums, dateFrom, dateTo);
        System.out.println();
    }

    @Test
    public void testConsume(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = sdf.parse("2013-06-19 09:50:56");
            dateTo = sdf.parse("2017-02-15 00:21:15");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String systemBookCode = "4020";

        List<BranchConsumeReport> consumeByBranch = reportRpc.findConsumeByBranch(systemBookCode, null, dateFrom, null);
        System.out.println();
    }

    @Test
    public void testRegion(){

        List<BranchRegion> branchRegion = reportRpc.findBranchRegion("4020");
        System.out.println();
    }

    @Test
    public void testRegionBranch(){
        List<Branch> branchByBranchRegionNum = reportRpc.findBranchByBranchRegionNum("4020", 402000002);

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i<branchByBranchRegionNum.size(); i++){
            Integer branchNum = branchByBranchRegionNum.get(i).getId().getBranchNum();
            list.add(branchNum);
        }
        System.out.println();
    }


}
