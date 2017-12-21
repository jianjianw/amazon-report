package com.nhsoft.module.report.rpc;

import com.nhsoft.module.azure.model.PosItem;
import com.nhsoft.module.azure.model.PosItemLat;
import com.nhsoft.module.report.dto.PosItemDTO;

import java.util.Date;
import java.util.List;

public interface PosItemRpc {
    /**
     * bi 商品维度
     * */
    public List<PosItemLat> findItemLat(String systemBookCode);


    public List<PosItemDTO> findAll(String systemBookCode);
}
