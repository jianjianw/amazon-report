package com.nhsoft.report.dao;



import java.util.Date;


public interface PreSettlementDao {



	   /**
     * 按门店统计单据数
     * @param systemBookCode
     * @param branchNum
	 * @param dateTo
	 * @param dateFrom
     * @return
     */
    public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);


	
}
