package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.ScreenDao;
import com.nhsoft.module.report.dto.ScreenCategoryDTO;
import com.nhsoft.module.report.dto.ScreenItemSaleDTO;
import com.nhsoft.module.report.dto.ScreenPlatformSaleDTO;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

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

    @Override
    public Integer readCardUsers(String systemBookCode, Integer branchNum, Boolean isNew) {
        StringBuilder sb = new StringBuilder("select count(*) from card_user where system_book_code = '" + systemBookCode + "' and card_user_enroll_shop = " + branchNum + " ");
        sb.append("and card_user_state_code in (3,7) and (card_user_deadline >= '" + DateUtil.getLongDateTimeStr(Calendar.getInstance().getTime())+ "' or card_user_deadline is null or card_user_deadline = '" + AppConstants.INIT_TIME + "') ");
        if(isNew != null && isNew) {
            sb.append("and card_user_date >= '" + DateUtil.getLongDateTimeStr(DateUtil.getMinOfDate(Calendar.getInstance().getTime())) + "' ");
        }
        SQLQuery query = currentSession().createSQLQuery(sb.toString());
        return AppUtil.getValue(query.uniqueResult(), Integer.class);
    }

    @Override
    public Integer[] readOrderCounts(String systemBookCode, Integer branchNum) {
        StringBuilder sb = new StringBuilder("select count(order_no) as orderCount, count(case when order_card_user_num > 0 then order_no end) as memberOrderCount, count(case when order_source in " + AppUtil.getStringParmeList(AppConstants.ONLINE_SOURCES) + " then order_no end) as onlineOrderCount from pos_order with(nolock) where system_book_code = '" + systemBookCode + "' and branch_num = " + branchNum + " ");
        sb.append("and shift_table_bizday =  '" + DateUtil.getDateShortStr(Calendar.getInstance().getTime()) + "' ");
        sb.append("and order_state_code in (5,7) ");
        SQLQuery query = currentSession().createSQLQuery(sb.toString());
        Object[] objects = (Object[]) query.uniqueResult();
        if(objects == null) {
            return new Integer[]{0, 0, 0};
        }
        return new Integer[]{AppUtil.getValue(objects[0], Integer.class), AppUtil.getValue(objects[1], Integer.class), AppUtil.getValue(objects[2], Integer.class)};
    }

    @Override
    public BigDecimal[] readOrderMoney(String systemBookCode, Integer branchNum) {
        StringBuilder sb = new StringBuilder("select sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as orderMoney, sum(case when order_card_user_num > 0 then order_payment_money + order_coupon_total_money - order_mgr_discount_money end) as memberOrderMoney from pos_order with(nolock) where system_book_code = '" + systemBookCode + "' and branch_num = " + branchNum + " ");
        sb.append("and shift_table_bizday =  '" + DateUtil.getDateShortStr(Calendar.getInstance().getTime()) + "' ");
        sb.append("and order_state_code in (5,7) ");
        SQLQuery query = currentSession().createSQLQuery(sb.toString());
        Object[] objects = (Object[]) query.uniqueResult();
        if(objects == null) {
            return new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO};
        }
        return new BigDecimal[]{AppUtil.getValue(objects[0], BigDecimal.class), AppUtil.getValue(objects[1], BigDecimal.class)};
    }

    @Override
    public List<ScreenPlatformSaleDTO> findPlatformSales(String systemBookCode, Integer branchNum) {
        StringBuilder sb = new StringBuilder("select order_source as platformName, sum(order_payment_money + order_coupon_total_money - order_mgr_discount_money) as saleMoney from pos_order with(nolock) where system_book_code = '" + systemBookCode + "' and branch_num = " + branchNum + " ");
        sb.append("and shift_table_bizday =  '" + DateUtil.getDateShortStr(Calendar.getInstance().getTime()) + "' ");
        sb.append("and order_state_code in (5,7) ");
        sb.append("and order_source in " + AppUtil.getStringParmeList(AppConstants.ONLINE_SOURCES));
        sb.append("group by order_source");
        SQLQuery query = currentSession().createSQLQuery(sb.toString());
        query.setResultTransformer(new AliasToBeanResultTransformer(ScreenPlatformSaleDTO.class));
        return query.list();
    }
}