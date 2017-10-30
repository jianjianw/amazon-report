package com.nhsoft.module.report.model;

import com.nhsoft.module.origin.export.AppConstants;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Inventory entity. @author MyEclipse Persistence Tools
 */

@Entity
public class Inventory implements java.io.Serializable {

	private static final long serialVersionUID = 7870322134326430600L;
	@EmbeddedId
	private InventoryId id;
	private Integer itemNum;
	private BigDecimal inventoryAmount;
	private BigDecimal inventoryMoney;
	private BigDecimal inventoryAssistAmount;
	private String systemBookCode;

	@OneToMany
	@JoinColumns({@JoinColumn(name = "storehouseNum", referencedColumnName = "storehouseNum", insertable = false, updatable = false), @JoinColumn(name="inventoryNum", referencedColumnName = "inventoryNum", insertable = false, updatable = false)})
	private List<InventoryBatchDetail> inventoryBatchDetails = new ArrayList<InventoryBatchDetail>();

	@OneToMany
	@JoinColumns({@JoinColumn(name = "storehouseNum", referencedColumnName = "storehouseNum", insertable = false, updatable = false), @JoinColumn(name="inventoryNum", referencedColumnName = "inventoryNum", insertable = false, updatable = false)})
	private List<InventoryLnDetail> inventoryLnDetails = new ArrayList<InventoryLnDetail>();

	@OneToMany
	@JoinColumns({@JoinColumn(name = "storehouseNum", referencedColumnName = "storehouseNum", insertable = false, updatable = false), @JoinColumn(name="inventoryNum", referencedColumnName = "inventoryNum", insertable = false, updatable = false)})
	private List<InventoryMatrix> inventoryMatrixs = new ArrayList<InventoryMatrix>();
	
