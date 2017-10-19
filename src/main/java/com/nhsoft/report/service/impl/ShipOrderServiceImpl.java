package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.ShipOrderDao;
import com.nhsoft.report.dto.ShipDetailSummary;
import com.nhsoft.report.dto.ShipMoneySummary;
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
    public List<ShipMoneySummary> findShipMoneyByCompanies(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> companies) {
        List<Object[]> objects = shipOrderDao.findShipMoneyByCompanies(systemBookCode, branchNums, dateFrom, dateTo, companies);
        List<ShipMoneySummary> list = new ArrayList<>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            ShipMoneySummary shipMoneySummary = new ShipMoneySummary();
            shipMoneySummary.setCompanies((String) object[0]);
            shipMoneySummary.setMoney((BigDecimal) object[1]);
            list.add(shipMoneySummary);
        }
        return list;
    }

    @Override
    public List<ShipDetailSummary> findShipDetailByCompanies(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> companies) {

        List<Object[]> objects = shipOrderDao.findShipDetailByCompanies(systemBookCode, branchNums, dateFrom, dateTo, companies);
        List<ShipDetailSummary> list = new ArrayList<>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            ShipDetailSummary shipDetailSummary = new ShipDetailSummary();
            shipDetailSummary.setOrderId((String) object[0]);
            shipDetailSummary.setCompanies((String) object[1]);
            shipDetailSummary.setMoney((BigDecimal) object[2]);
            shipDetailSummary.setTime((Date) object[3]);
            list.add(shipDetailSummary);
        }
        return list;
    }
















}
