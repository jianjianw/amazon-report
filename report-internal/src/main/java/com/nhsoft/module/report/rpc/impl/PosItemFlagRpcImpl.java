package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.ItemFlagDetailDTO;
import com.nhsoft.module.report.dto.PosItemFlagDTO;
import com.nhsoft.module.report.rpc.PosItemFlagRpc;
import com.nhsoft.module.report.service.PosItemFlagService;
import com.nhsoft.report.utils.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PosItemFlagRpcImpl implements PosItemFlagRpc {

	@Autowired
	private PosItemFlagService posItemFlagService;

	@Override
	public List<PosItemFlagDTO> find(String systemBookCode, String itemFlagType) {
		return CopyUtil.toList(posItemFlagService.find(systemBookCode, itemFlagType), PosItemFlagDTO.class);
	}

	@Override
	public List<ItemFlagDetailDTO> findDetails(String systemBookCode, int itemFlagNum) {
		return CopyUtil.toList(posItemFlagService.findDetails(itemFlagNum), ItemFlagDetailDTO.class);
	}

	@Override
	public PosItemFlagDTO readWithoutDetails(String systemBookCode, Integer itemFlagNum) {
		return CopyUtil.to(posItemFlagService.readWithoutDetails(itemFlagNum), PosItemFlagDTO.class);
	}

	@Override
	public List<Integer> findItemNums(String systemBookCode, int itemFlagNum) {
		return posItemFlagService.findItemNums(itemFlagNum);
	}

}