package com.nhsoft.module.azure.rest;

import com.nhsoft.module.azure.model.Branch;
import com.nhsoft.module.azure.model.BranchDaily;
import com.nhsoft.module.azure.model.ItemDaily;
import com.nhsoft.module.azure.model.ItemDailyDetail;
import com.nhsoft.module.azure.service.AzureService;
import com.nhsoft.module.report.rpc.BranchRpc;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @RequestMapping(method = RequestMethod.GET, value = "/init/{systemBookCode}/{dateFrom}/{dateTo}")
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
        azureService.insertBranchDaily(systemBookCode, branchDailySummary);
        return "SUCCESS";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/init/branch/{systemBookCode}")
    public String insertBranch(@PathVariable("systemBookCode") String systemBookCode) {//@PathVariable("systemBookCode")
        List<Branch> branch = branchRpc.findBranch(systemBookCode);
        azureService.insertBranch(systemBookCode, branch);
        return "SUCCESS";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/init/itemdetail/{systemBookCode}/{dateFrom}/{dateTo}")
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
        List<ItemDailyDetail> itemDailyDetailSummary = posOrderRpc.findItemDailyDetailSummary(systemBookCode, form, to);
        azureService.insertItemDailyDetail(systemBookCode, itemDailyDetailSummary);
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
        azureService.insertItemDaily(systemBookCode, itemDailySummary);
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
        azureService.deleteBranchDaily(systemBookCode,from,to);
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
        azureService.deleteItemDetailDaily(systemBookCode,from,to);
        return "SUCCESS";
    }
}
