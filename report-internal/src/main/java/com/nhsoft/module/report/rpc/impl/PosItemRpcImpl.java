package com.nhsoft.module.report.rpc.impl;


import com.nhsoft.module.report.dto.PosItemDTO;
import com.nhsoft.module.report.dto.PosItemKitDTO;
import com.nhsoft.module.report.queryBuilder.PosItemQuery;
import com.nhsoft.module.report.rpc.PosItemRpc;
import com.nhsoft.module.report.service.PosItemService;
import com.nhsoft.report.utils.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PosItemRpcImpl implements PosItemRpc {

    @Autowired
    private PosItemService posItemService;

    @Override
    public List<PosItemDTO> findShortItems(String systemBookCode) {
        return CopyUtil.toList(posItemService.findShortItems(systemBookCode),PosItemDTO.class);
    }

    @Override
    public List<PosItemDTO> findByItemNums(String systemBookCode,List<Integer> itemNums) {
        return CopyUtil.toList(posItemService.findByItemNums(itemNums),PosItemDTO.class);
    }

    @Override
    public List<PosItemKitDTO> findPosItemKitsWithDetails(String systemBookCode,List<Integer> itemNums) {
        return CopyUtil.toList(posItemService.findPosItemKitsWithDetails(itemNums),PosItemKitDTO.class);
    }

    @Override
    public List<PosItemDTO> findByPosItemQuery(PosItemQuery posItemQuery, String sortField, String sortType, int offset, int limit) {
        return CopyUtil.toList(posItemService.findByPosItemQuery(posItemQuery,sortField,sortType,offset,limit),PosItemDTO.class);
    }

    @Override
    public List<PosItemDTO> findProperties(String systemBookCode, List<Integer> itemNums, String... properties) {
        return CopyUtil.toList(posItemService.findProperties(systemBookCode, itemNums, properties), PosItemDTO.class);
    }
}
