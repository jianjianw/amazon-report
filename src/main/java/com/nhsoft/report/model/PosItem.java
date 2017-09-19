package com.nhsoft.report.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhsoft.report.shared.AppConstants;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.DateUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

@SuppressWarnings("unchecked")
public class PosItem implements java.io.Serializable {

	private static final Logger logger = LoggerFactory.getLogger(PosItem.class);
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

	private List<ItemBar> itemBars = new ArrayList<ItemBar>(); 
	private List<ItemMatrix> itemMatrixs = new ArrayList<ItemMatrix>(); //lazy = false
	private List<PosItemKit> posItemKits = new ArrayList<PosItemKit>();
	
	@JsonIgnore
	private List<StoreMatrix> storeMatrixs = new ArrayList<StoreMatrix>();
	
	@JsonIgnore
	private List<CollectionItem> collectionItems = new ArrayList<CollectionItem>();
	
	@JsonIgnore
	private List<PosItemGrade> posItemGrades = new ArrayList<PosItemGrade>();
	
	
	@JsonIgnore
	private List<ItemProperty> itemPropertys = new ArrayList<ItemProperty>();
	
	//临时属性
	@JsonIgnore
	private SaleCommission saleCommission;

	
	//临时属性 供应商新品申请用到
	private Boolean updateImages = false;
	private List<PosImage> posImages;
	private PosItemDetail posItemDetail;
	private List<String> ossObjectIds;
	private String itemMemo;
	//导入商品时用到
	private StoreItemSupplier storeItemSupplier;
	private AppUser appUser;
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

	public List<ItemProperty> getItemPropertys() {
		return itemPropertys;
	}

