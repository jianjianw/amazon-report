package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.PurchaseOrderDao;
import com.nhsoft.module.report.model.PurchaseOrderDetail;
import com.nhsoft.module.report.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderDao purchaseOrderDao;

    @Override
    public List<Object[]> findUnReceivedItemAmount(String systemBookCode, Integer branchNum, List<Integer> itemNums) {
        return purchaseOrderDao.findUnReceivedItemAmount(systemBookCode,branchNum,itemNums);
    }

    @Override
    public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
        return purchaseOrderDao.countByBranch(systemBookCode,branchNum,dateFrom,dateTo);
    }

    @Override
    public Date getLastDate(String systemBookCode, Integer branchNum, Integer supplierNum) {
        return purchaseOrderDao.getLastDate(systemBookCode,branchNum,supplierNum);
    }

    @Override
    public List<PurchaseOrderDetail> findDetails(List<String> purchaseOrderFids) {
        return purchaseOrderDao.findDetails(purchaseOrderFids);
    }
}
