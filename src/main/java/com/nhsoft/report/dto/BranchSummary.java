package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchSummary implements Serializable {

    /**
     *
     sb.append("select p.branch_num, ");
     sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_gross_profit else detail.order_detail_gross_profit end) as profit, ");
     sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money when detail.order_detail_state_code = 1 then detail.order_detail_payment_money end) as money, ");
     sb.append("sum(case when detail.order_detail_state_code = 4 then (-detail.order_detail_amount * detail.order_detail_cost) else (detail.order_detail_amount * detail.order_detail_cost) end) as cost ");
     sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on p.order_no = detail.order_no ");
     if(queryPosItem){
     sb.append("inner join pos_item as item on item.item_num = detail.item_num ");
     }
     *
     *
     * */


    private Integer branchNum;
    private BigDecimal profit;
    private BigDecimal paymentMoney;
    private BigDecimal cost;

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(BigDecimal paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
