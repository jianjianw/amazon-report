package com.nhsoft;

import com.nhsoft.report.dto.BranchConsumeReport;
import com.nhsoft.report.dto.BranchDepositReport;
import com.nhsoft.report.dto.BranchMoneyReport;
import com.nhsoft.report.model.Branch;
import com.nhsoft.report.model.BranchRegion;
import com.nhsoft.report.rpc.ReportRpc;
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

    @Test
    public void testRpcMoney(){
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = sdf.parse("2017-02-10 00:20:41");
            dateTo = sdf.parse("2017-02-11 00:21:15");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String systemBookCode = "11001";
        List<Integer> branchNums = new ArrayList<Integer>();
        branchNums.add(99);
        String queryBy = AppConstants.BUSINESS_TREND_PAYMENT;

        List<BranchMoneyReport> moneyByBranch = reportRpc.findMoneyByBranch(systemBookCode, branchNums, queryBy, dateFrom, null,false);
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
            dateTo = sdf.parse("2017-02-15 00:21:15");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String systemBookCode = "4020";
        List<Integer> branchNums = new ArrayList<Integer>();
        branchNums.add(1);
        List<BranchDepositReport> depositByBranch = reportRpc.findDepositByBranch(systemBookCode, branchNums, dateFrom, null);
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
        List<Integer> branchNums = new ArrayList<Integer>();
        branchNums.add(1);
        List<BranchConsumeReport> consumeByBranch = reportRpc.findConsumeByBranch(systemBookCode, branchNums, dateFrom, null);
        System.out.println();
    }

    @Test
    public void testRegion(){

        List<BranchRegion> branchRegion = reportRpc.findBranchRegion("4344");
        System.out.println();
    }

    @Test
    public void testRegionBranch(){
        List<Branch> branchByBranchRegionNum = reportRpc.findBranchByBranchRegionNum("99999", 999990003);
        System.out.println();
    }


}
