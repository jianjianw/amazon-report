package com.nhsoft.module.report.query;

import com.nhsoft.module.report.dto.QueryBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class PosItemPriceBandQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6030091841456729185L;
	private String systemBookCode;
	private Integer branchNum;           		//当前店
	private List<Integer> branchNums;			//查询分店列表
    private Date dateFrom;							//开始时间
    private Date dateTo; 								//结束时间
    private List<String> categoryCodeList; 	//商品类别
    private BigDecimal lowPrice; 					//廉价
    private BigDecimal cheap;   					//便宜
    private BigDecimal popular; 					//大众化
    private BigDecimal suitable; 					//适宜
    private BigDecimal higher; 					//较高
    private BigDecimal highest;					//最高
    private String type;            					//【调出，批发， 销售】
	
	@Override
	public boolean checkQueryBuild(){
		 if (dateFrom != null && dateTo != null) {
            if (dateFrom.after(dateTo)) {
                return false;
            }
        }
        if (systemBookCode == null) {
            return false;
        }
        return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean isThrowTowYears(){
		if(dateFrom.getYear() != dateTo.getYear()){
		    return false;
		}
		return true;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public List<String> getCategoryCodeList() {
		return categoryCodeList;
	}

	public void setCategoryCodeList(List<String> categoryCodeList) {
		this.categoryCodeList = categoryCodeList;
	}

	public BigDecimal getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(BigDecimal lowPrice) {
		this.lowPrice = lowPrice;
	}

	public BigDecimal getCheap() {
		return cheap;
	}

	public void setCheap(BigDecimal cheap) {
		this.cheap = cheap;
	}

	public BigDecimal getPopular() {
		return popular;
	}

	public void setPopular(BigDecimal popular) {
		this.popular = popular;
	}

	public BigDecimal getSuitable() {
		return suitable;
	}

	public void setSuitable(BigDecimal suitable) {
		this.suitable = suitable;
	}

	public BigDecimal getHigher() {
		return higher;
	}

	public void setHigher(BigDecimal higher) {
		this.higher = higher;
	}

	public BigDecimal getHighest() {
		return highest;
	}

	public void setHighest(BigDecimal highest) {
		this.highest = highest;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}
	
}
