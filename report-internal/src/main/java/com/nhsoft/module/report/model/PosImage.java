package com.nhsoft.module.report.model;

import java.util.Date;

/**
 * PosImage entity. @author MyEclipse Persistence Tools
 */

public class PosImage implements java.io.Serializable {

	private static final long serialVersionUID = 4596685954662561706L;
	private String posImageId;
	private Integer itemNum;
	private String systemBookCode;
	private byte[] posImageResource;
	private Boolean posImageDefault;
	private Boolean posImageUpdated;
	private String posImageType;
	private Boolean posImageSynchTag;
	private Date posImageCreationDate;
	private Date posImageLastModdate;
	private String posImageUrl;
	private Boolean posImageDelTag;
	private Integer posImageWidth;
	private Integer posImageHeight;
	private Integer posImageFileSize;
	
	public Integer getPosImageFileSize() {
		return posImageFileSize;
	}

	public void setPosImageFileSize(Integer posImageFileSize) {
		this.posImageFileSize = posImageFileSize;
	}

	public String getPosImageUrl() {
		return posImageUrl;
	}

	public void setPosImageUrl(String posImageUrl) {
		this.posImageUrl = posImageUrl;
	}

	public Boolean getPosImageDelTag() {
		return posImageDelTag;
	}

	public void setPosImageDelTag(Boolean posImageDelTag) {
		this.posImageDelTag = posImageDelTag;
	}

	public Boolean getPosImageSynchTag() {
		return posImageSynchTag;
	}

	public void setPosImageSynchTag(Boolean posImageSynchTag) {
		this.posImageSynchTag = posImageSynchTag;
	}

	public Boolean getPosImageUpdated() {
		return posImageUpdated;
	}

	public void setPosImageUpdated(Boolean posImageUpdated) {
		this.posImageUpdated = posImageUpdated;
	}

	public Boolean getPosImageDefault() {
		return posImageDefault;
	}

	public void setPosImageDefault(Boolean posImageDefault) {
		this.posImageDefault = posImageDefault;
	}

	public String getPosImageType() {
		return posImageType;
	}

	public void setPosImageType(String posImageType) {
		this.posImageType = posImageType;
	}

	public String getPosImageId() {
		return posImageId;
	}

	public void setPosImageId(String posImageId) {
		this.posImageId = posImageId;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public byte[] getPosImageResource() {
		return posImageResource;
	}

	public void setPosImageResource(byte[] posImageResource) {
		this.posImageResource = posImageResource;
	}

	public Date getPosImageCreationDate() {
		return posImageCreationDate;
	}

	public void setPosImageCreationDate(Date posImageCreationDate) {
		this.posImageCreationDate = posImageCreationDate;
	}

	public Date getPosImageLastModdate() {
		return posImageLastModdate;
	}

	public void setPosImageLastModdate(Date posImageLastModdate) {
		this.posImageLastModdate = posImageLastModdate;
	}
	
	public Integer getPosImageWidth() {
		return posImageWidth;
	}

	public void setPosImageWidth(Integer posImageWidth) {
		this.posImageWidth = posImageWidth;
	}

	public Integer getPosImageHeight() {
		return posImageHeight;
	}

	public void setPosImageHeight(Integer posImageHeight) {
		this.posImageHeight = posImageHeight;
	}

	public String getOssImagePath(){
		return systemBookCode + "/posImage/" + itemNum + "/" + posImageId + posImageType;
	}

	public String getOssImageReadPath(){
		return "http://image.nhsoft.cn/" + getOssImagePath();
	}
}