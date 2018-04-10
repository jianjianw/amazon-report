package com.nhsoft.module.report.dto;

import com.nhsoft.module.report.query.QueryBuilder;

import java.util.Date;
import java.util.List;

public class ItemInventoryQueryDTO extends QueryBuilder {
    private static final long serialVersionUID = 6121536032225662128L;


    private List<String> itemCategorys;
    private Date dateFrom;
    private Date dateTo;
    private Boolean saleCrease;// 停售
    private Boolean stockCrease;// 停购
    private Boolean dromCrease; //休眠商品


    public List<String> getItemCategorys() {
        return itemCategorys;
    }

    public void setItemCategorys(List<String> itemCategorys) {
        this.itemCategorys = itemCategorys;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }


    public Boolean getSaleCrease() {
        return saleCrease;
    }

    public void setSaleCrease(Boolean saleCrease) {
        this.saleCrease = saleCrease;
    }

    public Boolean getStockCrease() {
        return stockCrease;
    }

    public void setStockCrease(Boolean stockCrease) {
        this.stockCrease = stockCrease;
    }

    public Boolean getDromCrease() {
        return dromCrease;
    }

    public void setDromCrease(Boolean dromCrease) {
        this.dromCrease = dromCrease;
    }
}
