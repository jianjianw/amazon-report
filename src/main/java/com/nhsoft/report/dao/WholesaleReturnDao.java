package com.nhsoft.report.dao;

import java.util.Date;
import java.util.List;

public interface WholesaleReturnDao {


	public List<Object[]> findItemSum(String systemBookCode, Integer branchNum, List<String> cleintFids, Date dateFrom, Date dateTo,
									  List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums);


	public List<Object[]> findSupplierSum(String systemBookCode, List<Integer> branchNums, List<String> clientFids, Date dateFrom,
										  Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums);


	public List<Object[]> findItemSupplierSum(String systemBookCode, List<Integer> branchNums, List<String> clientFids, Date dateFrom,
											  Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums);

	public List<Object[]> findBranchItemSupplierSum(String systemBookCode, List<Integer> branchNums, List<String> clientFids, Date dateFrom,
													Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums);

	public List<Object[]> findItemSupplierDetail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);





}