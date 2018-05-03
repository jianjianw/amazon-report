package com.nhsoft.module.report.rpc;


import com.nhsoft.module.report.dto.PosItemDTO;

import java.util.List;

public interface PosItemRpc {


    /**
     * 从缓存中读取帐套下所有商品
     * @param systemBookCode
     * @return
     */
    public List<PosItemDTO> findShortItems(String systemBookCode);


    /**
     * 根据主键查询
     * @param itemNums 商品主键列表
     * @return
     */
    public List<PosItemDTO> findByItemNums(List<Integer> itemNums);
}
