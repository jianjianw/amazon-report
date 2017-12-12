package com.nhsoft.module.azure.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 *商品纬度
 * */
@Entity
public class PosItemLat implements Serializable {
    @Id
    private String systemBookCode;
    @Id
    private Integer itemNum;

    private String item_category;

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

    public String getItem_category() {
        return item_category;
    }

    public void setItem_category(String item_category) {
        this.item_category = item_category;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PosItemLat that = (PosItemLat) o;

        if (systemBookCode != null ? !systemBookCode.equals(that.systemBookCode) : that.systemBookCode != null)
            return false;
        if (itemNum != null ? !itemNum.equals(that.itemNum) : that.itemNum != null) return false;
        return item_category != null ? item_category.equals(that.item_category) : that.item_category == null;
    }

    @Override
    public int hashCode() {
        int result = systemBookCode != null ? systemBookCode.hashCode() : 0;
        result = 31 * result + (itemNum != null ? itemNum.hashCode() : 0);
        result = 31 * result + (item_category != null ? item_category.hashCode() : 0);
        return result;
    }
}
