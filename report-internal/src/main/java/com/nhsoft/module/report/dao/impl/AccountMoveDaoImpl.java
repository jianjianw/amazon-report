package com.nhsoft.module.report.dao.impl;

import com.nhsoft.module.report.dao.AccountMoveDao;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AccountMoveDaoImpl extends  DaoImpl implements AccountMoveDao {

    public List<Object[]> findAccountMoveInMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> groups) {
        String group = groups.stream().collect(Collectors.joining(", "));
        StringBuilder sb = new StringBuilder("select " + group + ", sum(account_move_money) from account_move with(nolock) where system_book_code = '" + systemBookCode + "' ");
        if(branchNums != null && branchNums.size() > 0) {
            sb.append("and branch_num in " + AppUtil.getIntegerParmeList(branchNums));
        }
        if(dateFrom != null) {
            sb.append("and account_move_bizday >= '" + DateUtil.getDateShortStr(dateFrom) + "' ");
        }
        if(dateTo != null) {
            sb.append("and account_move_bizday <= '" + DateUtil.getDateShortStr(dateTo) + "' ");
        }
        sb.append("and account_move_state_code = 3 group by " + group);
        SQLQuery query = currentSession().createSQLQuery(sb.toString());
        return query.list();
    }
}
