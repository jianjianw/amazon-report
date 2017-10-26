package com.nhsoft.module.report.service.impl;


import com.alibaba.dubbo.rpc.protocol.dubbo.DubboInvoker;
import com.nhsoft.module.report.dao.ShipOrderDao;
import com.nhsoft.module.report.dto.ShipDetailDTO;
import com.nhsoft.module.report.dto.ShipOrderSummary;
import com.nhsoft.module.report.service.ShipOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ShipOrderServiceImpl implements ShipOrderService {


    @Autowired
    private ShipOrderDao shipOrderDao;
    @Override
    public List<ShipOrderSummary> findCarriageMoneyByCompanies(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> companies) {

        List<Object[]> objects = shipOrderDao.findCarriageMoneyByCompanies(systemBookCode, branchNums, dateFrom, dateTo, companies);
        List<ShipOrderSummary> list = new ArrayList<>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            ShipOrderSummary shipOrderSummary = new ShipOrderSummary();
            shipOrderSummary.setCompany((String) object[0]);
            shipOrderSummary.setCarriageMoney((BigDecimal) object[1]);
            list.add(shipOrderSummary);
        }
        return list;
    }

    @Override
    public List<ShipDetailDTO> findDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> companies) {

        List<Object[]> objects = shipOrderDao.findDetails(systemBookCode, branchNums, dateFrom, dateTo, companies);
        List<ShipDetailDTO> list = new ArrayList<>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            ShipDetailDTO shipDetailDTO = new ShipDetailDTO();
            shipDetailDTO.setOrderId((String) object[0]);
            shipDetailDTO.setCompany((String) object[1]);
            shipDetailDTO.setCarriageMoney((BigDecimal) object[2]);
            shipDetailDTO.setTime((Date) object[3]);
            list.add(shipDetailDTO);
        }
        return list;
    }
















}
