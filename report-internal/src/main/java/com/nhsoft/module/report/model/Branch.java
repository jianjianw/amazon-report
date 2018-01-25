package com.nhsoft.module.report.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhsoft.module.report.util.AppConstants;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Entity
public class Branch implements java.io.Serializable {

	private static final Logger logger = LoggerFactory.getLogger(Branch.class);
	private static final long serialVersionUID = 3898947251184465028L;
	@Embeddable
	public static class BranchId implements java.io.Serializable {
		
		private static final long serialVersionUID = -3155329949992475730L;
		private String systemBookCode;
		private Integer branchNum;
		
		public BranchId(){
		
		}
		
		public BranchId(String systemBookCode, Integer branchNum){
			this.systemBookCode = systemBookCode;
			this.branchNum = branchNum;
		}
		
		
		public String getSystemBookCode() {
			return systemBookCode;
		}
		
		public void setSystemBookCode(String systemBookCode) {
			this.systemBookCode = systemBookCode;
		}
		
		public Integer getBranchNum() {
			return branchNum;
		}
		
		public void setBranchNum(Integer branchNum) {
			this.branchNum = branchNum;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((branchNum == null) ? 0 : branchNum.hashCode());
			result = prime * result
					+ ((systemBookCode == null) ? 0 : systemBookCode.hashCode());
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
			BranchId other = (BranchId) obj;
			if (branchNum == null) {
				if (other.branchNum != null)
					return false;
			} else if (!branchNum.equals(other.branchNum))
				return false;
			if (systemBookCode == null) {
				if (other.systemBookCode != null)
					return false;
			} else if (!systemBookCode.equals(other.systemBookCode))
				return false;
			return true;
		}
		
		
		
	}
	@EmbeddedId
	private BranchId id;
	private String abchinaAccountId;
	private String toAbchinaAccountId;
	private Integer branchRegionNum;
	private String branchCode;
	private String branchName;
	private Boolean branchActived;
	private String branchPostcode;
	private String branchFax;
	private String branchPhone;
	private Boolean branchRdc;
	private String branchType;
	private String branchTransferType;
	private Boolean branchMatrixPriceActived;
	private String branchSynchId;
	private String branchDigest;
	@Transient
	private String branchLicense;
	private BigDecimal branchCreditLimit;
	private String branchBankNo;
	private Boolean branchRequestCheckMoney;
	private BigDecimal branchArea;
	private Integer branchOffLineDay;
	private BigDecimal branchCardBalanceLimit;
	private String branchModule;
	private String branchRegionType;
	private Integer branchEmployeeCount;
	private Date branchExpiration;
	private BigDecimal branchRent;
	private Integer branchStatus;
	private String branchProduct;
	private Boolean branchVirtual; // 是否虚拟店
	private Date branchCreateTime;
	@Transient
	private BigDecimal branchMatrixLongitude; //经度
	@Transient
	private BigDecimal branchMatrixLatitude; //纬度
	
	public Branch(){
		
	}
	
	public Date getBranchCreateTime() {
		return branchCreateTime;
	}

	public void setBranchCreateTime(Date branchCreateTime) {
		this.branchCreateTime = branchCreateTime;
	}

	public Branch(String systemBookCode, Integer branchNum){
		setId(new BranchId(systemBookCode, branchNum));
	}

	public Boolean getBranchVirtual() {
		return branchVirtual;
	}

	public void setBranchVirtual(Boolean branchVirtual) {
		this.branchVirtual = branchVirtual;
	}

	public String getBranchProduct() {
		return branchProduct;
	}

	public void setBranchProduct(String branchProduct) {
		this.branchProduct = branchProduct;
	}

	public Integer getBranchStatus() {
		return branchStatus;
	}

	public void setBranchStatus(Integer branchStatus) {
		this.branchStatus = branchStatus;
	}

	public BranchId getId() {
		return id;
	}

	public void setId(BranchId id) {
		this.id = id;
	}

	public String getBranchRegionType() {
		return branchRegionType;
	}

	public void setBranchRegionType(String branchRegionType) {
		this.branchRegionType = branchRegionType;
	}

	public Integer getBranchEmployeeCount() {
		return branchEmployeeCount;
	}

