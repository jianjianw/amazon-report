package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemSaleQtySummary implements Serializable {



    private Integer itemNum;
    private Integer matrixNum;





    /**
     * StringBuffer sb = new StringBuffer();
     sb.append("select i.itemNum, i.collectDetailItemMatrixNum, i.collectDetailInoutFlag, sum(i.collectDetailAmount), sum(i.collectDetailMoney), sum(i.collectDetailAssistAmount)  ");
     sb.append("from InventoryCollectDetail as i ");


     TransferProfitQuery transferProfitQuery = new TransferProfitQuery();
     transferProfitQuery.setSystemBookCode(systemBookCode);
     transferProfitQuery.setDistributionBranchNums(transferBranchNums);
     transferProfitQuery.setResponseBranchNums(branchNums);
     transferProfitQuery.setDtFrom(dateFrom);
     transferProfitQuery.setDtTo(dateTo);
     transferProfitQuery.setCategoryCodes(categoryCodeList);
     transferProfitQuery.setItemNums(itemNums);
     Criteria criteria = createProfitCriteria(transferProfitQuery);
     criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("detail.itemNum")).add(Projections.groupProperty("detail.outOrderDetailItemMatrixNum"))
     .add(Projections.sum("detail.outOrderDetailQty")).add(Projections.sum("detail.outOrderDetailSaleSubtotal")).add(Projections.sum("detail.outOrderDetailSubtotal"))
     .add(Projections.sum("detail.outOrderDetailPresentBasicQty")));
     return criteria.list();



     Criteria criteria = createCriteria(systemBookCode, branchNum, dateFrom, dateTo, regionNums);
     if(itemNums != null && itemNums.size() > 0){
     criteria.add(Restrictions.in("detail.itemNum", itemNums));
     }
     criteria.setProjection(Projections.projectionList()
     .add(Projections.groupProperty("detail.itemNum"))
     .add(Projections.groupProperty("detail.orderDetailItemMatrixNum"))
     .add(Projections.sum("detail.orderDetailQty"))
     .add(Projections.sum("detail.orderDetailMoney"))
     .add(Projections.sqlProjection("sum(order_detail_cost * order_detail_qty) as cost"
     , new String[]{"cost"}, new Type[]{StandardBasicTypes.BIG_DECIMAL}))
     .add(Projections.sum("detail.orderDetailPresentQty"))
     );
     return criteria.list();

     * */





}
