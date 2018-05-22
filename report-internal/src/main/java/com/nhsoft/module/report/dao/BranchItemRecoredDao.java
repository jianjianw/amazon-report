package com.nhsoft.module.report.dao;



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


	/**
	 * 按商品汇总最最近收货日期
	 * @param systemBookCode
	 * @param branchNums
	 * @param storehouseNum
	 * @param itemNums
	 * @param branchItemRecoredTypes
	 * @return
	 */
	public List<Object[]> findItemReceiveDate(String systemBookCode, List<Integer> branchNums, Integer storehouseNum,
											List<Integer> itemNums, List<String> branchItemRecoredTypes);


	/**
	 * 按商品汇总最后进价
	 * @param systemBookCode
	 * @param branchNum
	 * @param itemNums
	 * @param branchItemRecoredTypes
	 * @return
	 */
	public List<Object[]> findItemPrice(String systemBookCode, Integer branchNum,
										List<Integer> itemNums, List<String> branchItemRecoredTypes);


    /**
     * 按商品汇总最后生产日期
     * @param systemBookCode
     * @param branchNum
     * @param storehouseNum
     * @param itemNums
     * @param branchItemRecoredTypes
     * @return
     */
    public List<Object[]> findItemMaxProductDate(String systemBookCode, Integer branchNum, Integer storehouseNum,
                                                 List<Integer> itemNums, List<String> branchItemRecoredTypes);




}
