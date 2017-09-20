package com.nhsoft.report.model;


import com.nhsoft.report.util.AppConstants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * StoreItemSupplier entity. @author MyEclipse Persistence Tools
 */

public class StoreItemSupplier  implements java.io.Serializable {

	private static final long serialVersionUID = -7364262264481498941L;
	private StoreItemSupplierId id;
	private Integer storeItemSupplierPri;
	private BigDecimal storeItemSupplierMin;
	private BigDecimal storeItemSupplierCost;
	private BigDecimal storeItemSupplierMaxPrice;
	private BigDecimal storeItemSupplierMinPrice;
	private BigDecimal storeItemSupplierLastestPrice;
	private String storeItemRefItemCode;
	private Date storeItemSupplierLastestTime;
	private BigDecimal storeItemCarriage;
	private String storeItemReturnType;
	private Boolean storeItemSupplierDefault;
	
	public StoreItemSupplier(){
		storeItemSupplierMin = BigDecimal.ZERO;
		storeItemSupplierCost = BigDecimal.ZERO;
		storeItemSupplierPri = 1;
		
	}

    public Boolean getStoreItemSupplierDefault() {
		return storeItemSupplierDefault;
	}

	public void setStoreItemSupplierDefault(Boolean storeItemSupplierDefault) {
		this.storeItemSupplierDefault = storeItemSupplierDefault;
	}

	public String getStoreItemReturnType() {
		return storeItemReturnType;
	}

	public void setStoreItemReturnType(String storeItemReturnType) {
		this.storeItemReturnType = storeItemReturnType;
	}

	public Date getStoreItemSupplierLastestTime() {
		return storeItemSupplierLastestTime;
	}

	public void setStoreItemSupplierLastestTime(Date storeItemSupplierLastestTime) {
		this.storeItemSupplierLastestTime = storeItemSupplierLastestTime;
	}

	public StoreItemSupplierId getId() {
        return this.id;
    }
    
    public void setId(StoreItemSupplierId id) {
        this.id = id;
    }

    public Integer getStoreItemSupplierPri() {
        return this.storeItemSupplierPri;
    }
    
    public void setStoreItemSupplierPri(Integer storeItemSupplierPri) {
        this.storeItemSupplierPri = storeItemSupplierPri;
    }

    public BigDecimal getStoreItemSupplierMin() {
		return storeItemSupplierMin;
	}

	public void setStoreItemSupplierMin(BigDecimal storeItemSupplierMin) {
		this.storeItemSupplierMin = storeItemSupplierMin;
	}

	public BigDecimal getStoreItemSupplierCost() {
		return storeItemSupplierCost;
	}

	public void setStoreItemSupplierCost(BigDecimal storeItemSupplierCost) {
		this.storeItemSupplierCost = storeItemSupplierCost;
	}

	public BigDecimal getStoreItemSupplierMaxPrice() {
		return storeItemSupplierMaxPrice;
	}

	public void setStoreItemSupplierMaxPrice(BigDecimal storeItemSupplierMaxPrice) {
		this.storeItemSupplierMaxPrice = storeItemSupplierMaxPrice;
	}

	public BigDecimal getStoreItemSupplierMinPrice() {
		return storeItemSupplierMinPrice;
	}

	public void setStoreItemSupplierMinPrice(BigDecimal storeItemSupplierMinPrice) {
		this.storeItemSupplierMinPrice = storeItemSupplierMinPrice;
	}

	public BigDecimal getStoreItemSupplierLastestPrice() {
		return storeItemSupplierLastestPrice;
	}

	public void setStoreItemSupplierLastestPrice(
			BigDecimal storeItemSupplierLastestPrice) {
		this.storeItemSupplierLastestPrice = storeItemSupplierLastestPrice;
	}

	public String getStoreItemRefItemCode() {
        return this.storeItemRefItemCode;
    }
    
    public void setStoreItemRefItemCode(String storeItemRefItemCode) {
        this.storeItemRefItemCode = storeItemRefItemCode;
    }

	public BigDecimal getStoreItemCarriage() {
		return storeItemCarriage;
	}

	public void setStoreItemCarriage(BigDecimal storeItemCarriage) {
		this.storeItemCarriage = storeItemCarriage;
	}
   


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoreItemSupplier other = (StoreItemSupplier) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public static StoreItemSupplier getDefault(List<StoreItemSupplier> storeItemSuppliers, Integer branchNum, Integer itemNum){
		if(storeItemSuppliers == null){
			return null;
		}
		StoreItemSupplier centerSupplier = null;
		for(int i = 0;i < storeItemSuppliers.size();i++){
			StoreItemSupplier storeItemSupplier = storeItemSuppliers.get(i);
			if(!storeItemSupplier.getId().getItemNum().equals(itemNum)){
				continue;
			}
			if(storeItemSupplier.getId().getBranchNum().equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
				centerSupplier = storeItemSupplier;
			}
			if(storeItemSupplier.getId().getBranchNum().equals(branchNum)){
				return storeItemSupplier;
			}
		}
		return centerSupplier;
	}
	
	public BigDecimal getPrice(){
		if(storeItemSupplierCost != null && storeItemSupplierCost.compareTo(BigDecimal.ZERO) > 0){
			return storeItemSupplierCost;
		}
		if(storeItemSupplierLastestPrice != null && storeItemSupplierLastestPrice.compareTo(BigDecimal.ZERO) > 0){
			return storeItemSupplierLastestPrice;
		}
		return null;
		
	}
	





}