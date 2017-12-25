package com.nhsoft.module.azure.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
/**
 * 商品资料
 * */
@Entity
public class PosItem implements Serializable {

    @Id
    private String systemBookCode;
    @Id
    private Integer itemNum;
    private String itemName;
    private String itemCategory;        //商品顶级类
    private String itemSubCategory;     //商品小类

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemSubCategory() {
        return itemSubCategory;
    }

    public void setItemSubCategory(String itemSubCategory) {
        this.itemSubCategory = itemSubCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PosItem posItem = (PosItem) o;

        if (systemBookCode != null ? !systemBookCode.equals(posItem.systemBookCode) : posItem.systemBookCode != null)
            return false;
        if (itemNum != null ? !itemNum.equals(posItem.itemNum) : posItem.itemNum != null) return false;
        if (itemName != null ? !itemName.equals(posItem.itemName) : posItem.itemName != null) return false;
        if (itemCategory != null ? !itemCategory.equals(posItem.itemCategory) : posItem.itemCategory != null)
            return false;
        return itemSubCategory != null ? itemSubCategory.equals(posItem.itemSubCategory) : posItem.itemSubCategory == null;
    }

    @Override
    public int hashCode() {
        int result = systemBookCode != null ? systemBookCode.hashCode() : 0;
        result = 31 * result + (itemNum != null ? itemNum.hashCode() : 0);
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        result = 31 * result + (itemCategory != null ? itemCategory.hashCode() : 0);
        result = 31 * result + (itemSubCategory != null ? itemSubCategory.hashCode() : 0);
        return result;
    }
}