	public String getSystemBookCode() {
		return systemBookCode;
	}
	
	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}
	
	public InventoryId getId() {
		return id;
	}

	public void setId(InventoryId id) {
		this.id = id;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public BigDecimal getInventoryAmount() {
		return inventoryAmount;
	}

	public void setInventoryAmount(BigDecimal inventoryAmount) {
		this.inventoryAmount = inventoryAmount;
	}

	public BigDecimal getInventoryMoney() {
		return inventoryMoney;
	}

	public void setInventoryMoney(BigDecimal inventoryMoney) {
		this.inventoryMoney = inventoryMoney;
	}

	public BigDecimal getInventoryAssistAmount() {
		return inventoryAssistAmount;
	}

	public void setInventoryAssistAmount(BigDecimal inventoryAssistAmount) {
		this.inventoryAssistAmount = inventoryAssistAmount;
	}

	public List<InventoryBatchDetail> getInventoryBatchDetails() {
		return inventoryBatchDetails;
	}

	public void setInventoryBatchDetails(
			List<InventoryBatchDetail> inventoryBatchDetails) {
		this.inventoryBatchDetails = inventoryBatchDetails;
	}

	public List<InventoryLnDetail> getInventoryLnDetails() {
		return inventoryLnDetails;
	}

	public void setInventoryLnDetails(List<InventoryLnDetail> inventoryLnDetails) {
		this.inventoryLnDetails = inventoryLnDetails;
	}

	public List<InventoryMatrix> getInventoryMatrixs() {
		return inventoryMatrixs;
	}

	public void setInventoryMatrixs(List<InventoryMatrix> inventoryMatrixs) {
		this.inventoryMatrixs = inventoryMatrixs;
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
		Inventory other = (Inventory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public static Inventory get(List<Inventory> inventories, Integer itemNum) {
		if(inventories == null){
			return null;
		}
		for (int i = 0, len = inventories.size(); i < len; i++) {
			Inventory inventory = inventories.get(i);
			if (inventory.getItemNum().equals(itemNum)) {
				return inventory;
			}
		}
		return null;
	}

	public static Inventory get(List<Inventory> inventories, Integer itemNum, Integer storehouseNum) {
		if(inventories == null) {
			return null;
		}
		return inventories.stream().filter(i -> i.getItemNum().equals(itemNum) && i.getId().getStorehouseNum().equals(storehouseNum)).findFirst().orElse(null);
	}
	
	
	public static Object[] getInventoryAmount(Inventory inventory, PosItem posItem, Integer itemMatrixNum,
	                                          String lotNumber, Branch branch) {
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal money = BigDecimal.ZERO;
		BigDecimal assistAmount = BigDecimal.ZERO;
		BigDecimal cost = BigDecimal.ZERO;
		if(inventory != null){
			
			if (posItem.getItemCostMode(branch).equals(AppConstants.C_ITEM_COST_MODE_MANUAL)) {
				List<InventoryLnDetail> inventoryLnDetails = inventory.getInventoryLnDetails();
				for (int j = 0; j < inventoryLnDetails.size(); j++) {
					InventoryLnDetail inventoryLnDetail = inventoryLnDetails.get(j);
					if (StringUtils.isNotEmpty(lotNumber)) {
						if (!lotNumber.equals(inventoryLnDetail.getInventoryLnDetailLotNumber())) {
							continue;
						}
					}
					if (itemMatrixNum != null && itemMatrixNum != 0) {
						if (inventoryLnDetail.getItemMatrixNum() != null
								&& !itemMatrixNum.equals(inventoryLnDetail.getItemMatrixNum())) {
							continue;
						}
					}
					amount = amount.add(inventoryLnDetail.getInventoryLnDetailAmount());
					money = money.add(inventoryLnDetail.getInventoryLnDetailAmount().multiply(
							inventoryLnDetail.getInventoryLnDetailCostPrice()));
					assistAmount = assistAmount.add(inventoryLnDetail.getInventoryLnDetailAssistAmount());
					cost = inventoryLnDetail.getInventoryLnDetailCostPrice();
				}
			} else if (posItem.getItemCostMode(branch).equals(AppConstants.C_ITEM_COST_MODE_FIFO)) {
				List<InventoryBatchDetail> inventoryBatchDetails = inventory.getInventoryBatchDetails();
				for (int j = 0; j < inventoryBatchDetails.size(); j++) {
					InventoryBatchDetail inventoryBatchDetail = inventoryBatchDetails.get(j);
					if (itemMatrixNum != null && itemMatrixNum != 0) {
						if (inventoryBatchDetail.getItemMatrixNum() != null
								&& !itemMatrixNum.equals(inventoryBatchDetail.getItemMatrixNum())) {
							continue;
						}
					}
					amount = amount.add(inventoryBatchDetail.getInventoryBatchDetailAmount());
					money = money.add(inventoryBatchDetail.getInventoryBatchDetailAmount().multiply(
							inventoryBatchDetail.getInventoryBatchDetailCostPrice()));
					assistAmount = assistAmount.add(inventoryBatchDetail.getInventoryBatchDetailAssistAmount());
				}
			} else if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_MATRIX) {
				List<InventoryMatrix> inventoryMatrixs = inventory.getInventoryMatrixs();
				for (int j = 0; j < inventoryMatrixs.size(); j++) {
					InventoryMatrix inventoryMatrix = inventoryMatrixs.get(j);
					if (itemMatrixNum != null && itemMatrixNum != 0) {
						if (inventoryMatrix.getId().getItemMatrixNum() != null
								&& !itemMatrixNum.equals(inventoryMatrix.getId().getItemMatrixNum())) {
							continue;
						}
					}
					amount = amount.add(inventoryMatrix.getInventoryMatrixAmount());
					assistAmount = assistAmount.add(inventoryMatrix.getInventoryMatrixAssistAmount());
				}
			} else {
				amount = amount.add(inventory.getInventoryAmount());
				money = money.add(inventory.getInventoryMoney());
				assistAmount = assistAmount.add(inventory.getInventoryAssistAmount());
			}
		}
		
		
		if (!posItem.getItemCostMode(branch).equals(AppConstants.C_ITEM_COST_MODE_MANUAL)
				|| StringUtils.isNotEmpty(lotNumber)) {
			if (amount.compareTo(BigDecimal.ZERO) != 0) {
				cost = money.divide(amount, 4, BigDecimal.ROUND_HALF_UP);
			} else {
				if(branch.isJoinBranch()){
					cost = posItem.getItemTransferPrice();
					
				} else {
					cost = posItem.getItemCostPrice();
					
				}
			}
		}
		Object[] obj = new Object[5];
		obj[0] = amount;
		obj[1] = assistAmount;
		obj[2] = money;
		obj[3] = cost;
		return obj;
	}
	
	public static Object[] getInventoryAmount(List<Inventory> inventories, PosItem posItem, Integer itemMatrixNum,
	                                          String lotNumber, Branch branch) {
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal money = BigDecimal.ZERO;
		BigDecimal assistAmount = BigDecimal.ZERO;
		BigDecimal cost = BigDecimal.ZERO;
		boolean found = false;
		for (int i = 0; i < inventories.size(); i++) {
			Inventory inventory = inventories.get(i);
			if (inventory.getItemNum().equals(posItem.getItemNum())) {
				found = true;
				if (posItem.getItemCostMode(branch).equals(AppConstants.C_ITEM_COST_MODE_MANUAL)) {
					List<InventoryLnDetail> inventoryLnDetails = inventory.getInventoryLnDetails();
					for (int j = 0; j < inventoryLnDetails.size(); j++) {
						InventoryLnDetail inventoryLnDetail = inventoryLnDetails.get(j);
						if (StringUtils.isNotEmpty(lotNumber)) {
							if (!lotNumber.equals(inventoryLnDetail.getInventoryLnDetailLotNumber())) {
								continue;
							}
						}
						if (itemMatrixNum != null && itemMatrixNum != 0) {
							if (inventoryLnDetail.getItemMatrixNum() != null
									&& !itemMatrixNum.equals(inventoryLnDetail.getItemMatrixNum())) {
								continue;
							}
						}
						amount = amount.add(inventoryLnDetail.getInventoryLnDetailAmount());
						money = money.add(inventoryLnDetail.getInventoryLnDetailAmount().multiply(
								inventoryLnDetail.getInventoryLnDetailCostPrice()));
						assistAmount = assistAmount.add(inventoryLnDetail.getInventoryLnDetailAssistAmount());
						cost = inventoryLnDetail.getInventoryLnDetailCostPrice();
					}
				} else if (posItem.getItemCostMode(branch).equals(AppConstants.C_ITEM_COST_MODE_FIFO)) {
					List<InventoryBatchDetail> inventoryBatchDetails = inventory.getInventoryBatchDetails();
					for (int j = 0; j < inventoryBatchDetails.size(); j++) {
						InventoryBatchDetail inventoryBatchDetail = inventoryBatchDetails.get(j);
						if (itemMatrixNum != null && itemMatrixNum != 0) {
							if (inventoryBatchDetail.getItemMatrixNum() != null
									&& !itemMatrixNum.equals(inventoryBatchDetail.getItemMatrixNum())) {
								continue;
							}
						}
						amount = amount.add(inventoryBatchDetail.getInventoryBatchDetailAmount());
						money = money.add(inventoryBatchDetail.getInventoryBatchDetailAmount().multiply(
								inventoryBatchDetail.getInventoryBatchDetailCostPrice()));
						assistAmount = assistAmount.add(inventoryBatchDetail.getInventoryBatchDetailAssistAmount());
					}
				} else if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_MATRIX) {
					List<InventoryMatrix> inventoryMatrixs = inventory.getInventoryMatrixs();
					for (int j = 0; j < inventoryMatrixs.size(); j++) {
						InventoryMatrix inventoryMatrix = inventoryMatrixs.get(j);
						if (itemMatrixNum != null && itemMatrixNum != 0) {
							if (inventoryMatrix.getId().getItemMatrixNum() != null
									&& !itemMatrixNum.equals(inventoryMatrix.getId().getItemMatrixNum())) {
								continue;
							}
						}
						amount = amount.add(inventoryMatrix.getInventoryMatrixAmount());
						assistAmount = assistAmount.add(inventoryMatrix.getInventoryMatrixAssistAmount());
					}
				} else {
					amount = amount.add(inventory.getInventoryAmount());
					money = money.add(inventory.getInventoryMoney());
					assistAmount = assistAmount.add(inventory.getInventoryAssistAmount());
				}
			}
		}
		if (!posItem.getItemCostMode(branch).equals(AppConstants.C_ITEM_COST_MODE_MANUAL)
				|| StringUtils.isNotEmpty(lotNumber)) {
			if (amount.compareTo(BigDecimal.ZERO) > 0) {
				cost = money.divide(amount, 4, BigDecimal.ROUND_HALF_UP);
			}
		}
		Object[] obj = new Object[5];
		obj[0] = amount;
		obj[1] = assistAmount;
		obj[2] = money;
		obj[3] = cost;
		obj[4] = found;
		return obj;
	}
	
	public static Object[] getInventoryAmount(Inventory inventory, Integer itemNum, String lotNumber,
	                                          Integer itemMatrixNum) {
		Object[] objects = new Object[3];
		BigDecimal qty = BigDecimal.ZERO;
		BigDecimal assistQty = BigDecimal.ZERO;
		BigDecimal cost = BigDecimal.ZERO;
		if (inventory != null) {
			if (inventory.getInventoryAmount().compareTo(BigDecimal.ZERO) != 0) {
				
				cost = inventory.getInventoryMoney()
						.divide(inventory.getInventoryAmount(), 4, BigDecimal.ROUND_HALF_UP);
			}
			List<InventoryMatrix> inventoryMatrixs = inventory.getInventoryMatrixs();
			List<InventoryBatchDetail> inventoryBatchDetails = inventory.getInventoryBatchDetails();
			List<InventoryLnDetail> inventoryLnDetails = inventory.getInventoryLnDetails();
			if (inventoryMatrixs.size() > 0) {
				for (int i = 0; i < inventoryMatrixs.size(); i++) {
					InventoryMatrix inventoryMatrix = inventoryMatrixs.get(i);
					if (itemMatrixNum != null && itemMatrixNum != 0) {
						if (!itemMatrixNum.equals(inventoryMatrix.getId().getItemMatrixNum())) {
							continue;
						}
					}
					qty = qty.add(inventoryMatrix.getInventoryMatrixAmount());
					assistQty = assistQty.add(inventoryMatrix.getInventoryMatrixAssistAmount());
					
				}
			} else if (inventoryBatchDetails.size() > 0) {
				for (int i = 0; i < inventoryBatchDetails.size(); i++) {
					InventoryBatchDetail inventoryBatchDetail = inventoryBatchDetails.get(i);
					if (itemMatrixNum != null && itemMatrixNum != 0) {
						if (inventoryBatchDetail.getItemMatrixNum() != null
								&& !itemMatrixNum.equals(inventoryBatchDetail.getItemMatrixNum())) {
							continue;
						}
					}
					qty = qty.add(inventoryBatchDetail.getInventoryBatchDetailAmount());
					assistQty = assistQty.add(inventoryBatchDetail.getInventoryBatchDetailAssistAmount());
					
				}
			} else if (inventoryLnDetails.size() > 0) {
				for (int i = 0; i < inventoryLnDetails.size(); i++) {
					InventoryLnDetail inventoryLnDetail = inventoryLnDetails.get(i);
					if (itemMatrixNum != null && itemMatrixNum != 0) {
						if (inventoryLnDetail.getItemMatrixNum() != null
								&& !itemMatrixNum.equals(inventoryLnDetail.getItemMatrixNum())) {
							continue;
						}
					}
					if (lotNumber != null) {
						if (!StringUtils.equals(lotNumber, inventoryLnDetail.getInventoryLnDetailLotNumber())) {
							continue;
						}
						cost = inventoryLnDetail.getInventoryLnDetailCostPrice();
					}
					qty = qty.add(inventoryLnDetail.getInventoryLnDetailAmount());
					assistQty = assistQty.add(inventoryLnDetail.getInventoryLnDetailAssistAmount());
				}
			} else {
				qty = inventory.getInventoryAmount();
				assistQty = inventory.getInventoryAssistAmount();
			}
		}
		objects[0] = qty.setScale(4, BigDecimal.ROUND_HALF_UP);
		
		objects[1] = assistQty.setScale(4, BigDecimal.ROUND_HALF_UP);
		
		objects[2] = cost.setScale(4, BigDecimal.ROUND_HALF_UP);
		return objects;
	}
	
	
}