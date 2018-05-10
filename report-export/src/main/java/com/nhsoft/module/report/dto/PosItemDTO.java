package com.nhsoft.module.report.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

@SuppressWarnings("unchecked")
public class PosItemDTO implements java.io.Serializable {

	private static final Logger logger = LoggerFactory.getLogger(PosItemDTO.class);
	private static final long serialVersionUID = -4598224475348745971L;
	private Integer itemNum;
	private String systemBookCode;
	private String itemCode;
	private String itemBarcode;
	private String itemName;
	private String itemSpec;
	private String storeItemPinyin;
	private Integer itemType;
	private String itemUnit;
	private String itemDepartment;
	private String itemCategory;
	private BigDecimal itemRegularPrice;
	private BigDecimal itemLevel2Price;
	private BigDecimal itemLevel3Price;
	private BigDecimal itemLevel4Price;
	private String itemNoteInfo;
	private String itemSaleMessage;
	private String itemInvoiceMessage;
	private Boolean storeItemFlag;
	private String storeItemMatrixProperty;
	private Boolean itemPosChangePriceFlag;
	private BigDecimal itemMinPrice;
	private Boolean itemDelTag;
	private Boolean itemPointActived;
	private BigDecimal itemPointValue;
	private String itemBrand;
	private String itemCostMode;
	private Integer itemValidPeriod;
	private Integer itemRemindPeriod;
	private Boolean itemStockCeaseFlag;
	private Boolean itemSaleCeaseFlag;
	private String itemPurchaseScope;
	private BigDecimal itemCostPrice;
	private BigDecimal itemTransferPrice;
	private String itemCategoryCode;
	private String itemPlace;
	private Integer itemSequence;
	private Date itemCreateTime;
	private Boolean itemEliminativeFlag;
	private Boolean itemWeightFlag;
	private String itemStorePlace;
	private String itemAssistUnit;
	private BigDecimal itemAssistRate;
	private String itemPurchaseUnit;
	private BigDecimal itemPurchaseRate;
	private String itemInventoryUnit;
	private BigDecimal itemInventoryRate;
	private String itemTransferUnit;
	private BigDecimal itemTransferRate;
	private String itemWholesaleUnit;
	private BigDecimal itemWholesaleRate;
	private String itemUnitGroup;
	private BigDecimal itemWholesalePrice;
	private Boolean itemPriceTagFlag;
	private Date itemDelTime;
	private Date itemLastEditTime;
	private String itemMethod;
	private BigDecimal itemGrossRate;
	private Integer itemStatus;
	private BigDecimal itemLevel2Wholesale;
	private BigDecimal itemLevel3Wholesale;
	private BigDecimal itemLevel4Wholesale;
	private Boolean itemSynchFlag;
	private Boolean itemWholesaleFlag;
	private String itemEnName;
	private BigDecimal itemTransferGross;
	private Boolean itemTransferFixedGross;
	private BigDecimal itemWholesaleGross;
	private Boolean itemWholesaleFixedGross;
	private Boolean itemDiscounted;
	private Boolean itemPrintLabelFlag;
	private BigDecimal itemMinQuantity;
	private BigDecimal itemWholesaleGross2;
	private BigDecimal itemWholesaleGross3;
	private BigDecimal itemWholesaleGross4;
	private Boolean itemWholesaleFixedGross2;
	private Boolean itemWholesaleFixedGross3;
	private Boolean itemWholesaleFixedGross4;
	private BigDecimal itemMaxPrice;
	private Integer itemTransferDay;
	private BigDecimal itemOutTax;
	private BigDecimal itemInTax;
	private Boolean itemManufactureFlag;
	private BigDecimal itemFinishedRate;

	private List<ItemBarDTO> itemBars = new ArrayList<ItemBarDTO>(); 
	private List<ItemMatrixDTO> itemMatrixs = new ArrayList<ItemMatrixDTO>(); //lazy = false
	private List<PosItemKitDTO> posItemKits = new ArrayList<PosItemKitDTO>();
	
	@JsonIgnore
	private List<StoreMatrixDTO> storeMatrixs = new ArrayList<StoreMatrixDTO>();
	
	@JsonIgnore
	private List<CollectionItemDTO> collectionItems = new ArrayList<CollectionItemDTO>();
	
