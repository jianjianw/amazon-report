package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PolicyPosItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4754232900642686866L;
	private String itemCategoryCode; // 商品类别代码
	private String itemCategoryName; // 商品类别名称
	private String itemCode; // 商品代码
	private String itemName; // 商品名称
	private String itemSpec; // 规格
	private String itemUnit; // 计量单位
	private String policyType; // 促销类型
	private String policyNo; // 单据号
	private String policyAppliedBranch; // 应用分店
	private BigDecimal policyPrice; // 促销价
	private BigDecimal policyStdPrice; // 零售价
	private Date dateFrom; // 开始日期
	private Date dateTo; // 结束日期
	private Date timeFrom; // 开始时间
	private Date timeTo; // 结束时间
	private String effectiveDate; // 生效日期
	private BigDecimal billLimmit = BigDecimal.ZERO; // 每单限量
	private String policyCreator; // 制单人
	private String policyAuditor; // 审核人
	private Integer itemNum;
	private Integer itemGradeNum;
	private String itemGradeName;
	private Boolean cardOnly;//是否只支持会员
	
	public Boolean getCardOnly() {
		return cardOnly;
	}
	
	public void setCardOnly(Boolean cardOnly) {
		this.cardOnly = cardOnly;
	}
	
	public Integer getItemGradeNum() {
		return itemGradeNum;
	}

	public void setItemGradeNum(Integer itemGradeNum) {
		this.itemGradeNum = itemGradeNum;
	}

	public String getItemCategoryCode() {
		return itemCategoryCode;
	}

	public void setItemCategoryCode(String itemCategoryCode) {
		this.itemCategoryCode = itemCategoryCode;
	}

	public String getItemCategoryName() {
		return itemCategoryName;
	}

	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPolicyAppliedBranch() {
		return policyAppliedBranch;
	}

	public void setPolicyAppliedBranch(String policyAppliedBranch) {
		this.policyAppliedBranch = policyAppliedBranch;
	}

	public BigDecimal getPolicyPrice() {
		return policyPrice;
	}

	public void setPolicyPrice(BigDecimal policyPrice) {
		this.policyPrice = policyPrice;
	}

	public BigDecimal getPolicyStdPrice() {
		return policyStdPrice;
	}

	public void setPolicyStdPrice(BigDecimal policyStdPrice) {
		this.policyStdPrice = policyStdPrice;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Date getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(Date timeFrom) {
		this.timeFrom = timeFrom;
	}

	public Date getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(Date timeTo) {
		this.timeTo = timeTo;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public BigDecimal getBillLimmit() {
		return billLimmit;
	}

	public void setBillLimmit(BigDecimal billLimmit) {
		this.billLimmit = billLimmit;
	}

	public String getPolicyCreator() {
		return policyCreator;
	}

	public void setPolicyCreator(String policyCreator) {
		this.policyCreator = policyCreator;
	}

	public String getPolicyAuditor() {
		return policyAuditor;
	}

	public void setPolicyAuditor(String policyAuditor) {
		this.policyAuditor = policyAuditor;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemGradeName() {
		return itemGradeName;
	}

	public void setItemGradeName(String itemGradeName) {
		this.itemGradeName = itemGradeName;
	}

}
