package com.nhsoft.report.param;

import com.nhsoft.report.shared.AppConstants;
import com.nhsoft.report.util.AppUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;

public class PosItemParam implements Serializable {

	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(PosItemParam.class);
	private static final long serialVersionUID = 6287436636611622796L;
	private Boolean supportNonStock;//支持非库存商品
	private Boolean supportStandard;//支持标准
	private Boolean supportMatrix;//支持多特性
	private Boolean supportSerialized;//支持标识码
	private Boolean supportKit;//支持组合商品
	private Boolean supportAssemble;//支持制单组合
	private Boolean supportSplit;//支持制单拆分
	private Boolean supportCustomerKit;//支持自定义组合
	private Boolean supportElement; //支持成分商品
	private Boolean supportGrade; // 支持分级商品
	private Boolean autoCode; //自动产生项目代码
	private Integer codeLengh; //代码位数
	private String codePrix;//代码前缀
	private String codePrixType;//代码前缀类型
	private Boolean autoBarCode;//自动产生项目条码
	private Integer barCodeLength;//条码位数
	private String barCodePrix;//条码前缀
	private String barCodePrixType;//条码前缀类型
	private String itemPosBy; //业务单据商品输入定位
	private Boolean centerControlPrice; //分店价格中心统一设置
	private Boolean centerControlMatrix;//分店补货中心统一设置
	private Boolean transferInNeedOut;
	private Boolean transferOutRefInPrice;
	private Integer priceTagCopyAutoValue; //默认价钱打印份数使用业务单据中的特定值
	private Integer priceTagCopyDefaultValue;//默认价钱打印份数使用统一固定值
	private Boolean sharedCenterSupplier; //中心供应商共享
	private Boolean canChangeSpiltDetail; //组合拆分时允许修改明细商品
	private String priceChargeType;  //在商品档案中调价  or  通过调价单调价
	private Integer itemNewBoundDay;  //商品引进日期多少天内为新品
	private String autoSupplierCode; //自动产生供应商代码
	private Integer supplierCodeLength;//供应商代码长度
	private String supplierCodePrix;//供应商代码前缀
	private String supplierCodePrixType;//供应商前缀类别
	private Boolean enableAssistQty;//启用辅助数量
	
	//休眠商品定义
	private BigDecimal stockLimit; // 库存少于
	private Integer unPurchaseDay;// 未进货少于
	private Integer unPurchaseEffectiveDay;// 未进货有效期少于
	private String itemCheckType;// 未进货选择类型
	private Boolean autoCheckPosItem;// 是否启用
	
	private Boolean productDateAsLotNumber;//启用生产日期即批次号
	private String saleGrossCalcType;//商品售价毛利计算

	public PosItemParam(){
		stockLimit = BigDecimal.valueOf(0.1);
		itemCheckType = AppConstants.UNOUT_DAYS;
		unPurchaseDay = 180;
		unPurchaseEffectiveDay = 8;
		autoCheckPosItem = false;
		enableAssistQty = false;
		supportStandard = true;
		barCodeLength = 0;
		codeLengh = 0;
		supplierCodeLength = 0;
		priceChargeType = "在商品档案中调价";
		itemPosBy = "按商品项目代码查找";
		priceTagCopyAutoValue = 0;
		priceTagCopyDefaultValue = 0;
		productDateAsLotNumber = false;
		
	}
	
	public Boolean getSupportGrade() {
		return supportGrade;
	}

	public void setSupportGrade(Boolean supportGrade) {
		this.supportGrade = supportGrade;
	}

	public Boolean getProductDateAsLotNumber() {
		return productDateAsLotNumber;
	}

	public void setProductDateAsLotNumber(Boolean productDateAsLotNumber) {
		this.productDateAsLotNumber = productDateAsLotNumber;
	}

	public Boolean getSupportNonStock() {
		return supportNonStock;
	}

	public void setSupportNonStock(Boolean supportNonStock) {
		this.supportNonStock = supportNonStock;
	}

	public Boolean getSupportStandard() {
		return supportStandard;
	}

	public void setSupportStandard(Boolean supportStandard) {
		this.supportStandard = supportStandard;
	}

	public Boolean getSupportMatrix() {
		return supportMatrix;
	}

	public void setSupportMatrix(Boolean supportMatrix) {
		this.supportMatrix = supportMatrix;
	}

