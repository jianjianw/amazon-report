package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleByBranchSummary implements Serializable {


    private Integer branchNum;
    private int stateCode;
    private BigDecimal amount;
    private BigDecimal money;
    private BigDecimal assistAmount;
    private Integer itemNum;
}
