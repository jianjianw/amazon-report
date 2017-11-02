package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.PosItemLogSummaryDTO;
import com.nhsoft.module.report.query.StoreQueryCondition;
import com.nhsoft.module.report.rpc.PosItemLogRpc;
import com.nhsoft.module.report.service.PosItemLogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/11/2.
 */
public class PosItemLogRpcImpl implements PosItemLogRpc {
	
	
	@Autowired
	private PosItemLogService posItemLogService;
	
	
	@Override
	public List<PosItemLogSummaryDTO> findBranchItemFlagSummary(StoreQueryCondition storeQueryCondition) {
		List<Object[]> objects = posItemLogService.findBranchItemFlagSummary(storeQueryCondition.getSystemBookCode(), storeQueryCondition.getBranchNums(),
				storeQueryCondition.getDateStart(), storeQueryCondition.getDateEnd(), storeQueryCondition.getPosItemLogSummary(), storeQueryCondition.getItemNums(), storeQueryCondition.getStorehouseNum());
		List<PosItemLogSummaryDTO> list = new ArrayList<PosItemLogSummaryDTO>();
		Object[] object = null;
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
			
			PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
			dto.setBranchNum((Integer) object[0]);
			dto.setItemNum((Integer) object[1]);
			dto.setInoutFlag((Boolean) object[2]);
			dto.setQty(object[3] == null? BigDecimal.ZERO:(BigDecimal)object[3]);
			dto.setMoney(object[4] == null? BigDecimal.ZERO:(BigDecimal)object[4]);
			dto.setAssistQty(object[5] == null? BigDecimal.ZERO:(BigDecimal)object[5]);
			dto.setUseQty(object[6] == null? BigDecimal.ZERO:(BigDecimal)object[6]);
			dto.setSaleMoney(object[7] == null? BigDecimal.ZERO:(BigDecimal)object[7]);
			dto.setUseUnit(object[8] == null? "":(String)object[8]);
			list.add(dto);
		}
		
		return list;
	}
	
	@Override
	public List<PosItemLogSummaryDTO> findItemBizTypeFlagSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums, Integer storehouseNum) {
		return null;
	}
	
	@Override
	public List<PosItemLogSummaryDTO> findItemMatrixFlagSummary(String systemBookCode, List<Integer> branchNum, Date dateFrom, Date dateTo) {
		return null;
	}
	
	@Override
	public List<PosItemLogSummaryDTO> findItemFlagSummary(String systemBookCode, List<Integer> branchNum, Date dateFrom, Date dateTo) {
		return null;
	}
	
	@Override
	public List<PosItemLogSummaryDTO> findBranchFlagSummary(String systemBookCode, List<Integer> branchNum, Date dateFrom, Date dateTo) {
		return null;
	}
}
