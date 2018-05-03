package com.nhsoft.module.report.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryAnalysisDTO extends BaseModelDTO {

	private static final long serialVersionUID = -3709988588532059861L;
	private PosItemDTO posItem;
	private BigDecimal inventoryQty;// 当前库存（配送仓库库存）
	private BigDecimal basicInventoryQty;// 基础库存
	private BigDecimal lastWeekUseQty;// 7天用量
	private BigDecimal fourWeekUseQty;// 28天平均
	private BigDecimal inventoryBasicQty;// 补货订购点
	private BigDecimal onLoadQty;// 在订量
	private BigDecimal suggestionQty;// 建议订量
	private Integer canUseDate;// 可用天数=当前库存/(四周平均/7)（去小数取整）
	private Integer itemNum;
	private Integer itemMatrixNum;
	private ItemMatrixDTO itemMatrix;
	private String itemUnit;
	private BigDecimal itemRate;

	private List<SupplierDTO> suppliers = new ArrayList<>();// 供应商
	private Date nextPurchaseDate;// 下次采购日期
	private BigDecimal avgSaleQty;// 日均销量
	private BigDecimal buyQty;// 本次订购量

	private BigDecimal requestLostQty;// 要货缺货数量
	private Date lastProductDate;// 最后生产日期
	
	private BigDecimal itemMinQuantity;//最小加工量

	public InventoryAnalysisDTO(){
		setLastWeekUseQty(BigDecimal.ZERO);
		setFourWeekUseQty(BigDecimal.ZERO);
		setOnLoadQty(BigDecimal.ZERO);
		setInventoryQty(BigDecimal.ZERO);
		setInventoryBasicQty(BigDecimal.ZERO);
		setSuggestionQty(BigDecimal.ZERO);
		setBasicInventoryQty(BigDecimal.ZERO);
		itemMatrixNum = 0;
		setAvgSaleQty(BigDecimal.ZERO);
		setBuyQty(BigDecimal.ZERO);
		setRequestLostQty(BigDecimal.ZERO);
	}

	public BigDecimal getItemMinQuantity() {
		return itemMinQuantity;
	}



	public void setItemMinQuantity(BigDecimal itemMinQuantity) {
		this.itemMinQuantity = itemMinQuantity;
	}


	public PosItemDTO getPosItem() {
		return posItem;
	}

	public void setPosItem(PosItemDTO posItem) {
		this.posItem = posItem;
	}

	public BigDecimal getInventoryQty() {
		return inventoryQty;
	}

	public void setInventoryQty(BigDecimal inventoryQty) {
		this.inventoryQty = inventoryQty;
	}

	public BigDecimal getBasicInventoryQty() {
		return basicInventoryQty;
	}

	public void setBasicInventoryQty(BigDecimal basicInventoryQty) {
		this.basicInventoryQty = basicInventoryQty;
	}

	public BigDecimal getLastWeekUseQty() {
		return lastWeekUseQty;
	}

	public void setLastWeekUseQty(BigDecimal lastWeekUseQty) {
		this.lastWeekUseQty = lastWeekUseQty;
	}

	public BigDecimal getFourWeekUseQty() {
		return fourWeekUseQty;
	}

	public void setFourWeekUseQty(BigDecimal fourWeekUseQty) {
		this.fourWeekUseQty = fourWeekUseQty;
	}

	public BigDecimal getInventoryBasicQty() {
		return inventoryBasicQty;
	}

	public void setInventoryBasicQty(BigDecimal inventoryBasicQty) {
		this.inventoryBasicQty = inventoryBasicQty;
	}

	public BigDecimal getOnLoadQty() {
		return onLoadQty;
	}

	public void setOnLoadQty(BigDecimal onLoadQty) {
		this.onLoadQty = onLoadQty;
	}

	public BigDecimal getSuggestionQty() {
		return suggestionQty;
	}

	public void setSuggestionQty(BigDecimal suggestionQty) {
		this.suggestionQty = suggestionQty;
	}

	public Integer getCanUseDate() {
		return canUseDate;
	}

	public void setCanUseDate(Integer canUseDate) {
		this.canUseDate = canUseDate;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public Integer getItemMatrixNum() {
		return itemMatrixNum;
	}

	public void setItemMatrixNum(Integer itemMatrixNum) {
		this.itemMatrixNum = itemMatrixNum;
	}

	public ItemMatrixDTO getItemMatrix() {
		return itemMatrix;
	}

	public void setItemMatrix(ItemMatrixDTO itemMatrix) {
		this.itemMatrix = itemMatrix;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public BigDecimal getItemRate() {
		return itemRate;
	}

	public void setItemRate(BigDecimal itemRate) {
		this.itemRate = itemRate;
	}

	public List<SupplierDTO> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(List<SupplierDTO> suppliers) {
		this.suppliers = suppliers;
	}

	public Date getNextPurchaseDate() {
		return nextPurchaseDate;
	}

	public void setNextPurchaseDate(Date nextPurchaseDate) {
		this.nextPurchaseDate = nextPurchaseDate;
	}

	public BigDecimal getAvgSaleQty() {
		return avgSaleQty;
	}

	public void setAvgSaleQty(BigDecimal avgSaleQty) {
		this.avgSaleQty = avgSaleQty;
	}

	public BigDecimal getBuyQty() {
		return buyQty;
	}

	public void setBuyQty(BigDecimal buyQty) {
		this.buyQty = buyQty;
	}

	public BigDecimal getRequestLostQty() {
		return requestLostQty;
	}

	public void setRequestLostQty(BigDecimal requestLostQty) {
		this.requestLostQty = requestLostQty;
	}

	public Date getLastProductDate() {
		return lastProductDate;
	}

	public void setLastProductDate(Date lastProductDate) {
		this.lastProductDate = lastProductDate;
	}

	public static InventoryAnalysisDTO get(List<InventoryAnalysisDTO> list, Integer itemNum,
			Integer itemMatrixNum) {

		for (int i = 0; i < list.size(); i++) {
			InventoryAnalysisDTO dto = list.get(i);
			if (dto.getItemNum().equals(itemNum)) {
				if (itemMatrixNum != null) {
					if (dto.getItemMatrixNum().equals(itemMatrixNum)) {
						return dto;
					}
				} else {
					return dto;
				}
			}
		}
		return null;

	}

}
