package com.nhsoft.module.report.model;



import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * StoreMatrix generated by hbm2java
 */
@Entity
public class StoreMatrix implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7090355577718964721L;
	@EmbeddedId
	private StoreMatrixId id;
	private Boolean storeMatrixOrderEnabled;
	private BigDecimal storeMatrixBaseStock;
	private BigDecimal storeMatrixReorderPoint;
	private BigDecimal storeMatrixReorderQty;
	private String storeMatrixLocation;
	private BigDecimal storeMatrixCustOrderQty;
	private BigDecimal storeMatrixOnOrderQty;
	private Boolean storeMatrixPriceEnabled;
	private BigDecimal storeMatrixRegularPrice;
	@Column(name = "storeMatrixLevel2_Price")
	private BigDecimal storeMatrixLevel2Price;
	@Column(name = "storeMatrixLevel3_Price")
	private BigDecimal storeMatrixLevel3Price;
	@Column(name = "storeMatrixLevel4_Price")
	private BigDecimal storeMatrixLevel4Price;
	private BigDecimal storeMatrixMinPrice;
	private Boolean storeMatrixStockCeaseFlag;
	private Boolean storeMatrixSaleCeaseFlag;
	private Boolean storeMatrixRquestEnabled;
	private BigDecimal storeMatrixMinRequest;
	private BigDecimal storeMatrixMaxRequest;
	private BigDecimal storeMatrixCostPrice;
	private BigDecimal storeMatrixTransferPrice;
	private BigDecimal storeMatrixUpperStock;
	private Date storeMatrixLastEditTime;
	private String storeMatrixLastEditor;
	private Boolean storeMatrixSaleEnabled; //是否启用分店停售标记
	private Boolean storeMatrixStockEnabled; //是否启用分店停购标记
	
	
	public StoreMatrix() {
		storeMatrixOrderEnabled = false;
		storeMatrixBaseStock = BigDecimal.ZERO;
		storeMatrixReorderPoint = BigDecimal.ZERO;
		storeMatrixReorderQty = BigDecimal.ZERO;
		storeMatrixPriceEnabled = false;
		storeMatrixRegularPrice = BigDecimal.ZERO;
		storeMatrixLevel2Price = BigDecimal.ZERO;
		storeMatrixLevel3Price = BigDecimal.ZERO;
		storeMatrixLevel4Price = BigDecimal.ZERO;
		storeMatrixMinPrice = BigDecimal.ZERO;
		storeMatrixStockCeaseFlag = false;
		storeMatrixSaleCeaseFlag = false;
		storeMatrixRquestEnabled = false;
		storeMatrixMinRequest = BigDecimal.ZERO;
		storeMatrixMaxRequest = BigDecimal.ZERO;
		storeMatrixCostPrice = BigDecimal.ZERO;
		storeMatrixTransferPrice = BigDecimal.ZERO;
		storeMatrixUpperStock = BigDecimal.ZERO;
		storeMatrixCustOrderQty = BigDecimal.ZERO;
		storeMatrixOnOrderQty = BigDecimal.ZERO;
		storeMatrixSaleEnabled = false;
		
	}
	
	public Boolean getStoreMatrixStockEnabled() {
		return storeMatrixStockEnabled;
	}
	
	public void setStoreMatrixStockEnabled(Boolean storeMatrixStockEnabled) {
		this.storeMatrixStockEnabled = storeMatrixStockEnabled;
	}
	
	public Boolean getStoreMatrixSaleEnabled() {
		return storeMatrixSaleEnabled;
	}

	public void setStoreMatrixSaleEnabled(Boolean storeMatrixSaleEnabled) {
		this.storeMatrixSaleEnabled = storeMatrixSaleEnabled;
	}

	public StoreMatrixId getId() {
		return this.id;
	}

	public void setId(StoreMatrixId id) {
		this.id = id;
	}
	
	public BigDecimal getStoreMatrixBaseStock() {
		return this.storeMatrixBaseStock;
	}

	public void setStoreMatrixBaseStock(BigDecimal storeMatrixBaseStock) {
		this.storeMatrixBaseStock = storeMatrixBaseStock;
	}

	public BigDecimal getStoreMatrixReorderPoint() {
		return this.storeMatrixReorderPoint;
	}

	public void setStoreMatrixReorderPoint(BigDecimal storeMatrixReorderPoint) {
		this.storeMatrixReorderPoint = storeMatrixReorderPoint;
	}

	public BigDecimal getStoreMatrixReorderQty() {
		return this.storeMatrixReorderQty;
	}

	public void setStoreMatrixReorderQty(BigDecimal storeMatrixReorderQty) {
		this.storeMatrixReorderQty = storeMatrixReorderQty;
	}

	public String getStoreMatrixLocation() {
		return this.storeMatrixLocation;
	}

	public void setStoreMatrixLocation(String storeMatrixLocation) {
		this.storeMatrixLocation = storeMatrixLocation;
	}

	public BigDecimal getStoreMatrixCustOrderQty() {
		return this.storeMatrixCustOrderQty;
	}

	public void setStoreMatrixCustOrderQty(BigDecimal storeMatrixCustOrderQty) {
		this.storeMatrixCustOrderQty = storeMatrixCustOrderQty;
	}

	public BigDecimal getStoreMatrixOnOrderQty() {
		return this.storeMatrixOnOrderQty;
	}

	public void setStoreMatrixOnOrderQty(BigDecimal storeMatrixOnOrderQty) {
		this.storeMatrixOnOrderQty = storeMatrixOnOrderQty;
	}

	public BigDecimal getStoreMatrixRegularPrice() {
		return this.storeMatrixRegularPrice;
	}

	public void setStoreMatrixRegularPrice(BigDecimal storeMatrixRegularPrice) {
		this.storeMatrixRegularPrice = storeMatrixRegularPrice;
	}

	public BigDecimal getStoreMatrixLevel2Price() {
		return this.storeMatrixLevel2Price;
	}

	public void setStoreMatrixLevel2Price(BigDecimal storeMatrixLevel2Price) {
		this.storeMatrixLevel2Price = storeMatrixLevel2Price;
	}

	public BigDecimal getStoreMatrixLevel3Price() {
		return this.storeMatrixLevel3Price;
	}

	public void setStoreMatrixLevel3Price(BigDecimal storeMatrixLevel3Price) {
		this.storeMatrixLevel3Price = storeMatrixLevel3Price;
	}

	public BigDecimal getStoreMatrixLevel4Price() {
		return this.storeMatrixLevel4Price;
	}

	public void setStoreMatrixLevel4Price(BigDecimal storeMatrixLevel4Price) {
		this.storeMatrixLevel4Price = storeMatrixLevel4Price;
	}

	public BigDecimal getStoreMatrixMinPrice() {
		return this.storeMatrixMinPrice;
	}

	public void setStoreMatrixMinPrice(BigDecimal storeMatrixMinPrice) {
		this.storeMatrixMinPrice = storeMatrixMinPrice;
	}

	public Boolean getStoreMatrixStockCeaseFlag() {
		return this.storeMatrixStockCeaseFlag;
	}

	public void setStoreMatrixStockCeaseFlag(Boolean storeMatrixStockCeaseFlag) {
		this.storeMatrixStockCeaseFlag = storeMatrixStockCeaseFlag;
	}

	public Boolean getStoreMatrixSaleCeaseFlag() {
		return this.storeMatrixSaleCeaseFlag;
	}

	public void setStoreMatrixSaleCeaseFlag(Boolean storeMatrixSaleCeaseFlag) {
		this.storeMatrixSaleCeaseFlag = storeMatrixSaleCeaseFlag;
	}

	public BigDecimal getStoreMatrixMinRequest() {
		return this.storeMatrixMinRequest;
	}

	public void setStoreMatrixMinRequest(BigDecimal storeMatrixMinRequest) {
		this.storeMatrixMinRequest = storeMatrixMinRequest;
	}

	public BigDecimal getStoreMatrixMaxRequest() {
		return this.storeMatrixMaxRequest;
	}

	public void setStoreMatrixMaxRequest(BigDecimal storeMatrixMaxRequest) {
		this.storeMatrixMaxRequest = storeMatrixMaxRequest;
	}

	public BigDecimal getStoreMatrixCostPrice() {
		return this.storeMatrixCostPrice;
	}

	public void setStoreMatrixCostPrice(BigDecimal storeMatrixCostPrice) {
		this.storeMatrixCostPrice = storeMatrixCostPrice;
	}

	public BigDecimal getStoreMatrixTransferPrice() {
		return this.storeMatrixTransferPrice;
	}

	public void setStoreMatrixTransferPrice(BigDecimal storeMatrixTransferPrice) {
		this.storeMatrixTransferPrice = storeMatrixTransferPrice;
	}

	public BigDecimal getStoreMatrixUpperStock() {
		return this.storeMatrixUpperStock;
	}

	public void setStoreMatrixUpperStock(BigDecimal storeMatrixUpperStock) {
		this.storeMatrixUpperStock = storeMatrixUpperStock;
	}

	public Date getStoreMatrixLastEditTime() {
		return this.storeMatrixLastEditTime;
	}

	public void setStoreMatrixLastEditTime(Date storeMatrixLastEditTime) {
		this.storeMatrixLastEditTime = storeMatrixLastEditTime;
	}

	public String getStoreMatrixLastEditor() {
		return this.storeMatrixLastEditor;
	}

	public void setStoreMatrixLastEditor(String storeMatrixLastEditor) {
		this.storeMatrixLastEditor = storeMatrixLastEditor;
	}

	public Boolean getStoreMatrixOrderEnabled() {
		return storeMatrixOrderEnabled;
	}

	public void setStoreMatrixOrderEnabled(Boolean storeMatrixOrderEnabled) {
		this.storeMatrixOrderEnabled = storeMatrixOrderEnabled;
	}

	public Boolean getStoreMatrixPriceEnabled() {
		return storeMatrixPriceEnabled;
	}

	public void setStoreMatrixPriceEnabled(Boolean storeMatrixPriceEnabled) {
		this.storeMatrixPriceEnabled = storeMatrixPriceEnabled;
	}

	public Boolean getStoreMatrixRquestEnabled() {
		return storeMatrixRquestEnabled;
	}

	public void setStoreMatrixRquestEnabled(Boolean storeMatrixRquestEnabled) {
		this.storeMatrixRquestEnabled = storeMatrixRquestEnabled;
	}
	
	public BigDecimal getSalePrice() {
		
		if (!storeMatrixPriceEnabled) {
			return null;
		}
		if (storeMatrixRegularPrice.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		return storeMatrixRegularPrice;
	}
	
	public static StoreMatrix get(String systemBookCode, Integer branchNum, Integer itemNum,
	                                         List<StoreMatrix> storeMatrixs) {
		for (int i = 0; i < storeMatrixs.size(); i++) {
			StoreMatrix storeMatrix = storeMatrixs.get(i);
			if (storeMatrix.getId().getSystemBookCode().equals(systemBookCode)
					&& storeMatrix.getId().getBranchNum().equals(branchNum)
					&& storeMatrix.getId().getItemNum().equals(itemNum)) {
				return storeMatrix;
			}
		}
		return null;
	}

	public static BigDecimal getTransferPrice(StoreMatrix storeMatrix) {
		if (storeMatrix == null) {
			return null;
		}
		if (!storeMatrix.getStoreMatrixRquestEnabled()) {
			return null;
		}
		if (storeMatrix.getStoreMatrixTransferPrice().compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		return storeMatrix.getStoreMatrixTransferPrice();

	}
	
	public static BigDecimal getTransferPrice(List<StoreMatrix> storeMatrixs, Integer branchNum, Integer itemNum) {
		if(storeMatrixs == null){
			return null;
		}
		for (int i = 0; i < storeMatrixs.size(); i++) {
			StoreMatrix storeMatrix = storeMatrixs.get(i);
			if (!storeMatrix.getStoreMatrixRquestEnabled()) {
				continue;
			}
			if (storeMatrix.getStoreMatrixTransferPrice().compareTo(BigDecimal.ZERO) == 0) {
				continue;
			}
			if (storeMatrix.getId().getBranchNum().equals(branchNum)
					&& storeMatrix.getId().getItemNum().equals(itemNum)) {
				return storeMatrix.getStoreMatrixTransferPrice();
			}
		}
		return null;
	}
	
	public static BigDecimal getSalePrice(List<StoreMatrix> storeMatrixs, Integer branchNum, Integer itemNum) {
		for (int i = 0; i < storeMatrixs.size(); i++) {
			StoreMatrix storeMatrix = storeMatrixs.get(i);
			if (!storeMatrix.getStoreMatrixPriceEnabled()) {
				continue;
			}
			if (storeMatrix.getStoreMatrixRegularPrice().compareTo(BigDecimal.ZERO) == 0) {
				continue;
			}
			if (storeMatrix.getId().getBranchNum().equals(branchNum)
					&& storeMatrix.getId().getItemNum().equals(itemNum)) {
				return storeMatrix.getStoreMatrixRegularPrice();
			}
		}
		return null;
	}
	
	public static BigDecimal getSalePrice(StoreMatrix storeMatrix) {
		if (storeMatrix == null) {
			return null;
		}
		if (!storeMatrix.getStoreMatrixPriceEnabled()) {
			return null;
		}
		if (storeMatrix.getStoreMatrixRegularPrice().compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		return storeMatrix.getStoreMatrixRegularPrice();
	}

}
