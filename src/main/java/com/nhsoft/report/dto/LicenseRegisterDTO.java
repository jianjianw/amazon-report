package com.nhsoft.report.dto;

import com.nhsoft.report.model.Branch;

import java.io.Serializable;
import java.math.BigDecimal;

public class LicenseRegisterDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3140764210912329465L;
	private String registerName;// 用户名
	private String registerPhone;// 注册人联系电话（可作为用户电话）
	private String registerPsw;// 密码
	private String registerNumber;// 激活码
	private String registerBookName;// 账套名
	private String registerLinkMan;// 门店联系人
	private String registerLinkPhone;// 门店联系人电话
	private BigDecimal registerBranchLng;// 门店坐标-经度
	private BigDecimal registerBranchLat;// 门店坐标-纬度
	private String systemBookCode;
	private String registerBranchAddress;// 门店地址
	private String registerBranchName;// 门店名称
	private String parentBookCode;//上级账套

	private Branch branch;

	public String getParentBookCode() {
		return parentBookCode;
	}

	public void setParentBookCode(String parentBookCode) {
		this.parentBookCode = parentBookCode;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public String getRegisterBranchName() {
		return registerBranchName;
	}

	public void setRegisterBranchName(String registerBranchName) {
		this.registerBranchName = registerBranchName;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getRegisterPhone() {
		return registerPhone;
	}

	public void setRegisterPhone(String registerPhone) {
		this.registerPhone = registerPhone;
	}

	public String getRegisterPsw() {
		return registerPsw;
	}

	public void setRegisterPsw(String registerPsw) {
		this.registerPsw = registerPsw;
	}

	public String getRegisterNumber() {
		return registerNumber;
	}

	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	public String getRegisterBookName() {
		return registerBookName;
	}

	public void setRegisterBookName(String registerBookName) {
		this.registerBookName = registerBookName;
	}

	public String getRegisterLinkMan() {
		return registerLinkMan;
	}

	public void setRegisterLinkMan(String registerLinkMan) {
		this.registerLinkMan = registerLinkMan;
	}

	public String getRegisterLinkPhone() {
		return registerLinkPhone;
	}

	public void setRegisterLinkPhone(String registerLinkPhone) {
		this.registerLinkPhone = registerLinkPhone;
	}

	public BigDecimal getRegisterBranchLng() {
		return registerBranchLng;
	}

	public void setRegisterBranchLng(BigDecimal registerBranchLng) {
		this.registerBranchLng = registerBranchLng;
	}

	public BigDecimal getRegisterBranchLat() {
		return registerBranchLat;
	}

	public void setRegisterBranchLat(BigDecimal registerBranchLat) {
		this.registerBranchLat = registerBranchLat;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public String getRegisterBranchAddress() {
		return registerBranchAddress;
	}

	public void setRegisterBranchAddress(String registerBranchAddress) {
		this.registerBranchAddress = registerBranchAddress;
	}

}