	@JsonIgnore
	private List<PosItemGradeDTO> posItemGrades = new ArrayList<PosItemGradeDTO>();
	
	
	@JsonIgnore
	private List<ItemPropertyDTO> itemPropertys = new ArrayList<ItemPropertyDTO>();
	
	//临时属性
	@JsonIgnore
	private SaleCommissionDTO saleCommission;

	
	//临时属性 供应商新品申请用到
	private Boolean updateImages = false;
	private List<PosImageDTO> posImages;
	private PosItemDetailDTO posItemDetail;
	private List<String> ossObjectIds;
	private String itemMemo;
	private BigDecimal amount;
	private Integer repeatType; //导入失败原因 0 代码重复 1 名称重复
	
	public BigDecimal getItemFinishedRate() {
		return itemFinishedRate;
	}

	public void setItemFinishedRate(BigDecimal itemFinishedRate) {
		this.itemFinishedRate = itemFinishedRate;
	}

	public Integer getRepeatType() {
		return repeatType;
	}

	public void setRepeatType(Integer repeatType) {
		this.repeatType = repeatType;
	}

	public Boolean getItemManufactureFlag() {
		return itemManufactureFlag;
	}

	public void setItemManufactureFlag(Boolean itemManufactureFlag) {
		this.itemManufactureFlag = itemManufactureFlag;
	}

	public Boolean getUpdateImages() {
		return updateImages;
	}

	public void setUpdateImages(Boolean updateImages) {
		this.updateImages = updateImages;
	}

	public String getItemMemo() {
		return itemMemo;
	}