	public Boolean getSupportSerialized() {
		return supportSerialized;
	}

	public void setSupportSerialized(Boolean supportSerialized) {
		this.supportSerialized = supportSerialized;
	}

	public Boolean getSupportKit() {
		return supportKit;
	}

	public void setSupportKit(Boolean supportKit) {
		this.supportKit = supportKit;
	}

	public Boolean getSupportAssemble() {
		return supportAssemble;
	}

	public void setSupportAssemble(Boolean supportAssemble) {
		this.supportAssemble = supportAssemble;
	}

	public Boolean getSupportSplit() {
		return supportSplit;
	}

	public void setSupportSplit(Boolean supportSplit) {
		this.supportSplit = supportSplit;
	}

	public Boolean getSupportCustomerKit() {
		return supportCustomerKit;
	}

	public void setSupportCustomerKit(Boolean supportCustomerKit) {
		this.supportCustomerKit = supportCustomerKit;
	}

	public Boolean getSupportElement() {
		return supportElement;
	}

	public void setSupportElement(Boolean supportElement) {
		this.supportElement = supportElement;
	}

	public Boolean getAutoCode() {
		return autoCode;
	}

	public void setAutoCode(Boolean autoCode) {
		this.autoCode = autoCode;
	}

	public Integer getCodeLengh() {
		return codeLengh;
	}

	public void setCodeLengh(Integer codeLengh) {
		this.codeLengh = codeLengh;
	}

	public String getCodePrix() {
		return codePrix;
	}

	public void setCodePrix(String codePrix) {
		this.codePrix = codePrix;
	}

	public String getCodePrixType() {
		return codePrixType;
	}

	public void setCodePrixType(String codePrixType) {
		this.codePrixType = codePrixType;
	}

	public Boolean getAutoBarCode() {
		return autoBarCode;
	}

	public void setAutoBarCode(Boolean autoBarCode) {
		this.autoBarCode = autoBarCode;
	}

	public Integer getBarCodeLength() {
		return barCodeLength;
	}

	public void setBarCodeLength(Integer barCodeLength) {
		this.barCodeLength = barCodeLength;
	}

	public String getBarCodePrix() {
		return barCodePrix;
	}

	public void setBarCodePrix(String barCodePrix) {
		this.barCodePrix = barCodePrix;
	}

	public String getBarCodePrixType() {
		return barCodePrixType;
	}

	public void setBarCodePrixType(String barCodePrixType) {
		this.barCodePrixType = barCodePrixType;
	}

	public String getItemPosBy() {
		return itemPosBy;
	}

	public void setItemPosBy(String itemPosBy) {
		this.itemPosBy = itemPosBy;
	}

	public Boolean getCenterControlPrice() {
		return centerControlPrice;
	}

	public void setCenterControlPrice(Boolean centerControlPrice) {
		this.centerControlPrice = centerControlPrice;
	}

	public Boolean getCenterControlMatrix() {
		return centerControlMatrix;
	}

	public void setCenterControlMatrix(Boolean centerControlMatrix) {
		this.centerControlMatrix = centerControlMatrix;
	}

	public Boolean getTransferInNeedOut() {
		return transferInNeedOut;
	}

	public void setTransferInNeedOut(Boolean transferInNeedOut) {
		this.transferInNeedOut = transferInNeedOut;
	}

	public Boolean getTransferOutRefInPrice() {
		return transferOutRefInPrice;
	}

	public void setTransferOutRefInPrice(Boolean transferOutRefInPrice) {
		this.transferOutRefInPrice = transferOutRefInPrice;
	}

	public Integer getPriceTagCopyAutoValue() {
		return priceTagCopyAutoValue;
	}

	public void setPriceTagCopyAutoValue(Integer priceTagCopyAutoValue) {
		this.priceTagCopyAutoValue = priceTagCopyAutoValue;
	}

	public Integer getPriceTagCopyDefaultValue() {
		return priceTagCopyDefaultValue;
	}

	public void setPriceTagCopyDefaultValue(Integer priceTagCopyDefaultValue) {
		this.priceTagCopyDefaultValue = priceTagCopyDefaultValue;
	}

	public Boolean getSharedCenterSupplier() {
		return sharedCenterSupplier;
	}

