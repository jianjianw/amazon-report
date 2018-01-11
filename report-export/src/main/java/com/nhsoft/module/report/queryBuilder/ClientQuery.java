package com.nhsoft.module.report.queryBuilder;

import com.nhsoft.module.report.query.QueryBuilder;

import java.util.Date;
import java.util.List;

public class ClientQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8495245084064379589L;
	private Integer branchNum;
	private String pinYin;
	private String code;
	private String name;
	private String var;
	private String phone;
	private String type;
	private Date birthDay;
	private Boolean year;
	private Boolean month;
	private Boolean day;
	private Boolean isEnable;
	private List<Integer> regionNums;
	private String address;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Integer> getRegionNums() {
		return regionNums;
	}

	public void setRegionNums(List<Integer> regionNums) {
		this.regionNums = regionNums;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Boolean getYear() {
		return year;
	}

	public void setYear(Boolean year) {
		this.year = year;
	}

	public Boolean getMonth() {
		return month;
	}

	public void setMonth(Boolean month) {
		this.month = month;
	}

	public Boolean getDay() {
		return day;
	}

	public void setDay(Boolean day) {
		this.day = day;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

}
