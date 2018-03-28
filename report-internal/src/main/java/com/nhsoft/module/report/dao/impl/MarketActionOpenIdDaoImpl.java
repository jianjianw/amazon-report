package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.MarketActionOpenIdDao;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Repository
public class MarketActionOpenIdDaoImpl extends DaoImpl implements MarketActionOpenIdDao {




    @Override
    public BigDecimal findPayMoneyByBranch(String systemBookCode, Date dateFrom, Date dateTo) {

        StringBuilder sb = new StringBuilder();

        sb.append("select sum(action_pay_money) from market_action_open_id where system_book_code = :systemBookCode ");

        if (dateFrom != null) {
            sb.append("and shift_table_bizday >= :bizFrom ");
        }
        if (dateTo != null) {
            sb.append("and shift_table_bizday <= :bizTo ");
        }
        SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
        sqlQuery.setString("systemBookCode", systemBookCode);
        if (dateFrom != null) {
            sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
        }
        if (dateTo != null) {
            sqlQuery.setString("bizTo", DateUtil.getDateShortStr(dateTo));
        }
        Object object = sqlQuery.uniqueResult();
        return object == null ? BigDecimal.ZERO : (BigDecimal) object;
    }

    @Override
    public List<Object[]> findPayMoneyByBranchBizday(String systemBookCode, Date dateFrom, Date dateTo) {
        StringBuilder sb = new StringBuilder();
        sb.append("select shift_table_bizday,sum(action_pay_money) from market_action_open_id where system_book_code = :systemBookCode ");

        if (dateFrom != null) {
            sb.append("and shift_table_bizday >= :bizFrom ");
        }
        if (dateTo != null) {
            sb.append("and shift_table_bizday <= :bizTo ");
        }
        sb.append("group by shift_table_bizday");

        SQLQuery sqlQuery = currentSession().createSQLQuery(sb.toString());
        sqlQuery.setString("systemBookCode", systemBookCode);
        if (dateFrom != null) {
            sqlQuery.setString("bizFrom", DateUtil.getDateShortStr(dateFrom));
        }
        if (dateTo != null) {
            sqlQuery.setString("bizTo", DateUtil.getDateShortStr(dateTo));
        }
        return sqlQuery.list();
    }
}
