package com.nhsoft.report.dto;

import java.io.Serializable;

public class SaleByCategoryBranchSummary implements Serializable {

    /**
     *
     * sb.append("select detail.order_detail_branch_num, item.item_category, item.item_category_code, detail.order_detail_state_code, ");
     sb.append("sum(detail.order_detail_amount) as amount, sum(detail.order_detail_payment_money) as money, ");
     sb.append("sum(detail.order_detail_assist_amount) as assistAmount, count(detail.item_num) as amount_ ");
     sb.append("from pos_order_detail as detail with(nolock), pos_item as item with(nolock) ");
     *
     * */

    private Integer orderDetailBranchNum;

}
