package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.AdjustmentReason;

import java.util.List;

public interface BookResourceRpc {

    /**
     * 读取库存调整原因
     * @param systemBookCode
     * @return
     */
    public List<AdjustmentReason> findAdjustmentReasons(String systemBookCode);






}
