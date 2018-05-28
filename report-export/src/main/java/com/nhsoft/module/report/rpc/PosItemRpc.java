package com.nhsoft.module.report.rpc;


import com.nhsoft.module.report.dto.PosItemDTO;
import com.nhsoft.module.report.dto.PosItemKitDTO;
import com.nhsoft.module.report.queryBuilder.PosItemQuery;

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
    public List<PosItemDTO> findByItemNums(String systemBookCode,List<Integer> itemNums);

    /**
     * 查询带明细的组合商品
     * @param itemNums
     * @return
     */
    public List<PosItemKitDTO> findPosItemKitsWithDetails(String systemBookCode,List<Integer> itemNums);

    /**
     * 按条件查询
     * @param posItemQuery
     * @param sortField 排序字段
     * @param sortType ASC or DESC
     * @param offset 查询起始位
     * @param limit 查询数量
     * @return
     */
    public List<PosItemDTO> findByPosItemQuery(PosItemQuery posItemQuery, String sortField, String sortType, int offset, int limit);



    public List<PosItemDTO> findProperties(String systemBookCode, List<Integer> itemNums, String... properties);
}