	public void setBranchEmployeeCount(Integer branchEmployeeCount) {
		this.branchEmployeeCount = branchEmployeeCount;
	}

	public String getBranchModule() {
		return branchModule;
	}

	public void setBranchModule(String branchModule) {
		this.branchModule = branchModule;
	}

	public String getAbchinaAccountId() {
		return abchinaAccountId;
	}

	public void setAbchinaAccountId(String abchinaAccountId) {
		this.abchinaAccountId = abchinaAccountId;
	}

	public String getToAbchinaAccountId() {
		return toAbchinaAccountId;
	}

	public void setToAbchinaAccountId(String toAbchinaAccountId) {
		this.toAbchinaAccountId = toAbchinaAccountId;
	}

	public BigDecimal getBranchMatrixLongitude() {
		return branchMatrixLongitude;
	}

	public BigDecimal getBranchMatrixLatitude() {
		return branchMatrixLatitude;
	}

	public void setBranchMatrixLongitude(BigDecimal branchMatrixLongitude) {
		this.branchMatrixLongitude = branchMatrixLongitude;
	}

	public void setBranchMatrixLatitude(BigDecimal branchMatrixLatitude) {
		this.branchMatrixLatitude = branchMatrixLatitude;
	}

	public Integer getBranchRegionNum() {
		return branchRegionNum;
	}

