package com.nhsoft.module.report.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public class BranchDTO implements java.io.Serializable {

	private static final Logger logger = LoggerFactory.getLogger(BranchDTO.class);
	private static final long serialVersionUID = 3898947251184465028L;
	private String systemBookCode;
	private Integer branchNum;
	private String abchinaAccountId;
	private String toAbchinaAccountId;
	private Integer branchRegionNum;
	private String branchCode;
	private String branchName;
	private Boolean branchActived;
	private String branchPostcode;
	private String branchAddr;
	private String branchFax;
	private String branchPhone;
	private Boolean branchRdc;
	private String branchType;
	private String branchTransferType;
	private Boolean branchMatrixPriceActived;
	private String branchSynchId;
	private String branchDigest;
	private String branchLicense;
	private BigDecimal branchCreditLimit;
	private String branchBankNo;
	private Boolean branchRequestCheckMoney;
	private BigDecimal branchArea;
	private String branchLinkMan;
	private Integer branchOffLineDay;
	private BigDecimal branchCardBalanceLimit;
	private String branchModule;
	private String branchEmail;
	private String branchRegionType;
	private Integer branchEmployeeCount;
	private Date branchExpiration;
	private BigDecimal branchRent;
	private String branchSellerId;
	private String branchSellerEmail;
	private Integer branchStatus;
	private Integer managementTemplateNum;
	private Boolean branchSupportIc;
	private Boolean branchSupportId;
	private String branchProduct;
	private Boolean branchVirtual; // 是否虚拟店
	private Boolean branchKitEnabled;
	private Date branchCloseTime;
	private Boolean branchStockClosed;
	private Boolean branchTmall;
	private Date branchCreateTime;

	
	private BigDecimal branchMatrixLongitude; //经度
	private BigDecimal branchMatrixLatitude; //纬度
	
	public BranchDTO(){
		
	}
	
	public Date getBranchCreateTime() {
		return branchCreateTime;
	}

	public void setBranchCreateTime(Date branchCreateTime) {
		this.branchCreateTime = branchCreateTime;
	}

	public Boolean getBranchTmall() {
		return branchTmall;
	}

	public void setBranchTmall(Boolean branchTmall) {
		this.branchTmall = branchTmall;
	}

	public Boolean getBranchStockClosed() {
		return branchStockClosed;
	}

	public void setBranchStockClosed(Boolean branchStockClosed) {
		this.branchStockClosed = branchStockClosed;
	}

	public Date getBranchCloseTime() {
		return branchCloseTime;
	}

	public void setBranchCloseTime(Date branchCloseTime) {
		this.branchCloseTime = branchCloseTime;
	}

	public Boolean getBranchKitEnabled() {
		return branchKitEnabled;
	}

	public void setBranchKitEnabled(Boolean branchKitEnabled) {
		this.branchKitEnabled = branchKitEnabled;
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

	public Integer getManagementTemplateNum() {
		return managementTemplateNum;
	}

	public void setManagementTemplateNum(Integer managementTemplateNum) {
		this.managementTemplateNum = managementTemplateNum;
	}

	public Integer getBranchStatus() {
		return branchStatus;
	}

	public void setBranchStatus(Integer branchStatus) {
		this.branchStatus = branchStatus;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getBranchSellerId() {
		return branchSellerId;
	}

	public void setBranchSellerId(String branchSellerId) {
		this.branchSellerId = branchSellerId;
	}

	public String getBranchSellerEmail() {
		return branchSellerEmail;
	}

	public void setBranchSellerEmail(String branchSellerEmail) {
		this.branchSellerEmail = branchSellerEmail;
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

	public String getBranchEmail() {
		return branchEmail;
	}

	public void setBranchEmail(String branchEmail) {
		this.branchEmail = branchEmail;
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

	public String getBranchAddr() {
		return branchAddr;
	}

	public void setBranchAddr(String branchAddr) {
		this.branchAddr = branchAddr;
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

	public String getBranchLinkMan() {
		return branchLinkMan;
	}

	public void setBranchLinkMan(String branchLinkMan) {
		this.branchLinkMan = branchLinkMan;
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

	public Boolean getBranchSupportIc() {
		return branchSupportIc;
	}

	public void setBranchSupportIc(Boolean branchSupportIc) {
		this.branchSupportIc = branchSupportIc;
	}

	public Boolean getBranchSupportId() {
		return branchSupportId;
	}

	public void setBranchSupportId(Boolean branchSupportId) {
		this.branchSupportId = branchSupportId;
	}

	public static List<BranchDTO> readFromXml(String text) {
		List<BranchDTO> branchs = new ArrayList<BranchDTO>();
		if (StringUtils.isEmpty(text)) {
			return branchs;
		}
		try {
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			Iterator<Element> iterator = root.elementIterator("AppliedBranch");
			while (iterator.hasNext()) {
				Element element = iterator.next();
				BranchDTO branch = new BranchDTO();
				branch.setBranchNum(Integer.parseInt(element.selectSingleNode("AppliedBranchNum").getText()));
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

	public static String writeToXml(List<BranchDTO> branchs) {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("AppliedBranchArray");
		for (int i = 0; i < branchs.size(); i++) {
			BranchDTO branch = branchs.get(i);
			Element element = root.addElement("AppliedBranch");
			element.addElement("AppliedBranchNum").setText(branch.getBranchNum().toString());
			element.addElement("AppliedBranchName").setText(branch.getBranchName());

		}
		return document.asXML();
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

	public static void writeToNode(List<BranchDTO> branchs, Element root) {
		for (int i = 0; i < branchs.size(); i++) {
			BranchDTO branch = branchs.get(i);
			Element element = root.addElement("AppliedBranch");
			element.addElement("AppliedBranchNum").setText(branch.getBranchNum().toString());
			element.addElement("AppliedBranchName").setText(branch.getBranchName());

		}

	}

	public static List<BranchDTO> readFromNode(Element root) {
		List<BranchDTO> branchs = new ArrayList<BranchDTO>();
		Iterator<Element> iterator = root.elementIterator("AppliedBranch");
		while (iterator.hasNext()) {
			Element element = iterator.next();
			BranchDTO branch = new BranchDTO();
			branch.setBranchNum(Integer.parseInt(element.selectSingleNode("AppliedBranchNum").getText()));
			branch.setBranchName(element.selectSingleNode("AppliedBranchName").getText());
			branchs.add(branch);
		}
		return branchs;
	}

	public static BranchDTO getBranch(List<BranchDTO> list, Integer branchNum) {
		if(list == null) {
			return null;
		}
		return list.stream().filter(b -> b.getBranchNum().equals(branchNum)).findAny().orElse(null);
	}

	public static BranchDTO get(List<BranchDTO> branchs, Integer branchNum) {
		for (int i = 0; i < branchs.size(); i++) {
			if (branchs.get(i).getBranchNum().equals(branchNum)) {
				return branchs.get(i);
			}
		}
		return null;
	}

}