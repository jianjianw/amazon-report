package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BranchSaleQtySummary implements Serializable {

    /**
     *
     * ("select detail.order_detail_branch_num, ");
     sb.append("sum(case when detail.order_detail_state_code = 1 then detail.order_detail_payment_money when detail.order_detail_state_code = 4 then -detail.order_detail_payment_money end) as money, ");
     sb.append("sum(case when detail.order_detail_state_code = 4 then -detail.order_detail_amount else order_detail_amount end) as amount ");
     sb.append("from pos_order_detail as detail with(nolock) ");
     * */

    private Integer branchNum;
    private BigDecimal paymentMoney;
    private BigDecimal amount;

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public BigDecimal getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(BigDecimal paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
