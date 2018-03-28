package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.MonthReturnDTO;

import java.util.Date;
import java.util.List;

public interface ReturnOrderRpc {

    /**
     * 查询退货汇总
     * @param systemBookCode
     * @param branchNum
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public List<MonthReturnDTO> findReturnMonth(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String strDate);
}
