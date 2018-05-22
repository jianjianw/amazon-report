package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.RequestAnalysisDTO;
import com.nhsoft.module.report.dto.RequestOrderDTO;
import com.nhsoft.module.report.dto.RequestOrderDetailDTO;
import com.nhsoft.module.report.queryBuilder.RequestOrderQuery;
import com.nhsoft.module.report.rpc.RequestOrderRpc;
import com.nhsoft.module.report.service.RequestOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class RequestOrderRpcImpl implements RequestOrderRpc {

    @Autowired
    private RequestOrderService requestOrderService;


    @Override
    public List<RequestOrderDetailDTO> findItemSummary(String systemBookCode, Integer centerBranchNum, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums) {

        List<Object[]> objects = requestOrderService.findItemSummary(systemBookCode, centerBranchNum, branchNum, dateFrom, dateTo, itemNums);
        int size = objects.size();
        List<RequestOrderDetailDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            RequestOrderDetailDTO dto = new RequestOrderDetailDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setRequestOrderDetailQty((BigDecimal) object[1]);
            dto.setRequestOrderDetailUseQty((BigDecimal) object[2]);
            dto.setRequestOrderDetailOutQty((BigDecimal) object[3]);
            dto.setRequestOrderDetailOutUseQty((BigDecimal) object[4]);
            list.add(dto);

        }
        return list;
    }

    @Override
    public List<RequestOrderDetailDTO> findItemSummary(RequestOrderQuery query) {
        List<Object[]> objects = requestOrderService.findItemSummary(query);
        int size = objects.size();
        List<RequestOrderDetailDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            RequestOrderDetailDTO dto = new RequestOrderDetailDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setRequestOrderDetailQty((BigDecimal) object[1]);
            dto.setRequestOrderDetailUseQty((BigDecimal) object[2]);
            dto.setRequestOrderDetailOutQty((BigDecimal) object[3]);
            dto.setRequestOrderDetailOutUseQty((BigDecimal) object[4]);
            list.add(dto);

        }
        return list;
    }
}
