package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.WholesaleAmountAndMoneyDTO;

import java.util.Date;
import java.util.List;

public interface WholesaleOrderRpc {


    /**
     * 根据营业日汇总批发数量和金额
     * */
    public List<WholesaleAmountAndMoneyDTO> findAmountAndMoneyByBiz(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> itemNums);

}
