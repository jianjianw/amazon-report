package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.model.InvoiceChange;
import com.nhsoft.module.report.model.InvoiceChangeId;
import com.nhsoft.module.report.model.ShiftTable;

import java.util.List;

public interface InvoiceChangeDao {

	public InvoiceChange readByOrderNo(String systemBookCode, String orderNo);
	
	public List<InvoiceChange> findByShiftTable(ShiftTable shiftTable);
	
	/**
	 * 查询主键最大值的记录
	 * @param systemBookCode
	 * @param branchNum
	 * @param shiftTableBizday  营业日
	 * @param invoiceChangeMachine 终端标示号
	 * @return
	 */
	public InvoiceChange getMaxPK(String systemBookCode, Integer branchNum, String shiftTableBizday, String invoiceChangeMachine);

	public InvoiceChange read(InvoiceChangeId id);

	public void save(InvoiceChange invoiceChange);
	
	public String readContent(String orderNo);
	
	public List<InvoiceChange> find(List<String> orderNos);
}
