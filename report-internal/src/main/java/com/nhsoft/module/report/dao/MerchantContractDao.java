package com.nhsoft.module.report.dao;

import com.nhsoft.module.report.model.MerchantContract;

import java.util.List;

public interface MerchantContractDao {

    /**
     * 查询当前商户的对应合同
     *
     * @param systemBookCode
     * @param branchNum
     * @param merchantNum
     * @return
     */
    List<MerchantContract> findByMerchantNum(String systemBookCode, Integer branchNum, List<Integer> merchantNums);

}
