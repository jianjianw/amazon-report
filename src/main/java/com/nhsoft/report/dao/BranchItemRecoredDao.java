package com.nhsoft.report.dao;



import java.util.List;

public interface BranchItemRecoredDao {
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

}