	public void setBranchRegionNum(Integer branchRegionNum) {
		this.branchRegionNum = branchRegionNum;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Boolean getBranchActived() {
		return branchActived;
	}

	public void setBranchActived(Boolean branchActived) {
		this.branchActived = branchActived;
	}

	public String getBranchPostcode() {
		return branchPostcode;
	}

	public void setBranchPostcode(String branchPostcode) {
		this.branchPostcode = branchPostcode;
	}

	public String getBranchFax() {
		return branchFax;
	}

	public void setBranchFax(String branchFax) {
		this.branchFax = branchFax;
	}

	public String getBranchPhone() {
		return branchPhone;
	}

	public void setBranchPhone(String branchPhone) {
		this.branchPhone = branchPhone;
	}

	public Boolean getBranchRdc() {
		return branchRdc;
	}

	public void setBranchRdc(Boolean branchRdc) {
		this.branchRdc = branchRdc;
	}

	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

	public String getBranchTransferType() {
		return branchTransferType;
	}

	public void setBranchTransferType(String branchTransferType) {
		this.branchTransferType = branchTransferType;
	}

	public Boolean getBranchMatrixPriceActived() {
		if(branchMatrixPriceActived == null){
			return false;
		}
		return branchMatrixPriceActived;
	}

	public void setBranchMatrixPriceActived(Boolean branchMatrixPriceActived) {
		this.branchMatrixPriceActived = branchMatrixPriceActived;
	}

	public String getBranchSynchId() {
		return branchSynchId;
	}

	public void setBranchSynchId(String branchSynchId) {
		this.branchSynchId = branchSynchId;
	}

	public String getBranchDigest() {
		return branchDigest;
	}

	public void setBranchDigest(String branchDigest) {
		this.branchDigest = branchDigest;
	}

	public String getBranchLicense() {
		return branchLicense;
	}

	public void setBranchLicense(String branchLicense) {
		this.branchLicense = branchLicense;
	}

	public BigDecimal getBranchCreditLimit() {
		return branchCreditLimit;
	}

	public void setBranchCreditLimit(BigDecimal branchCreditLimit) {
		this.branchCreditLimit = branchCreditLimit;
	}

	public String getBranchBankNo() {
		return branchBankNo;
	}

	public void setBranchBankNo(String branchBankNo) {
		this.branchBankNo = branchBankNo;
	}

	public Boolean getBranchRequestCheckMoney() {
		return branchRequestCheckMoney;
	}

	public void setBranchRequestCheckMoney(Boolean branchRequestCheckMoney) {
		this.branchRequestCheckMoney = branchRequestCheckMoney;
	}

	public BigDecimal getBranchArea() {
		return branchArea;
	}

	public void setBranchArea(BigDecimal branchArea) {
		this.branchArea = branchArea;
	}

	public Integer getBranchOffLineDay() {
		return branchOffLineDay;
	}

	public void setBranchOffLineDay(Integer branchOffLineDay) {
		this.branchOffLineDay = branchOffLineDay;
	}

	public BigDecimal getBranchCardBalanceLimit() {
		return branchCardBalanceLimit;
	}

	public void setBranchCardBalanceLimit(BigDecimal branchCardBalanceLimit) {
		this.branchCardBalanceLimit = branchCardBalanceLimit;
	}

	public Date getBranchExpiration() {
		return branchExpiration;
	}

	public void setBranchExpiration(Date branchExpiration) {
		this.branchExpiration = branchExpiration;
	}

	public BigDecimal getBranchRent() {
		return branchRent;
	}

	public void setBranchRent(BigDecimal branchRent) {
		this.branchRent = branchRent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Branch other = (Branch) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public static List<Branch> readFromXml(String text) {
		List<Branch> branchs = new ArrayList<Branch>();
		if (StringUtils.isEmpty(text)) {
			return branchs;
		}
		try {
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			Iterator<Element> iterator = root.elementIterator("AppliedBranch");
			while (iterator.hasNext()) {
				Element element = iterator.next();
				Branch branch = new Branch();
				BranchId id = new BranchId();
				id.setBranchNum(Integer.parseInt(element.selectSingleNode("AppliedBranchNum").getText()));
				branch.setId(id);
				branch.setBranchName(element.selectSingleNode("AppliedBranchName").getText());
				branchs.add(branch);
			}
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		return branchs;
	}

	public static List<Integer> readNumsFromXml(String text) {
		List<Integer> branchNums = new ArrayList<Integer>();
		try {
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			Iterator<Element> iterator = root.elementIterator("AppliedBranch");
			while (iterator.hasNext()) {
				Element element = iterator.next();
				branchNums.add(Integer.parseInt(element.selectSingleNode("AppliedBranchNum").getText()));
			}
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		return branchNums;
	}

	public static String writeToXml(List<Branch> branchs) {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("AppliedBranchArray");
		for (int i = 0; i < branchs.size(); i++) {
			Branch branch = branchs.get(i);
			Element element = root.addElement("AppliedBranch");
			element.addElement("AppliedBranchNum").setText(branch.getId().getBranchNum().toString());
			element.addElement("AppliedBranchName").setText(branch.getBranchName());

		}
		return document.asXML();
	}
	/**
	 * 判断是否加盟店
	 *
	 * @return
	 */
	@JsonIgnore
	public boolean isJoinBranch() {
		if (!id.getBranchNum().equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)
				&& StringUtils.equals(branchType, AppConstants.BRANCH_TYPE_JOIN)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 判断是否配送中心
	 * @return
	 */
	@JsonIgnore
	public boolean isRdc(){
		if (branchRdc != null && branchRdc) {
			return true;
		}
		return false;
	}

	public static void writeToNode(List<Branch> branchs, Element root) {
		for (int i = 0; i < branchs.size(); i++) {
			Branch branch = branchs.get(i);
			Element element = root.addElement("AppliedBranch");
			element.addElement("AppliedBranchNum").setText(branch.getId().getBranchNum().toString());
			element.addElement("AppliedBranchName").setText(branch.getBranchName());

		}

	}

	public static List<Branch> readFromNode(Element root) {
		List<Branch> branchs = new ArrayList<Branch>();
		Iterator<Element> iterator = root.elementIterator("AppliedBranch");
		while (iterator.hasNext()) {
			Element element = iterator.next();
			Branch branch = new Branch();
			BranchId id = new BranchId();
			id.setBranchNum(Integer.parseInt(element.selectSingleNode("AppliedBranchNum").getText()));
			branch.setId(id);
			branch.setBranchName(element.selectSingleNode("AppliedBranchName").getText());
			branchs.add(branch);
		}
		return branchs;
	}
	
	public static Branch get(List<Branch> branchs, Integer branchNum) {
		for (int i = 0; i < branchs.size(); i++) {
			if (branchs.get(i).getId().getBranchNum().equals(branchNum)) {
				return branchs.get(i);
			}
		}
		return null;
	}

}