package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.Date;

public class ShopPosItemImage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4847892447506451012L;
	private String systemBookCode;
	private String imageName;
	private String imageType;
	private byte[] imageContext;

	private Integer imageId;
	private Date imageCreated;
	private String imageUrl;
	private String imageThumbnail;

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public byte[] getImageContext() {
		return imageContext;
	}

	public void setImageContext(byte[] imageContext) {
		this.imageContext = imageContext;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public Date getImageCreated() {
		return imageCreated;
	}

	public void setImageCreated(Date imageCreated) {
		this.imageCreated = imageCreated;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageThumbnail() {
		return imageThumbnail;
	}

	public void setImageThumbnail(String imageThumbnail) {
		this.imageThumbnail = imageThumbnail;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

}
