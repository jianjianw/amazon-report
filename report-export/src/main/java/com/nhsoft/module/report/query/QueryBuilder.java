package com.nhsoft.module.report.query;

import java.io.Serializable;

public abstract  class QueryBuilder implements Serializable {

    private static final long serialVersionUID = 5423299014884810696L;
    protected String systemBookCode;

    public String getSystemBookCode() {
        return systemBookCode;
    }


    public void setSystemBookCode(String systemBookCode) {
        this.systemBookCode = systemBookCode;
    }
    

}
