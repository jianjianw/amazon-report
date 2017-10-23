package com.nhsoft.module.report.param;

import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;

public class ChainDeliveryParam implements Serializable {
	
	private static final Logger logger = LoggerFactory.getLogger(ChainDeliveryParam.class);
	private static final long serialVersionUID = 7232030330079871583L;
	private Boolean transferInNeedOut;    //调入单必须通过调出单生成
	private Boolean transferOutRefInPrice; //调出单若根据调入单生成时，调出价参考调入价
	private Boolean centerAuditOutOrder;  //调出单统一由中心审核
	private Boolean centerTransferInRefCost;//调入单中心调入采用中心成本价入库
	private Boolean unEditTransferOutPrice;  //调出单不允许修改调出价
	private Boolean transferOutUseOnce;		// 没有用到
	private Boolean centerInvenoryRemoveRequest; //要货单中心库存去除已要货未配送的数量
	private Integer requestOrderValidDays;   //要货单有效天数
	private Integer purchaseOrderVaildDays;  //采购有效天数
	private Boolean centerpurchaseNotReceiveMore;	//直配订单不允许超量收货
	private Boolean requestOrderNotAllowAdd;   //要货单不允许手工添加商品
	private Boolean requestOrderNotAllowEditUnit; //要货单不允许修改计量单位
	//	private Boolean requestOrderNotEditUnit; //要货单不允许修改计量单位
	private Integer requestOrderAmountLower;  //下限  要货有效范围
	private Integer requestOrderAmountUpper;  //上限
	private Boolean requestOrderIntegerOnly;  //仅整数有效
	private Boolean outOrderPrintNeedAudit; //调出单未审核不允许打印
	private Integer outOrderSplitAmount;  // 未知
	private Boolean autoChangeItemCostPrice;  //采购收货自动维护进货价
	private Boolean wholesaleOrderPrintNeedAudit;  //批发销售单不允许打印
	private Boolean wholesaleStockoutOrder; //缺货商品允许订购
	private Integer calDayParamOne = 7;
	private Integer calDayParamTwo = 28;
	private Integer outAmountType = 2; //0, 1, 2
	private Boolean newWholeOrderInform;  //新订单
	private Boolean sendWholeOrderInform;  //发货
	private Boolean paymnetWholeOrderInform;  //收款
	private Boolean affirmWholeMoneyInform;  //确认到账
	private Boolean pickWholeOrderInform;  //到货
	private Boolean orderWholeAuditInform; //订单审核
	private String smsNewWholeContext;
	private String smsSendWholeContext;
	private String smsPaymentWholeContext;
	private String smsAffirmWholeContext;
	private String smsPickWholeContext;
	private String smsAuditWholeContext;//格式
	
	private Boolean newChainOrderInform;  //新订单
	private Boolean paymnetChainOrderInform;  //收款
	private Boolean affirmChainMoneyInform;  //确认到账
	private Boolean supportSmsNotice; //支持到货通知短信提醒参数
	private Boolean branchReceiveOrderInform; //门店收货通知
	private Boolean orderChainAuditInform; //订单审核
	private String smsNewChainContext;
	private String smsPaymentChainContext;
	private String smsAffirmChainContext;
	private String smsPickChainContext;
	private String smsBranchChainContext;
	private String smsAuditChainContext;
	private Boolean smsShipOrder;   //发货单审核后短信提醒收货方
	private String smsShipOrderContext;  //提醒格式
	private String financePhone;
	private String storehousePhone;
	private Boolean centerInvenoryRemoveWholeBook; //批发订单中心库存去除已要货未销售的数量
	private Boolean purChaseAuditInform; //采购订单审核通知供应商
	private Boolean preSettlementInform; //供应商预付单审核通知供应商
	private String smsPurChaseAuditContext; //格式
	private String smsPreSettlementContext;//格式
	private Boolean printTogetherByChain;//调出单相同商品合并打印
	private Boolean printTogetherByWholeBook;//批发销售单相同商品合并打印
	/** 批发 */
	private Boolean centerInvenoryIgnore; ;//订购时忽略中心库存量
	private Boolean requestShowProductingDate;
	private Boolean createWholesaleOrderByAudit;////批发订单审核时直接生成批发销售单
	private Boolean wholesaleLoginSms;//在线订货登陆需短信验证
	private Integer wholesaleBookValidDays;//批发订单有效天数
	
	/** 门店要货   */
	private Boolean requestCenterInvenoryIgnore;//要货订购时忽略中心库存量
	private Boolean requestStockoutOrder; //要货缺货商品允许在线订购
	
	private Boolean useLastWholePrice;//批发价取客户最后一次批发价
	private Boolean showClientBalance;//显示批发客户账户余额;
	private Boolean notReturnWholesaleAudit;//单据审核后停留明细界面
	private Boolean wholesaleInventoryUnit;//总部批发默认按库存单位出库
	private Boolean orderNotAuditFirst;//第一次保存时不提示是否审核
	private Boolean receiveOrderPrintNeedAudit;//收货单未审核不允许打印
	private Boolean wholesalePriceNotBelowThenMin;//批发价低于最低售价不能审核
	private Boolean wholesaleSalePriceNotShow;//经销商订货中不显示建议零售价
	private Integer singleTransferSendBranchCount;//单张配货装车单门店数量
	private Boolean requestIngoreLastRequest; //门店相同商品要货自动忽略上一次要货量
	private Boolean wholesaleMatrixAddOns;//启用多特性附加费
	private Boolean inOrderFromOutNotAllowEdit;//调入单通过调出单生成不允许修改
	private Boolean wholesaleReturnCreateByOrder;//退货单必须通过批发销售单生成
	private Boolean centerOutOrderGenerateBranchInOrder;//中心调出单直接生成门店调入单
	private Integer centerOutOrderGenerateBranchInOrderState = 2;//中心调出单直接生成门店调入单状态 1 制单 2 审核
	
	private Boolean purchaseUseBoxDeposit;
	private Boolean enableInventoryLnDetailRate;//手工指定商品启用库存批次转换率
	private Boolean enableLockInventory; //是否锁定库存
	private Integer transferLockDay;//锁定调出单天数
	private Integer wholesaleLockDay; //锁定批发销售单天数
	//界面不维护
	private Boolean requestAutoGenerOutOrder;
	private Boolean requestNotShowCenterInvetory;
	private Integer calDayParamThree;
	private Boolean notAllowChangeUnit; //手工指定批次商品不允许修改单位
	private Boolean selectOrderPosItemShowItemMatrix;//业务单据选择商品时显示多特性明细
	private Boolean notReturnChainAudit;//单据审核后停留明细界面
	
	private Integer calDayParamOneWholesale; //统计天数参数1(批发)
	private Integer calDayParamTwoWholesale; //统计天数参数2(批发)
	private Integer outAmountTypeWholesale; //0, 1, 2(批发)
	private Integer wholesaleOrderAmountLower; //下限 要货有效范围(批发)
	private Integer wholesaleOrderAmountUpper; //上限(批发)
	private Boolean showAllPosItemWholesale;//缺货和非缺货商品一起显示
	private Boolean transferPriceAsProfitRate = false;//配送单价按锁定配送毛利率
	private Boolean transferInOrderEditBaseQty = false;//调入单允许修改基本数量
	private Boolean transferPriceRounding;//配送单位价格取整
	private BigDecimal transferPriceRoundFrom;//起始金额
	private String transferPriceRoundType;//舍零方式
	private String transferPriceRoundTo;//精确到
	private String wholesaleBookDefaultState;//经销商订货系统默认生成批发订单状态
	private Boolean requestNotOverCenterStock;
	private Boolean padReceiveCanEditBasicQty;//PAD收货可以修改基本数量
	private Boolean outOrderQtySubTareQty;//配送中心调出数量扣除皮重
	private Boolean requestShowCenterInventoryName; //要货单中心库存量显示为有货/无货
	private Boolean enableTransferInSubTareQty;//启用皮重扣除功能
	private Boolean tranferInNeedOutSended;//门店只能调入中心已发车的调出单
	private Boolean enableTransferPolicyQuantity;//启用配送促销政策
	private Boolean enableAssistUnitAsUseUnit;//启用辅助单位替代常用单位
	private Boolean canNotEditAntiOrder;//冲红复制单据不允许修改
	
	private Boolean wholesalePriceRounding;//批发单位价格取整
	private BigDecimal wholesalePriceRoundFrom;//起始金额
	private String wholesalePriceRoundType;//舍零方式
	private String wholesalePriceRoundTo;//精确到
	private Boolean enableRequestMoney;//启用要货金额控制
	private BigDecimal requestAndSaleRate;//要货金额与销售额 比例值
	private Boolean enableLowReceivePrice;//只允许改低收货价
	
	private Boolean enableRequestRefItem; //是否启用关联商品要货
	private Boolean wholesalePriceAsProfitRate;//批发单价按锁定批发毛利率
	private Boolean forbidWholesaleAuditOverClientBalance; //超过授信余额不能审核
	private Boolean centerSendConfirmOnly = false;//仓管中心仅发车已确认商品
	
	public ChainDeliveryParam(){
		transferInNeedOut = false;
		transferOutRefInPrice = false;
		centerAuditOutOrder = false;
		centerTransferInRefCost = false;
		unEditTransferOutPrice = false;
		transferOutUseOnce = false;
		centerInvenoryRemoveRequest = false;
		requestOrderValidDays = 30;
		purchaseOrderVaildDays = 0;
		outOrderSplitAmount = 50;
		requestOrderAmountLower = 0;
		requestOrderAmountUpper = 0;
		centerpurchaseNotReceiveMore = false;
		requestOrderNotAllowAdd = false;
		requestOrderNotAllowEditUnit = false;
		smsShipOrder =false;
		outOrderPrintNeedAudit =false;
		wholesaleOrderPrintNeedAudit =false;
		supportSmsNotice = false;
		autoChangeItemCostPrice = false;
		newWholeOrderInform = false;  //新订单
		sendWholeOrderInform = false;  //发货
		paymnetWholeOrderInform = false;  //收款
		affirmWholeMoneyInform = false;  //确认到账
		pickWholeOrderInform = false;  //到货
		orderWholeAuditInform = false; //订单审核
		newChainOrderInform = false;  //新订单
		paymnetChainOrderInform = false;  //收款
		affirmChainMoneyInform = false;  //确认到账
		branchReceiveOrderInform = false; //门店收货通知
		orderChainAuditInform = false; //订单审核
		centerInvenoryRemoveWholeBook = false;
		useLastWholePrice = false;
		smsNewWholeContext = "客户[posClient]于[wholesaleBookDate]提交了一张金额为[wholesaleBookTotalMoney],单据号为[wholesaleBookFid]的订单，请管理员确认订单！";
		smsSendWholeContext = "您的订单已于[ShipOrderDate]通过货运公司([ShipOrderDeliver])运单号([ShipOrderBill])发出，请注意查收！";
		smsPaymentWholeContext = "客户[posClient]在[preSettlementDate]现金银行[accountBank]付款金额[preSettlementPaid]的预付款订单，请确认和查收！;";
		smsAffirmWholeContext = "财务人员在[preSettlementAuditTime]确认了您的付款金额为[preSettlementPaid]单据号[preSettlementNo]的订单！";
		smsPickWholeContext = "您订购的 [posItem]已到货,您可以订购商品！";
		smsAuditWholeContext = "您的订单[wholesaleBookFid]已通过审核，请关注订单的配送情况！";
		
		smsNewChainContext = "门店[branch]于[requestOrderApplyTime]提交了一张金额为[requestOrderTotalMoney],单据号为[requestOrderFid]订单，请管理员确认订单！";
		smsShipOrderContext = "您的订单已于[ShipOrderDate]通过货运公司([ShipOrderDeliver])运单号([ShipOrderBill])发出，请注意查收！";
		smsPaymentChainContext = "门店[branch]在[preSettlementDate]付款金额[preSettlementPaid]现金银行[accountBank]单据号[preSettlementNo]的预付款订单，请确认和查收！";
		smsAffirmChainContext = "财务人员在[preSettlementAuditTime]确认了客户您的付款金额为[preSettlementPaid]单据号为[preSettlementNo]的订单！";
		smsPickChainContext = "您订购的 [posItem]已到货,您可以订购商品！";
		smsBranchChainContext = "门店[branch]已收到订单[outOrderFid]";
		smsAuditChainContext = "您的订单[requestOrderFid]已通过审核，请关注订单的配送情况！";
		smsPurChaseAuditContext = "总部[systemBookName]于[purchaseOrderDate]提交了一张金额为[purchaseOrderTotalMoney],单据号为[purchaseOrderFid]的订单，请及时处理！";
		smsPreSettlementContext = "总部[systemBookName]在[preSettlementDate]付款金额为[preSettlementPaid]的预付款，请确认和查收！";
		
		purChaseAuditInform = false;
		preSettlementInform = false;
		centerInvenoryIgnore = false;
		requestCenterInvenoryIgnore = false;
		requestStockoutOrder = false;
		printTogetherByChain = false;
		printTogetherByWholeBook = false;
		createWholesaleOrderByAudit = false;
		showClientBalance = false;
		wholesaleLoginSms = false;
		notReturnWholesaleAudit = false;
		wholesaleInventoryUnit = false;
		orderNotAuditFirst = false;
		receiveOrderPrintNeedAudit = false;
		wholesalePriceNotBelowThenMin = false;
		wholesaleSalePriceNotShow = false;
		singleTransferSendBranchCount = 6;
		requestIngoreLastRequest = false;
		wholesaleMatrixAddOns = false;
		wholesaleBookValidDays = 0;
		enableLockInventory = false;
		inOrderFromOutNotAllowEdit = false;
		wholesaleReturnCreateByOrder = false;
		notAllowChangeUnit = false;
		centerOutOrderGenerateBranchInOrder = false;
		purchaseUseBoxDeposit = false;
		selectOrderPosItemShowItemMatrix = false;
		enableInventoryLnDetailRate = false;
		notReturnChainAudit = false;
		transferPriceRounding = false;
		requestNotOverCenterStock = false;
		outOrderQtySubTareQty = false;
		requestShowCenterInventoryName = false;
		enableTransferInSubTareQty = false;
		tranferInNeedOutSended = false;
		enableTransferPolicyQuantity = false;
		enableAssistUnitAsUseUnit = false;
		canNotEditAntiOrder = false;
		requestNotShowCenterInvetory = true;
		enableRequestRefItem = false;
		wholesalePriceAsProfitRate = false;
		forbidWholesaleAuditOverClientBalance = false;
	}
	
