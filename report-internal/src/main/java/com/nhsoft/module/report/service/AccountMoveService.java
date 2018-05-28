package com.nhsoft.module.report.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountMoveService {

    List<Object[]> findAccountMoveInMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

    List<Object[]> findAccountMoveInMoneyByBranchDay(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

}
