package com.nhsoft.report.dto;



import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * GroupCustomerStatistics generated by hbm2java
 */
public class GroupCustomerStatisticsDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3896661398176217338L;
	private String groupCustomerId;
	private String systemBookCode;
	private Integer branchNum;
	private Integer customerStatisticsCount;
	private BigDecimal customerStatisticsDepositCash;
	private BigDecimal customerStatisticsDepositMoney;
	private BigDecimal customerStatisticsConsumeMoney;
	private Integer customerStatisticsConusmeCount;
	private BigDecimal customerStatisticsConusmePonit;
	private Integer customerStatisticsConusmeTicket;
	private Date customerStatisticsTime;

	public GroupCustomerStatisticsDTO() {
		customerStatisticsCount = 0;
		customerStatisticsDepositCash = BigDecimal.ZERO;
		customerStatisticsDepositMoney = BigDecimal.ZERO;
		customerStatisticsConsumeMoney = BigDecimal.ZERO;
		customerStatisticsConusmeCount = 0;
		customerStatisticsConusmePonit = BigDecimal.ZERO;
		customerStatisticsConusmeTicket = 0;
	}

	public String getGroupCustomerId() {
		return this.groupCustomerId;
	}

	public void setGroupCustomerId(String groupCustomerId) {
		this.groupCustomerId = groupCustomerId;
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

	public Integer getCustomerStatisticsCount() {
		return this.customerStatisticsCount;
	}

	public void setCustomerStatisticsCount(Integer customerStatisticsCount) {
		this.customerStatisticsCount = customerStatisticsCount;
	}

	public BigDecimal getCustomerStatisticsDepositCash() {
		return this.customerStatisticsDepositCash;
	}

	public void setCustomerStatisticsDepositCash(BigDecimal customerStatisticsDepositCash) {
		this.customerStatisticsDepositCash = customerStatisticsDepositCash;
	}

	public BigDecimal getCustomerStatisticsDepositMoney() {
		return this.customerStatisticsDepositMoney;
	}

	public void setCustomerStatisticsDepositMoney(BigDecimal customerStatisticsDepositMoney) {
		this.customerStatisticsDepositMoney = customerStatisticsDepositMoney;
	}

	public BigDecimal getCustomerStatisticsConsumeMoney() {
		return this.customerStatisticsConsumeMoney;
	}

	public void setCustomerStatisticsConsumeMoney(BigDecimal customerStatisticsConsumeMoney) {
		this.customerStatisticsConsumeMoney = customerStatisticsConsumeMoney;
	}

	public Integer getCustomerStatisticsConusmeCount() {
		return this.customerStatisticsConusmeCount;
	}

	public void setCustomerStatisticsConusmeCount(Integer customerStatisticsConusmeCount) {
		this.customerStatisticsConusmeCount = customerStatisticsConusmeCount;
	}

	public BigDecimal getCustomerStatisticsConusmePonit() {
		return this.customerStatisticsConusmePonit;
	}

	public void setCustomerStatisticsConusmePonit(BigDecimal customerStatisticsConusmePonit) {
		this.customerStatisticsConusmePonit = customerStatisticsConusmePonit;
	}

	public Integer getCustomerStatisticsConusmeTicket() {
		return this.customerStatisticsConusmeTicket;
	}

	public void setCustomerStatisticsConusmeTicket(Integer customerStatisticsConusmeTicket) {
		this.customerStatisticsConusmeTicket = customerStatisticsConusmeTicket;
	}

	public Date getCustomerStatisticsTime() {
		return customerStatisticsTime;
	}

	public void setCustomerStatisticsTime(Date customerStatisticsTime) {
		this.customerStatisticsTime = customerStatisticsTime;
	}
	
	public static GroupCustomerStatisticsDTO getGroupCustomerStatistics(String groupCustomerId, List<GroupCustomerStatisticsDTO> groupCustomerStatisticses) {
		if(groupCustomerId == null || groupCustomerStatisticses == null) {
			return null;
		}
		for(GroupCustomerStatisticsDTO o : groupCustomerStatisticses) {
			if(o.getGroupCustomerId().equals(groupCustomerId)) {
				return o;
			}
		}
		return null;
	}

}