	public Boolean getCenterSendConfirmOnly() {
		return centerSendConfirmOnly;
	}
	
	public void setCenterSendConfirmOnly(Boolean centerSendConfirmOnly) {
		this.centerSendConfirmOnly = centerSendConfirmOnly;
	}
	
	public Integer getCenterOutOrderGenerateBranchInOrderState() {
		return centerOutOrderGenerateBranchInOrderState;
	}
	
	public void setCenterOutOrderGenerateBranchInOrderState(Integer centerOutOrderGenerateBranchInOrderState) {
		this.centerOutOrderGenerateBranchInOrderState = centerOutOrderGenerateBranchInOrderState;
	}
	
	public Boolean getWholesalePriceAsProfitRate() {
		return wholesalePriceAsProfitRate;
	}
	
	public void setWholesalePriceAsProfitRate(Boolean wholesalePriceAsProfitRate) {
		this.wholesalePriceAsProfitRate = wholesalePriceAsProfitRate;
	}
	
	public Boolean getEnableRequestRefItem() {
		return enableRequestRefItem;
	}
	
	public void setEnableRequestRefItem(Boolean enableRequestRefItem) {
		this.enableRequestRefItem = enableRequestRefItem;
	}
	
	public Boolean getEnableLowReceivePrice() {
		return enableLowReceivePrice;
	}
	
	public void setEnableLowReceivePrice(Boolean enableLowReceivePrice) {
		this.enableLowReceivePrice = enableLowReceivePrice;
	}
	
	public Boolean getEnableRequestMoney() {
		return enableRequestMoney;
	}
	
	public void setEnableRequestMoney(Boolean enableRequestMoney) {
		this.enableRequestMoney = enableRequestMoney;
	}
	
	public BigDecimal getRequestAndSaleRate() {
		return requestAndSaleRate;
	}
	
	public void setRequestAndSaleRate(BigDecimal requestAndSaleRate) {
		this.requestAndSaleRate = requestAndSaleRate;
	}
	
	public Boolean getWholesalePriceRounding() {
		return wholesalePriceRounding;
	}
	
	
	public void setWholesalePriceRounding(Boolean wholesalePriceRounding) {
		this.wholesalePriceRounding = wholesalePriceRounding;
	}
	
	
	public BigDecimal getWholesalePriceRoundFrom() {
		return wholesalePriceRoundFrom;
	}
	
	
	public void setWholesalePriceRoundFrom(BigDecimal wholesalePriceRoundFrom) {
		this.wholesalePriceRoundFrom = wholesalePriceRoundFrom;
	}
	
	
	public String getWholesalePriceRoundType() {
		return wholesalePriceRoundType;
	}
	
	
	public void setWholesalePriceRoundType(String wholesalePriceRoundType) {
		this.wholesalePriceRoundType = wholesalePriceRoundType;
	}
	
	
	public String getWholesalePriceRoundTo() {
		return wholesalePriceRoundTo;
	}
	
	
	public void setWholesalePriceRoundTo(String wholesalePriceRoundTo) {
		this.wholesalePriceRoundTo = wholesalePriceRoundTo;
	}
	
	
	public Boolean getCanNotEditAntiOrder() {
		return canNotEditAntiOrder;
	}
	
	
	public void setCanNotEditAntiOrder(Boolean canNotEditAntiOrder) {
		this.canNotEditAntiOrder = canNotEditAntiOrder;
	}
	
	
	public Boolean getEnableAssistUnitAsUseUnit() {
		return enableAssistUnitAsUseUnit;
	}
	
	public void setEnableAssistUnitAsUseUnit(Boolean enableAssistUnitAsUseUnit) {
		this.enableAssistUnitAsUseUnit = enableAssistUnitAsUseUnit;
	}
	
	public Boolean getEnableTransferPolicyQuantity() {
		return enableTransferPolicyQuantity;
	}
	
	public void setEnableTransferPolicyQuantity(Boolean enableTransferPolicyQuantity) {
		this.enableTransferPolicyQuantity = enableTransferPolicyQuantity;
	}
	
	public Boolean getTranferInNeedOutSended() {
		return tranferInNeedOutSended;
	}
	
	public void setTranferInNeedOutSended(Boolean tranferInNeedOutSended) {
		this.tranferInNeedOutSended = tranferInNeedOutSended;
	}
	
	public Boolean getEnableTransferInSubTareQty() {
		return enableTransferInSubTareQty;
	}
	
	public void setEnableTransferInSubTareQty(Boolean enableTransferInSubTareQty) {
		this.enableTransferInSubTareQty = enableTransferInSubTareQty;
	}
	
	public Boolean getRequestShowCenterInventoryName() {
		return requestShowCenterInventoryName;
	}
	
	public void setRequestShowCenterInventoryName(Boolean requestShowCenterInventoryName) {
		this.requestShowCenterInventoryName = requestShowCenterInventoryName;
	}
	
	public Boolean getOutOrderQtySubTareQty() {
		return outOrderQtySubTareQty;
	}
	
	public void setOutOrderQtySubTareQty(Boolean outOrderQtySubTareQty) {
		this.outOrderQtySubTareQty = outOrderQtySubTareQty;
	}
	
	public Boolean getPadReceiveCanEditBasicQty() {
		return padReceiveCanEditBasicQty;
	}
	
	public void setPadReceiveCanEditBasicQty(Boolean padReceiveCanEditBasicQty) {
		this.padReceiveCanEditBasicQty = padReceiveCanEditBasicQty;
	}
	
	public Boolean getRequestNotOverCenterStock() {
		return requestNotOverCenterStock;
	}
	
	public void setRequestNotOverCenterStock(Boolean requestNotOverCenterStock) {
		this.requestNotOverCenterStock = requestNotOverCenterStock;
	}
	
	public String getWholesaleBookDefaultState() {
		return wholesaleBookDefaultState;
	}
	
	public void setWholesaleBookDefaultState(String wholesaleBookDefaultState) {
		this.wholesaleBookDefaultState = wholesaleBookDefaultState;
	}
	
	public Boolean getTransferPriceRounding() {
		return transferPriceRounding;
	}
	
	public void setTransferPriceRounding(Boolean transferPriceRounding) {
		this.transferPriceRounding = transferPriceRounding;
	}
	
	public BigDecimal getTransferPriceRoundFrom() {
		return transferPriceRoundFrom;
	}
	
	public void setTransferPriceRoundFrom(BigDecimal transferPriceRoundFrom) {
		this.transferPriceRoundFrom = transferPriceRoundFrom;
	}
	
	public String getTransferPriceRoundType() {
		return transferPriceRoundType;
	}
	
	public void setTransferPriceRoundType(String transferPriceRoundType) {
		this.transferPriceRoundType = transferPriceRoundType;
	}
	
	public String getTransferPriceRoundTo() {
		return transferPriceRoundTo;
	}
	
	public void setTransferPriceRoundTo(String transferPriceRoundTo) {
		this.transferPriceRoundTo = transferPriceRoundTo;
	}
	
	public Boolean getTransferInOrderEditBaseQty() {
		return transferInOrderEditBaseQty;
	}
	
	public void setTransferInOrderEditBaseQty(Boolean transferInOrderEditBaseQty) {
		this.transferInOrderEditBaseQty = transferInOrderEditBaseQty;
	}
	
	public Boolean getTransferPriceAsProfitRate() {
		return transferPriceAsProfitRate;
	}
	
	public void setTransferPriceAsProfitRate(Boolean transferPriceAsProfitRate) {
		this.transferPriceAsProfitRate = transferPriceAsProfitRate;
	}
	
	public Integer getWholesaleOrderAmountUpper() {
		return wholesaleOrderAmountUpper;
	}
	
	public void setWholesaleOrderAmountUpper(Integer wholesaleOrderAmountUpper) {
		this.wholesaleOrderAmountUpper = wholesaleOrderAmountUpper;
	}
	
	public Integer getCalDayParamOneWholesale() {
		return calDayParamOneWholesale;
	}
	
	public void setCalDayParamOneWholesale(Integer calDayParamOneWholesale) {
		this.calDayParamOneWholesale = calDayParamOneWholesale;
	}
	
	public Integer getCalDayParamTwoWholesale() {
		return calDayParamTwoWholesale;
	}
	
	public void setCalDayParamTwoWholesale(Integer calDayParamTwoWholesale) {
		this.calDayParamTwoWholesale = calDayParamTwoWholesale;
	}
	
	public Integer getOutAmountTypeWholesale() {
		return outAmountTypeWholesale;
	}
	
	public void setOutAmountTypeWholesale(Integer outAmountTypeWholesale) {
		this.outAmountTypeWholesale = outAmountTypeWholesale;
	}
	
	public Integer getWholesaleOrderAmountLower() {
		return wholesaleOrderAmountLower;
	}
	
	public void setWholesaleOrderAmountLower(Integer wholesaleOrderAmountLower) {
		this.wholesaleOrderAmountLower = wholesaleOrderAmountLower;
	}
	
	public Boolean getShowAllPosItemWholesale() {
		return showAllPosItemWholesale;
	}
	
	public void setShowAllPosItemWholesale(Boolean showAllPosItemWholesale) {
		this.showAllPosItemWholesale = showAllPosItemWholesale;
	}
	
	public Boolean getNotReturnChainAudit() {
		return notReturnChainAudit;
	}
	
	public void setNotReturnChainAudit(Boolean notReturnChainAudit) {
		this.notReturnChainAudit = notReturnChainAudit;
	}
	
	public Boolean getEnableInventoryLnDetailRate() {
		return enableInventoryLnDetailRate;
	}
	
	public void setEnableInventoryLnDetailRate(Boolean enableInventoryLnDetailRate) {
		this.enableInventoryLnDetailRate = enableInventoryLnDetailRate;
	}
	
	public Boolean getSelectOrderPosItemShowItemMatrix() {
		return selectOrderPosItemShowItemMatrix;
	}
	
	public void setSelectOrderPosItemShowItemMatrix(Boolean selectOrderPosItemShowItemMatrix) {
		this.selectOrderPosItemShowItemMatrix = selectOrderPosItemShowItemMatrix;
	}
	
	public Boolean getNotAllowChangeUnit() {
		return notAllowChangeUnit;
	}
	
	public void setNotAllowChangeUnit(Boolean notAllowChangeUnit) {
		this.notAllowChangeUnit = notAllowChangeUnit;
	}
	
	public Integer getCalDayParamThree() {
		return calDayParamThree;
	}
	
	public void setCalDayParamThree(Integer calDayParamThree) {
		this.calDayParamThree = calDayParamThree;
	}
	
	public Boolean getRequestAutoGenerOutOrder() {
		return requestAutoGenerOutOrder;
	}
	
	public void setRequestAutoGenerOutOrder(Boolean requestAutoGenerOutOrder) {
		this.requestAutoGenerOutOrder = requestAutoGenerOutOrder;
	}
	
	public Boolean getRequestNotShowCenterInvetory() {
		return requestNotShowCenterInvetory;
	}
	
	public void setRequestNotShowCenterInvetory(Boolean requestNotShowCenterInvetory) {
		this.requestNotShowCenterInvetory = requestNotShowCenterInvetory;
	}
	
	public Boolean getInOrderFromOutNotAllowEdit() {
		return inOrderFromOutNotAllowEdit;
	}
	
	public void setInOrderFromOutNotAllowEdit(Boolean inOrderFromOutNotAllowEdit) {
		this.inOrderFromOutNotAllowEdit = inOrderFromOutNotAllowEdit;
	}
	
	public Boolean getEnableLockInventory() {
		return enableLockInventory;
	}
	
	public void setEnableLockInventory(Boolean enableLockInventory) {
		this.enableLockInventory = enableLockInventory;
	}
	
	public Integer getTransferLockDay() {
		return transferLockDay;
	}
	
	public void setTransferLockDay(Integer transferLockDay) {
		this.transferLockDay = transferLockDay;
	}
	
	public Integer getWholesaleLockDay() {
		return wholesaleLockDay;
	}
	
	public void setWholesaleLockDay(Integer wholesaleLockDay) {
		this.wholesaleLockDay = wholesaleLockDay;
	}
	
	public Integer getWholesaleBookValidDays() {
		return wholesaleBookValidDays;
	}
	
	public void setWholesaleBookValidDays(Integer wholesaleBookValidDays) {
		this.wholesaleBookValidDays = wholesaleBookValidDays;
	}
	
	public Boolean getRequestIngoreLastRequest() {
		return requestIngoreLastRequest;
	}
	
	public void setRequestIngoreLastRequest(Boolean requestIngoreLastRequest) {
		this.requestIngoreLastRequest = requestIngoreLastRequest;
	}
	
	public Integer getSingleTransferSendBranchCount() {
		return singleTransferSendBranchCount;
	}
	
	public void setSingleTransferSendBranchCount(Integer singleTransferSendBranchCount) {
		this.singleTransferSendBranchCount = singleTransferSendBranchCount;
	}
	
	public Boolean getWholesaleSalePriceNotShow() {
		return wholesaleSalePriceNotShow;
	}
	
	public void setWholesaleSalePriceNotShow(Boolean wholesaleSalePriceNotShow) {
		this.wholesaleSalePriceNotShow = wholesaleSalePriceNotShow;
	}
	
	public Boolean getWholesalePriceNotBelowThenMin() {
		return wholesalePriceNotBelowThenMin;
	}
	
	public void setWholesalePriceNotBelowThenMin(Boolean wholesalePriceNotBelowThenMin) {
		this.wholesalePriceNotBelowThenMin = wholesalePriceNotBelowThenMin;
	}
	
	public Boolean getReceiveOrderPrintNeedAudit() {
		return receiveOrderPrintNeedAudit;
	}
	
	public void setReceiveOrderPrintNeedAudit(Boolean receiveOrderPrintNeedAudit) {
		this.receiveOrderPrintNeedAudit = receiveOrderPrintNeedAudit;
	}
	
	public Boolean getOrderNotAuditFirst() {
		return orderNotAuditFirst;
	}
	
	public void setOrderNotAuditFirst(Boolean orderNotAuditFirst) {
		this.orderNotAuditFirst = orderNotAuditFirst;
	}
	
	public Boolean getNotReturnWholesaleAudit() {
		return notReturnWholesaleAudit;
	}
	
	public void setNotReturnWholesaleAudit(Boolean notReturnWholesaleAudit) {
		this.notReturnWholesaleAudit = notReturnWholesaleAudit;
	}
	
	public Boolean getWholesaleInventoryUnit() {
		return wholesaleInventoryUnit;
	}
	
	public void setWholesaleInventoryUnit(Boolean wholesaleInventoryUnit) {
		this.wholesaleInventoryUnit = wholesaleInventoryUnit;
	}
	
