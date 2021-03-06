package com.nhsoft.module.report.model;

import javax.persistence.*;


/**
 * PolicyDiscountDetail generated by hbm2java
 */
@Entity
public class PolicyDiscountDetail implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2023492084255678977L;
	@EmbeddedId
	private PolicyDiscountDetailId id;
	private Integer itemNum;
	private String policyDiscountDetailMemo;
	
	//临时属性
	@Transient
	private PosItem posItem;
	//查询用属性
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="policyDiscountNo", insertable=false, updatable=false)
	private PolicyDiscount policyDiscount;

	public PosItem getPosItem() {
		return posItem;
	}

	public void setPosItem(PosItem posItem) {
		this.posItem = posItem;
	}

	public PolicyDiscountDetail() {
	}

	public PolicyDiscountDetailId getId() {
		return this.id;
	}

	public void setId(PolicyDiscountDetailId id) {
		this.id = id;
	}

	public Integer getItemNum() {
		return this.itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getPolicyDiscountDetailMemo() {
		return this.policyDiscountDetailMemo;
	}

	public void setPolicyDiscountDetailMemo(String policyDiscountDetailMemo) {
		this.policyDiscountDetailMemo = policyDiscountDetailMemo;
	}

	public PolicyDiscount getPolicyDiscount() {
		return policyDiscount;
	}

	public void setPolicyDiscount(PolicyDiscount policyDiscount) {
		this.policyDiscount = policyDiscount;
	}

}
