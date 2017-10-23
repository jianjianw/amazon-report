package com.nhsoft.module.report.model;

import java.io.Serializable;

public class MarketActionDetailId implements Serializable{

	private static final long serialVersionUID = -5025375005165135L;
	private String actionId;
	private Integer marketActionDetailNum;
	
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public Integer getMarketActionDetailNum() {
		return marketActionDetailNum;
	}
	public void setMarketActionDetailNum(Integer marketActionDetailNum) {
		this.marketActionDetailNum = marketActionDetailNum;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((actionId == null) ? 0 : actionId.hashCode());
		result = prime
				* result
				+ ((marketActionDetailNum == null) ? 0 : marketActionDetailNum
						.hashCode());
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
		MarketActionDetailId other = (MarketActionDetailId) obj;
		if (actionId == null) {
			if (other.actionId != null)
				return false;
		} else if (!actionId.equals(other.actionId))
			return false;
		if (marketActionDetailNum == null) {
			if (other.marketActionDetailNum != null)
				return false;
		} else if (!marketActionDetailNum.equals(other.marketActionDetailNum))
			return false;
		return true;
	}

	
	
}
