package com.nhsoft.report.dto;

import java.io.Serializable;

public class BranchBizSaleSummary implements Serializable {

    /**
     * "select detail.order_detail_branch_num, detail.order_detail_bizday, detail.order_detail_state_code, ");
     sb.append("sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money, ");
     sb.append("sum(detail.order_detail_assist_amount) as assistAmount, count(detail.item_num) as amount_ ");
     sb.append("from pos_order_detail as detail with(nolock) %s ");
     *
     * */

    private Integer branchNum;


}