	public void setItemPropertys(List<ItemProperty> itemPropertys) {
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

	public List<PosItemGrade> getPosItemGrades() {
		return posItemGrades;
	}

	public void setPosItemGrades(List<PosItemGrade> posItemGrades) {
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

	public List<PosImage> getPosImages() {
		return posImages;
	}

	public void setPosImages(List<PosImage> posImages) {
		this.posImages = posImages;
	}

	public PosItemDetail getPosItemDetail() {
		return posItemDetail;
	}

	public void setPosItemDetail(PosItemDetail posItemDetail) {
		this.posItemDetail = posItemDetail;
	}

	public List<PosItemKit> getPosItemKits() {
		return posItemKits;
	}

	public void setPosItemKits(List<PosItemKit> posItemKits) {
		this.posItemKits = posItemKits;
	}

	public List<ItemMatrix> getItemMatrixs() {
		return itemMatrixs;
	}

	public void setItemMatrixs(List<ItemMatrix> itemMatrixs) {
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
	
	public SaleCommission getSaleCommission() {
		return saleCommission;
	}

	public void setSaleCommission(SaleCommission saleCommission) {
		this.saleCommission = saleCommission;
	}

	public List<StoreMatrix> getStoreMatrixs() {
		return storeMatrixs;
	}

	public void setStoreMatrixs(List<StoreMatrix> storeMatrixs) {
		this.storeMatrixs = storeMatrixs;
	}

	public List<CollectionItem> getCollectionItems() {
		return collectionItems;
	}

	public void setCollectionItems(List<CollectionItem> collectionItems) {
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

	public List<ItemBar> getItemBars() {
		return itemBars;
	}

	public void setItemBars(List<ItemBar> itemBars) {
		this.itemBars = itemBars;
	}

	public Boolean getItemSynchFlag() {
		return itemSynchFlag;
	}

	public void setItemSynchFlag(Boolean itemSynchFlag) {
		this.itemSynchFlag = itemSynchFlag;
	}

	public StoreItemSupplier getStoreItemSupplier() {
		return storeItemSupplier;
	}

	public void setStoreItemSupplier(StoreItemSupplier storeItemSupplier) {
		this.storeItemSupplier = storeItemSupplier;
	}

	public String getItemEnName() {
		return itemEnName;
	}

	public void setItemEnName(String itemEnName) {
		this.itemEnName = itemEnName;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
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
		PosItem other = (PosItem) obj;
		if (itemNum == null) {
			if (other.itemNum != null)
				return false;
		} else if (!itemNum.equals(other.itemNum))
			return false;
		return true;
	}

	public String writeToXml(){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("POS_ITEM_LIST");
		Element element = root.addElement("POS_ITEM");
		
		if(itemNum != null){
			element.addElement("ITEM_NUM").setText(itemNum.toString());
			
		} else {
			element.addElement("ITEM_NUM").setText("0");
		}
		
		element.addElement("SYSTEM_BOOK_CODE").setText(systemBookCode);
		Element subElement = element.addElement("ITEM_CODE");
		if(itemCode != null){
			subElement.setText(itemCode);
		}
		
		subElement = element.addElement("ITEM_BARCODE");
		if(itemBarcode != null){
			subElement.addText(itemBarcode);
		}
		
		subElement = element.addElement("ITEM_NAME");
		if(itemName != null){
			subElement.addText(itemName);
		}
		
		subElement = element.addElement("ITEM_SPEC");
		if(itemSpec != null){
			subElement.addText(itemSpec);
		}
		
		subElement = element.addElement("STORE_ITEM_PINYIN");
		if(storeItemPinyin != null){
			subElement.addText(storeItemPinyin);
		}
		
		if(itemType != null){
			subElement = element.addElement("ITEM_TYPE");	
			subElement.addText(itemType.toString());
		}
		
		subElement = element.addElement("ITEM_UNIT");
		if(itemUnit != null){
			subElement.addText(itemUnit);
		}
		
		subElement = element.addElement("ITEM_DEPARTMENT");
		if(itemDepartment != null){
			subElement.addText(itemDepartment);
		}
		
		subElement = element.addElement("ITEM_CATEGORY");
		if(itemCategory != null){
			subElement.addText(itemCategory);
		}
		
		subElement = element.addElement("ITEM_CATEGORY_CODE");
		if(itemCategoryCode != null){
			subElement.addText(itemCategoryCode);
		}
		
		subElement = element.addElement("ITEM_REGULAR_PRICE");
		if(itemRegularPrice != null){
			subElement.addText(itemRegularPrice.toString());
		} else {
			subElement.addText("0");
		}
		
		subElement = element.addElement("ITEM_LEVEL2_PRICE");
		if(itemLevel2Price != null){
			subElement.addText(itemLevel2Price.toString());
		} else {
			subElement.addText("0");
		}
		
		subElement = element.addElement("ITEM_LEVEL3_PRICE");
		if(itemLevel3Price != null){
			subElement.addText(itemLevel3Price.toString());
		} else {
			subElement.addText("0");
		}
		
		subElement = element.addElement("ITEM_LEVEL4_PRICE");
		if(itemLevel4Price != null){
			subElement.addText(itemLevel4Price.toString());
		} else {
			subElement.addText("0");
		}
		
		subElement = element.addElement("ITEM_COST_MODE");
		if(itemCostMode != null){
			subElement.addText(itemCostMode);
		} 
		
		subElement = element.addElement("ITEM_VALID_PERIOD");
		if(itemValidPeriod != null){
			subElement.addText(itemValidPeriod.toString());
		} else {
			subElement.addText("0");
		}
		
		subElement = element.addElement("ITEM_REMIND_PERIOD");
		if(itemRemindPeriod != null){
			subElement.addText(itemRemindPeriod.toString());
		} else {
			subElement.addText("0");
		}
		
		if(itemStockCeaseFlag == null){
			itemStockCeaseFlag = false;
		}
		subElement = element.addElement("ITEM_STOCK_CEASE_FLAG").addText(BooleanUtils.toString(itemStockCeaseFlag, "1", "0"));
		
		if(itemSaleCeaseFlag == null){
			itemSaleCeaseFlag = false;
		}
		subElement = element.addElement("ITEM_SALE_CEASE_FLAG").addText(BooleanUtils.toString(itemSaleCeaseFlag, "1", "0"));

		subElement = element.addElement("ITEM_PURCHASE_SCOPE");
		if(itemPurchaseScope != null){
			subElement.addText(itemPurchaseScope);
		}
		
		subElement = element.addElement("ITEM_COST_PRICE");
		if(itemCostPrice != null){
			subElement.addText(itemCostPrice.toString());
		} else {
			subElement.addText("0");
		}
		
		subElement = element.addElement("ITEM_TRANSFER_PRICE");
		if(itemTransferPrice != null){
			subElement.addText(itemTransferPrice.toString());
		} else {
			subElement.addText("0");
		}
		
		subElement = element.addElement("ITEM_NOTE_INFO");
		if(itemNoteInfo != null){
			subElement.addText(itemNoteInfo);
		} 
		
		subElement = element.addElement("ITEM_SALE_MESSAGE");
		if(itemSaleMessage != null){
			subElement.addText(itemSaleMessage);
		} 
		
		subElement = element.addElement("ITEM_INVOICE_MESSAGE");
		if(itemInvoiceMessage != null){
			subElement.addText(itemInvoiceMessage);
		} 
		
		if(storeItemFlag == null){
			storeItemFlag = false;
		}
		subElement = element.addElement("STORE_ITEM_FLAG").addText(BooleanUtils.toString(storeItemFlag, "1", "0"));
		
		if(itemPosChangePriceFlag == null){
			itemPosChangePriceFlag = false;
		}
		subElement = element.addElement("ITEM_POS_CHANGE_PRICE_FLAG").addText(BooleanUtils.toString(itemPosChangePriceFlag, "1", "0"));

		if(itemMinPrice == null){
			itemMinPrice = BigDecimal.ZERO;
		}
		subElement = element.addElement("ITEM_MIN_PRICE").addText(itemMinPrice.toString());
		
		if(itemDelTag == null){
			itemDelTag = false;
		}
		subElement = element.addElement("ITEM_DEL_TAG").addText(BooleanUtils.toString(itemDelTag, "1", "0"));
		
		if(itemPointActived == null){
			itemPointActived = false;
		}
		subElement = element.addElement("ITEM_POINT_ACTIVED").addText(BooleanUtils.toString(itemPointActived, "1", "0"));
		
		if(itemPointValue == null){
			itemPointValue = BigDecimal.ZERO;
		}
		subElement = element.addElement("ITEM_POINT_ACTIVED").addText(itemPointValue.toString());
		
		subElement = element.addElement("ITEM_BRAND");
		if(itemBrand != null){
			subElement.addText(itemBrand);
		}
		
		if(itemSequence == null){
			itemSequence = 0;
		}
		element.addElement("ITEM_SEQUENCE").setText(itemSequence.toString());
		
		
		subElement = element.addElement("ITEM_PLACE");
		if(itemPlace != null){
			subElement.addText(itemPlace);
		}
		
		subElement = element.addElement("ITEM_STORE_PLACE");
		if(itemStorePlace != null){
			subElement.addText(itemStorePlace);
		}
		
		subElement = element.addElement("ITEM_STORE_PLACE");
		if(itemStorePlace != null){
			subElement.addText(itemStorePlace);
		}
		
		subElement = element.addElement("SALE_COMMISSION");
		if(saleCommission == null){
			saleCommission = new SaleCommission();
		}
		if(itemNum != null){
			subElement.addElement("ITEM_NUM").addText(itemNum.toString());
		} else {
			subElement.addElement("ITEM_NUM").addText("0");
			
		}
		subElement.addElement("COMMISSION_TYPE").addText(saleCommission.getCommissionType() == null?"":saleCommission.getCommissionType().toString());
		subElement.addElement("COMMISSION_MONEY").addText(saleCommission.getCommissionMoney() == null?"0":saleCommission.getCommissionMoney().toString());
		subElement.addElement("COMMISSION_MAX").addText(saleCommission.getCommissionMax() == null?"0":saleCommission.getCommissionMax().toString());
		subElement.addElement("COMMISSION_BASE").addText(saleCommission.getCommissionBase() == null?"0":saleCommission.getCommissionBase().toString());

		if(itemEliminativeFlag == null){
			itemEliminativeFlag = false;
		}		
		element.addElement("ITEM_ELIMINATIVE_FLAG").addText(BooleanUtils.toString(itemEliminativeFlag, "1", "0"));
		
		if(itemCreateTime == null){
			element.addElement("ITEM_CREATE_TIME").addText("18991230T00:00:00");
		} else {
			element.addElement("ITEM_CREATE_TIME").addText(DateUtil.getXmlTString(itemCreateTime));
		}
		
		if(itemWeightFlag == null){
			itemWeightFlag = false;
		}		
		element.addElement("ITEM_WEIGHT_FLAG").addText(BooleanUtils.toString(itemWeightFlag, "1", "0"));
		
		if(itemAssistRate == null){
			itemAssistRate = BigDecimal.ZERO;
		}		
		element.addElement("ITEM_WEIGHT_FLAG").addText(itemAssistRate.toString());
		
		subElement = element.addElement("ITEM_PURCHASE_UNIT");
		if(itemPurchaseUnit != null){
			subElement.setText(itemPurchaseUnit);
		}
		
		if(itemPurchaseRate == null){
			itemPurchaseRate = BigDecimal.ONE;
		}
		element.addElement("ITEM_PURCHASE_RATE").setText(itemPurchaseRate.toString());
		
		subElement = element.addElement("ITEM_INVENTORY_UNIT");
		if(itemInventoryUnit != null){
			subElement.setText(itemInventoryUnit);
		}
		
		if(itemInventoryRate == null){
			itemInventoryRate = BigDecimal.ONE;
		}
		element.addElement("ITEM_INVENTORY_RATE").setText(itemInventoryRate.toString());
		
		subElement = element.addElement("ITEM_TRANSFER_UNIT");
		if(itemTransferUnit != null){
			subElement.setText(itemTransferUnit);
		}
		
		if(itemTransferRate == null){
			itemTransferRate = BigDecimal.ONE;
		}
		element.addElement("ITEM_TRANSFER_RATE").setText(itemTransferRate.toString());
		
		subElement = element.addElement("ITEM_WHOLESALE_UNIT");
		if(itemWholesaleUnit != null){
			subElement.setText(itemWholesaleUnit);
		}
		
		if(itemWholesaleRate == null){
			itemWholesaleRate = BigDecimal.ONE;
		}
		element.addElement("ITEM_WHOLESALE_RATE").setText(itemWholesaleRate.toString());
				
		subElement = element.addElement("ITEM_UNIT_GROUP");
		if(itemUnitGroup != null){
			subElement.setText(itemUnitGroup);
		}
		if(itemWholesalePrice == null){
			itemWholesalePrice = BigDecimal.ZERO;
		}
		element.addElement("ITEM_WHOLESALE_PRICE").setText(itemWholesalePrice.toString());
		
		if(itemPriceTagFlag == null){
			itemPriceTagFlag = false;
		}		
		element.addElement("ITEM_PRICE_TAG_FLAG").addText(BooleanUtils.toString(itemPriceTagFlag, "1", "0"));
		
		subElement = element.addElement("ITEM_METHOD");
		if(itemMethod != null){
			subElement.setText(itemMethod);
		}
		
		if(itemGrossRate == null){
			itemGrossRate = BigDecimal.ZERO;
		}
		element.addElement("ITEM_GROSS_RATE").addText(itemGrossRate.toString());
		
		if(itemLevel2Wholesale == null){
			itemLevel2Wholesale = BigDecimal.ZERO;
		}
		element.addElement("ITEM_LEVEL2_WHOLESALE").addText(itemLevel2Wholesale.toString());
		
		if(itemLevel3Wholesale == null){
			itemLevel3Wholesale = BigDecimal.ZERO;
		}
		element.addElement("ITEM_LEVEL3_WHOLESALE").addText(itemLevel3Wholesale.toString());
		
		if(itemLevel4Wholesale == null){
			itemLevel4Wholesale = BigDecimal.ZERO;
		}
		element.addElement("ITEM_LEVEL4_WHOLESALE").addText(itemLevel4Wholesale.toString());
				
		element.addElement("ITEM_WHOLESALE_FLAG").addText(BooleanUtils.toString(itemWholesaleFlag, "1", "0", "1"));
		element.addElement("ITEM_TRANSFER_FIXED_GROSS").addText(BooleanUtils.toString(itemTransferFixedGross, "1", "0", "0"));
		element.addElement("ITEM_WHOLESALE_FIXED_GROSS").addText(BooleanUtils.toString(itemWholesaleFixedGross, "1", "0", "0"));
		element.addElement("ITEM_DISCOUNTED").addText(BooleanUtils.toString(itemDiscounted, "1", "0", "0"));
		
		if(itemWholesaleGross != null){
			element.addElement("ITEM_WHOLESALE_GROSS").addText(itemWholesaleGross.toString());
		}
		if(itemTransferGross != null){
			element.addElement("ITEM_TRANSFER_GROSS").addText(itemTransferGross.toString());
		}
		if(itemWholesaleGross2 != null){
			element.addElement("ITEM_WHOLESALE_GROSS2").addText(itemWholesaleGross2.toString());
		}
		if(itemWholesaleGross3 != null){
			element.addElement("ITEM_WHOLESALE_GROSS3").addText(itemWholesaleGross3.toString());
		}		
		if(itemWholesaleGross4 != null){
			element.addElement("ITEM_WHOLESALE_GROSS4").addText(itemWholesaleGross4.toString());
		}
		element.addElement("ITEM_WHOLESALE_FIXED_GROSS2").addText(BooleanUtils.toString(itemWholesaleFixedGross2, "1", "0", "0"));
		element.addElement("ITEM_WHOLESALE_FIXED_GROSS3").addText(BooleanUtils.toString(itemWholesaleFixedGross3, "1", "0", "0"));
		element.addElement("ITEM_WHOLESALE_FIXED_GROSS4").addText(BooleanUtils.toString(itemWholesaleFixedGross4, "1", "0", "0"));

		
		if(itemPointValue != null){
			element.addElement("ITEM_POINT_VALUE").addText(itemPointValue.toString());
		}
		if(itemTransferDay != null){
			element.addElement("ITEM_TRANSFER_DAY").addText(itemTransferDay.toString());
		}
		if(itemAssistUnit != null){
			element.addElement("ITEM_ASSIST_UNIT").addText(itemAssistUnit);
		}
		if(itemAssistRate != null){
			element.addElement("ITEM_ASSIST_RATE").addText(itemAssistRate.toString());
		}
		if(itemPrintLabelFlag != null){
			element.addElement("ITEM_PRINT_LABEL_FLAG").addText(BooleanUtils.toString(itemPrintLabelFlag, "1", "0", "0"));
		}
		if(itemMaxPrice != null){
			element.addElement("ITEM_MAX_PRICE").addText(itemMaxPrice.toString());
		}
		
		if(itemMemo != null){
			element.addElement("ITEM_MEMO").addText(itemMemo);

		}
		
		if(posItemKits != null){
			subElement = element.addElement("POS_ITEM_KIT_LIST");
			for(int i = 0;i < posItemKits.size();i++){
				PosItemKit posItemKit = posItemKits.get(i);
				Element kitElement = subElement.addElement("POS_ITEM_KIT");
				kitElement.addElement("ITEM_NUM").setText("0");
				kitElement.addElement("KIT_ITEM_NUM").setText(posItemKit.getId().getKitItemNum().toString());
				kitElement.addElement("POS_ITEM_KIT_AMOUNT").setText(posItemKit.getPosItemKitAmount().toString());
				
				if(posItemKit.getPosItemKitItemMatrixNum() != null){
					kitElement.addElement("POS_ITEM_KIT_ITEM_MATRIX_NUM").setText(posItemKit.getPosItemKitItemMatrixNum().toString());
				}
				if(posItemKit.getPosItemAmountUnFixed() != null){
					kitElement.addElement("POS_ITEM_AMOUNT_UN_FIXED").setText(BooleanUtils.toString(posItemKit.getPosItemAmountUnFixed(), "1", "0", "0"));
				}
				
			}
			
		}
		
		subElement = element.addElement("ITEM_BAR_LIST");
		for(int i = 0;i < itemBars.size();i++){
			ItemBar itemBar = itemBars.get(i);
			Element barElement = subElement.addElement("ITEM_BAR");
			barElement.addElement("ITEM_NUM").setText("0");
			barElement.addElement("ITEM_BAR_CODE").setText(itemBar.getItemBarCode());
			barElement.addElement("SYSTEM_BOOK_CODE").setText(itemBar.getSystemBookCode());
		}
		
		subElement = element.addElement("ITEM_MATRIX_LIST");
		for(int i = 0;i < itemMatrixs.size();i++){
			ItemMatrix itemMatrix = itemMatrixs.get(i);
			Element m = subElement.addElement("ITEM_MATRIX");
			m.addElement("ITEM_NUM").setText("0");
			m.addElement("ITEM_MATRIX_NUM").setText("0");
	
			Element matrixElement = m.addElement("ITEM_MATRIX_CODE");
			if(itemMatrix.getItemMatrixCode() != null){
				matrixElement.setText(itemMatrix.getItemMatrixCode());
				
			}
			
			matrixElement = m.addElement("ITEM_MATRIX_BARCODE");
			if(itemMatrix.getItemMatrixBarcode() != null){
				matrixElement.setText(itemMatrix.getItemMatrixBarcode());
				
			}
			
			matrixElement = m.addElement("ITEM_MATRIX_01");
			if(itemMatrix.getItemMatrix01() != null){
				matrixElement.setText(itemMatrix.getItemMatrix01());
				
			}
			
			matrixElement = m.addElement("ITEM_MATRIX_02");
			if(itemMatrix.getItemMatrix02() != null){
				matrixElement.setText(itemMatrix.getItemMatrix02());
				
			}
			
			matrixElement = m.addElement("ITEM_MATRIX_03");
			if(itemMatrix.getItemMatrix03() != null){
				matrixElement.setText(itemMatrix.getItemMatrix03());
				
			}
			
			matrixElement = m.addElement("ITEM_MATRIX_04");
			if(itemMatrix.getItemMatrix04() != null){
				matrixElement.setText(itemMatrix.getItemMatrix04());
				
			}
			
			matrixElement = m.addElement("ITEM_MATRIX_05");
			if(itemMatrix.getItemMatrix05() != null){
				matrixElement.setText(itemMatrix.getItemMatrix05());
				
			}
			
			matrixElement = m.addElement("ITEM_MATRIX_06");
			if(itemMatrix.getItemMatrix06() != null){
				matrixElement.setText(itemMatrix.getItemMatrix06());
				
			}
			m.addElement("ITEM_MATRIX_DEL_TAG").setText("0");
		}
		
		if(posImages != null){
			subElement = element.addElement("POS_IMAGE_LIST");
			for(int i = 0;i < posImages.size();i++){
				PosImage posImage = posImages.get(i);
				Element imageElement = subElement.addElement("POS_IMAGE");
				imageElement.addElement("POS_IMAGE_ID").setText(posImage.getPosImageId());
			}
		}
		if(posItemDetail != null){
			subElement = element.addElement("POS_ITEM_DETAIL");
			if(StringUtils.isNotEmpty(posItemDetail.getItemDetailContext())){
				subElement.addElement("ITEM_DETAIL_CONTEXT").setText(posItemDetail.getItemDetailContext());
				
			}
			if(posItemDetail.getSystemImageIds().size() > 0){
				Element imageElement = subElement.addElement("ITEM_DETAIL_IMAGE_ID_LIST");
				for(int i = 0;i < posItemDetail.getSystemImageIds().size();i++){
					imageElement.addElement("ITEM_DETAIL_IMAGE_ID").setText(posItemDetail.getSystemImageIds().get(i));
				}
			}
		}
		if(ossObjectIds != null){
			subElement = element.addElement("OSS_OBJECT_ID_LIST");
			for(int i = 0;i < ossObjectIds.size();i++){
				subElement.addElement("OSS_OBJECT_ID").setText(ossObjectIds.get(i));
			}
		}

		return document.asXML();
	}
	
	public static PosItem readFromXml(String text){	
		if(StringUtils.isEmpty(text)){
			return null;
		}
		try {
			PosItem posItem = new PosItem();
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			Element element = (Element) root.selectSingleNode("POS_ITEM");
			if(element != null){
				Element subElement = (Element) element.selectSingleNode("ITEM_NUM");
				if(subElement != null){
					posItem.setItemNum(Integer.parseInt(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("SYSTEM_BOOK_CODE");
				if(subElement != null){
					posItem.setSystemBookCode(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_CODE");
				if(subElement != null){
					posItem.setItemCode(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_BARCODE");
				if(subElement != null){
					posItem.setItemBarcode(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_BARCODE");
				if(subElement != null){
					posItem.setItemBarcode(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_NAME");
				if(subElement != null){
					posItem.setItemName(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_SPEC");
				if(subElement != null){
					posItem.setItemSpec(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("STORE_ITEM_PINYIN");
				if(subElement != null){
					posItem.setStoreItemPinyin(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_TYPE");
				if(subElement != null && !subElement.getText().equals("")){
					posItem.setItemType(Integer.parseInt(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_UNIT");
				if(subElement != null){
					posItem.setItemUnit(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_DEPARTMENT");
				if(subElement != null){
					posItem.setItemDepartment(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_CATEGORY");
				if(subElement != null){
					posItem.setItemCategory(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_CATEGORY_CODE");
				if(subElement != null){
					posItem.setItemCategoryCode(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_REGULAR_PRICE");
				if(subElement != null){
					posItem.setItemRegularPrice(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_LEVEL2_PRICE");
				if(subElement != null){
					posItem.setItemLevel2Price(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_LEVEL3_PRICE");
				if(subElement != null){
					posItem.setItemLevel3Price(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_LEVEL4_PRICE");
				if(subElement != null){
					posItem.setItemLevel4Price(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_COST_MODE");
				if(subElement != null){
					posItem.setItemCostMode(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_VALID_PERIOD");
				if(subElement != null){
					posItem.setItemValidPeriod(Integer.parseInt(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_REMIND_PERIOD");
				if(subElement != null){
					posItem.setItemRemindPeriod(Integer.parseInt(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_STOCK_CEASE_FLAG");
				if(subElement != null){
					posItem.setItemStockCeaseFlag(AppUtil.strToBool(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_SALE_CEASE_FLAG");
				if(subElement != null){
					posItem.setItemSaleCeaseFlag(AppUtil.strToBool(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_PURCHASE_SCOPE");
				if(subElement != null){
					posItem.setItemPurchaseScope(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_COST_PRICE");
				if(subElement != null){
					posItem.setItemCostPrice(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_TRANSFER_PRICE");
				if(subElement != null){
					posItem.setItemTransferPrice(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_NOTE_INFO");
				if(subElement != null){
					posItem.setItemNoteInfo(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_SALE_MESSAGE");
				if(subElement != null){
					posItem.setItemSaleMessage(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_INVOICE_MESSAGE");
				if(subElement != null){
					posItem.setItemInvoiceMessage(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("STORE_ITEM_FLAG");
				if(subElement != null){
					posItem.setStoreItemFlag(AppUtil.strToBool(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_POS_CHANGE_PRICE_FLAG");
				if(subElement != null){
					posItem.setItemPosChangePriceFlag(BooleanUtils.toBoolean(subElement.getText(), "1", "0"));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_MIN_PRICE");
				if(subElement != null){
					posItem.setItemMinPrice(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_DEL_TAG");
				if(subElement != null){
					posItem.setItemDelTag(AppUtil.strToBool(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_POINT_ACTIVED");
				if(subElement != null){
					posItem.setItemPointActived(AppUtil.strToBool(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_POINT_VALUE");
				if(subElement != null){
					posItem.setItemPointValue(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_BRAND");
				if(subElement != null){
					posItem.setItemBrand(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_SEQUENCE");
				if(subElement != null){
					posItem.setItemSequence(Integer.parseInt(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_PLACE");
				if(subElement != null){
					posItem.setItemPlace(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_STORE_PLACE");
				if(subElement != null){
					posItem.setItemStorePlace(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_ASSIST_UNIT");
				if(subElement != null){
					posItem.setItemAssistUnit(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_ELIMINATIVE_FLAG");
				if(subElement != null){
					posItem.setItemEliminativeFlag(AppUtil.strToBool(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_CREATE_TIME");
				if(subElement != null){
					posItem.setItemCreateTime(DateUtil.getXmlTDate(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_WEIGHT_FLAG");
				if(subElement != null){
					posItem.setItemWeightFlag(AppUtil.strToBool(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_ASSIST_RATE");
				if(subElement != null){
					posItem.setItemAssistRate(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_PURCHASE_UNIT");
				if(subElement != null){
					posItem.setItemPurchaseUnit(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_PURCHASE_RATE");
				if(subElement != null){
					posItem.setItemPurchaseRate(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_INVENTORY_UNIT");
				if(subElement != null){
					posItem.setItemInventoryUnit(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_INVENTORY_RATE");
				if(subElement != null){
					posItem.setItemInventoryRate(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_TRANSFER_UNIT");
				if(subElement != null){
					posItem.setItemTransferUnit(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_TRANSFER_RATE");
				if(subElement != null){
					posItem.setItemTransferRate(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_WHOLESALE_UNIT");
				if(subElement != null){
					posItem.setItemWholesaleUnit(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_WHOLESALE_RATE");
				if(subElement != null){
					posItem.setItemWholesaleRate(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_UNIT_GROUP");
				if(subElement != null){
					posItem.setItemUnitGroup(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_WHOLESALE_PRICE");
				if(subElement != null){
					posItem.setItemWholesalePrice(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_PRICE_TAG_FLAG");
				if(subElement != null){
					posItem.setItemPriceTagFlag(AppUtil.strToBool(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_METHOD");
				if(subElement != null){
					posItem.setItemMethod(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_GROSS_RATE");
				if(subElement != null){
					posItem.setItemGrossRate(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("item_level2_wholesale");
				if(subElement == null){
					subElement = (Element) element.selectSingleNode("item_level2_wholesale".toUpperCase());
				} 
				if(subElement != null){
					posItem.setItemLevel2Wholesale(new BigDecimal(subElement.getText()));
					
				}
				
				subElement = (Element) element.selectSingleNode("item_level3_wholesale");
				if(subElement == null){
					subElement = (Element) element.selectSingleNode("item_level3_wholesale".toUpperCase());
				} 
				if(subElement != null){
					posItem.setItemLevel3Wholesale(new BigDecimal(subElement.getText()));
					
				}
				
				subElement = (Element) element.selectSingleNode("item_level4_wholesale");
				if(subElement == null){
					subElement = (Element) element.selectSingleNode("item_level4_wholesale".toUpperCase());
				} 
				if(subElement != null){
					posItem.setItemLevel4Wholesale(new BigDecimal(subElement.getText()));
					
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_WHOLESALE_FLAG");
				if(subElement != null){
					posItem.setItemWholesaleFlag(AppUtil.strToBool(subElement.getText()));
				} else {
					posItem.setItemWholesaleFlag(true);
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_TRANSFER_FIXED_GROSS");
				if(subElement != null){
					posItem.setItemTransferFixedGross(AppUtil.strToBool(subElement.getText()));
				} else {
					posItem.setItemTransferFixedGross(false);
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_WHOLESALE_FIXED_GROSS");
				if(subElement != null){
					posItem.setItemWholesaleFixedGross(AppUtil.strToBool(subElement.getText()));
				} else {
					posItem.setItemWholesaleFixedGross(false);
				}
				subElement = (Element) element.selectSingleNode("ITEM_WHOLESALE_FIXED_GROSS2");
				if(subElement != null){
					posItem.setItemWholesaleFixedGross2(AppUtil.strToBool(subElement.getText()));
				} else {
					posItem.setItemWholesaleFixedGross2(false);
				}
				subElement = (Element) element.selectSingleNode("ITEM_WHOLESALE_FIXED_GROSS3");
				if(subElement != null){
					posItem.setItemWholesaleFixedGross3(AppUtil.strToBool(subElement.getText()));
				} else {
					posItem.setItemWholesaleFixedGross3(false);
				}
				subElement = (Element) element.selectSingleNode("ITEM_WHOLESALE_FIXED_GROSS4");
				if(subElement != null){
					posItem.setItemWholesaleFixedGross4(AppUtil.strToBool(subElement.getText()));
				} else {
					posItem.setItemWholesaleFixedGross4(false);
				}
								
				subElement = (Element) element.selectSingleNode("ITEM_WHOLESALE_GROSS");
				if(subElement != null){
					posItem.setItemWholesaleGross(new BigDecimal(subElement.getText()));
				}
				subElement = (Element) element.selectSingleNode("ITEM_WHOLESALE_GROSS2");
				if(subElement != null){
					posItem.setItemWholesaleGross2(new BigDecimal(subElement.getText()));
				}
				subElement = (Element) element.selectSingleNode("ITEM_WHOLESALE_GROSS3");
				if(subElement != null){
					posItem.setItemWholesaleGross3(new BigDecimal(subElement.getText()));
				}
				subElement = (Element) element.selectSingleNode("ITEM_WHOLESALE_GROSS4");
				if(subElement != null){
					posItem.setItemWholesaleGross4(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_TRANSFER_GROSS");
				if(subElement != null){
					posItem.setItemTransferGross(new BigDecimal(subElement.getText()));
				}
				subElement = (Element) element.selectSingleNode("ITEM_DISCOUNTED");
				if(subElement != null){
					posItem.setItemDiscounted(AppUtil.strToBool(subElement.getText()));
				} else {
					posItem.setItemDiscounted(false);
				}
				subElement = (Element) element.selectSingleNode("ITEM_TRANSFER_DAY");
				if(subElement != null){
					posItem.setItemTransferDay(new Integer(subElement.getText()));
				} 
				subElement = (Element) element.selectSingleNode("ITEM_PRINT_LABEL_FLAG");
				if(subElement != null){
					posItem.setItemPrintLabelFlag(AppUtil.strToBool(subElement.getText()));
				} 
				subElement = (Element) element.selectSingleNode("ITEM_MAX_PRICE");
				if(subElement != null){
					posItem.setItemMaxPrice(new BigDecimal(subElement.getText()));
				}
				subElement = (Element) element.selectSingleNode("ITEM_MEMO");
				if(subElement != null){
					posItem.setItemMemo(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("SALE_COMMISSION");
				if(subElement != null){
					SaleCommission saleCommission = new SaleCommission();
					saleCommission.setItemNum(null);
					saleCommission.setCommissionType(subElement.selectSingleNode("COMMISSION_TYPE").getText());
					saleCommission.setCommissionMoney(new BigDecimal(subElement.selectSingleNode("COMMISSION_MONEY").getText()));
					saleCommission.setCommissionMax(new BigDecimal(subElement.selectSingleNode("COMMISSION_MAX").getText()));
					saleCommission.setCommissionBase(new BigDecimal(subElement.selectSingleNode("COMMISSION_BASE").getText()));
					posItem.setSaleCommission(saleCommission);
				}
				
				subElement = (Element) element.selectSingleNode("POS_ITEM_KIT_LIST");
				if(subElement != null){
					Iterator<Element> iterator = subElement.elementIterator("POS_ITEM_KIT");
					while(iterator.hasNext()){
						Element kitElement = iterator.next();
						PosItemKit posItemKit = new PosItemKit();
						PosItemKitId id = new PosItemKitId();
						id.setItemNum(Integer.parseInt(kitElement.selectSingleNode("ITEM_NUM").getText()));
						id.setKitItemNum(Integer.parseInt(kitElement.selectSingleNode("KIT_ITEM_NUM").getText()));
						posItemKit.setId(id);
						posItemKit.setPosItemKitAmount(new BigDecimal(kitElement.selectSingleNode("POS_ITEM_KIT_AMOUNT").getText()));			
					
						Element kitSubElement = (Element) kitElement.selectSingleNode("POS_ITEM_KIT_ITEM_MATRIX_NUM");
						if(kitSubElement != null){
							posItemKit.setPosItemKitItemMatrixNum(Integer.parseInt(kitSubElement.getText()));
						}
						kitSubElement = (Element) kitElement.selectSingleNode("POS_ITEM_AMOUNT_UN_FIXED");
						if(kitSubElement != null){
							posItemKit.setPosItemAmountUnFixed(AppUtil.strToBool(kitSubElement.getText()));
						}
						
						posItem.getPosItemKits().add(posItemKit);
					}
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_MATRIX_LIST");
				if(subElement != null){
					Iterator<Element> iterator = subElement.elementIterator("ITEM_MATRIX");
					while(iterator.hasNext()){
						Element matrixElement = iterator.next();
						ItemMatrix itemMatrix = new ItemMatrix();
						ItemMatrixId id = new ItemMatrixId();
						id.setItemMatrixNum(0);
						id.setItemNum(0);
						itemMatrix.setId(id);
						itemMatrix.setItemMatrixCode(matrixElement.selectSingleNode("ITEM_MATRIX_CODE").getText());
						itemMatrix.setItemMatrixBarcode(matrixElement.selectSingleNode("ITEM_MATRIX_BARCODE").getText());
						itemMatrix.setItemMatrix01(matrixElement.selectSingleNode("ITEM_MATRIX_01").getText());
						itemMatrix.setItemMatrix02(matrixElement.selectSingleNode("ITEM_MATRIX_02").getText());
						itemMatrix.setItemMatrix03(matrixElement.selectSingleNode("ITEM_MATRIX_03").getText());
						itemMatrix.setItemMatrix04(matrixElement.selectSingleNode("ITEM_MATRIX_04").getText());
						itemMatrix.setItemMatrix05(matrixElement.selectSingleNode("ITEM_MATRIX_05").getText());
						itemMatrix.setItemMatrix06(matrixElement.selectSingleNode("ITEM_MATRIX_06").getText());
						itemMatrix.setItemMatrixDelTag(false);
						posItem.getItemMatrixs().add(itemMatrix);
					}
				}
				
				subElement = (Element) element.selectSingleNode("ITEM_BAR_LIST");
				if(subElement != null){
					Iterator<Element> iterator = subElement.elementIterator("ITEM_BAR");
					while(iterator.hasNext()){
						Element barElement = iterator.next();
						ItemBar itemBar = new ItemBar();
						ItemBarId id = new ItemBarId();
						id.setItemNum(0);
						itemBar.setId(id);
						
						Node node = barElement.selectSingleNode("ITEM_BAR_CODE");
						if(node == null){
							node = barElement.selectSingleNode("ITEM_BAR_CODE".toLowerCase());
						}
						if(node != null){
							itemBar.setItemBarCode(node.getText());
							
						}
						
						node = barElement.selectSingleNode("SYSTEM_BOOK_CODE");
						if(node == null){
							node = barElement.selectSingleNode("SYSTEM_BOOK_CODE".toLowerCase());
						}
						if(node != null){
							itemBar.setSystemBookCode(node.getText());
							
						}
						posItem.getItemBars().add(itemBar);
					}
				}
				
				subElement = (Element) element.selectSingleNode("POS_IMAGE_LIST");
				if(subElement != null){
					Iterator<Element> iterator = subElement.elementIterator("POS_IMAGE");
					while(iterator.hasNext()){
						Element imageElement = iterator.next();
						PosImage posImage = new PosImage();
						
						Node node = imageElement.selectSingleNode("POS_IMAGE_ID");
						if(node != null){
							posImage.setPosImageId(node.getText());
							
						}
						if(posItem.getPosImages() == null){
							posItem.setPosImages(new ArrayList<PosImage>());
						}
						posItem.getPosImages().add(posImage);
					}
				}
				subElement = (Element) element.selectSingleNode("POS_ITEM_DETAIL");
				if(subElement != null){
					PosItemDetail posItemDetail = new PosItemDetail();
					Node node = subElement.selectSingleNode("ITEM_DETAIL_CONTEXT");
					if(node != null){
						posItemDetail.setItemDetailContext(node.getText());
						
					}
					Element idElement = (Element) subElement.selectSingleNode("ITEM_DETAIL_IMAGE_ID_LIST");
					if(idElement != null){
						Iterator<Element> iterator = idElement.elementIterator();
						while(iterator.hasNext()){
							posItemDetail.getSystemImageIds().add(iterator.next().getText());
						}
					}
					posItem.setPosItemDetail(posItemDetail);
				}
				
				subElement = (Element) element.selectSingleNode("OSS_OBJECT_ID_LIST");
				if(subElement != null){
					Iterator<Element> iterator = subElement.elementIterator();
					while(iterator.hasNext()){
						if(posItem.getOssObjectIds() == null){
							posItem.setOssObjectIds(new ArrayList<String>());
						}
						posItem.getOssObjectIds().add(iterator.next().getText());
					}
				}
			}	
			
			
			return posItem;
		} catch (DocumentException e) {
			logger.error(e.getMessage());
		}				
		return null;
	}
	
	public static List<PosItem> readFromNode(Element node){
		List<PosItem> posItems = new ArrayList<PosItem>();
		if(node == null){
			return posItems;
		}
		Iterator<Element> iterator = node.elementIterator("商品");
		while(iterator.hasNext()){
			PosItem posItem = new PosItem();
			Element element = iterator.next();
			posItem.setItemNum(Integer.parseInt(element.selectSingleNode("商品编号").getText()));
			posItem.setItemName(element.selectSingleNode("商品名称").getText());
			posItem.setItemCode(element.selectSingleNode("商品代码").getText());
			posItems.add(posItem);
		}		
		return posItems;
	}
	
	public static void writeToNode(List<PosItem> posItems, Element node){
		for(int i = 0;i < posItems.size();i++){
			PosItem posItem = posItems.get(i);
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
	
	public static List<PosItem> readShortItemFromXml(String text){
		List<PosItem> posItems = new ArrayList<PosItem>();
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
				PosItem posItem = new PosItem();
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
	
	public static String writeShortItemToXml(List<PosItem> posItems){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("商品列表");
		writeToNode(posItems, root);
		return document.asXML();
	}
	
	public static String writeSequenceToXml(List<PosItem> posItems){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("PosItemList");
		for(int i = 0;i < posItems.size();i++){
			PosItem posItem = posItems.get(i);
			Element element = root.addElement("PosItem");
			element.addElement("ItemNum").setText(posItem.getItemNum().toString());
			element.addElement("ItemSequence").setText(posItem.getItemSequence().toString());
		}
		return document.asXML();
	}
	
	public static List<PosItem> readSequenceFromXml(String text){
		List<PosItem> posItems = new ArrayList<PosItem>();
		try {
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			Iterator<Element> iterator = root.elementIterator();
			Element subElement;
			while(iterator.hasNext()){
				Element element = iterator.next();
				PosItem posItem = new PosItem();
				
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
	
	public String getItemCostMode(Integer branchNum){
		if(itemCostMode.trim().equals(AppConstants.C_ITEM_COST_MODE_CENTER_MANUAL)){		
			
			if(branchNum == null || branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
				return AppConstants.C_ITEM_COST_MODE_MANUAL;
			} else {
				return AppConstants.C_ITEM_COST_MODE_AVERAGE;
			}
			
		} else {
			return itemCostMode;
		}
	}
	
	public String getItemCostMode(Branch branch){
		if(itemCostMode.trim().equals(AppConstants.C_ITEM_COST_MODE_CENTER_MANUAL)){		
			
			if(branch == null 
					|| branch.getId().getBranchNum().equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)
					|| branch.getBranchRdc()){
				return AppConstants.C_ITEM_COST_MODE_MANUAL;
			} else {
				return AppConstants.C_ITEM_COST_MODE_AVERAGE;
			}
			
		} else {
			return itemCostMode;
		}
	}
	
	public String getItemCostMode(Branch branch, Integer branchNum){
		if(itemCostMode.trim().equals(AppConstants.C_ITEM_COST_MODE_CENTER_MANUAL)){		
			
			if(branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)
					|| (branch != null && branch.getBranchRdc())){
				return AppConstants.C_ITEM_COST_MODE_MANUAL;
			} else {
				return AppConstants.C_ITEM_COST_MODE_AVERAGE;
			}
			
		} else {
			return itemCostMode;
		}
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

	public boolean checkAllowRequest(){
		if(StringUtils.equals(itemPurchaseScope, AppConstants.ITEM_PURCHASE_SCOPE_BRANCH)){
			return false;
		}
		if(StringUtils.equals(itemPurchaseScope, AppConstants.ITEM_PURCHASE_SCOPE_SELFPRODUCT)){
			return false;
		}
		if(itemStatus != null && itemStatus.equals(AppConstants.ITEM_STATUS_SLEEP)){
			return false;
		}
		if(itemType.equals(AppConstants.C_ITEM_TYPE_KIT)){
			return false;
		}
		if(itemDelTag != null && itemDelTag){
			return false;
		}
		if(itemEliminativeFlag != null && itemEliminativeFlag){
			return false;
		}
		if(itemType.equals(AppConstants.C_ITEM_TYPE_NON_STOCK)){
			return false;
		}
		if(itemType.equals(AppConstants.C_ITEM_TYPE_CUSTOMER_KIT)){
			return false;
		}
		return true;
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

}