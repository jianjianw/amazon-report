package com.nhsoft.module.azure.rest;

import com.nhsoft.module.azure.model.*;
import com.nhsoft.module.azure.service.AzureService;
import com.nhsoft.module.report.dto.BranchDTO;
import com.nhsoft.module.report.rpc.BranchRpc;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/azure")
public class InitApi {

    @Autowired
    private PosOrderRpc posOrderRpc;
    @Autowired
    private AzureService azureService;
    @Autowired
    private BranchRpc branchRpc;

    @RequestMapping(method = RequestMethod.GET,value = "/echo")
    public String echo(){
        Date time = Calendar.getInstance().getTime();
        return time.toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/init/branchDaily/{systemBookCode}/{dateFrom}/{dateTo}")
    public String initAzure(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) {
        Date form = null;
        Date to = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            form = sdf.parse(dateFrom);
            to = sdf.parse(dateTo);
        } catch (ParseException e) {
            throw new RuntimeException("日期解析失败");
        }
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBookCode, form, to);
        azureService.batchSaveBranchDailies(systemBookCode, branchDailySummary,form,to);
        return "SUCCESS";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/init/branch/{systemBookCode}")
    public String insertBranch(@PathVariable("systemBookCode") String systemBookCode) {//@PathVariable("systemBookCode")
        List<BranchDTO> brachDTO = branchRpc.findInCache(systemBookCode);
        List<Branch> list = new ArrayList<Branch>();
       /* for (int i = 0; i < brachDTO.size(); i++) {
            BranchDTO branchDTO = brachDTO.get(i);
            Branch branch = new Branch();
            branch.setSystemBookCode(systemBookCode);
            branch.setBranchNum(branchDTO.getBranchNum());
            branch.setBranchCode(branchDTO.getBranchCode());
            branch.setBranchName(branchDTO.getBranchName());
            branch.setBranchActived(branchDTO.getBranchActived());
            branch.setBranchRdc(branchDTO.getBranchRdc());
            branch.setBranchType(branchDTO.getBranchType());
            branch.setBranchArea(branchDTO.getBranchArea());
            branch.setBranchEmployeeCount(branchDTO.getBranchEmployeeCount());
            branch.setBranchCreateTime(branchDTO.getBranchCreateTime());
            list.add(branch);
        }*/
        Branch branch = new Branch();
        branch.setSystemBookCode(systemBookCode);
        branch.setBranchNum(99);
        branch.setBranchCode("01");
        branch.setBranchName("中心");
        branch.setBranchActived(true);
        //branch.setBranchRdc(true);
       /* branch.setBranchType();
        branch.setBranchArea();
        branch.setBranchEmployeeCount();
        branch.setBranchCreateTime();*/
        list.add(branch);


        azureService.batchSaveBranchs(systemBookCode, list);
        return "SUCCESS";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/init/itemDetail/{systemBookCode}/{dateFrom}/{dateTo}")
    public String initItemDeatil(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) {

        Date form = null;
        Date to = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            form = sdf.parse(dateFrom);
            to = sdf.parse(dateTo);
        } catch (ParseException e) {
            throw new RuntimeException("日期解析失败");
        }
        List<Integer> posItemNums = azureService.findPosItemNums(systemBookCode);
        List<ItemDailyDetail> itemDailyDetailSummary = posOrderRpc.findItemDailyDetailSummary(systemBookCode, form, to,posItemNums);
        azureService.batchSaveItemDailyDetails(systemBookCode, itemDailyDetailSummary,form,to);
        return "SUCCESS";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/init/item/{systemBookCode}/{dateFrom}/{dateTo}")
    public String initItem(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) {
        Date from = null;
        Date to = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            from = sdf.parse(dateFrom);
            to = sdf.parse(dateTo);
        } catch (ParseException e) {
            throw new RuntimeException("日期解析失败");
        }
        List<ItemDaily> itemDailySummary = posOrderRpc.findItemDailySummary(systemBookCode, from, to);
        azureService.batchSaveItemDailies(systemBookCode, itemDailySummary);
        return "SUCCESS";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete/branchDaily/{systemBookCode}/{dateFrom}/{dateTo}")
    public String deleteBranchDaily(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) {
        Date from = null;
        Date to = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            from = sdf.parse(dateFrom);
            to = sdf.parse(dateTo);
        } catch (ParseException e) {
            throw new RuntimeException("日期解析失败");
        }
        azureService.batchDeleteBranchDailies(systemBookCode,from,to);
        return "SUCCESS";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete/itemDetail/{systemBookCode}/{dateFrom}/{dateTo}")
    public String deleteItemDetailDaily(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) {
        Date from = null;
        Date to = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            from = sdf.parse(dateFrom);
            to = sdf.parse(dateTo);
        } catch (ParseException e) {
            throw new RuntimeException("日期解析失败");
        }
        azureService.batchDeleteItemDetailDailies(systemBookCode,from,to);
        return "SUCCESS";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/init/branchDailyDirect/{systemBookCode}/{dateFrom}/{dateTo}")
    public String initBranchDailyDirect(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) {
        Date from = null;
        Date to = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            from = sdf.parse(dateFrom);
            to = sdf.parse(dateTo);
        } catch (ParseException e) {
            throw new RuntimeException("日期解析失败");
        }
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBookCode, from, to);
        List<BranchDailyDirect> list = new ArrayList<BranchDailyDirect>();
        for (int i = 0; i <branchDailySummary.size() ; i++) {
            BranchDaily branchDaily = branchDailySummary.get(i);
            BranchDailyDirect branchDailyDirect = new BranchDailyDirect();
            branchDailyDirect.setSystemBookCode(branchDaily.getSystemBookCode());
            branchDailyDirect.setBranchNum(branchDaily.getBranchNum());
            branchDailyDirect.setShiftTableBizday(branchDaily.getShiftTableBizday());
            branchDailyDirect.setDailyMoney(branchDaily.getDailyMoney());
            branchDailyDirect.setDailyQty(branchDaily.getDailyQty());
            branchDailyDirect.setShiftTableDate(branchDaily.getShiftTableDate());
            branchDailyDirect.setDailyPrice(branchDaily.getDailyPrice());
            branchDailyDirect.setTargetMoney(branchDaily.getTargetMoney());
            list.add(branchDailyDirect);
        }
        azureService.batchSaveBranchDailyDirects(systemBookCode,list,from,to);
        return "SUCCESS";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/delete/branchDailyDirect/{systemBookCode}/{dateFrom}/{dateTo}")
    public String deleteBranchDailyDirect(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) {
        Date from = null;
        Date to = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            from = sdf.parse(dateFrom);
            to = sdf.parse(dateTo);
        } catch (ParseException e) {
            throw new RuntimeException("日期解析失败");
        }
        azureService.batchDeleteBranchDailyDirects(systemBookCode,from,to);
        return "SUCCESS";
    }

}
