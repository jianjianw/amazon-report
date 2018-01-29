package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.BranchBizItemSummary;
import com.nhsoft.module.report.dto.BranchItemRecoredDTO;

import java.util.List;

public interface BranchItemRecoredRpc {

    /**
     * 按商品汇总最后审核日期
     * @param systemBookCode
     * @param branchNum
     * @param storehouseNum
     * @param itemNums
     * @param branchItemRecoredTypes
     * @return
     */
    public List<BranchItemRecoredDTO> findItemAuditDate(String systemBookCode, Integer branchNum, Integer storehouseNum,
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
    public List<BranchItemRecoredDTO> findItemMinAuditDate(String systemBookCode, Integer branchNum, Integer storehouseNum,
                                               List<Integer> itemNums, List<String> branchItemRecoredTypes);

}
