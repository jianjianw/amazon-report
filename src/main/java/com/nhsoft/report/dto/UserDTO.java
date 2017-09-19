package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class UserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3426254197374881389L;
	private Integer id;
	private String name;
	private Date birth;
	private BigDecimal height;
	private String pic;
	
	public UserDTO(){
		
	}
	
	public UserDTO(Integer id, String name, BigDecimal height){
		this.id = id;
		this.name = name;
		this.height = height;
		this.birth = Calendar.getInstance().getTime();
		this.pic = "";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

}
