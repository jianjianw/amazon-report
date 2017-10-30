package com.nhsoft.module.report.model;

import com.nhsoft.module.report.query.State;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * WholesaleReturn generated by hbm2java
 */
@Entity
public class WholesaleReturn implements java.io.Serializable {

	private static final long serialVersionUID = -8504069183670091595L;
	@Id
	private String wholesaleReturnFid;
	private String clientFid;
	@Transient
	private String shipOrderFid;
	private String wholesaleOrderFid;
	private String systemBookCode;
	private Integer branchNum;
	private Integer storehouseNum;
	private Date wholesaleReturnDate;
	private Date wholesaleReturnPaymentDate;
	private String wholesaleReturnSeller;
	@Embedded
	@AttributeOverrides( {
		 			@AttributeOverride(name="stateCode", column = @Column(name="wholesaleReturnStateCode")), 
		@AttributeOverride(name="stateName", column = @Column(name="wholesaleReturnStateName")) } )
	private State state;
	private String wholesaleReturnCreator;
	private Date wholesaleReturnCreateTime;
	private String wholesaleReturnAuditor;
	private Date wholesaleReturnAuditTime;
	private String wholesaleReturnMemo;
	private BigDecimal wholesaleReturnCommission;
	private BigDecimal wholesaleReturnTotalMoney;
	private BigDecimal wholesaleReturnDiscountMoney;
	private BigDecimal wholesaleReturnDueMoney;
	private BigDecimal wholesaleReturnPaidMoney;
	private Date wholesaleReturnLastestPaymentDate;
	private String wholesaleReturnUuid;
	private Boolean wholesaleReturnTransferFlag;
	private Integer wholesaleReturnPrintCount;
	private Integer regionNum;
	private Boolean wholesaleReturnRepealFlag;
	private String wholesaleReturnBillNo;
	private Boolean wholesaleReturnAntiFlag;
	private String wholesaleReturnAuditBizday;
	@OneToMany
	@Fetch(FetchMode.SUBSELECT)
	@JoinColumn(name = "wholesaleReturnFid", updatable=false, insertable=false)
	private List<WholesaleReturnDetail> wholesaleReturnDetails = new ArrayList<WholesaleReturnDetail>();
	
	//临时属性
	@Transient
	private AppUser appUser;
	@Transient
	private Boolean tempAudit;
	
    public WholesaleReturn() {
	}

	public Boolean getTempAudit() {
		return tempAudit;
	}