	public void setSharedCenterSupplier(Boolean sharedCenterSupplier) {
		this.sharedCenterSupplier = sharedCenterSupplier;
	}

	public Boolean getCanChangeSpiltDetail() {
		return canChangeSpiltDetail;
	}

	public void setCanChangeSpiltDetail(Boolean canChangeSpiltDetail) {
		this.canChangeSpiltDetail = canChangeSpiltDetail;
	}

	public String getPriceChargeType() {
		return priceChargeType;
	}

	public void setPriceChargeType(String priceChargeType) {
		this.priceChargeType = priceChargeType;
	}

	public Integer getItemNewBoundDay() {
		return itemNewBoundDay;
	}

	public void setItemNewBoundDay(Integer itemNewBoundDay) {
		this.itemNewBoundDay = itemNewBoundDay;
	}

	public String getAutoSupplierCode() {
		return autoSupplierCode;
	}

	public void setAutoSupplierCode(String autoSupplierCode) {
		this.autoSupplierCode = autoSupplierCode;
	}

	public Integer getSupplierCodeLength() {
		return supplierCodeLength;
	}

	public void setSupplierCodeLength(Integer supplierCodeLength) {
		this.supplierCodeLength = supplierCodeLength;
	}

	public String getSupplierCodePrix() {
		return supplierCodePrix;
	}

	public void setSupplierCodePrix(String supplierCodePrix) {
		this.supplierCodePrix = supplierCodePrix;
	}

	public String getSupplierCodePrixType() {
		return supplierCodePrixType;
	}

	public void setSupplierCodePrixType(String supplierCodePrixType) {
		this.supplierCodePrixType = supplierCodePrixType;
	}

	public Boolean getEnableAssistQty() {
		return enableAssistQty;
	}

	public void setEnableAssistQty(Boolean enableAssistQty) {
		this.enableAssistQty = enableAssistQty;
	}

	public BigDecimal getStockLimit() {
		return stockLimit;
	}

	public void setStockLimit(BigDecimal stockLimit) {
		this.stockLimit = stockLimit;
	}

	public Integer getUnPurchaseDay() {
		return unPurchaseDay;
	}

	public void setUnPurchaseDay(Integer unPurchaseDay) {
		this.unPurchaseDay = unPurchaseDay;
	}

	public Integer getUnPurchaseEffectiveDay() {
		return unPurchaseEffectiveDay;
	}

	public void setUnPurchaseEffectiveDay(Integer unPurchaseEffectiveDay) {
		this.unPurchaseEffectiveDay = unPurchaseEffectiveDay;
	}

	public String getItemCheckType() {
		return itemCheckType;
	}

	public void setItemCheckType(String itemCheckType) {
		this.itemCheckType = itemCheckType;
	}

	public Boolean getAutoCheckPosItem() {
		return autoCheckPosItem;
	}

	public void setAutoCheckPosItem(Boolean autoCheckPosItem) {
		this.autoCheckPosItem = autoCheckPosItem;
	}

	public String getSaleGrossCalcType() {
		return saleGrossCalcType;
	}

	public void setSaleGrossCalcType(String saleGrossCalcType) {
		this.saleGrossCalcType = saleGrossCalcType;
	}

