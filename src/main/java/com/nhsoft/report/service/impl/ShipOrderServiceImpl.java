package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.ShipOrderDao;
import com.nhsoft.module.report.dto.ShipDetailsDTO;
import com.nhsoft.module.report.dto.ShipOrderSummary;
import com.nhsoft.report.service.ShipOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ShipOrderServiceImpl implements ShipOrderService {


    @Autowired
    private  ShipOrderDao shipOrderDao;
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
    public List<ShipDetailsDTO> findDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> companies) {

        List<Object[]> objects = shipOrderDao.findDetails(systemBookCode, branchNums, dateFrom, dateTo, companies);
        List<ShipDetailsDTO> list = new ArrayList<>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            ShipDetailsDTO shipDetailsDTO = new ShipDetailsDTO();
            shipDetailsDTO.setOrderId((String) object[0]);
            shipDetailsDTO.setCompany((String) object[1]);
            shipDetailsDTO.setCarriageMoney((BigDecimal) object[2]);
            shipDetailsDTO.setTime((Date) object[3]);
            list.add(shipDetailsDTO);
        }
        return list;
    }
















}
