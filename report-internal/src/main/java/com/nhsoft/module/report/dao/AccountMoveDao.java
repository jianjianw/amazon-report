package com.nhsoft.module.report.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountMoveDao {

    List<Object[]> findAccountMoveInMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> groups);
}