	public static PosItemParam readFromXml(String text){
		try {
			Document document = DocumentHelper.parseText(text);
			PosItemParam posItemParam = new PosItemParam();
			Element root = document.getRootElement();
			
			Element element = (Element) root.selectSingleNode("SupportNonStock");
			if(element != null ){
				posItemParam.setSupportNonStock(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("SupportStandard");
			if(element != null){
				posItemParam.setSupportStandard(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("SupportMatirx");
			if(element != null){
				posItemParam.setSupportMatrix(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("SupportSerialized");
			if(element != null){
				posItemParam.setSupportSerialized(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("Supportkit");
			if(element != null){
				posItemParam.setSupportKit(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("SupportAssemble");
			if(element != null){
				posItemParam.setSupportAssemble(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("SupportSplit");
			if(element != null){
				posItemParam.setSupportSplit(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("SupportCustomerKit");
			if(element != null){
				posItemParam.setSupportCustomerKit(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("SupportElement");
			if(element != null){
				posItemParam.setSupportElement(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("AutoCode");
			if(element != null){
				posItemParam.setAutoCode(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("CodeLenth");
			if(element != null){
				posItemParam.setCodeLengh(Integer.parseInt(element.getText()));
			}
			
			element = (Element) root.selectSingleNode("CodePrix");
			if(element != null){
				posItemParam.setCodePrix(element.getText());
			}
			
			element = (Element) root.selectSingleNode("CodePrixType");
			if(element != null){
				posItemParam.setCodePrixType(element.getText());
			}	
			
			element = (Element) root.selectSingleNode("AutoBarCode");
			if(element != null){
				posItemParam.setAutoBarCode(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}	
			
			element = (Element) root.selectSingleNode("BarCodeLenth");
			if(element != null){
				posItemParam.setBarCodeLength(Integer.parseInt(element.getText()));
			}
			
			element = (Element) root.selectSingleNode("BarCodePrix");
			if(element != null){
				posItemParam.setBarCodePrix(element.getText());
			}	
			
			element = (Element) root.selectSingleNode("BarCodePrixType");
			if(element != null){
				posItemParam.setBarCodePrixType(element.getText());
			}	
			
			element = (Element) root.selectSingleNode("ItemPosBy");
			if(element != null){
				posItemParam.setItemPosBy(element.getText());
			}	
			
			element = (Element) root.selectSingleNode("CenterControlPrice");
			if(element != null){
				posItemParam.setCenterControlPrice(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}	
			
			element = (Element) root.selectSingleNode("CenterControlMatrix");
			if(element != null){
				posItemParam.setCenterControlMatrix(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("TransferInNeedOut");
			if(element != null){
				posItemParam.setTransferInNeedOut(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("TransferOutRefInPrice");
			if(element != null){
				posItemParam.setTransferOutRefInPrice(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("PriceTagCopyAutoValue");
			if(element != null){
				posItemParam.setPriceTagCopyAutoValue(Integer.parseInt(element.getText()));
			}
			
			element = (Element) root.selectSingleNode("PriceTagCopyDefaultValue");
			if(element != null){
				posItemParam.setPriceTagCopyDefaultValue(Integer.parseInt(element.getText()));
			}
			
			element = (Element) root.selectSingleNode("SharedCenterSupplier");
			if(element != null){
				posItemParam.setSharedCenterSupplier(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("CanChangeSpiltDetail");
			if(element != null){
				posItemParam.setCanChangeSpiltDetail(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("PriceChargeType");
			if(element != null){
				posItemParam.setPriceChargeType(element.getText());
			}
			
			element = (Element) root.selectSingleNode("ItemNewBoundDay");
			if(element != null){
				posItemParam.setItemNewBoundDay(Integer.parseInt(element.getText()));
			}
			
			element = (Element) root.selectSingleNode("AutoSupplierCode");
			if(element != null){
				posItemParam.setAutoSupplierCode(element.getText());
			}
			
			element = (Element) root.selectSingleNode("SupplierCodeLenth");
			if(element != null){
				posItemParam.setSupplierCodeLength(Integer.parseInt(element.getText()));
			}
			
			element = (Element) root.selectSingleNode("SupplierCodePrix");
			if(element != null){
				posItemParam.setSupplierCodePrix(element.getText());
			}
			
			element = (Element) root.selectSingleNode("SupplierCodePrixType");
			if(element != null){
				posItemParam.setSupplierCodePrixType(element.getText());
			}
			
			element = (Element) root.selectSingleNode("EnableAssistQty");
			if(element != null){
				posItemParam.setEnableAssistQty(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("StockLimit");
			if(element!=null){
				posItemParam.setStockLimit(new BigDecimal(element.getText()));
			}
			
			element = (Element) root.selectSingleNode("UnPurchaseDay");
			if(element!=null){
				posItemParam.setUnPurchaseDay(Integer.parseInt(element.getText()));
			}
			
			element = (Element) root.selectSingleNode("UnPurchaseEffectiveDay");
			if(element!=null){
				posItemParam.setUnPurchaseEffectiveDay(Integer.parseInt(element.getText()));
			}
			
			element = (Element) root.selectSingleNode("ItemCheckType");
			if(element!=null){
				posItemParam.setItemCheckType(element.getText());
			}
			
			element = (Element) root.selectSingleNode("AutoCheckPosItem");
			if(element!=null){
				posItemParam.setAutoCheckPosItem(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element) root.selectSingleNode("ProductDateAsLotNumber");
			if(element!=null){
				posItemParam.setProductDateAsLotNumber(AppUtil.strToBool(element.getText()));
			}	
			element = (Element) root.selectSingleNode("ProductDateAsLotNumber");
			if(element!=null){
				posItemParam.setProductDateAsLotNumber(AppUtil.strToBool(element.getText()));
			}	
			element = (Element) root.selectSingleNode("SupportGrade");
			if(element!=null){
				posItemParam.setSupportGrade(AppUtil.strToBool(element.getText()));
			} else {
				posItemParam.setSupportGrade(false);
			}
			element = (Element) root.selectSingleNode("SaleGrossCalcType");
			if(element!=null){
				posItemParam.setSaleGrossCalcType(element.getText());
			}
			
			
			return posItemParam;
		} catch (DocumentException e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	public static String writeToXml(PosItemParam posItemParam){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("PosItemParam");
		if(posItemParam.getSupportNonStock() != null){
			root.addElement("SupportNonStock").setText(BooleanUtils.toString(posItemParam.getSupportNonStock(), "1", "0"));
		}
		
		if(posItemParam.getSupportStandard() != null){
			root.addElement("SupportStandard").setText(BooleanUtils.toString(posItemParam.getSupportStandard(), "1", "0"));

		}
		
		if(posItemParam.getSupportMatrix() != null){
			root.addElement("SupportMatirx").setText(BooleanUtils.toString(posItemParam.getSupportMatrix(), "1", "0"));

		}
		
		if(posItemParam.getSupportSerialized() != null){
			root.addElement("SupportSerialized").setText(BooleanUtils.toString(posItemParam.getSupportSerialized(), "1", "0"));

		}
		
		if(posItemParam.getSupportKit() != null){
			root.addElement("Supportkit").setText(BooleanUtils.toString(posItemParam.getSupportKit(), "1", "0"));

		}
		
		if(posItemParam.getSupportAssemble() != null){
			root.addElement("SupportAssemble").setText(BooleanUtils.toString(posItemParam.getSupportAssemble(), "1", "0"));

		}
		
		if(posItemParam.getSupportSplit() != null){
			root.addElement("SupportSplit").setText(BooleanUtils.toString(posItemParam.getSupportSplit(), "1", "0"));

		}
		
		if(posItemParam.getSupportCustomerKit() != null){
			root.addElement("SupportCustomerKit").setText(BooleanUtils.toString(posItemParam.getSupportCustomerKit(), "1", "0"));

		}
		
		if(posItemParam.getSupportElement() != null){
			root.addElement("SupportElement").setText(BooleanUtils.toString(posItemParam.getSupportElement(), "1", "0"));

		}
		
		if(posItemParam.getAutoCode() != null){
			root.addElement("AutoCode").setText(BooleanUtils.toString(posItemParam.getAutoCode(), "1", "0"));

		}
		
		if(posItemParam.getCodeLengh() != null){
			root.addElement("CodeLenth").setText(posItemParam.getCodeLengh().toString());

		} 
		
		if(posItemParam.getCodePrix() != null){
			root.addElement("CodePrix").setText(posItemParam.getCodePrix());

		}
		
		if(posItemParam.getCodePrixType() != null){
			root.addElement("CodePrixType").setText(posItemParam.getCodePrixType());

		}
		
		if(posItemParam.getAutoBarCode() != null){
			root.addElement("AutoBarCode").setText(BooleanUtils.toString(posItemParam.getAutoBarCode(), "1", "0"));

		}
		
		if(posItemParam.getBarCodeLength() != null){
			root.addElement("BarCodeLenth").setText(posItemParam.getBarCodeLength().toString());

		}
		
		if(posItemParam.getBarCodePrix() != null){
			root.addElement("BarCodePrix").setText(posItemParam.getBarCodePrix());

		}
		
		if(posItemParam.getBarCodePrixType() != null){
			root.addElement("BarCodePrixType").setText(posItemParam.getBarCodePrixType());

		}
		
		if(posItemParam.getItemPosBy() != null){
			root.addElement("ItemPosBy").setText(posItemParam.getItemPosBy());

		}
		
		if(posItemParam.getCenterControlPrice() != null){
			root.addElement("CenterControlPrice").setText(BooleanUtils.toString(posItemParam.getCenterControlPrice(), "1", "0"));

		}
		
		if(posItemParam.getCenterControlMatrix() != null){
			root.addElement("CenterControlMatrix").setText(BooleanUtils.toString(posItemParam.getCenterControlMatrix(), "1", "0"));

		}
		
		if(posItemParam.getTransferInNeedOut() != null){
			root.addElement("TransferInNeedOut").setText(BooleanUtils.toString(posItemParam.getTransferInNeedOut(), "1", "0"));

		}
		
		if(posItemParam.getTransferOutRefInPrice() != null){
			root.addElement("TransferOutRefInPrice").setText(BooleanUtils.toString(posItemParam.getTransferOutRefInPrice(), "1", "0"));

		}
		if(posItemParam.getPriceTagCopyAutoValue() != null){
			root.addElement("PriceTagCopyAutoValue").setText(posItemParam.getPriceTagCopyAutoValue().toString());

		}
		
		if(posItemParam.getPriceTagCopyDefaultValue() != null){
			root.addElement("PriceTagCopyDefaultValue").setText(posItemParam.getPriceTagCopyDefaultValue().toString());

		}
		
		if(posItemParam.getSharedCenterSupplier() != null){
			root.addElement("SharedCenterSupplier").setText(BooleanUtils.toString(posItemParam.getSharedCenterSupplier(), "1", "0"));

		}
		
		if(posItemParam.getCanChangeSpiltDetail() != null){
			root.addElement("CanChangeSpiltDetail").setText(BooleanUtils.toString(posItemParam.getCanChangeSpiltDetail(), "1", "0"));

		}	
		
		if(posItemParam.getPriceChargeType() != null){
			root.addElement("PriceChargeType").setText(posItemParam.getPriceChargeType());

		}
		
		if(posItemParam.getItemNewBoundDay() != null){
			root.addElement("ItemNewBoundDay").setText(posItemParam.getItemNewBoundDay().toString());

		}
		
		if(posItemParam.getAutoSupplierCode() != null){
			root.addElement("AutoSupplierCode").setText(posItemParam.getAutoSupplierCode());

		}
		
		if(posItemParam.getSupplierCodeLength() != null){
			root.addElement("SupplierCodeLenth").setText(posItemParam.getSupplierCodeLength().toString());

		}
		
		if(posItemParam.getSupplierCodePrix() != null){
			root.addElement("SupplierCodePrix").setText(posItemParam.getSupplierCodePrix());

		}
		
		if(posItemParam.getSupplierCodePrixType() != null){
			root.addElement("SupplierCodePrixType").setText(posItemParam.getSupplierCodePrixType());

		}
		if(posItemParam.getEnableAssistQty() != null){
			root.addElement("EnableAssistQty").setText(BooleanUtils.toString(posItemParam.getEnableAssistQty(), "1", "0"));

		}
		
		if(posItemParam.getStockLimit()!=null){
			root.addElement("StockLimit").setText(posItemParam.getStockLimit().toPlainString());
		}
		
		if(posItemParam.getUnPurchaseDay()!=null){
			root.addElement("UnPurchaseDay").setText(posItemParam.getUnPurchaseDay().toString());
		}
		
		if(posItemParam.getUnPurchaseEffectiveDay()!=null){
			root.addElement("UnPurchaseEffectiveDay").setText(posItemParam.getUnPurchaseEffectiveDay().toString());
		}
		
		if(posItemParam.getItemCheckType()!=null){
			root.addElement("ItemCheckType").setText(posItemParam.getItemCheckType().toString());
		}
		
		if(posItemParam.getAutoCheckPosItem()!=null){
			root.addElement("AutoCheckPosItem").setText(BooleanUtils.toString(posItemParam.getAutoCheckPosItem(), "1", "0"));
		}
		
		if(posItemParam.getProductDateAsLotNumber()!=null){
			root.addElement("ProductDateAsLotNumber").setText(BooleanUtils.toString(posItemParam.getProductDateAsLotNumber(), "1", "0"));
		}
		if(posItemParam.getSaleGrossCalcType() != null){
			root.addElement("SaleGrossCalcType").setText(posItemParam.getSaleGrossCalcType());
		}
		
		root.addElement("SupportGrade").setText(BooleanUtils.toString(posItemParam.getSupportGrade(), "1", "0", "0"));
		return document.asXML();
	}
}
