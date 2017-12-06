package com.nhsoft.module.azure.model;

import java.io.Serializable;

/**
 *商品纬度
 * */
public class PosItemLat implements Serializable {

    private String systemBookCode;
    private Integer itemNum;
    private String category;

    public String getSystemBookCode() {
        return systemBookCode;
    }

    public void setSystemBookCode(String systemBookCode) {
        this.systemBookCode = systemBookCode;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
