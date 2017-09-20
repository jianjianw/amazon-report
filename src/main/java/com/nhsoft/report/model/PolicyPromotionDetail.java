package com.nhsoft.report.model;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * PolicyPromotionDetail generated by hbm2java
 */
public class PolicyPromotionDetail implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4603372850763326011L;
	private PolicyPromotionDetailId id;
	private Integer itemNum;
	private BigDecimal policyPromotionDetailStdPrice;
	private BigDecimal policyPromotionDetailSpecialPrice;
	private BigDecimal policyPromotionDetailBillLimit;
	private BigDecimal policyPromotionDetailPolicyLimit;
	private BigDecimal policyPromotionDetailCost;
	private String policyPromotionDetailMemo;
	private Integer itemGradeNum;
	private String policyPromotionDetailLotNum;
	
	//临时属性
	private PosItem posItem;

	public PolicyPromotionDetail() {
	}

	public String getPolicyPromotionDetailLotNum() {
		return policyPromotionDetailLotNum;
	}

	public void setPolicyPromotionDetailLotNum(String policyPromotionDetailLotNum) {
		this.policyPromotionDetailLotNum = policyPromotionDetailLotNum;
	}

	public Integer getItemGradeNum() {
		return itemGradeNum;
	}

	public void setItemGradeNum(Integer itemGradeNum) {
		this.itemGradeNum = itemGradeNum;
	}

	public PolicyPromotionDetailId getId() {
		return this.id;
	}

	public void setId(PolicyPromotionDetailId id) {
		this.id = id;
	}

	public BigDecimal getPolicyPromotionDetailStdPrice() {
		return this.policyPromotionDetailStdPrice;
	}

	public void setPolicyPromotionDetailStdPrice(
			BigDecimal policyPromotionDetailStdPrice) {
		this.policyPromotionDetailStdPrice = policyPromotionDetailStdPrice;
	}

	public BigDecimal getPolicyPromotionDetailSpecialPrice() {
		return this.policyPromotionDetailSpecialPrice;
	}

	public void setPolicyPromotionDetailSpecialPrice(
			BigDecimal policyPromotionDetailSpecialPrice) {
		this.policyPromotionDetailSpecialPrice = policyPromotionDetailSpecialPrice;
	}

	public BigDecimal getPolicyPromotionDetailBillLimit() {
		return this.policyPromotionDetailBillLimit;
	}

	public void setPolicyPromotionDetailBillLimit(
			BigDecimal policyPromotionDetailBillLimit) {
		this.policyPromotionDetailBillLimit = policyPromotionDetailBillLimit;
	}

	public BigDecimal getPolicyPromotionDetailPolicyLimit() {
		return this.policyPromotionDetailPolicyLimit;
	}

	public void setPolicyPromotionDetailPolicyLimit(
			BigDecimal policyPromotionDetailPolicyLimit) {
		this.policyPromotionDetailPolicyLimit = policyPromotionDetailPolicyLimit;
	}

	public BigDecimal getPolicyPromotionDetailCost() {
		return this.policyPromotionDetailCost;
	}

	public void setPolicyPromotionDetailCost(
			BigDecimal policyPromotionDetailCost) {
		this.policyPromotionDetailCost = policyPromotionDetailCost;
	}

	public String getPolicyPromotionDetailMemo() {
		return this.policyPromotionDetailMemo;
	}

	public void setPolicyPromotionDetailMemo(String policyPromotionDetailMemo) {
		this.policyPromotionDetailMemo = policyPromotionDetailMemo;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public PosItem getPosItem() {
		return posItem;
	}

	public void setPosItem(PosItem posItem) {
		this.posItem = posItem;
	}
	
	public static PolicyPromotionDetail get(List<PolicyPromotionDetail> policyPromotionDetails, Integer itemNum, String policyPromotionDetailLotNum){
		for(int i = 0;i < policyPromotionDetails.size();i++){
			PolicyPromotionDetail policyPromotionDetail = policyPromotionDetails.get(i);
			if(policyPromotionDetail.getItemNum().equals(itemNum) 
					&& policyPromotionDetail.getPolicyPromotionDetailLotNum().equals(policyPromotionDetailLotNum)){
				return policyPromotionDetail;
			}
		}
		return null;
	}
	
	public static List<PolicyPromotionDetail> find(List<PolicyPromotionDetail> policyPromotionDetails, Integer itemNum){
		List<PolicyPromotionDetail> list = new ArrayList<PolicyPromotionDetail>();
		for(int i = 0;i < policyPromotionDetails.size();i++){
			PolicyPromotionDetail policyPromotionDetail = policyPromotionDetails.get(i);
			if(policyPromotionDetail.getItemNum().equals(itemNum)){
				list.add(policyPromotionDetail);
			}
		}
		return list;
	}
	
	public static List<PolicyPromotionDetail> find(List<PolicyPromotionDetail> policyPromotionDetails, String policyPromotionNo){
		List<PolicyPromotionDetail> list = new ArrayList<PolicyPromotionDetail>();
		for(int i = 0;i < policyPromotionDetails.size();i++){
			PolicyPromotionDetail policyPromotionDetail = policyPromotionDetails.get(i);
			if(policyPromotionDetail.getId().getPolicyPromotionNo().equals(policyPromotionNo)){
				list.add(policyPromotionDetail);
			}
		}
		return list;
	}

}
