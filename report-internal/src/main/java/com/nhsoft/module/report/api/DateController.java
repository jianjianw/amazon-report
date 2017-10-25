package com.nhsoft.module.report.api;

import com.nhsoft.module.report.util.DateUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
public class DateController {
    @RequestMapping("/echo")
    public String echo(){
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        String dateStr = DateUtil.getDateTimeString(time);
        return dateStr;
    }
}