	public Boolean getWholesaleLoginSms() {
		return wholesaleLoginSms;
	}
	
	public void setWholesaleLoginSms(Boolean wholesaleLoginSms) {
		this.wholesaleLoginSms = wholesaleLoginSms;
	}
	
	public Boolean getShowClientBalance() {
		return showClientBalance;
	}
	
	public void setShowClientBalance(Boolean showClientBalance) {
		this.showClientBalance = showClientBalance;
	}
	
	public Boolean getRequestCenterInvenoryIgnore() {
		return requestCenterInvenoryIgnore;
	}
	
	public void setRequestCenterInvenoryIgnore(Boolean requestCenterInvenoryIgnore) {
		this.requestCenterInvenoryIgnore = requestCenterInvenoryIgnore;
	}
	
	public Boolean getRequestStockoutOrder() {
		return requestStockoutOrder;
	}
	
	public void setRequestStockoutOrder(Boolean requestStockoutOrder) {
		this.requestStockoutOrder = requestStockoutOrder;
	}
	
	public Boolean getRequestShowProductingDate() {
		return requestShowProductingDate;
	}
	
	public void setRequestShowProductingDate(Boolean requestShowProductingDate) {
		this.requestShowProductingDate = requestShowProductingDate;
	}
	
	public Boolean getCenterInvenoryIgnore() {
		return centerInvenoryIgnore;
	}
	
	public void setCenterInvenoryIgnore(Boolean centerInvenoryIgnore) {
		this.centerInvenoryIgnore = centerInvenoryIgnore;
	}
	
	public Boolean getPurChaseAuditInform() {
		return purChaseAuditInform;
	}
	
	public void setPurChaseAuditInform(Boolean purChaseAuditInform) {
		this.purChaseAuditInform = purChaseAuditInform;
	}
	
	public Boolean getPreSettlementInform() {
		return preSettlementInform;
	}
	
	public void setPreSettlementInform(Boolean preSettlementInform) {
		this.preSettlementInform = preSettlementInform;
	}
	
	public String getSmsPurChaseAuditContext() {
		return smsPurChaseAuditContext;
	}
	
	public void setSmsPurChaseAuditContext(String smsPurChaseAuditContext) {
		this.smsPurChaseAuditContext = smsPurChaseAuditContext;
	}
	
	public String getSmsPreSettlementContext() {
		return smsPreSettlementContext;
	}
	
	public void setSmsPreSettlementContext(String smsPreSettlementContext) {
		this.smsPreSettlementContext = smsPreSettlementContext;
	}
	
