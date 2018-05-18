package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.RequestAnalysisDTO;
import com.nhsoft.module.report.dto.RequestOrderDTO;
import com.nhsoft.module.report.dto.RequestOrderDetailDTO;
import com.nhsoft.module.report.queryBuilder.RequestOrderQuery;

import java.util.Date;
import java.util.List;

public interface RequestOrderRpc {


    /**
     * 按商品汇总数据
     * @param systemBookCode
     * @param centerBranchNum
     * @param branchNum
     * @param dateFrom
     * @param dateTo
     * @param itemNums
     * @return
     */
    public List<RequestOrderDetailDTO> findItemSummary(String systemBookCode, Integer centerBranchNum, Integer branchNum,
                                                       Date dateFrom, Date dateTo, List<Integer> itemNums);


    /**
     * 按商品汇总数据 NEW
     * @return
     */
    public List<RequestOrderDetailDTO> findItemSummary(RequestOrderQuery query);
}
