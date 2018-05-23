package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.ScreenDao;
import com.nhsoft.module.report.dao.SettlementDao;
import com.nhsoft.module.report.dto.ScreenCategoryDTO;
import com.nhsoft.module.report.dto.ScreenItemSaleDTO;
import com.nhsoft.module.report.dto.ScreenMerchantDTO;
import com.nhsoft.report.utils.DateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class ScreenDaoImpl extends DaoImpl implements ScreenDao {

    @Override
    public List<ScreenItemSaleDTO> findItemSales(String systemBookCode, Integer branchNum) {
        StringBuilder sb = new StringBuilder("select top(10) item_num as itemNum, max(order_detail_item) as itemName, sum(case order_detail_state_code when 4 then -order_detail_payment_money when 1 then order_detail_payment_money end) as saleMoney, ");
        sb.append("sum(case when order_detail_state_code = 4 then -order_detail_amount else order_detail_amount end) as saleAmount, sum(case when order_detail_state_code = 4 then -1 else 1 end) as saleCount ");
        sb.append("from pos_order_detail with(nolock) where order_detail_book_code = '" + systemBookCode + "' and order_detail_branch_num = " + branchNum + " ");
        sb.append("and order_detail_order_state in (5, 7) and order_detail_state_code != 8 and item_num is not null and order_detail_bizday = '" + DateUtil.getDateShortStr(Calendar.getInstance().getTime()) + "' ");
        sb.append("group by item_num order by saleMoney desc");
        SQLQuery query = currentSession().createSQLQuery(sb.toString());
        query.setResultTransformer(new AliasToBeanResultTransformer(ScreenItemSaleDTO.class));
        return query.list();
    }

    @Override
    public List<ScreenCategoryDTO> findCategorySales(String systemBookCode, Integer branchNum) {
        StringBuilder sb = new StringBuilder("select item_category_code as categoryCode, max(item_category) as categoryName, sum(case order_detail_state_code when 4 then -order_detail_payment_money when 1 then order_detail_payment_money end) as saleMoney, ");
        sb.append("sum(case when order_detail_state_code = 4 then -order_detail_amount else order_detail_amount end) as saleAmount ");
        sb.append("from pos_order_detail as detail with(nolock) inner join pos_item as p with(nolock) on detail.item_num = p.item_num where order_detail_book_code = '" + systemBookCode + "' and order_detail_branch_num = " + branchNum + " and p.system_book_code = '" + systemBookCode + "' ");
        sb.append("and order_detail_order_state in (5, 7) and order_detail_state_code != 8 and detail.item_num is not null and order_detail_bizday = '" + DateUtil.getDateShortStr(Calendar.getInstance().getTime()) + "' ");
        sb.append("group by item_category_code order by saleMoney desc");
        SQLQuery query = currentSession().createSQLQuery(sb.toString());
        query.setResultTransformer(new AliasToBeanResultTransformer(ScreenCategoryDTO.class));
        return query.list();
    }

    @Override
    public List<Object[]> findMerchantSales(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
        StringBuilder sb = new StringBuilder("select merchant_num as merchantNum, sum(case order_detail_state_code when 4 then -order_detail_payment_money when 1 then order_detail_payment_money end) as saleMoney ");
        sb.append("from pos_order_detail as detail with(nolock) inner join pos_order as p with(nolock) on detail.order_no = p.order_no where order_detail_book_code = '" + systemBookCode + "' and order_detail_branch_num = " + branchNum + " and p.system_book_code = '" + systemBookCode + "' and p.branch_num = " + branchNum + " ");
        sb.append("and order_detail_order_state in (5, 7) and order_state_code in (5, 7) and order_detail_state_code != 8 and detail.item_num is not null and order_detail_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' and order_detail_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
        sb.append("group by merchant_num order by saleMoney desc");
        SQLQuery query = currentSession().createSQLQuery(sb.toString());
        return query.list();
    }
}