	public Boolean getCenterInvenoryRemoveWholeBook() {
		return centerInvenoryRemoveWholeBook;
	}
	
	
	public void setCenterInvenoryRemoveWholeBook(
			Boolean centerInvenoryRemoveWholeBook) {
		this.centerInvenoryRemoveWholeBook = centerInvenoryRemoveWholeBook;
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
	
	public Boolean getCenterAuditOutOrder() {
		return centerAuditOutOrder;
	}
	
	public void setCenterAuditOutOrder(Boolean centerAuditOutOrder) {
		this.centerAuditOutOrder = centerAuditOutOrder;
	}
	
	public Boolean getCenterTransferInRefCost() {
		return centerTransferInRefCost;
	}
	
	public void setCenterTransferInRefCost(Boolean centerTransferInRefCost) {
		this.centerTransferInRefCost = centerTransferInRefCost;
	}
	
	public Boolean getUnEditTransferOutPrice() {
		return unEditTransferOutPrice;
	}
	
	public void setUnEditTransferOutPrice(Boolean unEditTransferOutPrice) {
		this.unEditTransferOutPrice = unEditTransferOutPrice;
	}
	
	public Boolean getTransferOutUseOnce() {
		return transferOutUseOnce;
	}
	
	public void setTransferOutUseOnce(Boolean transferOutUseOnce) {
		this.transferOutUseOnce = transferOutUseOnce;
	}
	
	public Boolean getCenterInvenoryRemoveRequest() {
		return centerInvenoryRemoveRequest;
	}
	
	public void setCenterInvenoryRemoveRequest(
			Boolean centerInvenoryRemoveRequest) {
		this.centerInvenoryRemoveRequest = centerInvenoryRemoveRequest;
	}
	
	public Integer getRequestOrderValidDays() {
		return requestOrderValidDays;
	}
	
	public void setRequestOrderValidDays(Integer requestOrderValidDays) {
		this.requestOrderValidDays = requestOrderValidDays;
	}
	
	public Boolean getCenterpurchaseNotReceiveMore() {
		return centerpurchaseNotReceiveMore;
	}
	
	public void setCenterpurchaseNotReceiveMore(
			Boolean centerpurchaseNotReceiveMore) {
		this.centerpurchaseNotReceiveMore = centerpurchaseNotReceiveMore;
	}
	
	public Boolean getRequestOrderNotAllowAdd() {
		return requestOrderNotAllowAdd;
	}
	
	public void setRequestOrderNotAllowAdd(Boolean requestOrderNotAllowAdd) {
		this.requestOrderNotAllowAdd = requestOrderNotAllowAdd;
	}
	
	public Integer getPurchaseOrderVaildDays() {
		return purchaseOrderVaildDays;
	}
	
	
	public void setPurchaseOrderVaildDays(Integer purchaseOrderVaildDays) {
		this.purchaseOrderVaildDays = purchaseOrderVaildDays;
	}
	
	
	public Boolean getRequestOrderNotAllowEditUnit() {
		return requestOrderNotAllowEditUnit;
	}
	
	
	public void setRequestOrderNotAllowEditUnit(Boolean requestOrderNotAllowEditUnit) {
		this.requestOrderNotAllowEditUnit = requestOrderNotAllowEditUnit;
	}
	
	
	public Integer getRequestOrderAmountLower() {
		return requestOrderAmountLower;
	}
	
	
	public void setRequestOrderAmountLower(Integer requestOrderAmountLower) {
		this.requestOrderAmountLower = requestOrderAmountLower;
	}
	
	
	public Integer getRequestOrderAmountUpper() {
		return requestOrderAmountUpper;
	}
	
	
	public void setRequestOrderAmountUpper(Integer requestOrderAmountUpper) {
		this.requestOrderAmountUpper = requestOrderAmountUpper;
	}
	
	
	public Boolean getRequestOrderIntegerOnly() {
		return requestOrderIntegerOnly;
	}
	
	
	public void setRequestOrderIntegerOnly(Boolean requestOrderIntegerOnly) {
		this.requestOrderIntegerOnly = requestOrderIntegerOnly;
	}
	
	
	public Boolean getOutOrderPrintNeedAudit() {
		return outOrderPrintNeedAudit;
	}
	
	
	public void setOutOrderPrintNeedAudit(Boolean outOrderPrintNeedAudit) {
		this.outOrderPrintNeedAudit = outOrderPrintNeedAudit;
	}
	
	
	public Integer getOutOrderSplitAmount() {
		return outOrderSplitAmount;
	}
	
	
	public void setOutOrderSplitAmount(Integer outOrderSplitAmount) {
		this.outOrderSplitAmount = outOrderSplitAmount;
	}
	
	
	public Boolean getAutoChangeItemCostPrice() {
		return autoChangeItemCostPrice;
	}
	
	
	public void setAutoChangeItemCostPrice(Boolean autoChangeItemCostPrice) {
		this.autoChangeItemCostPrice = autoChangeItemCostPrice;
	}
	
	public Boolean getWholesaleOrderPrintNeedAudit() {
		return wholesaleOrderPrintNeedAudit;
	}
	
	public void setWholesaleOrderPrintNeedAudit(Boolean wholesaleOrderPrintNeedAudit) {
		this.wholesaleOrderPrintNeedAudit = wholesaleOrderPrintNeedAudit;
	}
	
	public Boolean getSmsShipOrder() {
		return smsShipOrder;
	}
	
	public void setSmsShipOrder(Boolean smsShipOrder) {
		this.smsShipOrder = smsShipOrder;
	}
	
	public String getSmsShipOrderContext() {
		return smsShipOrderContext;
	}
	
	public void setSmsShipOrderContext(String smsShipOrderContext) {
		this.smsShipOrderContext = smsShipOrderContext;
	}
	
	public Integer getCalDayParamOne() {
		return calDayParamOne;
	}
	
	
	public void setCalDayParamOne(Integer calDayParamOne) {
		this.calDayParamOne = calDayParamOne;
	}
	
	
	public Integer getCalDayParamTwo() {
		return calDayParamTwo;
	}
	
	
	public void setCalDayParamTwo(Integer calDayParamTwo) {
		this.calDayParamTwo = calDayParamTwo;
	}
	
	
	public Integer getOutAmountType() {
		return outAmountType;
	}
	
	
	public void setOutAmountType(Integer outAmountType) {
		this.outAmountType = outAmountType;
	}
	
	
	public Boolean getSupportSmsNotice() {
		return supportSmsNotice;
	}
	
	
	public void setSupportSmsNotice(Boolean supportSmsNotice) {
		this.supportSmsNotice = supportSmsNotice;
	}
	
	
	public Boolean getNewWholeOrderInform() {
		return newWholeOrderInform;
	}
	
	
	public void setNewWholeOrderInform(Boolean newWholeOrderInform) {
		this.newWholeOrderInform = newWholeOrderInform;
	}
	
	
	public Boolean getSendWholeOrderInform() {
		return sendWholeOrderInform;
	}
	
	
	public void setSendWholeOrderInform(Boolean sendWholeOrderInform) {
		this.sendWholeOrderInform = sendWholeOrderInform;
	}
	
	
	public Boolean getPaymnetWholeOrderInform() {
		return paymnetWholeOrderInform;
	}
	
	
	public void setPaymnetWholeOrderInform(Boolean paymnetWholeOrderInform) {
		this.paymnetWholeOrderInform = paymnetWholeOrderInform;
	}
	
	
	public Boolean getAffirmWholeMoneyInform() {
		return affirmWholeMoneyInform;
	}
	
	public void setAffirmWholeMoneyInform(Boolean affirmWholeMoneyInform) {
		this.affirmWholeMoneyInform = affirmWholeMoneyInform;
	}
	
	public Boolean getPickWholeOrderInform() {
		return pickWholeOrderInform;
	}
	
	public void setPickWholeOrderInform(Boolean pickWholeOrderInform) {
		this.pickWholeOrderInform = pickWholeOrderInform;
	}
	
	public Boolean getOrderWholeAuditInform() {
		return orderWholeAuditInform;
	}
	
	public void setOrderWholeAuditInform(Boolean orderWholeAuditInform) {
		this.orderWholeAuditInform = orderWholeAuditInform;
	}
	
	public String getSmsNewWholeContext() {
		return smsNewWholeContext;
	}
	
	public void setSmsNewWholeContext(String smsNewWholeContext) {
		this.smsNewWholeContext = smsNewWholeContext;
	}
	
	public String getSmsSendWholeContext() {
		return smsSendWholeContext;
	}
	
	public void setSmsSendWholeContext(String smsSendWholeContext) {
		this.smsSendWholeContext = smsSendWholeContext;
	}
	
	public String getSmsPaymentWholeContext() {
		return smsPaymentWholeContext;
	}
	
	public void setSmsPaymentWholeContext(String smsPaymentWholeContext) {
		this.smsPaymentWholeContext = smsPaymentWholeContext;
	}
	
	public String getSmsAffirmWholeContext() {
		return smsAffirmWholeContext;
	}
	
	public void setSmsAffirmWholeContext(String smsAffirmWholeContext) {
		this.smsAffirmWholeContext = smsAffirmWholeContext;
	}
	
	public String getSmsPickWholeContext() {
		return smsPickWholeContext;
	}
	
	public void setSmsPickWholeContext(String smsPickWholeContext) {
		this.smsPickWholeContext = smsPickWholeContext;
	}
	
	public String getSmsAuditWholeContext() {
		return smsAuditWholeContext;
	}
	
	
	public void setSmsAuditWholeContext(String smsAuditWholeContext) {
		this.smsAuditWholeContext = smsAuditWholeContext;
	}
	
	
	public Boolean getNewChainOrderInform() {
		return newChainOrderInform;
	}
	
	
	public void setNewChainOrderInform(Boolean newChainOrderInform) {
		this.newChainOrderInform = newChainOrderInform;
	}
	
	public Boolean getPaymnetChainOrderInform() {
		return paymnetChainOrderInform;
	}
	
	public void setPaymnetChainOrderInform(Boolean paymnetChainOrderInform) {
		this.paymnetChainOrderInform = paymnetChainOrderInform;
	}
	
	public Boolean getAffirmChainMoneyInform() {
		return affirmChainMoneyInform;
	}
	
	public void setAffirmChainMoneyInform(Boolean affirmChainMoneyInform) {
		this.affirmChainMoneyInform = affirmChainMoneyInform;
	}
	
	public Boolean getBranchReceiveOrderInform() {
		return branchReceiveOrderInform;
	}
	
	public void setBranchReceiveOrderInform(Boolean branchReceiveOrderInform) {
		this.branchReceiveOrderInform = branchReceiveOrderInform;
	}
	
	public Boolean getOrderChainAuditInform() {
		return orderChainAuditInform;
	}
	
	public void setOrderChainAuditInform(Boolean orderChainAuditInform) {
		this.orderChainAuditInform = orderChainAuditInform;
	}
	
	public String getSmsNewChainContext() {
		return smsNewChainContext;
	}
	
	public void setSmsNewChainContext(String smsNewChainContext) {
		this.smsNewChainContext = smsNewChainContext;
	}
	
	public String getSmsPaymentChainContext() {
		return smsPaymentChainContext;
	}
	
	public void setSmsPaymentChainContext(String smsPaymentChainContext) {
		this.smsPaymentChainContext = smsPaymentChainContext;
	}
	
	public String getSmsAffirmChainContext() {
		return smsAffirmChainContext;
	}
	
	public void setSmsAffirmChainContext(String smsAffirmChainContext) {
		this.smsAffirmChainContext = smsAffirmChainContext;
	}
	
	public String getSmsPickChainContext() {
		return smsPickChainContext;
	}
	
	public void setSmsPickChainContext(String smsPickChainContext) {
		this.smsPickChainContext = smsPickChainContext;
	}
	
	public String getSmsBranchChainContext() {
		return smsBranchChainContext;
	}
	
	public void setSmsBranchChainContext(String smsBranchChainContext) {
		this.smsBranchChainContext = smsBranchChainContext;
	}
	
	public String getSmsAuditChainContext() {
		return smsAuditChainContext;
	}
	
	public void setSmsAuditChainContext(String smsAuditChainContext) {
		this.smsAuditChainContext = smsAuditChainContext;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getFinancePhone() {
		return financePhone;
	}
	
	public void setFinancePhone(String financePhone) {
		this.financePhone = financePhone;
	}
	
	public String getStorehousePhone() {
		return storehousePhone;
	}
	
	public void setStorehousePhone(String storehousePhone) {
		this.storehousePhone = storehousePhone;
	}
	
	public Boolean getWholesaleStockoutOrder() {
		return wholesaleStockoutOrder;
	}
	
	
	public void setWholesaleStockoutOrder(Boolean wholesaleStockoutOrder) {
		this.wholesaleStockoutOrder = wholesaleStockoutOrder;
	}
	
	
	public Boolean getUseLastWholePrice() {
		return useLastWholePrice;
	}
	
	public void setUseLastWholePrice(Boolean useLastWholePrice) {
		this.useLastWholePrice = useLastWholePrice;
	}
	
	public Boolean getPrintTogetherByChain() {
		return printTogetherByChain;
	}
	
	public void setPrintTogetherByChain(Boolean printTogetherByChain) {
		this.printTogetherByChain = printTogetherByChain;
	}
	
	public Boolean getPrintTogetherByWholeBook() {
		return printTogetherByWholeBook;
	}
	
	public void setPrintTogetherByWholeBook(Boolean printTogetherByWholeBook) {
		this.printTogetherByWholeBook = printTogetherByWholeBook;
	}
	
	public Boolean getCreateWholesaleOrderByAudit() {
		return createWholesaleOrderByAudit;
	}
	
	public void setCreateWholesaleOrderByAudit(Boolean createWholesaleOrderByAudit) {
		this.createWholesaleOrderByAudit = createWholesaleOrderByAudit;
	}
	
	public Boolean getWholesaleMatrixAddOns() {
		return wholesaleMatrixAddOns;
	}
	
	public void setWholesaleMatrixAddOns(Boolean wholesaleMatrixAddOns) {
		this.wholesaleMatrixAddOns = wholesaleMatrixAddOns;
	}
	
	public Boolean getWholesaleReturnCreateByOrder() {
		return wholesaleReturnCreateByOrder;
	}
	
	public void setWholesaleReturnCreateByOrder(Boolean wholesaleReturnCreateByOrder) {
		this.wholesaleReturnCreateByOrder = wholesaleReturnCreateByOrder;
	}
	
	public Boolean getCenterOutOrderGenerateBranchInOrder() {
		return centerOutOrderGenerateBranchInOrder;
	}
	
	public void setCenterOutOrderGenerateBranchInOrder(Boolean centerOutOrderGenerateBranchInOrder) {
		this.centerOutOrderGenerateBranchInOrder = centerOutOrderGenerateBranchInOrder;
	}
	
	public Boolean getPurchaseUseBoxDeposit() {
		return purchaseUseBoxDeposit;
	}
	
	public void setPurchaseUseBoxDeposit(Boolean purchaseUseBoxDeposit) {
		this.purchaseUseBoxDeposit = purchaseUseBoxDeposit;
	}
	
	public Boolean getForbidWholesaleAuditOverClientBalance() {
		return forbidWholesaleAuditOverClientBalance;
	}
	
	public void setForbidWholesaleAuditOverClientBalance(Boolean forbidWholesaleAuditOverClientBalance) {
		this.forbidWholesaleAuditOverClientBalance = forbidWholesaleAuditOverClientBalance;
	}
	
	public static String writeToXml(ChainDeliveryParam chainDeliveryParam) {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("ChainDeliveryParam");
		root.addElement("TransferInNeedOut").setText(BooleanUtils.toString(chainDeliveryParam.getTransferInNeedOut(), "1", "0", "0"));
		root.addElement("TransferOutRefInPrice").setText(BooleanUtils.toString(chainDeliveryParam.getTransferOutRefInPrice(), "1", "0", "0"));
		root.addElement("CenterAuditOutOrder").setText(BooleanUtils.toString(chainDeliveryParam.getCenterAuditOutOrder(), "1", "0", "0"));
		root.addElement("CenterTransferInRefCost").setText(BooleanUtils.toString(chainDeliveryParam.getCenterTransferInRefCost(), "1", "0", "0"));
		root.addElement("UnEditTransferOutPrice").setText(BooleanUtils.toString(chainDeliveryParam.getUnEditTransferOutPrice(), "1", "0", "0"));
		
		if(chainDeliveryParam.getRequestOrderValidDays() != null){
			root.addElement("RequestOrderVaildDays").setText(chainDeliveryParam.getRequestOrderValidDays().toString());
		}
		root.addElement("CenterPurchaseNotReceiveMore").setText(BooleanUtils.toString(chainDeliveryParam.getCenterpurchaseNotReceiveMore(), "1", "0", "0"));
		if(chainDeliveryParam.getPurchaseOrderVaildDays() != null){
			root.addElement("PurchaseOrderVaildDays").setText(chainDeliveryParam.getPurchaseOrderVaildDays().toString());
		}
		root.addElement("TransferOutUsedOnce").setText(BooleanUtils.toString(chainDeliveryParam.getTransferOutUseOnce(), "1", "0", "0"));
		root.addElement("CenterInvenoryRemoveRequest").setText(BooleanUtils.toString(chainDeliveryParam.getCenterInvenoryRemoveRequest(), "1", "0", "0"));
		root.addElement("RequestOrderNotAllowAdd").setText(BooleanUtils.toString(chainDeliveryParam.getRequestOrderNotAllowAdd(), "1", "0", "0"));
		root.addElement("RequestOrderNotAllowEditUnit").setText(BooleanUtils.toString(chainDeliveryParam.getRequestOrderNotAllowEditUnit(), "1", "0", "0"));
		root.addElement("RequestOrderIntegerOnly").setText(BooleanUtils.toString(chainDeliveryParam.getRequestOrderIntegerOnly(), "1", "0", "0"));
		root.addElement("OutOrderPrintNeedAudit").setText(BooleanUtils.toString(chainDeliveryParam.getOutOrderPrintNeedAudit(), "1", "0", "0"));
		root.addElement("AutoChangeItemCostPrice").setText(BooleanUtils.toString(chainDeliveryParam.getAutoChangeItemCostPrice(), "1", "0", "0"));
		root.addElement("WholesaleOrderPrintNeedAudit").setText(BooleanUtils.toString(chainDeliveryParam.getWholesaleOrderPrintNeedAudit(), "1", "0", "0"));
		root.addElement("WholesaleStockoutOrder").setText(BooleanUtils.toString(chainDeliveryParam.getWholesaleStockoutOrder(), "1", "0", "0"));
		if(chainDeliveryParam.getRequestOrderAmountLower() != null){
			root.addElement("RequestOrderAmountLower").setText(chainDeliveryParam.getRequestOrderAmountLower().toString());
		}
		if(chainDeliveryParam.getRequestOrderAmountUpper() != null){
			root.addElement("RequestOrderAmountUpper").setText(chainDeliveryParam.getRequestOrderAmountUpper().toString());
		}
		if(chainDeliveryParam.getOutOrderSplitAmount() != null){
			root.addElement("OutOrderSplitAmount").setText(chainDeliveryParam.getOutOrderSplitAmount().toString());
		}
		if(chainDeliveryParam.getCalDayParamOne() != null){
			root.addElement("CalDayParamOne").setText(chainDeliveryParam.getCalDayParamOne().toString());
		}
		if(chainDeliveryParam.getCalDayParamTwo() != null){
			root.addElement("CalDayParamTwo").setText(chainDeliveryParam.getCalDayParamTwo().toString());
		}
		if(chainDeliveryParam.getOutAmountType() != null){
			root.addElement("OutAmountType").setText(chainDeliveryParam.getOutAmountType().toString());
		}
		root.addElement("NewWholeOrderInform").setText(BooleanUtils.toString(chainDeliveryParam.getNewWholeOrderInform(), "1", "0", "0"));
		root.addElement("SendWholeOrderInform").setText(BooleanUtils.toString(chainDeliveryParam.getSendWholeOrderInform(), "1", "0", "0"));
		root.addElement("PaymnetWholeOrderInform").setText(BooleanUtils.toString(chainDeliveryParam.getPaymnetWholeOrderInform(), "1", "0", "0"));
		root.addElement("AffirmWholeMoneyInform").setText(BooleanUtils.toString(chainDeliveryParam.getAffirmWholeMoneyInform(), "1", "0", "0"));
		root.addElement("PickWholeOrderInform").setText(BooleanUtils.toString(chainDeliveryParam.getPickWholeOrderInform(), "1", "0", "0"));
		root.addElement("OrderWholeAuditInform").setText(BooleanUtils.toString(chainDeliveryParam.getOrderWholeAuditInform(), "1", "0", "0"));
		
		if(chainDeliveryParam.getSmsNewWholeContext() != null){
			root.addElement("SmsNewWholeContext").setText(chainDeliveryParam.getSmsNewWholeContext());
		}
		if(chainDeliveryParam.getSmsSendWholeContext() != null){
			root.addElement("SmsSendWholeContext").setText(chainDeliveryParam.getSmsSendWholeContext());
		}
		if(chainDeliveryParam.getSmsPaymentWholeContext() != null){
			root.addElement("SmsPaymentWholeContext").setText(chainDeliveryParam.getSmsPaymentWholeContext());
		}
		if(chainDeliveryParam.getSmsAffirmWholeContext() != null){
			root.addElement("SmsAffirmWholeContext").setText(chainDeliveryParam.getSmsAffirmWholeContext());
		}
		if(chainDeliveryParam.getSmsPickWholeContext() != null){
			root.addElement("SmsPickWholeContext").setText(chainDeliveryParam.getSmsPickWholeContext());
		}
		if(chainDeliveryParam.getSmsAuditWholeContext() != null){
			root.addElement("SmsAuditWholeContext").setText(chainDeliveryParam.getSmsAuditWholeContext());
		}
		
		root.addElement("NewChainOrderInform").setText(BooleanUtils.toString(chainDeliveryParam.getNewChainOrderInform(), "1", "0", "0"));
		root.addElement("SMSShipOrder").setText(BooleanUtils.toString(chainDeliveryParam.getSmsShipOrder(), "1", "0", "0"));
		root.addElement("PaymnetChainOrderInform").setText(BooleanUtils.toString(chainDeliveryParam.getPaymnetChainOrderInform(), "1", "0", "0"));
		root.addElement("AffirmChainMoneyInform").setText(BooleanUtils.toString(chainDeliveryParam.getAffirmChainMoneyInform(), "1", "0", "0"));
		if(chainDeliveryParam.getSupportSmsNotice() != null){
			root.addElement("SupportSmsNotice").setText(BooleanUtils.toString(chainDeliveryParam.getSupportSmsNotice(), "1", "0"));
		}
		root.addElement("BranchReceiveOrderInform").setText(BooleanUtils.toString(chainDeliveryParam.getBranchReceiveOrderInform(), "1", "0", "0"));
		root.addElement("OrderChainAuditInform").setText(BooleanUtils.toString(chainDeliveryParam.getOrderChainAuditInform(), "1", "0", "0"));
		
		if(chainDeliveryParam.getSmsNewChainContext() != null){
			root.addElement("SmsNewChainContext").setText(chainDeliveryParam.getSmsNewChainContext());
		}
		if(chainDeliveryParam.getSmsShipOrderContext() != null){
			root.addElement("SMSShipOrderContext").setText(chainDeliveryParam.getSmsShipOrderContext());
		}
		if(chainDeliveryParam.getSmsPaymentChainContext() != null){
			root.addElement("SmsPaymentChainContext").setText(chainDeliveryParam.getSmsPaymentChainContext());
		}
		if(chainDeliveryParam.getSmsAffirmChainContext() != null){
			root.addElement("SmsAffirmChainContext").setText(chainDeliveryParam.getSmsAffirmChainContext());
		}
		if(chainDeliveryParam.getSmsPickChainContext() != null){
			root.addElement("SmsPickChainContext").setText(chainDeliveryParam.getSmsPickChainContext());
		}
		if(chainDeliveryParam.getSmsBranchChainContext() != null){
			root.addElement("SmsBranchChainContext").setText(chainDeliveryParam.getSmsBranchChainContext());
		}
		if(chainDeliveryParam.getSmsAuditChainContext() != null){
			root.addElement("SmsAuditChainContext").setText(chainDeliveryParam.getSmsAuditChainContext());
		}
		if(chainDeliveryParam.getStorehousePhone() != null){
			root.addElement("StorehousePhone").setText(chainDeliveryParam.getStorehousePhone());
		}
		if(chainDeliveryParam.getFinancePhone() != null){
			root.addElement("FinancePhone").setText(chainDeliveryParam.getFinancePhone());
		}
		root.addElement("CenterInvenoryRemoveWholeBook").setText(BooleanUtils.toString(chainDeliveryParam.getCenterInvenoryRemoveWholeBook(), "1", "0", "0"));
		root.addElement("PurChaseAuditInform").setText(BooleanUtils.toString(chainDeliveryParam.getPurChaseAuditInform(), "1", "0", "0"));
		root.addElement("PreSettlementInform").setText(BooleanUtils.toString(chainDeliveryParam.getPreSettlementInform(), "1", "0", "0"));
		
		if(chainDeliveryParam.getSmsPurChaseAuditContext() != null){
			root.addElement("SmsPurChaseAuditContext").setText(chainDeliveryParam.getSmsPurChaseAuditContext());
		}
		
		if(chainDeliveryParam.getSmsPreSettlementContext() != null){
			root.addElement("SmsPreSettlementContext").setText(chainDeliveryParam.getSmsPreSettlementContext());
		}
		root.addElement("CenterInvenoryIgnore").setText(BooleanUtils.toString(chainDeliveryParam.getCenterInvenoryIgnore(), "1", "0", "0"));
		root.addElement("RequestShowProductingDate").setText(BooleanUtils.toString(chainDeliveryParam.getRequestShowProductingDate(), "1", "0", "0"));
		
		root.addElement("RequestCenterInvenoryIgnore").setText(BooleanUtils.toString(chainDeliveryParam.getRequestCenterInvenoryIgnore(), "1", "0", "0"));
		root.addElement("RequestStockoutOrder").setText(BooleanUtils.toString(chainDeliveryParam.getRequestStockoutOrder(), "1", "0", "0"));
		root.addElement("UseLastWholePrice").setText(BooleanUtils.toString(chainDeliveryParam.getUseLastWholePrice(), "1", "0", "0"));
		root.addElement("PrintTogetherByChain").setText(BooleanUtils.toString(chainDeliveryParam.getPrintTogetherByChain(), "1", "0", "0"));
		root.addElement("PrintTogetherByWholeBook").setText(BooleanUtils.toString(chainDeliveryParam.getPrintTogetherByWholeBook(), "1", "0", "0"));
		root.addElement("CreateWholesaleOrderByAudit").setText(BooleanUtils.toString(chainDeliveryParam.getCreateWholesaleOrderByAudit(), "1", "0", "0"));
		root.addElement("ShowClientBalance").setText(BooleanUtils.toString(chainDeliveryParam.getShowClientBalance(), "1", "0", "0"));
		root.addElement("WholesaleLoginSms").setText(BooleanUtils.toString(chainDeliveryParam.getWholesaleLoginSms(), "1", "0", "0"));
		root.addElement("NotReturnWholesaleAudit").setText(BooleanUtils.toString(chainDeliveryParam.getNotReturnWholesaleAudit(), "1", "0", "0"));
		root.addElement("WholesaleInventoryUnit").setText(BooleanUtils.toString(chainDeliveryParam.getWholesaleInventoryUnit(), "1", "0", "0"));
		root.addElement("OrderNotAuditFirst").setText(BooleanUtils.toString(chainDeliveryParam.getOrderNotAuditFirst(), "1", "0", "0"));
		root.addElement("ReceiveOrderPrintNeedAudit").setText(BooleanUtils.toString(chainDeliveryParam.getReceiveOrderPrintNeedAudit(), "1", "0", "0"));
		root.addElement("WholesalePriceNotBelowThenMin").setText(BooleanUtils.toString(chainDeliveryParam.getWholesalePriceNotBelowThenMin(), "1", "0", "0"));
		root.addElement("WholesaleSalePriceNotShow").setText(BooleanUtils.toString(chainDeliveryParam.getWholesaleSalePriceNotShow(), "1", "0", "0"));
		root.addElement("RequestIngoreLastRequest").setText(BooleanUtils.toString(chainDeliveryParam.getRequestIngoreLastRequest(), "1", "0", "0"));
		root.addElement("WholesaleMatrixAddOns").setText(BooleanUtils.toString(chainDeliveryParam.getWholesaleMatrixAddOns(), "1", "0", "0"));
		root.addElement("EnableLockInventory").setText(BooleanUtils.toString(chainDeliveryParam.getEnableLockInventory(), "1", "0", "0"));
		root.addElement("InOrderFromOutNotAllowEdit").setText(BooleanUtils.toString(chainDeliveryParam.getInOrderFromOutNotAllowEdit(), "1", "0", "0"));
		
		if(chainDeliveryParam.getSingleTransferSendBranchCount() != null){
			root.addElement("SingleTransferSendBranchCount").setText(chainDeliveryParam.getSingleTransferSendBranchCount().toString());
			
		} else {
			root.addElement("SingleTransferSendBranchCount").setText("7");
			
		}
		if(chainDeliveryParam.getWholesaleBookValidDays() != null){
			root.addElement("WholesaleBookValidDays").setText(chainDeliveryParam.getWholesaleBookValidDays().toString());
			
		} else {
			root.addElement("WholesaleBookValidDays").setText("0");
			
		}
		if(chainDeliveryParam.getTransferLockDay() != null){
			root.addElement("TransferLockDay").setText(chainDeliveryParam.getTransferLockDay().toString());
			
		} else {
			root.addElement("TransferLockDay").setText("0");
		}
		if(chainDeliveryParam.getWholesaleLockDay() != null){
			root.addElement("WholesaleLockDay").setText(chainDeliveryParam.getWholesaleLockDay().toString());
			
		} else {
			root.addElement("WholesaleLockDay").setText("0");
		}
		root.addElement("WholesaleReturnCreateByOrder").setText(BooleanUtils.toString(chainDeliveryParam.getWholesaleReturnCreateByOrder(), "1", "0", "0"));
		
		if(chainDeliveryParam.getRequestAutoGenerOutOrder() != null){
			root.addElement("RequestAutoGenerOutOrder").setText(BooleanUtils.toString(chainDeliveryParam.getRequestAutoGenerOutOrder(), "1", "0"));
			
		}
		if(chainDeliveryParam.getRequestNotShowCenterInvetory() != null){
			root.addElement("RequestNotShowCenterInvetory").setText(BooleanUtils.toString(chainDeliveryParam.getRequestNotShowCenterInvetory(), "1", "0"));
			
		}
		if(chainDeliveryParam.getCalDayParamThree() != null){
			root.addElement("CalDayParamThree").setText(chainDeliveryParam.getCalDayParamThree().toString());
			
		}
		if(chainDeliveryParam.getCalDayParamOneWholesale() != null){
			root.addElement("CalDayParamOneWholesale").setText(chainDeliveryParam.getCalDayParamOneWholesale().toString());
			
		}
		if(chainDeliveryParam.getCalDayParamTwoWholesale() != null){
			root.addElement("CalDayParamTwoWholesale").setText(chainDeliveryParam.getCalDayParamTwoWholesale().toString());
			
		}
		if(chainDeliveryParam.getOutAmountTypeWholesale() != null){
			root.addElement("OutAmountTypeWholesale").setText(chainDeliveryParam.getOutAmountTypeWholesale().toString());
			
		}
		if(chainDeliveryParam.getWholesaleOrderAmountLower() != null){
			root.addElement("WholesaleOrderAmountLower").setText(chainDeliveryParam.getWholesaleOrderAmountLower().toString());
			
		}
		if(chainDeliveryParam.getWholesaleOrderAmountUpper() != null){
			root.addElement("WholesaleOrderAmountUpper").setText(chainDeliveryParam.getWholesaleOrderAmountUpper().toString());
			
		}
		
		if(chainDeliveryParam.getTransferPriceRoundFrom() != null){
			root.addElement("TransferPriceRoundFrom").setText(chainDeliveryParam.getTransferPriceRoundFrom().toString());
			
		}
		if(chainDeliveryParam.getTransferPriceRoundType() != null){
			root.addElement("TransferPriceRoundType").setText(chainDeliveryParam.getTransferPriceRoundType().toString());
			
		}
		if(chainDeliveryParam.getTransferPriceRoundTo() != null){
			root.addElement("TransferPriceRoundTo").setText(chainDeliveryParam.getTransferPriceRoundTo().toString());
			
		}
		if(chainDeliveryParam.getWholesaleBookDefaultState() != null){
			root.addElement("WholesaleBookDefaultState").setText(chainDeliveryParam.getWholesaleBookDefaultState());
			
		}
		
		root.addElement("NotAllowChangeUnit").setText(BooleanUtils.toString(chainDeliveryParam.getNotAllowChangeUnit(), "1", "0", "0"));
		root.addElement("CenterOutOrderGenerateBranchInOrder").setText(BooleanUtils.toString(chainDeliveryParam.getCenterOutOrderGenerateBranchInOrder(), "1", "0", "0"));
		root.addElement("PurchaseUseBoxDeposit").setText(BooleanUtils.toString(chainDeliveryParam.getPurchaseUseBoxDeposit(), "1", "0", "0"));
		root.addElement("SelectOrderPosItemShowItemMatrix").setText(BooleanUtils.toString(chainDeliveryParam.getSelectOrderPosItemShowItemMatrix(), "1", "0", "0"));
		root.addElement("EnableInventoryLnDetailRate").setText(BooleanUtils.toString(chainDeliveryParam.getEnableInventoryLnDetailRate(), "1", "0", "0"));
		root.addElement("NotReturnChainAudit").setText(BooleanUtils.toString(chainDeliveryParam.getNotReturnChainAudit(), "1", "0", "0"));
		root.addElement("ShowAllPosItemWholesale").setText(BooleanUtils.toString(chainDeliveryParam.getShowAllPosItemWholesale(), "1", "0", "0"));
		root.addElement("TransferPriceAsProfitRate").setText(BooleanUtils.toString(chainDeliveryParam.getTransferPriceAsProfitRate(), "1", "0", "0"));
		root.addElement("TransferInOrderEditBaseQty").setText(BooleanUtils.toString(chainDeliveryParam.getTransferInOrderEditBaseQty(), "1", "0", "0"));
		root.addElement("TransferPriceRounding").setText(BooleanUtils.toString(chainDeliveryParam.getTransferPriceRounding(), "1", "0", "0"));
		root.addElement("RequestNotOverCenterStock").setText(BooleanUtils.toString(chainDeliveryParam.getRequestNotOverCenterStock(), "1", "0", "0"));
		root.addElement("PadReceiveCanEditBasicQty").setText(BooleanUtils.toString(chainDeliveryParam.getPadReceiveCanEditBasicQty(), "1", "0", "0"));
		root.addElement("OutOrderQtySubTareQty").setText(BooleanUtils.toString(chainDeliveryParam.getOutOrderQtySubTareQty(), "1", "0", "0"));
		root.addElement("RequestShowCenterInventoryName").setText(BooleanUtils.toString(chainDeliveryParam.getRequestShowCenterInventoryName(), "1", "0", "0"));
		root.addElement("EnableTransferInSubTareQty").setText(BooleanUtils.toString(chainDeliveryParam.getEnableTransferInSubTareQty(), "1", "0", "0"));
		root.addElement("TranferInNeedOutSended").setText(BooleanUtils.toString(chainDeliveryParam.getTranferInNeedOutSended(), "1", "0", "0"));
		root.addElement("EnableTransferPolicyQuantity").setText(BooleanUtils.toString(chainDeliveryParam.getEnableTransferPolicyQuantity(), "1", "0", "0"));
		root.addElement("EnableAssistUnitAsUseUnit").setText(BooleanUtils.toString(chainDeliveryParam.getEnableAssistUnitAsUseUnit(), "1", "0", "0"));
		root.addElement("CanNotEditAntiOrder").setText(BooleanUtils.toString(chainDeliveryParam.getCanNotEditAntiOrder(), "1", "0", "0"));
		root.addElement("WholesalePriceRounding").setText(BooleanUtils.toString(chainDeliveryParam.getWholesalePriceRounding(), "1", "0", "0"));
		root.addElement("EnableRequestMoney").setText(BooleanUtils.toString(chainDeliveryParam.getEnableRequestMoney(), "1", "0", "0"));
		root.addElement("EnableLowReceivePrice").setText(BooleanUtils.toString(chainDeliveryParam.getEnableLowReceivePrice(), "1", "0", "0"));
		
		if(chainDeliveryParam.getWholesalePriceRoundFrom() != null){
			root.addElement("WholesalePriceRoundFrom").setText(chainDeliveryParam.getWholesalePriceRoundFrom().toString());
			
		}
		if(chainDeliveryParam.getWholesalePriceRoundType() != null){
			root.addElement("WholesalePriceRoundType").setText(chainDeliveryParam.getWholesalePriceRoundType().toString());
			
		}
		if(chainDeliveryParam.getWholesalePriceRoundTo() != null){
			root.addElement("WholesalePriceRoundTo").setText(chainDeliveryParam.getWholesalePriceRoundTo().toString());
			
		}
		if(chainDeliveryParam.getRequestAndSaleRate() != null){
			root.addElement("RequestAndSaleRate").setText(chainDeliveryParam.getRequestAndSaleRate().toString());
			
		}
		
		if(chainDeliveryParam.getCenterOutOrderGenerateBranchInOrderState() != null){
			root.addElement("CenterOutOrderGenerateBranchInOrderState").setText(chainDeliveryParam.getCenterOutOrderGenerateBranchInOrderState().toString());
			
		}
		
		root.addElement("EnableRequestRefItem").setText(BooleanUtils.toString(chainDeliveryParam.getEnableRequestRefItem(), "1", "0", "0"));
		root.addElement("WholesalePriceAsProfitRate").setText(BooleanUtils.toString(chainDeliveryParam.getWholesalePriceAsProfitRate(), "1", "0", "0"));
		root.addElement("ForbidWholesaleAuditOverClientBalance").setText(BooleanUtils.toString(chainDeliveryParam.getForbidWholesaleAuditOverClientBalance(), "1", "0", "0"));
		root.addElement("CenterSendConfirmOnly").setText(BooleanUtils.toString(chainDeliveryParam.getCenterSendConfirmOnly(), "1", "0", "0"));
		
		
		
		return document.asXML();
	}
	
	public static ChainDeliveryParam fromXml(String text) {
		ChainDeliveryParam param = new ChainDeliveryParam();
		try {
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			Element element;
			element = (Element)root.selectSingleNode("TransferInNeedOut");
			if(element != null){
				param.setTransferInNeedOut(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("TransferOutRefInPrice");
			if(element != null){
				param.setTransferOutRefInPrice(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("CenterAuditOutOrder");
			if(element != null){
				param.setCenterAuditOutOrder(AppUtil.strToBool(element.getText()));
			} else {
				param.setCenterAuditOutOrder(false);
				
			}
			
			element = (Element)root.selectSingleNode("CenterTransferInRefCost");
			if(element != null){
				param.setCenterTransferInRefCost(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("UnEditTransferOutPrice");
			if(element != null){
				param.setUnEditTransferOutPrice(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("TransferOutUsedOnce");
			if(element != null){
				param.setTransferOutUseOnce(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("CenterInvenoryRemoveRequest");
			if(element != null){
				param.setCenterInvenoryRemoveRequest(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("RequestOrderVaildDays");
			if(element != null){
				param.setRequestOrderValidDays(Integer.parseInt(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("PurchaseOrderVaildDays");
			if(element != null){
				param.setPurchaseOrderVaildDays(Integer.parseInt(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("CenterPurchaseNotReceiveMore");
			if(element != null){
				param.setCenterpurchaseNotReceiveMore(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("RequestOrderNotAllowAdd");
			if(element != null){
				param.setRequestOrderNotAllowAdd(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("RequestOrderNotAllowEditUnit");
			if(element != null){
				param.setRequestOrderNotAllowEditUnit(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("RequestOrderAmountLower");
			if(element != null){
				param.setRequestOrderAmountLower(Integer.parseInt(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("RequestOrderAmountUpper");
			if(element != null){
				param.setRequestOrderAmountUpper(Integer.parseInt(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("RequestOrderIntegerOnly");
			if(element != null){
				param.setRequestOrderIntegerOnly(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("OutOrderPrintNeedAudit");
			if(element != null){
				param.setOutOrderPrintNeedAudit(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("AutoChangeItemCostPrice");
			if(element != null){
				param.setAutoChangeItemCostPrice(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("WholesaleOrderPrintNeedAudit");
			if(element != null){
				param.setWholesaleOrderPrintNeedAudit(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("WholesaleStockoutOrder");
			if(element != null){
				param.setWholesaleStockoutOrder(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("SMSShipOrder");
			if(element != null){
				param.setSmsShipOrder(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("OutOrderSplitAmount");
			if(element != null){
				param.setOutOrderSplitAmount(Integer.parseInt(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("SMSShipOrderContext");
			if(element != null){
				param.setSmsShipOrderContext(element.getText());
			}
			
			element = (Element)root.selectSingleNode("CalDayParamOne");
			if(element != null){
				param.setCalDayParamOne(Integer.parseInt(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("CalDayParamTwo");
			if(element != null){
				param.setCalDayParamTwo(Integer.parseInt(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("OutAmountType");
			if(element != null){
				param.setOutAmountType(Integer.parseInt(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("SupportSmsNotice");
			if(element != null){
				param.setSupportSmsNotice(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("SupportSmsNotice");
			if(element != null){
				param.setSupportSmsNotice(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("NewWholeOrderInform");
			if(element != null){
				param.setNewWholeOrderInform(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("SendWholeOrderInform");
			if(element != null){
				param.setSendWholeOrderInform(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("PaymnetWholeOrderInform");
			if(element != null){
				param.setPaymnetWholeOrderInform(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("AffirmWholeMoneyInform");
			if(element != null){
				param.setAffirmWholeMoneyInform(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("PickWholeOrderInform");
			if(element != null){
				param.setPickWholeOrderInform(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("OrderWholeAuditInform");
			if(element != null){
				param.setOrderWholeAuditInform(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("SmsNewWholeContext");
			if(element != null){
				param.setSmsNewWholeContext(element.getText());
			}
			
			element = (Element)root.selectSingleNode("SmsSendWholeContext");
			if(element != null){
				param.setSmsSendWholeContext(element.getText());
			}
			
			element = (Element)root.selectSingleNode("SmsPaymentWholeContext");
			if(element != null){
				param.setSmsPaymentWholeContext(element.getText());
			}
			
			element = (Element)root.selectSingleNode("SmsAffirmWholeContext");
			if(element != null){
				param.setSmsAffirmWholeContext(element.getText());
			}
			
			element = (Element)root.selectSingleNode("SmsPickWholeContext");
			if(element != null){
				param.setSmsPickWholeContext(element.getText());
			}
			
			element = (Element)root.selectSingleNode("SmsAuditWholeContext");
			if(element != null){
				param.setSmsAuditWholeContext(element.getText());
			}
			
			element = (Element)root.selectSingleNode("NewChainOrderInform");
			if(element != null){
				param.setNewChainOrderInform(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("PaymnetChainOrderInform");
			if(element != null){
				param.setPaymnetChainOrderInform(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("AffirmChainMoneyInform");
			if(element != null){
				param.setAffirmChainMoneyInform(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("BranchReceiveOrderInform");
			if(element != null){
				param.setBranchReceiveOrderInform(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("OrderChainAuditInform");
			if(element != null){
				param.setOrderChainAuditInform(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("SmsNewChainContext");
			if(element != null){
				param.setSmsNewChainContext(element.getText());
			}
			
			element = (Element)root.selectSingleNode("SmsPaymentChainContext");
			if(element != null){
				param.setSmsPaymentChainContext(element.getText());
			}
			
			element = (Element)root.selectSingleNode("SmsAffirmChainContext");
			if(element != null){
				param.setSmsAffirmChainContext(element.getText());
			}
			
			element = (Element)root.selectSingleNode("SmsPickChainContext");
			if(element != null){
				param.setSmsPickChainContext(element.getText());
			}
			
			element = (Element)root.selectSingleNode("SmsBranchChainContext");
			if(element != null){
				param.setSmsBranchChainContext(element.getText());
			}
			
			element = (Element)root.selectSingleNode("SmsAuditChainContext");
			if(element != null){
				param.setSmsAuditChainContext(element.getText());
			}
			
			element = (Element)root.selectSingleNode("StorehousePhone");
			if(element != null){
				param.setStorehousePhone(element.getText());
			}
			
			element = (Element)root.selectSingleNode("FinancePhone");
			if(element != null){
				param.setFinancePhone(element.getText());
			}
			
			element = (Element)root.selectSingleNode("CenterInvenoryRemoveWholeBook");
			if(element != null){
				param.setCenterInvenoryRemoveWholeBook(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("PurChaseAuditInform");
			if(element != null){
				param.setPurChaseAuditInform(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("PreSettlementInform");
			if(element != null){
				param.setPreSettlementInform(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("SmspurChaseAuditContext");
			if(element != null){
				param.setSmsPurChaseAuditContext(element.getText());
			}
			
			element = (Element)root.selectSingleNode("SmsPreSettlementContext");
			if(element != null){
				param.setSmsPreSettlementContext(element.getText());
			}
			
			element = (Element)root.selectSingleNode("CenterInvenoryIgnore");
			if(element != null){
				param.setCenterInvenoryIgnore(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("RequestShowProductingDate");
			if(element != null){
				param.setRequestShowProductingDate(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("RequestCenterInvenoryIgnore");
			if(element != null){
				param.setRequestCenterInvenoryIgnore(AppUtil.strToBool(element.getText()));
			} else{
				param.setRequestCenterInvenoryIgnore(false);
			}
			
			element = (Element)root.selectSingleNode("RequestStockoutOrder");
			if(element != null){
				param.setRequestStockoutOrder(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("UseLastWholePrice");
			if(element != null){
				param.setUseLastWholePrice(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("PrintTogetherByChain");
			if(element != null){
				param.setPrintTogetherByChain(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("PrintTogetherByWholeBook");
			if(element != null){
				param.setPrintTogetherByWholeBook(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("CreateWholesaleOrderByAudit");
			if(element != null){
				param.setCreateWholesaleOrderByAudit(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("ShowClientBalance");
			if(element != null){
				param.setShowClientBalance(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("WholesaleLoginSms");
			if(element != null){
				param.setWholesaleLoginSms(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("NotReturnWholesaleAudit");
			if(element != null){
				param.setNotReturnWholesaleAudit(AppUtil.strToBool(element.getText()));
			}
			element = (Element)root.selectSingleNode("WholesaleInventoryUnit");
			if(element != null){
				param.setWholesaleInventoryUnit(AppUtil.strToBool(element.getText()));
			}
			element = (Element)root.selectSingleNode("OrderNotAuditFirst");
			if(element != null){
				param.setOrderNotAuditFirst(AppUtil.strToBool(element.getText()));
			} else {
				param.setOrderNotAuditFirst(false);
			}
			element = (Element)root.selectSingleNode("ReceiveOrderPrintNeedAudit");
			if(element != null){
				param.setReceiveOrderPrintNeedAudit(AppUtil.strToBool(element.getText()));
			} else {
				param.setReceiveOrderPrintNeedAudit(false);
			}
			
			element = (Element)root.selectSingleNode("WholesalePriceNotBelowThenMin");
			if(element != null){
				param.setWholesalePriceNotBelowThenMin(AppUtil.strToBool(element.getText()));
			} else {
				param.setWholesalePriceNotBelowThenMin(false);
			}
			
			element = (Element)root.selectSingleNode("WholesaleSalePriceNotShow");
			if(element != null){
				param.setWholesaleSalePriceNotShow(AppUtil.strToBool(element.getText()));
			} else {
				param.setWholesaleSalePriceNotShow(false);
			}
			
			element = (Element)root.selectSingleNode("SingleTransferSendBranchCount");
			if(element != null){
				param.setSingleTransferSendBranchCount(Integer.parseInt(element.getText()));
			} else {
				param.setSingleTransferSendBranchCount(7);
			}
			
			element = (Element)root.selectSingleNode("RequestIngoreLastRequest");
			if(element != null){
				param.setRequestIngoreLastRequest(AppUtil.strToBool(element.getText()));
			} else {
				param.setRequestIngoreLastRequest(false);
			}
			element = (Element)root.selectSingleNode("WholesaleMatrixAddOns");
			if(element != null){
				param.setWholesaleMatrixAddOns(AppUtil.strToBool(element.getText()));
			} else {
				param.setWholesaleMatrixAddOns(false);
			}
			element = (Element)root.selectSingleNode("WholesaleBookValidDays");
			if(element != null){
				param.setWholesaleBookValidDays(Integer.parseInt(element.getText()));
			} else {
				param.setWholesaleBookValidDays(0);
			}
			element = (Element)root.selectSingleNode("TransferLockDay");
			if(element != null){
				param.setTransferLockDay(Integer.parseInt(element.getText()));
			} else {
				param.setTransferLockDay(0);
			}
			element = (Element)root.selectSingleNode("WholesaleLockDay");
			if(element != null){
				param.setWholesaleLockDay(Integer.parseInt(element.getText()));
			} else {
				param.setWholesaleLockDay(0);
			}
			
			element = (Element)root.selectSingleNode("EnableLockInventory");
			if(element != null){
				param.setEnableLockInventory(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("InOrderFromOutNotAllowEdit");
			if(element != null){
				param.setInOrderFromOutNotAllowEdit(AppUtil.strToBool(element.getText()));
			} else {
				param.setInOrderFromOutNotAllowEdit(false);
			}
			
			element = (Element)root.selectSingleNode("WholesaleReturnCreateByOrder");
			if(element != null){
				param.setWholesaleReturnCreateByOrder(AppUtil.strToBool(element.getText()));
			} else {
				param.setWholesaleReturnCreateByOrder(false);
			}
			
			element = (Element)root.selectSingleNode("RequestAutoGenerOutOrder");
			if(element != null){
				param.setRequestAutoGenerOutOrder(AppUtil.strToBool(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("RequestNotShowCenterInvetory");
			if(element != null){
				param.setRequestNotShowCenterInvetory(AppUtil.strToBool(element.getText()));
			} else {
				param.setRequestNotShowCenterInvetory(true);
				
			}
			
			element = (Element)root.selectSingleNode("CalDayParamThree");
			if(element != null){
				param.setCalDayParamThree(new Integer(element.getText()));
			}
			element = (Element)root.selectSingleNode("NotAllowChangeUnit");
			if(element != null){
				param.setNotAllowChangeUnit(AppUtil.strToBool(element.getText()));
			} else {
				param.setNotAllowChangeUnit(false);
			}
			element = (Element)root.selectSingleNode("CenterOutOrderGenerateBranchInOrder");
			if(element != null){
				param.setCenterOutOrderGenerateBranchInOrder(AppUtil.strToBool(element.getText()));
			} else {
				param.setCenterOutOrderGenerateBranchInOrder(false);
			}
			element = (Element)root.selectSingleNode("PurchaseUseBoxDeposit");
			if(element != null){
				param.setPurchaseUseBoxDeposit(AppUtil.strToBool(element.getText()));
			} else {
				param.setPurchaseUseBoxDeposit(false);
			}
			element = (Element)root.selectSingleNode("SelectOrderPosItemShowItemMatrix");
			if(element != null){
				param.setSelectOrderPosItemShowItemMatrix(AppUtil.strToBool(element.getText()));
			} else {
				param.setSelectOrderPosItemShowItemMatrix(false);
			}
			element = (Element)root.selectSingleNode("EnableInventoryLnDetailRate");
			if(element != null){
				param.setEnableInventoryLnDetailRate(AppUtil.strToBool(element.getText()));
			} else {
				param.setEnableInventoryLnDetailRate(false);
			}
			element = (Element)root.selectSingleNode("NotReturnChainAudit");
			if(element != null){
				param.setNotReturnChainAudit(AppUtil.strToBool(element.getText()));
			} else {
				param.setNotReturnChainAudit(false);
			}
			
			element = (Element)root.selectSingleNode("CalDayParamOneWholesale");
			if(element != null){
				param.setCalDayParamOneWholesale(Integer.parseInt(element.getText()));
			} else {
				param.setCalDayParamOneWholesale(param.getCalDayParamOne());
			}
			
			element = (Element)root.selectSingleNode("CalDayParamTwoWholesale");
			if(element != null){
				param.setCalDayParamTwoWholesale(Integer.parseInt(element.getText()));
			} else {
				param.setCalDayParamTwoWholesale(param.getCalDayParamTwo());
			}
			
			element = (Element)root.selectSingleNode("OutAmountTypeWholesale");
			if(element != null){
				param.setOutAmountTypeWholesale(Integer.parseInt(element.getText()));
			} else {
				param.setOutAmountTypeWholesale(param.getOutAmountType());
			}
			
			element = (Element)root.selectSingleNode("WholesaleOrderAmountLower");
			if(element != null){
				param.setWholesaleOrderAmountLower(Integer.parseInt(element.getText()));
			} else {
				param.setWholesaleOrderAmountLower(param.getRequestOrderAmountLower());
			}
			
			element = (Element)root.selectSingleNode("WholesaleOrderAmountUpper");
			if(element != null){
				param.setWholesaleOrderAmountUpper(Integer.parseInt(element.getText()));
			} else {
				param.setWholesaleOrderAmountUpper(param.getRequestOrderAmountUpper());
			}
			
			element = (Element)root.selectSingleNode("ShowAllPosItemWholesale");
			if(element != null){
				param.setShowAllPosItemWholesale(AppUtil.strToBool(element.getText()));
			} else {
				param.setShowAllPosItemWholesale(false);
			}
			element = (Element)root.selectSingleNode("TransferPriceAsProfitRate");
			if(element != null){
				param.setTransferPriceAsProfitRate(AppUtil.strToBool(element.getText()));
			} else {
				param.setTransferPriceAsProfitRate(false);
			}
			element = (Element)root.selectSingleNode("TransferInOrderEditBaseQty");
			if(element != null){
				param.setTransferInOrderEditBaseQty(AppUtil.strToBool(element.getText()));
			} else {
				param.setTransferInOrderEditBaseQty(false);
			}
			
			element = (Element)root.selectSingleNode("TransferPriceRounding");
			if(element != null){
				param.setTransferPriceRounding(AppUtil.strToBool(element.getText()));
			} else {
				param.setTransferPriceRounding(false);
			}
			element = (Element)root.selectSingleNode("TransferPriceRoundFrom");
			if(element != null){
				param.setTransferPriceRoundFrom(new BigDecimal(element.getText()));
			} else {
				param.setTransferPriceRoundFrom(BigDecimal.ZERO);
			}
			
			element = (Element)root.selectSingleNode("TransferPriceRoundType");
			if(element != null){
				param.setTransferPriceRoundType(element.getText());
			} else {
				param.setTransferPriceRoundType("");
			}
			element = (Element)root.selectSingleNode("TransferPriceRoundTo");
			if(element != null){
				param.setTransferPriceRoundTo(element.getText());
			} else {
				param.setTransferPriceRoundTo("");
			}
			element = (Element)root.selectSingleNode("WholesaleBookDefaultState");
			if(element != null){
				param.setWholesaleBookDefaultState(element.getText());
			}
			element = (Element)root.selectSingleNode("RequestNotOverCenterStock");
			if(element != null){
				param.setRequestNotOverCenterStock(AppUtil.strToBool(element.getText()));
			} else {
				param.setRequestNotOverCenterStock(false);
			}
			element = (Element)root.selectSingleNode("PadReceiveCanEditBasicQty");
			if(element != null){
				param.setPadReceiveCanEditBasicQty(AppUtil.strToBool(element.getText()));
			} else {
				param.setPadReceiveCanEditBasicQty(false);
			}
			element = (Element)root.selectSingleNode("OutOrderQtySubTareQty");
			if(element != null){
				param.setOutOrderQtySubTareQty(AppUtil.strToBool(element.getText()));
			} else {
				param.setOutOrderQtySubTareQty(false);
			}
			element = (Element)root.selectSingleNode("RequestShowCenterInventoryName");
			if(element != null){
				param.setRequestShowCenterInventoryName(AppUtil.strToBool(element.getText()));
			} else {
				param.setRequestShowCenterInventoryName(false);
			}
			element = (Element)root.selectSingleNode("EnableTransferInSubTareQty");
			if(element != null){
				param.setEnableTransferInSubTareQty(AppUtil.strToBool(element.getText()));
			} else {
				param.setEnableTransferInSubTareQty(false);
			}
			element = (Element)root.selectSingleNode("TranferInNeedOutSended");
			if(element != null){
				param.setTranferInNeedOutSended(AppUtil.strToBool(element.getText()));
			} else {
				param.setTranferInNeedOutSended(false);
			}
			
			element = (Element)root.selectSingleNode("EnableTransferPolicyQuantity");
			if(element != null){
				param.setEnableTransferPolicyQuantity(AppUtil.strToBool(element.getText()));
			} else {
				param.setEnableTransferPolicyQuantity(false);
			}
			
			element = (Element)root.selectSingleNode("EnableAssistUnitAsUseUnit");
			if(element != null){
				param.setEnableAssistUnitAsUseUnit(AppUtil.strToBool(element.getText()));
			} else {
				param.setEnableAssistUnitAsUseUnit(false);
			}
			element = (Element)root.selectSingleNode("CanNotEditAntiOrder");
			if(element != null){
				param.setCanNotEditAntiOrder(AppUtil.strToBool(element.getText()));
			} else {
				param.setCanNotEditAntiOrder(false);
			}
			
			element = (Element)root.selectSingleNode("WholesalePriceRounding");
			if(element != null){
				param.setWholesalePriceRounding(AppUtil.strToBool(element.getText()));
			} else {
				param.setWholesalePriceRounding(false);
			}
			element = (Element)root.selectSingleNode("WholesalePriceRoundFrom");
			if(element != null){
				param.setWholesalePriceRoundFrom(new BigDecimal(element.getText()));
			} else {
				param.setWholesalePriceRoundFrom(BigDecimal.ZERO);
			}
			
			element = (Element)root.selectSingleNode("WholesalePriceRoundType");
			if(element != null){
				param.setWholesalePriceRoundType(element.getText());
			} else {
				param.setWholesalePriceRoundType("");
			}
			element = (Element)root.selectSingleNode("WholesalePriceRoundTo");
			if(element != null){
				param.setWholesalePriceRoundTo(element.getText());
			} else {
				param.setWholesalePriceRoundTo("");
			}
			
			element = (Element)root.selectSingleNode("EnableRequestMoney");
			if(element != null){
				param.setEnableRequestMoney(AppUtil.strToBool(element.getText()));
			} else {
				param.setEnableRequestMoney(false);
			}
			
			element = (Element)root.selectSingleNode("RequestAndSaleRate");
			if(element != null){
				param.setRequestAndSaleRate(new BigDecimal(element.getText()));
			}
			
			element = (Element)root.selectSingleNode("EnableLowReceivePrice");
			if(element != null){
				param.setEnableLowReceivePrice(AppUtil.strToBool(element.getText()));
			} else {
				param.setEnableLowReceivePrice(false);
			}
			element = (Element)root.selectSingleNode("EnableRequestRefItem");
			if(element != null){
				param.setEnableRequestRefItem(AppUtil.strToBool(element.getText()));
			} else {
				param.setEnableRequestRefItem(false);
			}
			
			element = (Element)root.selectSingleNode("WholesalePriceAsProfitRate");
			if(element != null){
				param.setWholesalePriceAsProfitRate(AppUtil.strToBool(element.getText()));
			} else {
				param.setWholesalePriceAsProfitRate(false);
			}
			
			element = (Element)root.selectSingleNode("ForbidWholesaleAuditOverClientBalance");
			if(element != null){
				param.setForbidWholesaleAuditOverClientBalance(AppUtil.strToBool(element.getText()));
			} else {
				param.setForbidWholesaleAuditOverClientBalance(false);
			}
			element = (Element)root.selectSingleNode("CenterOutOrderGenerateBranchInOrderState");
			if(element != null){
				param.setCenterOutOrderGenerateBranchInOrderState(Integer.parseInt(element.getText()));
			} else {
				param.setCenterOutOrderGenerateBranchInOrderState(2);
			}
			element = (Element)root.selectSingleNode("CenterSendConfirmOnly");
			if(element != null){
				param.setCenterSendConfirmOnly(AppUtil.strToBool(element.getText()));
			} else {
				param.setCenterSendConfirmOnly(false);
			}
			
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		return param;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((affirmChainMoneyInform == null) ? 0 : affirmChainMoneyInform.hashCode());
		result = prime * result + ((affirmWholeMoneyInform == null) ? 0 : affirmWholeMoneyInform.hashCode());
		result = prime * result + ((autoChangeItemCostPrice == null) ? 0 : autoChangeItemCostPrice.hashCode());
		result = prime * result + ((branchReceiveOrderInform == null) ? 0 : branchReceiveOrderInform.hashCode());
		result = prime * result + ((calDayParamOne == null) ? 0 : calDayParamOne.hashCode());
		result = prime * result + ((calDayParamTwo == null) ? 0 : calDayParamTwo.hashCode());
		result = prime * result + ((centerAuditOutOrder == null) ? 0 : centerAuditOutOrder.hashCode());
		result = prime * result + ((centerInvenoryIgnore == null) ? 0 : centerInvenoryIgnore.hashCode());
		result = prime * result + ((centerInvenoryRemoveRequest == null) ? 0 : centerInvenoryRemoveRequest.hashCode());
		result = prime * result
				+ ((centerInvenoryRemoveWholeBook == null) ? 0 : centerInvenoryRemoveWholeBook.hashCode());
		result = prime * result + ((centerTransferInRefCost == null) ? 0 : centerTransferInRefCost.hashCode());
		result = prime * result
				+ ((centerpurchaseNotReceiveMore == null) ? 0 : centerpurchaseNotReceiveMore.hashCode());
		result = prime * result + ((financePhone == null) ? 0 : financePhone.hashCode());
		result = prime * result + ((newChainOrderInform == null) ? 0 : newChainOrderInform.hashCode());
		result = prime * result + ((newWholeOrderInform == null) ? 0 : newWholeOrderInform.hashCode());
		result = prime * result + ((orderChainAuditInform == null) ? 0 : orderChainAuditInform.hashCode());
		result = prime * result + ((orderWholeAuditInform == null) ? 0 : orderWholeAuditInform.hashCode());
		result = prime * result + ((outAmountType == null) ? 0 : outAmountType.hashCode());
		result = prime * result + ((outOrderPrintNeedAudit == null) ? 0 : outOrderPrintNeedAudit.hashCode());
		result = prime * result + ((outOrderSplitAmount == null) ? 0 : outOrderSplitAmount.hashCode());
		result = prime * result + ((paymnetChainOrderInform == null) ? 0 : paymnetChainOrderInform.hashCode());
		result = prime * result + ((paymnetWholeOrderInform == null) ? 0 : paymnetWholeOrderInform.hashCode());
		result = prime * result + ((pickWholeOrderInform == null) ? 0 : pickWholeOrderInform.hashCode());
		result = prime * result + ((preSettlementInform == null) ? 0 : preSettlementInform.hashCode());
		result = prime * result + ((printTogetherByChain == null) ? 0 : printTogetherByChain.hashCode());
		result = prime * result + ((printTogetherByWholeBook == null) ? 0 : printTogetherByWholeBook.hashCode());
		result = prime * result + ((purChaseAuditInform == null) ? 0 : purChaseAuditInform.hashCode());
		result = prime * result + ((purchaseOrderVaildDays == null) ? 0 : purchaseOrderVaildDays.hashCode());
		result = prime * result + ((requestCenterInvenoryIgnore == null) ? 0 : requestCenterInvenoryIgnore.hashCode());
		result = prime * result + ((requestOrderAmountLower == null) ? 0 : requestOrderAmountLower.hashCode());
		result = prime * result + ((requestOrderAmountUpper == null) ? 0 : requestOrderAmountUpper.hashCode());
		result = prime * result + ((requestOrderIntegerOnly == null) ? 0 : requestOrderIntegerOnly.hashCode());
		result = prime * result + ((requestOrderNotAllowAdd == null) ? 0 : requestOrderNotAllowAdd.hashCode());
		result = prime * result
				+ ((requestOrderNotAllowEditUnit == null) ? 0 : requestOrderNotAllowEditUnit.hashCode());
		result = prime * result + ((requestOrderValidDays == null) ? 0 : requestOrderValidDays.hashCode());
		result = prime * result + ((requestShowProductingDate == null) ? 0 : requestShowProductingDate.hashCode());
		result = prime * result + ((requestStockoutOrder == null) ? 0 : requestStockoutOrder.hashCode());
		result = prime * result + ((sendWholeOrderInform == null) ? 0 : sendWholeOrderInform.hashCode());
		result = prime * result + ((smsAffirmChainContext == null) ? 0 : smsAffirmChainContext.hashCode());
		result = prime * result + ((smsAffirmWholeContext == null) ? 0 : smsAffirmWholeContext.hashCode());
		result = prime * result + ((smsAuditChainContext == null) ? 0 : smsAuditChainContext.hashCode());
		result = prime * result + ((smsAuditWholeContext == null) ? 0 : smsAuditWholeContext.hashCode());
		result = prime * result + ((smsBranchChainContext == null) ? 0 : smsBranchChainContext.hashCode());
		result = prime * result + ((smsNewChainContext == null) ? 0 : smsNewChainContext.hashCode());
		result = prime * result + ((smsNewWholeContext == null) ? 0 : smsNewWholeContext.hashCode());
		result = prime * result + ((smsPaymentChainContext == null) ? 0 : smsPaymentChainContext.hashCode());
		result = prime * result + ((smsPaymentWholeContext == null) ? 0 : smsPaymentWholeContext.hashCode());
		result = prime * result + ((smsPickChainContext == null) ? 0 : smsPickChainContext.hashCode());
		result = prime * result + ((smsPickWholeContext == null) ? 0 : smsPickWholeContext.hashCode());
		result = prime * result + ((smsPreSettlementContext == null) ? 0 : smsPreSettlementContext.hashCode());
		result = prime * result + ((smsPurChaseAuditContext == null) ? 0 : smsPurChaseAuditContext.hashCode());
		result = prime * result + ((smsSendWholeContext == null) ? 0 : smsSendWholeContext.hashCode());
		result = prime * result + ((smsShipOrder == null) ? 0 : smsShipOrder.hashCode());
		result = prime * result + ((smsShipOrderContext == null) ? 0 : smsShipOrderContext.hashCode());
		result = prime * result + ((storehousePhone == null) ? 0 : storehousePhone.hashCode());
		result = prime * result + ((supportSmsNotice == null) ? 0 : supportSmsNotice.hashCode());
		result = prime * result + ((transferInNeedOut == null) ? 0 : transferInNeedOut.hashCode());
		result = prime * result + ((transferOutRefInPrice == null) ? 0 : transferOutRefInPrice.hashCode());
		result = prime * result + ((transferOutUseOnce == null) ? 0 : transferOutUseOnce.hashCode());
		result = prime * result + ((unEditTransferOutPrice == null) ? 0 : unEditTransferOutPrice.hashCode());
		result = prime * result + ((useLastWholePrice == null) ? 0 : useLastWholePrice.hashCode());
		result = prime * result
				+ ((wholesaleOrderPrintNeedAudit == null) ? 0 : wholesaleOrderPrintNeedAudit.hashCode());
		result = prime * result + ((wholesaleStockoutOrder == null) ? 0 : wholesaleStockoutOrder.hashCode());
		result = prime * result + ((forbidWholesaleAuditOverClientBalance == null) ? 0 : forbidWholesaleAuditOverClientBalance.hashCode());
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
		ChainDeliveryParam other = (ChainDeliveryParam) obj;
		if (affirmChainMoneyInform == null) {
			if (other.affirmChainMoneyInform != null)
				return false;
		} else if (!affirmChainMoneyInform.equals(other.affirmChainMoneyInform))
			return false;
		if (affirmWholeMoneyInform == null) {
			if (other.affirmWholeMoneyInform != null)
				return false;
		} else if (!affirmWholeMoneyInform.equals(other.affirmWholeMoneyInform))
			return false;
		if (autoChangeItemCostPrice == null) {
			if (other.autoChangeItemCostPrice != null)
				return false;
		} else if (!autoChangeItemCostPrice.equals(other.autoChangeItemCostPrice))
			return false;
		if (branchReceiveOrderInform == null) {
			if (other.branchReceiveOrderInform != null)
				return false;
		} else if (!branchReceiveOrderInform.equals(other.branchReceiveOrderInform))
			return false;
		if (calDayParamOne == null) {
			if (other.calDayParamOne != null)
				return false;
		} else if (!calDayParamOne.equals(other.calDayParamOne))
			return false;
		if (calDayParamTwo == null) {
			if (other.calDayParamTwo != null)
				return false;
		} else if (!calDayParamTwo.equals(other.calDayParamTwo))
			return false;
		if (centerAuditOutOrder == null) {
			if (other.centerAuditOutOrder != null)
				return false;
		} else if (!centerAuditOutOrder.equals(other.centerAuditOutOrder))
			return false;
		if (centerInvenoryIgnore == null) {
			if (other.centerInvenoryIgnore != null)
				return false;
		} else if (!centerInvenoryIgnore.equals(other.centerInvenoryIgnore))
			return false;
		if (centerInvenoryRemoveRequest == null) {
			if (other.centerInvenoryRemoveRequest != null)
				return false;
		} else if (!centerInvenoryRemoveRequest.equals(other.centerInvenoryRemoveRequest))
			return false;
		if (centerInvenoryRemoveWholeBook == null) {
			if (other.centerInvenoryRemoveWholeBook != null)
				return false;
		} else if (!centerInvenoryRemoveWholeBook.equals(other.centerInvenoryRemoveWholeBook))
			return false;
		if (centerTransferInRefCost == null) {
			if (other.centerTransferInRefCost != null)
				return false;
		} else if (!centerTransferInRefCost.equals(other.centerTransferInRefCost))
			return false;
		if (centerpurchaseNotReceiveMore == null) {
			if (other.centerpurchaseNotReceiveMore != null)
				return false;
		} else if (!centerpurchaseNotReceiveMore.equals(other.centerpurchaseNotReceiveMore))
			return false;
		if (financePhone == null) {
			if (other.financePhone != null)
				return false;
		} else if (!financePhone.equals(other.financePhone))
			return false;
		if (newChainOrderInform == null) {
			if (other.newChainOrderInform != null)
				return false;
		} else if (!newChainOrderInform.equals(other.newChainOrderInform))
			return false;
		if (newWholeOrderInform == null) {
			if (other.newWholeOrderInform != null)
				return false;
		} else if (!newWholeOrderInform.equals(other.newWholeOrderInform))
			return false;
		if (orderChainAuditInform == null) {
			if (other.orderChainAuditInform != null)
				return false;
		} else if (!orderChainAuditInform.equals(other.orderChainAuditInform))
			return false;
		if (orderWholeAuditInform == null) {
			if (other.orderWholeAuditInform != null)
				return false;
		} else if (!orderWholeAuditInform.equals(other.orderWholeAuditInform))
			return false;
		if (outAmountType == null) {
			if (other.outAmountType != null)
				return false;
		} else if (!outAmountType.equals(other.outAmountType))
			return false;
		if (outOrderPrintNeedAudit == null) {
			if (other.outOrderPrintNeedAudit != null)
				return false;
		} else if (!outOrderPrintNeedAudit.equals(other.outOrderPrintNeedAudit))
			return false;
		if (outOrderSplitAmount == null) {
			if (other.outOrderSplitAmount != null)
				return false;
		} else if (!outOrderSplitAmount.equals(other.outOrderSplitAmount))
			return false;
		if (paymnetChainOrderInform == null) {
			if (other.paymnetChainOrderInform != null)
				return false;
		} else if (!paymnetChainOrderInform.equals(other.paymnetChainOrderInform))
			return false;
		if (paymnetWholeOrderInform == null) {
			if (other.paymnetWholeOrderInform != null)
				return false;
		} else if (!paymnetWholeOrderInform.equals(other.paymnetWholeOrderInform))
			return false;
		if (pickWholeOrderInform == null) {
			if (other.pickWholeOrderInform != null)
				return false;
		} else if (!pickWholeOrderInform.equals(other.pickWholeOrderInform))
			return false;
		if (preSettlementInform == null) {
			if (other.preSettlementInform != null)
				return false;
		} else if (!preSettlementInform.equals(other.preSettlementInform))
			return false;
		if (printTogetherByChain == null) {
			if (other.printTogetherByChain != null)
				return false;
		} else if (!printTogetherByChain.equals(other.printTogetherByChain))
			return false;
		if (printTogetherByWholeBook == null) {
			if (other.printTogetherByWholeBook != null)
				return false;
		} else if (!printTogetherByWholeBook.equals(other.printTogetherByWholeBook))
			return false;
		if (purChaseAuditInform == null) {
			if (other.purChaseAuditInform != null)
				return false;
		} else if (!purChaseAuditInform.equals(other.purChaseAuditInform))
			return false;
		if (purchaseOrderVaildDays == null) {
			if (other.purchaseOrderVaildDays != null)
				return false;
		} else if (!purchaseOrderVaildDays.equals(other.purchaseOrderVaildDays))
			return false;
		if (requestCenterInvenoryIgnore == null) {
			if (other.requestCenterInvenoryIgnore != null)
				return false;
		} else if (!requestCenterInvenoryIgnore.equals(other.requestCenterInvenoryIgnore))
			return false;
		if (requestOrderAmountLower == null) {
			if (other.requestOrderAmountLower != null)
				return false;
		} else if (!requestOrderAmountLower.equals(other.requestOrderAmountLower))
			return false;
		if (requestOrderAmountUpper == null) {
			if (other.requestOrderAmountUpper != null)
				return false;
		} else if (!requestOrderAmountUpper.equals(other.requestOrderAmountUpper))
			return false;
		if (requestOrderIntegerOnly == null) {
			if (other.requestOrderIntegerOnly != null)
				return false;
		} else if (!requestOrderIntegerOnly.equals(other.requestOrderIntegerOnly))
			return false;
		if (requestOrderNotAllowAdd == null) {
			if (other.requestOrderNotAllowAdd != null)
				return false;
		} else if (!requestOrderNotAllowAdd.equals(other.requestOrderNotAllowAdd))
			return false;
		if (requestOrderNotAllowEditUnit == null) {
			if (other.requestOrderNotAllowEditUnit != null)
				return false;
		} else if (!requestOrderNotAllowEditUnit.equals(other.requestOrderNotAllowEditUnit))
			return false;
		if (requestOrderValidDays == null) {
			if (other.requestOrderValidDays != null)
				return false;
		} else if (!requestOrderValidDays.equals(other.requestOrderValidDays))
			return false;
		if (requestShowProductingDate == null) {
			if (other.requestShowProductingDate != null)
				return false;
		} else if (!requestShowProductingDate.equals(other.requestShowProductingDate))
			return false;
		if (requestStockoutOrder == null) {
			if (other.requestStockoutOrder != null)
				return false;
		} else if (!requestStockoutOrder.equals(other.requestStockoutOrder))
			return false;
		if (sendWholeOrderInform == null) {
			if (other.sendWholeOrderInform != null)
				return false;
		} else if (!sendWholeOrderInform.equals(other.sendWholeOrderInform))
			return false;
		if (smsAffirmChainContext == null) {
			if (other.smsAffirmChainContext != null)
				return false;
		} else if (!smsAffirmChainContext.equals(other.smsAffirmChainContext))
			return false;
		if (smsAffirmWholeContext == null) {
			if (other.smsAffirmWholeContext != null)
				return false;
		} else if (!smsAffirmWholeContext.equals(other.smsAffirmWholeContext))
			return false;
		if (smsAuditChainContext == null) {
			if (other.smsAuditChainContext != null)
				return false;
		} else if (!smsAuditChainContext.equals(other.smsAuditChainContext))
			return false;
		if (smsAuditWholeContext == null) {
			if (other.smsAuditWholeContext != null)
				return false;
		} else if (!smsAuditWholeContext.equals(other.smsAuditWholeContext))
			return false;
		if (smsBranchChainContext == null) {
			if (other.smsBranchChainContext != null)
				return false;
		} else if (!smsBranchChainContext.equals(other.smsBranchChainContext))
			return false;
		if (smsNewChainContext == null) {
			if (other.smsNewChainContext != null)
				return false;
		} else if (!smsNewChainContext.equals(other.smsNewChainContext))
			return false;
		if (smsNewWholeContext == null) {
			if (other.smsNewWholeContext != null)
				return false;
		} else if (!smsNewWholeContext.equals(other.smsNewWholeContext))
			return false;
		if (smsPaymentChainContext == null) {
			if (other.smsPaymentChainContext != null)
				return false;
		} else if (!smsPaymentChainContext.equals(other.smsPaymentChainContext))
			return false;
		if (smsPaymentWholeContext == null) {
			if (other.smsPaymentWholeContext != null)
				return false;
		} else if (!smsPaymentWholeContext.equals(other.smsPaymentWholeContext))
			return false;
		if (smsPickChainContext == null) {
			if (other.smsPickChainContext != null)
				return false;
		} else if (!smsPickChainContext.equals(other.smsPickChainContext))
			return false;
		if (smsPickWholeContext == null) {
			if (other.smsPickWholeContext != null)
				return false;
		} else if (!smsPickWholeContext.equals(other.smsPickWholeContext))
			return false;
		if (smsPreSettlementContext == null) {
			if (other.smsPreSettlementContext != null)
				return false;
		} else if (!smsPreSettlementContext.equals(other.smsPreSettlementContext))
			return false;
		if (smsPurChaseAuditContext == null) {
			if (other.smsPurChaseAuditContext != null)
				return false;
		} else if (!smsPurChaseAuditContext.equals(other.smsPurChaseAuditContext))
			return false;
		if (smsSendWholeContext == null) {
			if (other.smsSendWholeContext != null)
				return false;
		} else if (!smsSendWholeContext.equals(other.smsSendWholeContext))
			return false;
		if (smsShipOrder == null) {
			if (other.smsShipOrder != null)
				return false;
		} else if (!smsShipOrder.equals(other.smsShipOrder))
			return false;
		if (smsShipOrderContext == null) {
			if (other.smsShipOrderContext != null)
				return false;
		} else if (!smsShipOrderContext.equals(other.smsShipOrderContext))
			return false;
		if (storehousePhone == null) {
			if (other.storehousePhone != null)
				return false;
		} else if (!storehousePhone.equals(other.storehousePhone))
			return false;
		if (supportSmsNotice == null) {
			if (other.supportSmsNotice != null)
				return false;
		} else if (!supportSmsNotice.equals(other.supportSmsNotice))
			return false;
		if (transferInNeedOut == null) {
			if (other.transferInNeedOut != null)
				return false;
		} else if (!transferInNeedOut.equals(other.transferInNeedOut))
			return false;
		if (transferOutRefInPrice == null) {
			if (other.transferOutRefInPrice != null)
				return false;
		} else if (!transferOutRefInPrice.equals(other.transferOutRefInPrice))
			return false;
		if (transferOutUseOnce == null) {
			if (other.transferOutUseOnce != null)
				return false;
		} else if (!transferOutUseOnce.equals(other.transferOutUseOnce))
			return false;
		if (unEditTransferOutPrice == null) {
			if (other.unEditTransferOutPrice != null)
				return false;
		} else if (!unEditTransferOutPrice.equals(other.unEditTransferOutPrice))
			return false;
		if (useLastWholePrice == null) {
			if (other.useLastWholePrice != null)
				return false;
		} else if (!useLastWholePrice.equals(other.useLastWholePrice))
			return false;
		if (wholesaleOrderPrintNeedAudit == null) {
			if (other.wholesaleOrderPrintNeedAudit != null)
				return false;
		} else if (!wholesaleOrderPrintNeedAudit.equals(other.wholesaleOrderPrintNeedAudit))
			return false;
		if (wholesaleStockoutOrder == null) {
			if (other.wholesaleStockoutOrder != null)
				return false;
		} else if (!wholesaleStockoutOrder.equals(other.wholesaleStockoutOrder))
			return false;
		if (forbidWholesaleAuditOverClientBalance == null) {
			if (other.forbidWholesaleAuditOverClientBalance != null)
				return false;
		} else if (!forbidWholesaleAuditOverClientBalance.equals(other.forbidWholesaleAuditOverClientBalance))
			return false;
		return true;
	}
	
	public BigDecimal roundValue(BigDecimal value){
		BigDecimal newValue = value;
		if(transferPriceRounding == null || !transferPriceRounding){
			return newValue;
		}
		if(transferPriceRoundFrom != null && newValue.compareTo(transferPriceRoundFrom) < 0){
			newValue = newValue.setScale(2, BigDecimal.ROUND_HALF_UP);
			return newValue;
		}
		
		int scale = 2;
		if(transferPriceRoundTo.equals(AppConstants.MONEY_SCALE_TYPE_YUAN)){
			scale = 0;
		} else 	if(transferPriceRoundTo.equals(AppConstants.MONEY_SCALE_TYPE_JIAO)){
			scale = 1;
		} else 	if(transferPriceRoundTo.equals(AppConstants.MONEY_SCALE_TYPE_FEN)){
			scale = 2;
		}
		if(transferPriceRoundType.equals(AppConstants.ROUND_TYPE_HALF)){
			newValue = newValue.setScale(scale, BigDecimal.ROUND_HALF_UP);
		} else if(transferPriceRoundType.equals(AppConstants.ROUND_TYPE_OFF)){
			newValue = newValue.setScale(scale, BigDecimal.ROUND_DOWN);
		} else if(transferPriceRoundType.equals(AppConstants.ROUND_TYPE_TO)){
			newValue = newValue.setScale(scale, BigDecimal.ROUND_UP);
		} else {
			newValue = newValue.setScale(scale, BigDecimal.ROUND_HALF_UP);
		}
		return newValue;
	}
	
	
	public BigDecimal roundWholesaleValue(BigDecimal value){
		BigDecimal newValue = value;
		if(wholesalePriceRounding == null || !wholesalePriceRounding){
			newValue = newValue.setScale(2, BigDecimal.ROUND_HALF_UP);
			return newValue;
		}
		if(wholesalePriceRoundFrom != null && newValue.compareTo(wholesalePriceRoundFrom) < 0){
			newValue = newValue.setScale(2, BigDecimal.ROUND_HALF_UP);
			return newValue;
		}
		
		int scale = 2;
		if(wholesalePriceRoundTo.equals(AppConstants.MONEY_SCALE_TYPE_YUAN)){
			scale = 0;
		} else 	if(wholesalePriceRoundTo.equals(AppConstants.MONEY_SCALE_TYPE_JIAO)){
			scale = 1;
		} else 	if(wholesalePriceRoundTo.equals(AppConstants.MONEY_SCALE_TYPE_FEN)){
			scale = 2;
		}
		if(wholesalePriceRoundType.equals(AppConstants.ROUND_TYPE_HALF)){
			newValue = newValue.setScale(scale, BigDecimal.ROUND_HALF_UP);
		} else if(wholesalePriceRoundType.equals(AppConstants.ROUND_TYPE_OFF)){
			newValue = newValue.setScale(scale, BigDecimal.ROUND_DOWN);
		} else if(wholesalePriceRoundType.equals(AppConstants.ROUND_TYPE_TO)){
			newValue = newValue.setScale(scale, BigDecimal.ROUND_UP);
		} else {
			newValue = newValue.setScale(scale, BigDecimal.ROUND_HALF_UP);
		}
		return newValue;
	}
	
}