	public void setTempAudit(Boolean tempAudit) {
		this.tempAudit = tempAudit;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public String getWholesaleReturnAuditBizday() {
		return wholesaleReturnAuditBizday;
	}

	public void setWholesaleReturnAuditBizday(String wholesaleReturnAuditBizday) {
		this.wholesaleReturnAuditBizday = wholesaleReturnAuditBizday;
	}

	public Boolean getWholesaleReturnAntiFlag() {
		return wholesaleReturnAntiFlag;
	}

	public void setWholesaleReturnAntiFlag(Boolean wholesaleReturnAntiFlag) {
		this.wholesaleReturnAntiFlag = wholesaleReturnAntiFlag;
	}

	public Boolean getWholesaleReturnRepealFlag() {
		return wholesaleReturnRepealFlag;
	}

	public void setWholesaleReturnRepealFlag(Boolean wholesaleReturnRepealFlag) {
		this.wholesaleReturnRepealFlag = wholesaleReturnRepealFlag;
	}

	public String getWholesaleReturnBillNo() {
		return wholesaleReturnBillNo;
	}

	public void setWholesaleReturnBillNo(String wholesaleReturnBillNo) {
		this.wholesaleReturnBillNo = wholesaleReturnBillNo;
	}

	public Integer getRegionNum() {
		return regionNum;
	}

	public void setRegionNum(Integer regionNum) {
		this.regionNum = regionNum;
	}

	public String getWholesaleReturnFid() {
		return this.wholesaleReturnFid;
	}

	public void setWholesaleReturnFid(String wholesaleReturnFid) {
		this.wholesaleReturnFid = wholesaleReturnFid;
	}

	public String getWholesaleOrderFid() {
		return this.wholesaleOrderFid;
	}

	public void setWholesaleOrderFid(String wholesaleOrderFid) {
		this.wholesaleOrderFid = wholesaleOrderFid;
	}

	public String getSystemBookCode() {
		return this.systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public Integer getBranchNum() {
		return this.branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Integer getStorehouseNum() {
		return this.storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public Date getWholesaleReturnDate() {
		return this.wholesaleReturnDate;
	}

	public void setWholesaleReturnDate(Date wholesaleReturnDate) {
		this.wholesaleReturnDate = wholesaleReturnDate;
	}

	public Date getWholesaleReturnPaymentDate() {
		return this.wholesaleReturnPaymentDate;
	}

	public void setWholesaleReturnPaymentDate(Date wholesaleReturnPaymentDate) {
		this.wholesaleReturnPaymentDate = wholesaleReturnPaymentDate;
	}

	public String getWholesaleReturnSeller() {
		return this.wholesaleReturnSeller;
	}

	public void setWholesaleReturnSeller(String wholesaleReturnSeller) {
		this.wholesaleReturnSeller = wholesaleReturnSeller;
	}

	public String getWholesaleReturnCreator() {
		return this.wholesaleReturnCreator;
	}

	public void setWholesaleReturnCreator(String wholesaleReturnCreator) {
		this.wholesaleReturnCreator = wholesaleReturnCreator;
	}

	public Date getWholesaleReturnCreateTime() {
		return this.wholesaleReturnCreateTime;
	}

	public void setWholesaleReturnCreateTime(Date wholesaleReturnCreateTime) {
		this.wholesaleReturnCreateTime = wholesaleReturnCreateTime;
	}

	public String getWholesaleReturnAuditor() {
		return this.wholesaleReturnAuditor;
	}

	public void setWholesaleReturnAuditor(String wholesaleReturnAuditor) {
		this.wholesaleReturnAuditor = wholesaleReturnAuditor;
	}

	public Date getWholesaleReturnAuditTime() {
		return this.wholesaleReturnAuditTime;
	}

	public void setWholesaleReturnAuditTime(Date wholesaleReturnAuditTime) {
		this.wholesaleReturnAuditTime = wholesaleReturnAuditTime;

	}

	public String getWholesaleReturnMemo() {
		return this.wholesaleReturnMemo;
	}

	public void setWholesaleReturnMemo(String wholesaleReturnMemo) {
		this.wholesaleReturnMemo = wholesaleReturnMemo;
	}

	public BigDecimal getWholesaleReturnCommission() {
		return this.wholesaleReturnCommission;
	}

	public void setWholesaleReturnCommission(
			BigDecimal wholesaleReturnCommission) {
		this.wholesaleReturnCommission = wholesaleReturnCommission;
	}

	public BigDecimal getWholesaleReturnTotalMoney() {
		return this.wholesaleReturnTotalMoney;
	}

	public void setWholesaleReturnTotalMoney(
			BigDecimal wholesaleReturnTotalMoney) {
		this.wholesaleReturnTotalMoney = wholesaleReturnTotalMoney;
	}

	public BigDecimal getWholesaleReturnDiscountMoney() {
		return this.wholesaleReturnDiscountMoney;
	}

	public void setWholesaleReturnDiscountMoney(
			BigDecimal wholesaleReturnDiscountMoney) {
		if(wholesaleReturnDiscountMoney != null){
			wholesaleReturnDiscountMoney = wholesaleReturnDiscountMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		this.wholesaleReturnDiscountMoney = wholesaleReturnDiscountMoney;
	}

	public BigDecimal getWholesaleReturnDueMoney() {
		return this.wholesaleReturnDueMoney;
	}

	public void setWholesaleReturnDueMoney(BigDecimal wholesaleReturnDueMoney) {
		
		if(wholesaleReturnDueMoney != null){
			wholesaleReturnDueMoney = wholesaleReturnDueMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		this.wholesaleReturnDueMoney = wholesaleReturnDueMoney;
	}

	public BigDecimal getWholesaleReturnPaidMoney() {
		return this.wholesaleReturnPaidMoney;
	}

	public void setWholesaleReturnPaidMoney(BigDecimal wholesaleReturnPaidMoney) {
		if(wholesaleReturnPaidMoney != null){
			wholesaleReturnPaidMoney = wholesaleReturnPaidMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		this.wholesaleReturnPaidMoney = wholesaleReturnPaidMoney;
	}

	public Date getWholesaleReturnLastestPaymentDate() {
		return this.wholesaleReturnLastestPaymentDate;
	}

	public void setWholesaleReturnLastestPaymentDate(
			Date wholesaleReturnLastestPaymentDate) {
		this.wholesaleReturnLastestPaymentDate = wholesaleReturnLastestPaymentDate;
	}

	public String getWholesaleReturnUuid() {
		return this.wholesaleReturnUuid;
	}

	public void setWholesaleReturnUuid(String wholesaleReturnUuid) {
		this.wholesaleReturnUuid = wholesaleReturnUuid;
	}

	public Boolean getWholesaleReturnTransferFlag() {
		return this.wholesaleReturnTransferFlag;
	}

	public void setWholesaleReturnTransferFlag(
			Boolean wholesaleReturnTransferFlag) {
		this.wholesaleReturnTransferFlag = wholesaleReturnTransferFlag;
	}

	public String getClientFid() {
		return clientFid;
	}

	public void setClientFid(String clientFid) {
		this.clientFid = clientFid;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<WholesaleReturnDetail> getWholesaleReturnDetails() {
		return wholesaleReturnDetails;
	}

	public void setWholesaleReturnDetails(
			List<WholesaleReturnDetail> wholesaleReturnDetails) {
		this.wholesaleReturnDetails = wholesaleReturnDetails;
	}

	public String getShipOrderFid() {
		return shipOrderFid;
	}

	public void setShipOrderFid(String shipOrderFid) {
		this.shipOrderFid = shipOrderFid;
	}
	
	public Integer getWholesaleReturnPrintCount() {
		return wholesaleReturnPrintCount;
	}

	public void setWholesaleReturnPrintCount(Integer wholesaleReturnPrintCount) {
		this.wholesaleReturnPrintCount = wholesaleReturnPrintCount;
	}

	public void removeZeroDetail(){
		for(int i = wholesaleReturnDetails.size() - 1;i >= 0;i--){
			WholesaleReturnDetail detail = wholesaleReturnDetails.get(i);
			if(detail.getReturnDetailPresentQty() == null){
				detail.setReturnDetailPresentQty(BigDecimal.ZERO);
			}
			if(detail.getReturnDetailQty().compareTo(BigDecimal.ZERO) == 0 
					&& detail.getReturnDetailPresentQty().compareTo(BigDecimal.ZERO) == 0){
				wholesaleReturnDetails.remove(i);
			}
		}
	}
	
	public static WholesaleReturn get(List<WholesaleReturn> wholesaleReturns, String wholesaleReturnFid){
		for(int i = 0;i < wholesaleReturns.size();i++){
			WholesaleReturn wholesaleReturn = wholesaleReturns.get(i);
			if(wholesaleReturn.getWholesaleReturnFid().equals(wholesaleReturnFid)){
				return wholesaleReturn;
			}
		}
		return null;
	}
	
	public void recal() {
		wholesaleReturnTotalMoney = BigDecimal.ZERO;
		for (int i = wholesaleReturnDetails.size() - 1; i >= 0; i--) {
			WholesaleReturnDetail detail = wholesaleReturnDetails.get(i);
			
			if(detail.getReturnDetailUseQty().compareTo(BigDecimal.ZERO) != 0){
				detail.setReturnDetailMoney(detail.getReturnDetailUseQty().multiply(detail.getReturnDetailUsePrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
				detail.setReturnDetailQty(detail.getReturnDetailUseQty().multiply(detail.getReturnDetailUseRate()));
				
			}
			if(detail.getReturnDetailQty().compareTo(BigDecimal.ZERO) > 0){
				detail.setReturnDetailPrice(detail.getReturnDetailMoney().divide(detail.getReturnDetailQty(), 4, BigDecimal.ROUND_HALF_UP));
			}
			wholesaleReturnTotalMoney = wholesaleReturnTotalMoney.add(detail.getReturnDetailMoney());
		}
		wholesaleReturnTotalMoney = wholesaleReturnTotalMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		wholesaleReturnDueMoney = wholesaleReturnTotalMoney.negate();
	}

}
