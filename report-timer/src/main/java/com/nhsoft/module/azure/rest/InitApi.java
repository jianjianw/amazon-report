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

    @RequestMapping(method = RequestMethod.GET,value="/init/{systemBookCode}/{dateFrom}/{dateTo}")
    public String initAzure(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom,@PathVariable("dateTo") String dateTo){
        Date form = null;
        Date to = null;
        try {
            SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMdd");
            form = sdf.parse(dateFrom);
            to = sdf.parse(dateTo);
        } catch (ParseException e) {
            throw new RuntimeException("日期解析失败");
        }
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBookCode, form, to);
        azureService.insertBranchDaily(systemBookCode,branchDailySummary);
        return "SUCCESS";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/itemDetail")
    public String itemDetail(){
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = sdf.parse("2017-01-01");
            dateTo = sdf.parse("2017-10-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String systembookCode = "4344";
        List<ItemDailyDetail> itemDailyDetailSummary = posOrderRpc.findItemDailyDetailSummary(systembookCode, dateFrom, dateTo);
        azureService.insertItemDailyDetail(systembookCode,itemDailyDetailSummary);
        return "SUCCESS";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/init/branch/{systemBookCode}")
    public String insertBranch(@PathVariable("systemBookCode") String systemBookCode){//@PathVariable("systemBookCode")
        List<Branch> branch = branchRpc.findBranch(systemBookCode);
        azureService.insertBranch(systemBookCode,branch);
        return "SUCCESS";
    }
}
