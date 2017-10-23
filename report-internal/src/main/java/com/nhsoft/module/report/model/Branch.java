package com.nhsoft.module.report.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.ArrayList;
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
	private Integer branchRegionNum;
	private String branchCode;
	private String branchName;
	private Boolean branchRdc;
	private String branchType;
	private String branchModule;
	private Boolean branchActived;
	private Boolean branchMatrixPriceActived;
	
	public Branch(){
		
	}
	
	
	public Boolean getBranchMatrixPriceActived() {
		return branchMatrixPriceActived;
	}
	
	public void setBranchMatrixPriceActived(Boolean branchMatrixPriceActived) {
		this.branchMatrixPriceActived = branchMatrixPriceActived;
	}
	
	public Branch(String systemBookCode, Integer branchNum){
		setId(new BranchId(systemBookCode, branchNum));
	}

	public BranchId getId() {
		return id;
	}

	public void setId(BranchId id) {
		this.id = id;
	}


	public String getBranchModule() {
		return branchModule;
	}

	public void setBranchModule(String branchModule) {
		this.branchModule = branchModule;
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
/*	@JsonIgnore
	public boolean isJoinBranch() {
		if (!id.getBranchNum().equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)
				&& StringUtils.equals(branchType, AppConstants.BRANCH_TYPE_JOIN)) {
			return true;
		} else {
			return false;
		}
	}*/
	
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