	public void setItemMemo(String itemMemo) {
		this.itemMemo = itemMemo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public List<ItemPropertyDTO> getItemPropertys() {
		return itemPropertys;
	}

	public void setItemPropertys(List<ItemPropertyDTO> itemPropertys) {
		this.itemPropertys = itemPropertys;
	}

	public Integer getItemTransferDay() {
		return itemTransferDay;
	}

	public void setItemTransferDay(Integer itemTransferDay) {
		this.itemTransferDay = itemTransferDay;
	}

	public BigDecimal getItemMaxPrice() {
		return itemMaxPrice;
	}

	public void setItemMaxPrice(BigDecimal itemMaxPrice) {
		this.itemMaxPrice = itemMaxPrice;
	}

	public BigDecimal getItemMinQuantity() {
		return itemMinQuantity;
	}

	public BigDecimal getItemWholesaleGross2() {
		return itemWholesaleGross2;
	}

	public void setItemWholesaleGross2(BigDecimal itemWholesaleGross2) {
		this.itemWholesaleGross2 = itemWholesaleGross2;
	}

	public BigDecimal getItemWholesaleGross3() {
		return itemWholesaleGross3;
	}

	public void setItemWholesaleGross3(BigDecimal itemWholesaleGross3) {
		this.itemWholesaleGross3 = itemWholesaleGross3;
	}

	public BigDecimal getItemWholesaleGross4() {
		return itemWholesaleGross4;
	}

	public void setItemWholesaleGross4(BigDecimal itemWholesaleGross4) {
		this.itemWholesaleGross4 = itemWholesaleGross4;
	}

	public Boolean getItemWholesaleFixedGross2() {
		return itemWholesaleFixedGross2;
	}

	public void setItemWholesaleFixedGross2(Boolean itemWholesaleFixedGross2) {
		this.itemWholesaleFixedGross2 = itemWholesaleFixedGross2;
	}

	public Boolean getItemWholesaleFixedGross3() {
		return itemWholesaleFixedGross3;
	}

	public void setItemWholesaleFixedGross3(Boolean itemWholesaleFixedGross3) {
		this.itemWholesaleFixedGross3 = itemWholesaleFixedGross3;
	}

	public Boolean getItemWholesaleFixedGross4() {
		return itemWholesaleFixedGross4;
	}

	public void setItemWholesaleFixedGross4(Boolean itemWholesaleFixedGross4) {
		this.itemWholesaleFixedGross4 = itemWholesaleFixedGross4;
	}

	public void setItemMinQuantity(BigDecimal itemMinQuantity) {
		this.itemMinQuantity = itemMinQuantity;
	}

	public Boolean getItemPrintLabelFlag() {
		return itemPrintLabelFlag;
	}

	public void setItemPrintLabelFlag(Boolean itemPrintLabelFlag) {
		this.itemPrintLabelFlag = itemPrintLabelFlag;
	}

	public Boolean getItemDiscounted() {
		return itemDiscounted;
	}

	public void setItemDiscounted(Boolean itemDiscounted) {
		this.itemDiscounted = itemDiscounted;
	}

	public List<PosItemGradeDTO> getPosItemGrades() {
		return posItemGrades;
	}

	public void setPosItemGrades(List<PosItemGradeDTO> posItemGrades) {
		this.posItemGrades = posItemGrades;
	}

	public Boolean getItemWholesaleFlag() {
		return itemWholesaleFlag;
	}

	public void setItemWholesaleFlag(Boolean itemWholesaleFlag) {
		this.itemWholesaleFlag = itemWholesaleFlag;
	}

	public List<String> getOssObjectIds() {
		return ossObjectIds;
	}

	public void setOssObjectIds(List<String> ossObjectIds) {
		this.ossObjectIds = ossObjectIds;
	}

	public List<PosImageDTO> getPosImages() {
		return posImages;
	}

	public void setPosImages(List<PosImageDTO> posImages) {
		this.posImages = posImages;
	}

	public PosItemDetailDTO getPosItemDetail() {
		return posItemDetail;
	}

	public void setPosItemDetail(PosItemDetailDTO posItemDetail) {
		this.posItemDetail = posItemDetail;
	}

	public List<PosItemKitDTO> getPosItemKits() {
		return posItemKits;
	}

	public void setPosItemKits(List<PosItemKitDTO> posItemKits) {
		this.posItemKits = posItemKits;
	}

	public List<ItemMatrixDTO> getItemMatrixs() {
		return itemMatrixs;
	}

	public void setItemMatrixs(List<ItemMatrixDTO> itemMatrixs) {
		this.itemMatrixs = itemMatrixs;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemBarcode() {
		return itemBarcode;
	}

	public void setItemBarcode(String itemBarcode) {
		this.itemBarcode = itemBarcode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public String getStoreItemPinyin() {
		return storeItemPinyin;
	}

	public void setStoreItemPinyin(String storeItemPinyin) {
		this.storeItemPinyin = storeItemPinyin;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getItemDepartment() {
		return itemDepartment;
	}

	public void setItemDepartment(String itemDepartment) {
		this.itemDepartment = itemDepartment;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public BigDecimal getItemRegularPrice() {
		return itemRegularPrice;
	}

	public void setItemRegularPrice(BigDecimal itemRegularPrice) {
		this.itemRegularPrice = itemRegularPrice;
	}

	public BigDecimal getItemLevel2Price() {
		return itemLevel2Price;
	}

	public void setItemLevel2Price(BigDecimal itemLevel2Price) {
		this.itemLevel2Price = itemLevel2Price;
	}

	public BigDecimal getItemLevel3Price() {
		return itemLevel3Price;
	}

	public void setItemLevel3Price(BigDecimal itemLevel3Price) {
		this.itemLevel3Price = itemLevel3Price;
	}

	public BigDecimal getItemLevel4Price() {
		return itemLevel4Price;
	}

	public void setItemLevel4Price(BigDecimal itemLevel4Price) {
		this.itemLevel4Price = itemLevel4Price;
	}

	public String getItemNoteInfo() {
		return itemNoteInfo;
	}

	public void setItemNoteInfo(String itemNoteInfo) {
		this.itemNoteInfo = itemNoteInfo;
	}

	public String getItemSaleMessage() {
		return itemSaleMessage;
	}

	public void setItemSaleMessage(String itemSaleMessage) {
		this.itemSaleMessage = itemSaleMessage;
	}

	public String getItemInvoiceMessage() {
		return itemInvoiceMessage;
	}

	public void setItemInvoiceMessage(String itemInvoiceMessage) {
		this.itemInvoiceMessage = itemInvoiceMessage;
	}

	public Boolean getStoreItemFlag() {
		return storeItemFlag;
	}

	public void setStoreItemFlag(Boolean storeItemFlag) {
		this.storeItemFlag = storeItemFlag;
	}

	public String getStoreItemMatrixProperty() {
		return storeItemMatrixProperty;
	}

	public void setStoreItemMatrixProperty(String storeItemMatrixProperty) {
		this.storeItemMatrixProperty = storeItemMatrixProperty;
	}

	public Boolean getItemPosChangePriceFlag() {
		return itemPosChangePriceFlag;
	}

	public void setItemPosChangePriceFlag(Boolean itemPosChangePriceFlag) {
		this.itemPosChangePriceFlag = itemPosChangePriceFlag;
	}

	public BigDecimal getItemMinPrice() {
		return itemMinPrice;
	}

	public void setItemMinPrice(BigDecimal itemMinPrice) {
		this.itemMinPrice = itemMinPrice;
	}

	public Boolean getItemDelTag() {
		return itemDelTag;
	}

	public void setItemDelTag(Boolean itemDelTag) {
		this.itemDelTag = itemDelTag;
	}

	public Boolean getItemPointActived() {
		return itemPointActived;
	}

	public void setItemPointActived(Boolean itemPointActived) {
		this.itemPointActived = itemPointActived;
	}

	public BigDecimal getItemPointValue() {
		return itemPointValue;
	}

	public void setItemPointValue(BigDecimal itemPointValue) {
		this.itemPointValue = itemPointValue;
	}

	public String getItemBrand() {
		return itemBrand;
	}

	public void setItemBrand(String itemBrand) {
		this.itemBrand = itemBrand;
	}

	public String getItemCostMode() {
		return itemCostMode;
	}

	public void setItemCostMode(String itemCostMode) {
		if(itemCostMode != null){
			itemCostMode = itemCostMode.trim();
		}
		this.itemCostMode = itemCostMode;
	}

	public Integer getItemValidPeriod() {
		return itemValidPeriod;
	}

	public void setItemValidPeriod(Integer itemValidPeriod) {
		this.itemValidPeriod = itemValidPeriod;
	}

	public Integer getItemRemindPeriod() {
		return itemRemindPeriod;
	}

	public void setItemRemindPeriod(Integer itemRemindPeriod) {
		this.itemRemindPeriod = itemRemindPeriod;
	}

	public Boolean getItemStockCeaseFlag() {
		return itemStockCeaseFlag;
	}

	public void setItemStockCeaseFlag(Boolean itemStockCeaseFlag) {
		this.itemStockCeaseFlag = itemStockCeaseFlag;
	}

	public Boolean getItemSaleCeaseFlag() {
		return itemSaleCeaseFlag;
	}

	public void setItemSaleCeaseFlag(Boolean itemSaleCeaseFlag) {
		this.itemSaleCeaseFlag = itemSaleCeaseFlag;
	}

	public String getItemPurchaseScope() {
		return itemPurchaseScope;
	}

	public void setItemPurchaseScope(String itemPurchaseScope) {
		this.itemPurchaseScope = itemPurchaseScope;
	}

	public BigDecimal getItemCostPrice() {
		return itemCostPrice;
	}

	public void setItemCostPrice(BigDecimal itemCostPrice) {
		this.itemCostPrice = itemCostPrice;
	}

	public BigDecimal getItemTransferPrice() {
		return itemTransferPrice;
	}

	public void setItemTransferPrice(BigDecimal itemTransferPrice) {
		this.itemTransferPrice = itemTransferPrice;
	}

	public String getItemCategoryCode() {
		return itemCategoryCode;
	}

	public void setItemCategoryCode(String itemCategoryCode) {
		this.itemCategoryCode = itemCategoryCode;
	}

	public String getItemPlace() {
		return itemPlace;
	}

	public void setItemPlace(String itemPlace) {
		this.itemPlace = itemPlace;
	}

	public Integer getItemSequence() {
		return itemSequence;
	}

	public void setItemSequence(Integer itemSequence) {
		this.itemSequence = itemSequence;
	}

	public Date getItemCreateTime() {
		return itemCreateTime;
	}

	public void setItemCreateTime(Date itemCreateTime) {
		this.itemCreateTime = itemCreateTime;
	}

	public Boolean getItemEliminativeFlag() {
		return itemEliminativeFlag;
	}

	public void setItemEliminativeFlag(Boolean itemEliminativeFlag) {
		this.itemEliminativeFlag = itemEliminativeFlag;
	}

	public Boolean getItemWeightFlag() {
		return itemWeightFlag;
	}

	public void setItemWeightFlag(Boolean itemWeightFlag) {
		this.itemWeightFlag = itemWeightFlag;
	}

	public String getItemStorePlace() {
		return itemStorePlace;
	}

	public void setItemStorePlace(String itemStorePlace) {
		this.itemStorePlace = itemStorePlace;
	}

	public String getItemAssistUnit() {
		return itemAssistUnit;
	}

	public void setItemAssistUnit(String itemAssistUnit) {
		this.itemAssistUnit = itemAssistUnit;
	}

	public BigDecimal getItemAssistRate() {
		return itemAssistRate;
	}

	public void setItemAssistRate(BigDecimal itemAssistRate) {
		this.itemAssistRate = itemAssistRate;
	}

	public String getItemPurchaseUnit() {
		return itemPurchaseUnit;
	}

	public void setItemPurchaseUnit(String itemPurchaseUnit) {
		this.itemPurchaseUnit = itemPurchaseUnit;
	}

	public BigDecimal getItemPurchaseRate() {
		return itemPurchaseRate;
	}

	public void setItemPurchaseRate(BigDecimal itemPurchaseRate) {
		this.itemPurchaseRate = itemPurchaseRate;
	}

	public String getItemInventoryUnit() {
		return itemInventoryUnit;
	}

	public void setItemInventoryUnit(String itemInventoryUnit) {
		this.itemInventoryUnit = itemInventoryUnit;
	}

	public BigDecimal getItemInventoryRate() {
		return itemInventoryRate;
	}

	public void setItemInventoryRate(BigDecimal itemInventoryRate) {
		this.itemInventoryRate = itemInventoryRate;
	}

	public String getItemTransferUnit() {
		return itemTransferUnit;
	}

	public void setItemTransferUnit(String itemTransferUnit) {
		this.itemTransferUnit = itemTransferUnit;
	}

	public BigDecimal getItemTransferRate() {
		return itemTransferRate;
	}

	public void setItemTransferRate(BigDecimal itemTransferRate) {
		this.itemTransferRate = itemTransferRate;
	}

	public String getItemWholesaleUnit() {
		return itemWholesaleUnit;
	}

	public void setItemWholesaleUnit(String itemWholesaleUnit) {
		this.itemWholesaleUnit = itemWholesaleUnit;
	}

	public BigDecimal getItemWholesaleRate() {
		return itemWholesaleRate;
	}

	public void setItemWholesaleRate(BigDecimal itemWholesaleRate) {
		this.itemWholesaleRate = itemWholesaleRate;
	}

	public String getItemUnitGroup() {
		return itemUnitGroup;
	}

	public void setItemUnitGroup(String itemUnitGroup) {
		this.itemUnitGroup = itemUnitGroup;
	}

	public BigDecimal getItemWholesalePrice() {
		return itemWholesalePrice;
	}

	public void setItemWholesalePrice(BigDecimal itemWholesalePrice) {
		this.itemWholesalePrice = itemWholesalePrice;
	}

	public Boolean getItemPriceTagFlag() {
		return itemPriceTagFlag;
	}

	public void setItemPriceTagFlag(Boolean itemPriceTagFlag) {
		this.itemPriceTagFlag = itemPriceTagFlag;
	}

	public Date getItemLastEditTime() {
		return itemLastEditTime;
	}

	public void setItemLastEditTime(Date itemLastEditTime) {
		this.itemLastEditTime = itemLastEditTime;
	}

	public String getItemMethod() {
		return itemMethod;
	}

	public void setItemMethod(String itemMethod) {
		this.itemMethod = itemMethod;
	}

	public BigDecimal getItemGrossRate() {
		return itemGrossRate;
	}

	public void setItemGrossRate(BigDecimal itemGrossRate) {
		this.itemGrossRate = itemGrossRate;
	}
	
	public SaleCommissionDTO getSaleCommission() {
		return saleCommission;
	}

	public void setSaleCommission(SaleCommissionDTO saleCommission) {
		this.saleCommission = saleCommission;
	}

	public List<StoreMatrixDTO> getStoreMatrixs() {
		return storeMatrixs;
	}

	public void setStoreMatrixs(List<StoreMatrixDTO> storeMatrixs) {
		this.storeMatrixs = storeMatrixs;
	}

	public List<CollectionItemDTO> getCollectionItems() {
		return collectionItems;
	}

	public void setCollectionItems(List<CollectionItemDTO> collectionItems) {
		this.collectionItems = collectionItems;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Date getItemDelTime() {
		return itemDelTime;
	}

	public void setItemDelTime(Date itemDelTime) {
		this.itemDelTime = itemDelTime;
	}

	public BigDecimal getItemLevel2Wholesale() {
		return itemLevel2Wholesale;
	}

	public void setItemLevel2Wholesale(BigDecimal itemLevel2Wholesale) {
		this.itemLevel2Wholesale = itemLevel2Wholesale;
	}

	public BigDecimal getItemLevel3Wholesale() {
		return itemLevel3Wholesale;
	}

	public void setItemLevel3Wholesale(BigDecimal itemLevel3Wholesale) {
		this.itemLevel3Wholesale = itemLevel3Wholesale;
	}

	public BigDecimal getItemLevel4Wholesale() {
		return itemLevel4Wholesale;
	}

	public void setItemLevel4Wholesale(BigDecimal itemLevel4Wholesale) {
		this.itemLevel4Wholesale = itemLevel4Wholesale;
	}

	public List<ItemBarDTO> getItemBars() {
		return itemBars;
	}

	public void setItemBars(List<ItemBarDTO> itemBars) {
		this.itemBars = itemBars;
	}

	public Boolean getItemSynchFlag() {
		return itemSynchFlag;
	}

	public void setItemSynchFlag(Boolean itemSynchFlag) {
		this.itemSynchFlag = itemSynchFlag;
	}

	public String getItemEnName() {
		return itemEnName;
	}

	public void setItemEnName(String itemEnName) {
		this.itemEnName = itemEnName;
	}

	public BigDecimal getItemTransferGross() {
		return itemTransferGross;
	}

	public void setItemTransferGross(BigDecimal itemTransferGross) {
		this.itemTransferGross = itemTransferGross;
	}

	public Boolean getItemTransferFixedGross() {
		return itemTransferFixedGross;
	}

	public void setItemTransferFixedGross(Boolean itemTransferFixedGross) {
		this.itemTransferFixedGross = itemTransferFixedGross;
	}

	public BigDecimal getItemWholesaleGross() {
		return itemWholesaleGross;
	}

	public void setItemWholesaleGross(BigDecimal itemWholesaleGross) {
		this.itemWholesaleGross = itemWholesaleGross;
	}

	public Boolean getItemWholesaleFixedGross() {
		return itemWholesaleFixedGross;
	}

	public void setItemWholesaleFixedGross(Boolean itemWholesaleFixedGross) {
		this.itemWholesaleFixedGross = itemWholesaleFixedGross;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemNum == null) ? 0 : itemNum.hashCode());
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
		PosItemDTO other = (PosItemDTO) obj;
		if (itemNum == null) {
			if (other.itemNum != null)
				return false;
		} else if (!itemNum.equals(other.itemNum))
			return false;
		return true;
	}
	public static List<PosItemDTO> readFromNode(Element node){
		List<PosItemDTO> posItems = new ArrayList<PosItemDTO>();
		if(node == null){
			return posItems;
		}
		Iterator<Element> iterator = node.elementIterator("商品");
		while(iterator.hasNext()){
			PosItemDTO posItem = new PosItemDTO();
			Element element = iterator.next();
			posItem.setItemNum(Integer.parseInt(element.selectSingleNode("商品编号").getText()));
			posItem.setItemName(element.selectSingleNode("商品名称").getText());
			posItem.setItemCode(element.selectSingleNode("商品代码").getText());
			posItems.add(posItem);
		}		
		return posItems;
	}
	
	public static void writeToNode(List<PosItemDTO> posItems, Element node){
		for(int i = 0;i < posItems.size();i++){
			PosItemDTO posItem = posItems.get(i);
			Element element = node.addElement("商品");
			element.addElement("商品编号").setText(posItem.getItemNum().toString());
			element.addElement("商品名称").setText(posItem.getItemName());
			element.addElement("商品代码").setText(posItem.getItemCode());
			
			if(posItem.getItemInventoryUnit() != null){
				element.addElement("商品库存计量单位").setText(posItem.getItemInventoryUnit());
			}
			
			if(posItem.getItemSpec() != null){
				element.addElement("商品规格").setText(posItem.getItemSpec());
			}

		}
	}
	
	public static List<PosItemDTO> readShortItemFromXml(String text){
		List<PosItemDTO> posItems = new ArrayList<PosItemDTO>();
		if(StringUtils.isEmpty(text)){
			return posItems;
		}
		Document document;
		try {
			document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			Iterator<Element> iterator = root.elementIterator();
			while(iterator.hasNext()){
				Element element = iterator.next();
				PosItemDTO posItem = new PosItemDTO();
				posItem.setItemNum(Integer.parseInt(element.selectSingleNode("商品编号").getText()));
				posItem.setItemName(element.selectSingleNode("商品名称").getText());
				posItem.setItemCode(element.selectSingleNode("商品代码").getText());
				
				Element subElement = (Element) element.selectSingleNode("商品库存计量单位");
				if(subElement != null){
					posItem.setItemInventoryUnit(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("商品规格");
				if(subElement != null){
					posItem.setItemSpec(subElement.getText());
				}
				posItems.add(posItem);
			}
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		return posItems;
	}
	
	public static String writeShortItemToXml(List<PosItemDTO> posItems){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("商品列表");
		writeToNode(posItems, root);
		return document.asXML();
	}
	
	public static String writeSequenceToXml(List<PosItemDTO> posItems){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("PosItemList");
		for(int i = 0;i < posItems.size();i++){
			PosItemDTO posItem = posItems.get(i);
			Element element = root.addElement("PosItemDTO");
			element.addElement("ItemNum").setText(posItem.getItemNum().toString());
			element.addElement("ItemSequence").setText(posItem.getItemSequence().toString());
		}
		return document.asXML();
	}
	
	public static List<PosItemDTO> readSequenceFromXml(String text){
		List<PosItemDTO> posItems = new ArrayList<PosItemDTO>();
		try {
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			Iterator<Element> iterator = root.elementIterator();
			Element subElement;
			while(iterator.hasNext()){
				Element element = iterator.next();
				PosItemDTO posItem = new PosItemDTO();
				
			    subElement = (Element) element.selectSingleNode("ItemNum");
				if(subElement != null){
					posItem.setItemNum(Integer.parseInt(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ItemSequence");
				if(subElement != null){
					posItem.setItemSequence(Integer.parseInt(subElement.getText()));
				}
				posItems.add(posItem);
			}			
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return posItems;
	}


	

	
	public BigDecimal getItemOutTax() {
		return itemOutTax;
	}

	public void setItemOutTax(BigDecimal itemOutTax) {
		this.itemOutTax = itemOutTax;
	}

	public BigDecimal getItemInTax() {
		return itemInTax;
	}

	public void setItemInTax(BigDecimal itemInTax) {
		this.itemInTax = itemInTax;
	}

	
	public boolean checkItemUnit(){
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		map.put(itemUnit, BigDecimal.ONE); //基本单位
		
		BigDecimal value = null;
		value = map.get(itemPurchaseUnit);
		if(value == null){
			map.put(itemPurchaseUnit, itemPurchaseRate);
		} else {
			if(value.compareTo(itemPurchaseRate) != 0){
				return false;
			} 
		}
		
		value = map.get(itemInventoryUnit);
		if(value == null){
			map.put(itemInventoryUnit, itemInventoryRate);
		} else {
			if(value.compareTo(itemInventoryRate) != 0){
				return false;
			} 
		}
		
		value = map.get(itemTransferUnit);
		if(value == null){
			map.put(itemTransferUnit, itemTransferRate);
		} else {
			if(value.compareTo(itemTransferRate) != 0){
				return false;
			} 
		}
		
		value = map.get(itemWholesaleUnit);
		if(value == null){
			map.put(itemWholesaleUnit, itemWholesaleRate);
		} else {
			if(value.compareTo(itemWholesaleRate) != 0){
				return false;
			} 
		}
		return true;
	}

	public static PosItemDTO getPosItem(Integer itemNum, List<PosItemDTO> posItems) {
		if (posItems == null) {
			return null;
		}
		for (int i = 0; i < posItems.size(); i++) {
			if (posItems.get(i).getItemNum().equals(itemNum)) {
				return posItems.get(i);
			}
		}
		return null;
	}


	public String getItemCostMode(BranchDTO branch){
		if(itemCostMode.trim().equals("中心手工指定")){

			if(branch == null
					|| branch.getBranchNum().equals(99)
					|| branch.getBranchRdc()){
				return "手工指定";
			} else {
				return "加权平均法";
			}

		} else {
			return itemCostMode;
		}
	}

}