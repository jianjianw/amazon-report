package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MobileCustomerModelDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6682001203574010740L;
	private String modelType;
	private String modelName;
	private BigDecimal modelValue;
	private Integer modelValueInt;
	private String modelMemo;

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public BigDecimal getModelValue() {
		return modelValue;
	}

	public void setModelValue(BigDecimal modelValue) {
		this.modelValue = modelValue;
	}

	public Integer getModelValueInt() {
		return modelValueInt;
	}

	public void setModelValueInt(Integer modelValueInt) {
		this.modelValueInt = modelValueInt;
	}

	public String getModelMemo() {
		return modelMemo;
	}

	public void setModelMemo(String modelMemo) {
		this.modelMemo = modelMemo;
	}

}
