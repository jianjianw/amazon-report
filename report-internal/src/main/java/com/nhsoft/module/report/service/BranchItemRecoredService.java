package com.nhsoft.module.report.service;

import java.util.List;

public interface BranchItemRecoredService {

    /**
     * 按商品汇总最后审核日期
     * @param systemBookCode
     * @param branchNum
     * @param storehouseNum
     * @param itemNums
     * @param branchItemRecoredTypes
     * @return
     */
    public List<Object[]> findItemAuditDate(String systemBookCode, Integer branchNum, Integer storehouseNum,
                                            List<Integer> itemNums, List<String> branchItemRecoredTypes);


    /**
     * 按商品汇总首次审核日期
     * @param systemBookCode
     * @param branchNum
     * @param storehouseNum
     * @param itemNums
     * @param branchItemRecoredTypes
     * @return
     */
    public List<Object[]> findItemMinAuditDate(String systemBookCode, Integer branchNum, Integer storehouseNum,
                                               List<Integer> itemNums, List<String> branchItemRecoredTypes);

}
