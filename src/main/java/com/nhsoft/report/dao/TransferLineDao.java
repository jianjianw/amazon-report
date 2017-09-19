package com.nhsoft.report.dao;


import com.nhsoft.report.model.TransferLine;
import com.nhsoft.report.model.TransferLineDetail;

import java.util.List;

public interface TransferLineDao {

	public TransferLine read(Integer transferLineNum);

	public List<TransferLine> find(String systemBookCode);

	public void saveOrUpdate(TransferLine transferLine);

	public Integer readNumByCode(String systemBookCode, Integer branchNum, String transferLineCode);

	public void delete(TransferLine transferLine);

	public List<TransferLineDetail> findDetails(String systemBookCode);

	/**
	 * 按帐套和分店号查询
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public List<TransferLine> findByBranch(String systemBookCode, Integer branchNum);

	/**
	 * 根据分店获取线路主键
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public Integer getNum(String systemBookCode, Integer branchNum);
}
