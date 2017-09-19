package com.nhsoft.report.model;


import com.nhsoft.report.shared.State;

import java.math.BigDecimal;
import java.util.Date;

/**
 * OriCardUser generated by hbm2java
 */
public class OriCardUser implements java.io.Serializable {

	/**
	 * 
	 */
	public static class OriCardUserId implements java.io.Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2944271185144700656L;
		private Integer cardUserNum;
		private String systemBookCode;

		public OriCardUserId() {
		}

		public OriCardUserId(Integer cardUserNum, String systemBookCode) {
			this.cardUserNum = cardUserNum;
			this.systemBookCode = systemBookCode;
		}

		public Integer getCardUserNum() {
			return cardUserNum;
		}

		public void setCardUserNum(Integer cardUserNum) {
			this.cardUserNum = cardUserNum;
		}

		public String getSystemBookCode() {
			return systemBookCode;
		}

		public void setSystemBookCode(String systemBookCode) {
			this.systemBookCode = systemBookCode;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((cardUserNum == null) ? 0 : cardUserNum.hashCode());
			result = prime * result + ((systemBookCode == null) ? 0 : systemBookCode.hashCode());
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
			OriCardUserId other = (OriCardUserId) obj;
			if (cardUserNum == null) {
				if (other.cardUserNum != null)
					return false;
			} else if (!cardUserNum.equals(other.cardUserNum))
				return false;
			if (systemBookCode == null) {
				if (other.systemBookCode != null)
					return false;
			} else if (!systemBookCode.equals(other.systemBookCode))
				return false;
			return true;
		}

	}

	private static final long serialVersionUID = 5988622627409304729L;
	private OriCardUserId id;
	private String cardUserCustName;
	private Date cardUserBirth;
	private String cardUserCustSex;
	private String cardUserIdCardType;
	private String cardUserIdCardNum;
	private String cardUserPhone;
	private String cardUserAddress;
	private String cardUserEmail;
	private Date cardUserDeadline;
	private String cardUserPassword;
	private Integer cardUserCardType;
	private String cardUserStorage;
	private String cardUserMemo;
	private String cardUserTips;
	private String cardUserFirm;
	private String cardUserPhysicalNum;
	private String cardUserPrintedNum;
	private State state;
	private String cardUserOperator;
	private BigDecimal cardBalanceMoney;
	private BigDecimal cardBalancePoint;
	private String cardUserNewPrintedNum;
	private Integer cardUserNewNum;
	private Date cardUserOperateTime;
	private Integer cardUserOperateBranch;
	private String cardUserOperateBizday;
	private Integer cardUserOperateShift;

	// 临时属性
	private Integer cardUserNum;  //jsonRPC传数据用
	private String systemBookCode;//jsonRPC传数据用

	public OriCardUser() {
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public Integer getCardUserNum() {
		return cardUserNum;
	}

	public void setCardUserNum(Integer cardUserNum) {
		this.cardUserNum = cardUserNum;
	}

	public String getCardUserCustName() {
		return this.cardUserCustName;
	}

	public void setCardUserCustName(String cardUserCustName) {
		this.cardUserCustName = cardUserCustName;
	}

	public Date getCardUserBirth() {
		return this.cardUserBirth;
	}

	public void setCardUserBirth(Date cardUserBirth) {
		this.cardUserBirth = cardUserBirth;
	}

	public String getCardUserCustSex() {
		return this.cardUserCustSex;
	}

	public void setCardUserCustSex(String cardUserCustSex) {
		this.cardUserCustSex = cardUserCustSex;
	}

	public String getCardUserIdCardType() {
		return this.cardUserIdCardType;
	}

	public void setCardUserIdCardType(String cardUserIdCardType) {
		this.cardUserIdCardType = cardUserIdCardType;
	}

	public String getCardUserIdCardNum() {
		return this.cardUserIdCardNum;
	}

	public void setCardUserIdCardNum(String cardUserIdCardNum) {
		this.cardUserIdCardNum = cardUserIdCardNum;
	}

	public String getCardUserPhone() {
		return this.cardUserPhone;
	}

	public void setCardUserPhone(String cardUserPhone) {
		this.cardUserPhone = cardUserPhone;
	}

	public String getCardUserAddress() {
		return this.cardUserAddress;
	}

	public void setCardUserAddress(String cardUserAddress) {
		this.cardUserAddress = cardUserAddress;
	}

	public String getCardUserEmail() {
		return this.cardUserEmail;
	}

	public void setCardUserEmail(String cardUserEmail) {
		this.cardUserEmail = cardUserEmail;
	}

	public Date getCardUserDeadline() {
		return this.cardUserDeadline;
	}

	public void setCardUserDeadline(Date cardUserDeadline) {
		this.cardUserDeadline = cardUserDeadline;
	}

	public String getCardUserPassword() {
		return this.cardUserPassword;
	}

	public void setCardUserPassword(String cardUserPassword) {
		this.cardUserPassword = cardUserPassword;
	}

	public Integer getCardUserCardType() {
		return this.cardUserCardType;
	}

	public void setCardUserCardType(Integer cardUserCardType) {
		this.cardUserCardType = cardUserCardType;
	}

	public String getCardUserStorage() {
		return this.cardUserStorage;
	}

	public void setCardUserStorage(String cardUserStorage) {
		this.cardUserStorage = cardUserStorage;
	}

	public String getCardUserMemo() {
		return this.cardUserMemo;
	}

	public void setCardUserMemo(String cardUserMemo) {
		this.cardUserMemo = cardUserMemo;
	}

	public String getCardUserTips() {
		return this.cardUserTips;
	}

	public void setCardUserTips(String cardUserTips) {
		this.cardUserTips = cardUserTips;
	}

	public String getCardUserFirm() {
		return this.cardUserFirm;
	}

	public void setCardUserFirm(String cardUserFirm) {
		this.cardUserFirm = cardUserFirm;
	}

	public String getCardUserPhysicalNum() {
		return this.cardUserPhysicalNum;
	}

	public void setCardUserPhysicalNum(String cardUserPhysicalNum) {
		this.cardUserPhysicalNum = cardUserPhysicalNum;
	}

	public String getCardUserPrintedNum() {
		return this.cardUserPrintedNum;
	}

	public void setCardUserPrintedNum(String cardUserPrintedNum) {
		this.cardUserPrintedNum = cardUserPrintedNum;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getCardUserOperator() {
		return this.cardUserOperator;
	}

	public void setCardUserOperator(String cardUserOperator) {
		this.cardUserOperator = cardUserOperator;
	}

	public BigDecimal getCardBalanceMoney() {
		return this.cardBalanceMoney;
	}

	public void setCardBalanceMoney(BigDecimal cardBalanceMoney) {
		this.cardBalanceMoney = cardBalanceMoney;
	}

	public BigDecimal getCardBalancePoint() {
		return this.cardBalancePoint;
	}

	public void setCardBalancePoint(BigDecimal cardBalancePoint) {
		this.cardBalancePoint = cardBalancePoint;
	}

	public String getCardUserNewPrintedNum() {
		return this.cardUserNewPrintedNum;
	}

	public void setCardUserNewPrintedNum(String cardUserNewPrintedNum) {
		this.cardUserNewPrintedNum = cardUserNewPrintedNum;
	}

	public Integer getCardUserNewNum() {
		return this.cardUserNewNum;
	}

	public void setCardUserNewNum(Integer cardUserNewNum) {
		this.cardUserNewNum = cardUserNewNum;
	}

	public Date getCardUserOperateTime() {
		return this.cardUserOperateTime;
	}

	public void setCardUserOperateTime(Date cardUserOperateTime) {
		this.cardUserOperateTime = cardUserOperateTime;
	}

	public Integer getCardUserOperateBranch() {
		return this.cardUserOperateBranch;
	}

	public void setCardUserOperateBranch(Integer cardUserOperateBranch) {
		this.cardUserOperateBranch = cardUserOperateBranch;
	}

	public String getCardUserOperateBizday() {
		return this.cardUserOperateBizday;
	}

	public void setCardUserOperateBizday(String cardUserOperateBizday) {
		this.cardUserOperateBizday = cardUserOperateBizday;
	}

	public Integer getCardUserOperateShift() {
		return this.cardUserOperateShift;
	}

	public void setCardUserOperateShift(Integer cardUserOperateShift) {
		this.cardUserOperateShift = cardUserOperateShift;
	}

	public OriCardUserId getId() {
		return id;
	}

	public void setId(OriCardUserId id) {
		this.id = id;
	}

}
