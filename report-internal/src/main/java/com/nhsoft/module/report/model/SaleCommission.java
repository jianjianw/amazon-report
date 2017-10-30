package com.nhsoft.module.report.model;

import com.nhsoft.amazon.shared.AppConstants;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;

/**
 * SaleCommission generated by hbm2java
 */
@Entity
public class SaleCommission implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8672034237386178488L;
	@Id
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
	
	public static BigDecimal getItemCommission(SaleCommission saleCommission, BigDecimal qty, BigDecimal saleMoney,
	                                           BigDecimal price, BigDecimal cost) {
		BigDecimal commission = BigDecimal.ZERO;
		if (saleCommission == null) {
			return commission;
		}
		if (saleCommission.getCommissionType().equals(AppConstants.POS_ITEM_COMMISSION_TPYE_NONE)) {
			return commission;
		} else if (saleCommission.getCommissionType().equals(AppConstants.POS_ITEM_COMMISSION_TPYE_FIX)) {
			return saleCommission.getCommissionMoney().multiply(qty);
		} else if (saleCommission.getCommissionType().equals(AppConstants.POS_ITEM_COMMISSION_TPYE_SALE_MONEY)) {
			return saleMoney.multiply(saleCommission.getCommissionMoney());
		} else if (saleCommission.getCommissionType().equals(AppConstants.POS_ITEM_COMMISSION_TPYE_PROFIT)) {
			if (saleCommission.getCommissionBase() == null) {
				saleCommission.setCommissionBase(BigDecimal.ZERO);
			}
			if (saleCommission.getCommissionBase().compareTo(BigDecimal.ZERO) != 0) {
				return (price.subtract(saleCommission.getCommissionBase())).multiply(
						saleCommission.getCommissionMoney()).multiply(qty);
				
			} else {
				return saleMoney.subtract(qty.multiply(cost)).multiply(saleCommission.getCommissionMoney());
			}
		}
		return commission;
	}

}
