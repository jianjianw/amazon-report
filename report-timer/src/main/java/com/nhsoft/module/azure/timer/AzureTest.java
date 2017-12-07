package com.nhsoft.module.azure.timer;

import com.nhsoft.module.azure.model.ItemDaily;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class AzureTest {


    @Autowired
    private PosOrderRpc posOrderRpc;

    public void test(){
        System.out.println("所有bean初始化完成");
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = sdf.parse("2017-06-01");
            dateTo = sdf.parse("2017-12-04");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final String systembookCode = "4344";
        List<ItemDaily> itemDailySummary = posOrderRpc.findItemDailySummary(systembookCode, dateFrom, dateTo);
        System.out.println(itemDailySummary.toString());
    }
}
