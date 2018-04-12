package com.nhsoft.module.report.rpc.impl;


import com.nhsoft.module.report.dto.PosItemDTO;
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
}
