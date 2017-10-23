package com.nhsoft.module.report.dto;

import java.io.Serializable;

public class MobileMarketActionDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2453292844790951902L;
	private String actionId;
	private String actionName;
	private int actionCount;//营销人数
	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public int getActionCount() {
		return actionCount;
	}
	public void setActionCount(int actionCount) {
		this.actionCount = actionCount;
	}
	

}
