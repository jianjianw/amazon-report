package com.nhsoft.report.dao;


import java.util.Date;


public interface SettlementDao {




    public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);


    
    
}
