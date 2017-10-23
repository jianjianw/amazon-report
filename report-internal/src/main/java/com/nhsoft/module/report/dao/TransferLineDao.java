package com.nhsoft.module.report.dao;



import com.nhsoft.module.report.model.TransferLine;
import com.nhsoft.module.report.model.TransferLineDetail;

import java.util.List;

public interface TransferLineDao {


	public List<TransferLine> find(String systemBookCode);

	public List<TransferLineDetail> findDetails(String systemBookCode);

	/**
	 * 按帐套和分店号查询
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public List<TransferLine> findByBranch(String systemBookCode, Integer branchNum);

}
