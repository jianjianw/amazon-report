package com.nhsoft.module.azure.rest;

import com.nhsoft.module.azure.invoke.Invoke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value="/invoke")
public class InvokeApi {

    @Autowired
    private Invoke invoke;

    @RequestMapping(method = RequestMethod.GET, value = "/init/branchDaily/{systemBookCode}/{dateFrom}/{dateTo}")
    public String initBranchDaily(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) {
        Date form = null;
        Date to = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            form = sdf.parse(dateFrom);
            to = sdf.parse(dateTo);
        } catch (ParseException e) {
            throw new RuntimeException("日期解析失败");
        }
        invoke.saveBranchDailys(systemBookCode, form, to);
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
        invoke.saveBranchDailyDirects(systemBookCode, from, to);
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
        invoke.saveItemDailyDeatils(systemBookCode, form, to);
        return "SUCCESS";
    }



    //一下都是新加的表
    @RequestMapping(method = RequestMethod.GET, value = "/init/bizday/{systemBookCode}")
    public String saveBizday(@PathVariable("systemBookCode") String systemBookCode) {
        invoke.saveBizday(systemBookCode);
        return "SUCCESS";

    }


    //商品日销售
    @RequestMapping(method = RequestMethod.GET, value = "/init/saleDaily/{systemBookCode}/{dateFrom}/{dateTo}")
    public String initSaleDaily(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date from = sdf.parse(dateFrom);
        Date to = sdf.parse(dateTo);
        invoke.saveItemSaleDailys(systemBookCode, from, to);
        return "SUCCESS";
    }

    //商品日报损
    @RequestMapping(method = RequestMethod.GET, value = "/init/lossDaily/{systemBookCode}/{dateFrom}/{dateTo}")
    public String initLossDaily(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date from = sdf.parse(dateFrom);
        Date to = sdf.parse(dateTo);
        invoke.saveItemLossDailys(systemBookCode, from, to);
        return "SUCCESS";
    }

    //会员统计
    @RequestMapping(method = RequestMethod.GET, value = "/init/cardDaily/{systemBookCode}/{dateFrom}/{dateTo}")
    public String initCardDaily(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date from = sdf.parse(dateFrom);
        Date to = sdf.parse(dateTo);
        invoke.saveCardDailys(systemBookCode, from, to);
        return "SUCCESS";
    }


    @RequestMapping(method = RequestMethod.GET,value = "/init/item/{systemBookCode}")
    public String initItem(@PathVariable("systemBookCode") String systemBookCode) {
        invoke.saveItems(systemBookCode);
        return "SUCCESS";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/init/branch/{systemBookCode}")
    public String insertBranch(@PathVariable("systemBookCode") String systemBookCode) {//@PathVariable("systemBookCode")
        invoke.saveBranch(systemBookCode);
        return "SUCCESS";
    }
}
