package com.nhsoft.report.model;



import java.util.Date;

/**
 * ItemVersion generated by hbm2java
 */
public class ItemVersion implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5995638568399178168L;
	private String itemVersionId;
	private String systemBookCode;
	private String itemVersionType;
	private Date itemVersionTime;
	private String itemVersionOperator;
	private String itemVersionContext;
	private String itemVersionRef;
	private Boolean itemVersionUpload;

	public ItemVersion() {
	}

	public String getItemVersionId() {
		return this.itemVersionId;
	}

	public void setItemVersionId(String itemVersionId) {
		this.itemVersionId = itemVersionId;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public String getItemVersionType() {
		return this.itemVersionType;
	}

	public void setItemVersionType(String itemVersionType) {
		this.itemVersionType = itemVersionType;
	}

	public Date getItemVersionTime() {
		return this.itemVersionTime;
	}

	public void setItemVersionTime(Date itemVersionTime) {
		this.itemVersionTime = itemVersionTime;
	}

	public String getItemVersionOperator() {
		return this.itemVersionOperator;
	}

	public void setItemVersionOperator(String itemVersionOperator) {
		this.itemVersionOperator = itemVersionOperator;
	}

	public String getItemVersionContext() {
		return this.itemVersionContext;
	}

	public void setItemVersionContext(String itemVersionContext) {
		this.itemVersionContext = itemVersionContext;
	}

	public String getItemVersionRef() {
		return this.itemVersionRef;
	}

	public void setItemVersionRef(String itemVersionRef) {
		this.itemVersionRef = itemVersionRef;
	}

	public Boolean getItemVersionUpload() {
		return itemVersionUpload;
	}

	public void setItemVersionUpload(Boolean itemVersionUpload) {
		this.itemVersionUpload = itemVersionUpload;
	}
	
	public String createOssObjectId(){
		
		return String.format("%s/ITEM_VERSION/%s/%s/%s.txt", systemBookCode, itemVersionType,
				itemVersionRef, itemVersionId);
		
	}
	
}