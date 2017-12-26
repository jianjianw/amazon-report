package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.PosItemLogSummaryDTO;
import com.nhsoft.module.report.query.StoreQueryCondition;
import com.nhsoft.module.report.rpc.PosItemLogRpc;
import com.nhsoft.module.report.service.PosItemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangqin on 2017/11/2.
 */
@Component
public class PosItemLogRpcImpl implements PosItemLogRpc {
	
	
	@Autowired
	private PosItemLogService posItemLogService;
	
	
	@Override
	public List<PosItemLogSummaryDTO> findBranchItemFlagSummary(StoreQueryCondition storeQueryCondition) {
		List<Object[]> objects = posItemLogService.findBranchItemFlagSummary(storeQueryCondition.getSystemBookCode(), storeQueryCondition.getBranchNums(),
				storeQueryCondition.getDateStart(), storeQueryCondition.getDateEnd(), storeQueryCondition.getPosItemLogSummary(), storeQueryCondition.getItemNums(), storeQueryCondition.getStorehouseNum());
		int size = objects.size();
		List<PosItemLogSummaryDTO> list = new ArrayList<PosItemLogSummaryDTO>(size);
		Object[] object = null;
		for(int i = 0;i < size;i++){
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
	public List<PosItemLogSummaryDTO> findItemBizTypeFlagSummary(StoreQueryCondition storeQueryCondition) {
		List<Object[]> objects = posItemLogService.findItemBizTypeFlagSummary(storeQueryCondition);
		int size = objects.size();
		List<PosItemLogSummaryDTO> list = new ArrayList<PosItemLogSummaryDTO>(size);
		Object[] object = null;
		for(int i = 0;i < size;i++){
			object = objects.get(i);
			
			PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
			dto.setItemNum((Integer) object[0]);
			dto.setBizday((String) object[1]);
			dto.setSummary((String) object[2]);
			dto.setInoutFlag((Boolean) object[3]);
			dto.setQty(object[4] == null? BigDecimal.ZERO:(BigDecimal)object[4]);
			dto.setMoney(object[5] == null? BigDecimal.ZERO:(BigDecimal)object[5]);
			dto.setAssistQty(object[6] == null? BigDecimal.ZERO:(BigDecimal)object[6]);
			list.add(dto);
		}
		
		return list;
	}
	
	@Override
	public List<PosItemLogSummaryDTO> findItemMatrixFlagSummary(StoreQueryCondition storeQueryCondition) {
		List<Object[]> objects = posItemLogService.findSumByItemFlag(storeQueryCondition.getSystemBookCode(), storeQueryCondition.getBranchNums(),
				storeQueryCondition.getDateStart(), storeQueryCondition.getDateEnd(), storeQueryCondition.getPosItemLogSummary(), storeQueryCondition.getItemNums(),
				storeQueryCondition.getStorehouseNum(),storeQueryCondition.getMemos());
		int size = objects.size();
		List<PosItemLogSummaryDTO> list = new ArrayList<PosItemLogSummaryDTO>(size);
		Object[] object = null;
		for(int i = 0;i < size;i++){
			object = objects.get(i);
			
			PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
			dto.setItemNum((Integer) object[0]);
			dto.setItemMatrixNum((Integer) object[1]);
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
	public List<PosItemLogSummaryDTO> findItemFlagSummary(StoreQueryCondition storeQueryCondition) {
		List<Object[]> objects = posItemLogService.findItemFlagSummary(storeQueryCondition);
		int size = objects.size();
		List<PosItemLogSummaryDTO> list = new ArrayList<PosItemLogSummaryDTO>(size);
		Object[] object = null;
		for(int i = 0;i < size;i++){
			object = objects.get(i);
			
			PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
			dto.setItemNum((Integer) object[0]);
			dto.setInoutFlag((Boolean) object[1]);
			dto.setQty(object[2] == null? BigDecimal.ZERO:(BigDecimal)object[2]);
			dto.setMoney(object[3] == null? BigDecimal.ZERO:(BigDecimal)object[3]);
			dto.setAssistQty(object[4] == null? BigDecimal.ZERO:(BigDecimal)object[4]);
			list.add(dto);
		}
		
		return list;
	}
	
	@Override
	public List<PosItemLogSummaryDTO> findBranchFlagSummary(StoreQueryCondition storeQueryCondition) {
		List<Object[]> objects = posItemLogService.findBranchFlagSummary(storeQueryCondition);
		int size = objects.size();
		List<PosItemLogSummaryDTO> list = new ArrayList<PosItemLogSummaryDTO>(size);
		Object[] object = null;
		for(int i = 0;i < size;i++){
			object = objects.get(i);
			
			PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
			dto.setBranchNum((Integer) object[0]);
			dto.setInoutFlag((Boolean) object[1]);
			dto.setQty(object[2] == null? BigDecimal.ZERO:(BigDecimal)object[2]);
			dto.setMoney(object[3] == null? BigDecimal.ZERO:(BigDecimal)object[3]);
			dto.setAssistQty(object[4] == null? BigDecimal.ZERO:(BigDecimal)object[4]);
			list.add(dto);
		}
		
		return list;
	}
}
