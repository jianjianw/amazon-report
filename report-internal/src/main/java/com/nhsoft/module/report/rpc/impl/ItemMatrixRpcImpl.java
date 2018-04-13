package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.ItemMatrixDTO;
import com.nhsoft.module.report.rpc.ItemMatrixRpc;
import com.nhsoft.module.report.service.ItemMatrixService;
import com.nhsoft.report.utils.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemMatrixRpcImpl implements ItemMatrixRpc {

	@Autowired
	private ItemMatrixService itemMatrixService;

	@Override
	public ItemMatrixDTO read(String systemBookCode, Integer itemNum, Integer itemMatrixNum) {
		return CopyUtil.to(itemMatrixService.read(itemNum, itemMatrixNum), ItemMatrixDTO.class);
	}

	@Override
	public List<ItemMatrixDTO> findByItem(String systemBookCode, Integer itemNum) {
		return CopyUtil.toList(itemMatrixService.findByItem(itemNum), ItemMatrixDTO.class);
	}

}