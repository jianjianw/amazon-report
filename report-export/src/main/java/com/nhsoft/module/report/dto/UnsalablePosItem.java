package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 滞销商品
 * 
 * @author Administrator
 * 
 */
public class UnsalablePosItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3079515772109884915L;
	private Integer itemNum;
	private String posItemType; // 商品类别名称
	private String posItemTypeCode; // 商品类别代码
	private String posItemCode; // 商品代码
	private String posItemName; // 商品名称
	private String sepc; // 规格
	private String posItemUnit; // 单位
	private BigDecimal price; // 零售价
	private BigDecimal currentStore; // 当前库存
	private BigDecimal currentSaleNum; // 最近销售量
	private BigDecimal currentSaleMoney; // 最近销售额
	private BigDecimal currentSaleProfit; // 最近销售毛利
	private BigDecimal currentOutNum; // 最近调出量
	private BigDecimal currentOutMoney; // 最近销售额
	private BigDecimal currentOutProfit; // 最近销售毛利
	private BigDecimal currentPifaNum; // 最近批发量
	private BigDecimal currentPifaMoney; // 最近销售额
	private BigDecimal currentPifaProfit; // 最近销售毛利
	private Date lastestInDate; // 最近进货日期
	private String lastProductionDate;//最近生产日期

	private BigDecimal itemInventoryRate;
	private BigDecimal itemTransferRate;
	private BigDecimal itemWholesaleRate;
	private BigDecimal itemPurchaseRate;
	private Boolean stockCrease; //停购
	private Boolean eliminativeFlag;//淘汰

	public UnsalablePosItem(){
		setCurrentOutMoney(BigDecimal.ZERO);
		setCurrentOutNum(BigDecimal.ZERO);
		setCurrentOutProfit(BigDecimal.ZERO);
		setCurrentPifaMoney(BigDecimal.ZERO);
		setCurrentPifaNum(BigDecimal.ZERO);
		setCurrentPifaProfit(BigDecimal.ZERO);
		setCurrentSaleMoney(BigDecimal.ZERO);
		setCurrentSaleNum(BigDecimal.ZERO);
		setCurrentSaleProfit(BigDecimal.ZERO);
		setCurrentStore(BigDecimal.ZERO);
	}
	
	public Date getLastestInDate() {
		return lastestInDate;
	}

	public void setLastestInDate(Date lastestInDate) {
		this.lastestInDate = lastestInDate;
	}

	public String getPosItemName() {
		return posItemName;
	}

	public void setPosItemName(String posItemName) {
		this.posItemName = posItemName;
	}

	public String getPosItemType() {
		return posItemType;
	}

	public void setPosItemType(String posItemType) {
		this.posItemType = posItemType;
	}

	public String getPosItemUnit() {
		return posItemUnit;
	}

	public void setPosItemUnit(String posItemUnit) {
		this.posItemUnit = posItemUnit;
	}

	public Boolean getStockCrease() {
		return stockCrease;
	}

	public void setStockCrease(Boolean stockCrease) {
		this.stockCrease = stockCrease;
	}

	public String getPosItemCode() {
		return posItemCode;
	}

	public void setPosItemCode(String posItemCode) {
		this.posItemCode = posItemCode;
	}

	public String getSepc() {
		return sepc;
	}

	public void setSepc(String sepc) {
		this.sepc = sepc;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getCurrentStore() {
		return currentStore;
	}

	public void setCurrentStore(BigDecimal currentStore) {
		this.currentStore = currentStore;
	}

	public BigDecimal getCurrentSaleNum() {
		return currentSaleNum;
	}

	public void setCurrentSaleNum(BigDecimal currentSaleNum) {
		this.currentSaleNum = currentSaleNum;
	}

	public BigDecimal getCurrentSaleMoney() {
		return currentSaleMoney;
	}

	public void setCurrentSaleMoney(BigDecimal currentSaleMoney) {
		this.currentSaleMoney = currentSaleMoney;
	}

	public BigDecimal getCurrentSaleProfit() {
		return currentSaleProfit;
	}

	public void setCurrentSaleProfit(BigDecimal currentSaleProfit) {
		this.currentSaleProfit = currentSaleProfit;
	}

	public BigDecimal getCurrentOutNum() {
		return currentOutNum;
	}

	public void setCurrentOutNum(BigDecimal currentOutNum) {
		this.currentOutNum = currentOutNum;
	}

	public BigDecimal getCurrentPifaNum() {
		return currentPifaNum;
	}

	public void setCurrentPifaNum(BigDecimal currentPifaNum) {
		this.currentPifaNum = currentPifaNum;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getPosItemTypeCode() {
		return posItemTypeCode;
	}

	public void setPosItemTypeCode(String posItemTypeCode) {
		this.posItemTypeCode = posItemTypeCode;
	}

	public BigDecimal getCurrentOutMoney() {
		return currentOutMoney;
	}

	public void setCurrentOutMoney(BigDecimal currentOutMoney) {
		this.currentOutMoney = currentOutMoney;
	}

	public BigDecimal getCurrentOutProfit() {
		return currentOutProfit;
	}

	public void setCurrentOutProfit(BigDecimal currentOutProfit) {
		this.currentOutProfit = currentOutProfit;
	}

	public BigDecimal getCurrentPifaMoney() {
		return currentPifaMoney;
	}

	public void setCurrentPifaMoney(BigDecimal currentPifaMoney) {
		this.currentPifaMoney = currentPifaMoney;
	}

	public BigDecimal getCurrentPifaProfit() {
		return currentPifaProfit;
	}

	public void setCurrentPifaProfit(BigDecimal currentPifaProfit) {
		this.currentPifaProfit = currentPifaProfit;
	}

	public BigDecimal getItemInventoryRate() {
		return itemInventoryRate;
	}

	public void setItemInventoryRate(BigDecimal itemInventoryRate) {
		this.itemInventoryRate = itemInventoryRate;
	}

	public BigDecimal getItemTransferRate() {
		return itemTransferRate;
	}

	public void setItemTransferRate(BigDecimal itemTransferRate) {
		this.itemTransferRate = itemTransferRate;
	}

	public BigDecimal getItemWholesaleRate() {
		return itemWholesaleRate;
	}

	public void setItemWholesaleRate(BigDecimal itemWholesaleRate) {
		this.itemWholesaleRate = itemWholesaleRate;
	}

	public BigDecimal getItemPurchaseRate() {
		return itemPurchaseRate;
	}

	public void setItemPurchaseRate(BigDecimal itemPurchaseRate) {
		this.itemPurchaseRate = itemPurchaseRate;
	}

	public Boolean getEliminativeFlag() {
		return eliminativeFlag;
	}

	public void setEliminativeFlag(Boolean eliminativeFlag) {
		this.eliminativeFlag = eliminativeFlag;
	}

	public String getLastProductionDate() {
		return lastProductionDate;
	}

	public void setLastProductionDate(String lastProductionDate) {
		this.lastProductionDate = lastProductionDate;
	}
}
