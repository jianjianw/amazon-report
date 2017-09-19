package com.nhsoft.report.model;



import java.math.BigDecimal;
import java.util.List;

/**
 * SaleCommission generated by hbm2java
 */
public class SaleCommission implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8672034237386178488L;
	private Integer itemNum;
	private String commissionType;
	private BigDecimal commissionMoney;
	private BigDecimal commissionMax;
	private BigDecimal commissionBase;

	public SaleCommission() {
	}

	public Integer getItemNum() {
		return this.itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getCommissionType() {
		return this.commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

	public BigDecimal getCommissionMoney() {
		return this.commissionMoney;
	}

	public void setCommissionMoney(BigDecimal commissionMoney) {
		this.commissionMoney = commissionMoney;
	}

	public BigDecimal getCommissionMax() {
		return this.commissionMax;
	}

	public void setCommissionMax(BigDecimal commissionMax) {
		this.commissionMax = commissionMax;
	}

	public BigDecimal getCommissionBase() {
		return this.commissionBase;
	}

	public void setCommissionBase(BigDecimal commissionBase) {
		this.commissionBase = commissionBase;
	}

	public static SaleCommission get(List<SaleCommission> saleCommissions, Integer itemNum) {
		for(int i = 0;i < saleCommissions.size();i++){
			SaleCommission saleCommission = saleCommissions.get(i);
			if(saleCommission.getItemNum().equals(itemNum)){
				saleCommissions.remove(i);
				return saleCommission;
			}
		}
		return null;
	}

}
