package com.nhsoft.module.report.rpc;

import com.nhsoft.module.azure.model.PosItemLat;

import java.util.List;

public interface PosItemRpc {
    /**
     * bi 商品维度
     * */
    public List<PosItemLat> findItemLat(String systemBookCode);
}
