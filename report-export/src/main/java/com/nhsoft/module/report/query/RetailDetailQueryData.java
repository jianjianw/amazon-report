package com.nhsoft.module.report.query;

import com.nhsoft.module.report.dto.QueryBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RetailDetailQueryData extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6962430787553312235L;
	private Date dtFromShiftTable;				//营业日开始时间
	private Date dtToShiftTable;					//营业日结束时间
	private List<Integer> branchNums;		//分店
	private List<Integer> posItemNums;		//商品
	private String posMachine;						//收款机
	private String saleMoneyFlag;					//实际销售额标记 大于等于小于
	private BigDecimal saleMoney;				//实际销售额
	private String orderNo;							//单据号
	private String cashier;								//收银员
	private String retailPriceFlag;					//实际零售价标记 大于等于小于
	private BigDecimal retailPrice;					//实际零售价
	private List<String> posClientFid;			//客户编号
	private String exceptionConditon;			//异常條件
	private String saler;//销售员
	private String saleType;//销售方式：微商城、实体店
	private Date timeFrom;
	private Date timeTo;
	
	
	public Date getTimeFrom() {
		return timeFrom;
	}
	
	public void setTimeFrom(Date timeFrom) {
		this.timeFrom = timeFrom;
	}
	
	public Date getTimeTo() {
		return timeTo;
	}
	
	public void setTimeTo(Date timeTo) {
		this.timeTo = timeTo;
	}
	
	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public String getSaler() {
		return saler;
	}

	public void setSaler(String saler) {
		this.saler = saler;
	}

	public Date getDtFromShiftTable() {
		return dtFromShiftTable;
	}

	public void setDtFromShiftTable(Date dtFromShiftTable) {
		this.dtFromShiftTable = dtFromShiftTable;
	}

	public Date getDtToShiftTable() {
		return dtToShiftTable;
	}

	public void setDtToShiftTable(Date dtToShiftTable) {
		this.dtToShiftTable = dtToShiftTable;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}

	public List<Integer> getPosItemNums() {
		return posItemNums;
	}

	public void setPosItemNums(List<Integer> posItemNums) {
		this.posItemNums = posItemNums;
	}

	public String getPosMachine() {
		return posMachine;
	}

	public void setPosMachine(String posMachine) {
		this.posMachine = posMachine;
	}

	public String getSaleMoneyFlag() {
		return saleMoneyFlag;
	}

	public void setSaleMoneyFlag(String saleMoneyFlag) {
		this.saleMoneyFlag = saleMoneyFlag;
	}

	public BigDecimal getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	public String getRetailPriceFlag() {
		return retailPriceFlag;
	}

	public void setRetailPriceFlag(String retailPriceFlag) {
		this.retailPriceFlag = retailPriceFlag;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	public List<String> getPosClientFid() {
		return posClientFid;
	}

	public void setPosClientFid(List<String> posClientFid) {
		this.posClientFid = posClientFid;
	}

	public String getExceptionConditon() {
		return exceptionConditon;
	}

	public void setExceptionConditon(String exceptionConditon) {
		this.exceptionConditon = exceptionConditon;
	}

	public void clear(){
		dtFromShiftTable = null;		
		 dtToShiftTable = null;		
		branchNums = null;		
		posItemNums = null;		
		posMachine = null;		
		saleMoneyFlag = null;		
		saleMoney = null;		
		 orderNo = null;		
		cashier = null;		
		retailPriceFlag = null;		
		retailPrice = null;		
		posClientFid = null;		
		exceptionConditon = null;		
	}
	
	@Override
	public boolean checkQueryBuild() {
		return false;
	}

}
