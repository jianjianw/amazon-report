package com.nhsoft.report.rpc;

import com.nhsoft.report.dao.impl.TransferOutMoney;

import java.util.Date;
import java.util.List;

public interface TransferOutOrderRpc {

    /***
     * 按分店查询配送额
     * @param systemBookCode
     * @param branchNums 分店号
     * @param dateFrom 时间起
     * @param dateTo 时间止
     */
    public List<TransferOutMoney> findTransferOutMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

}
