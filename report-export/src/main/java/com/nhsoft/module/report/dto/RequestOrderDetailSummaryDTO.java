package com.nhsoft.module.report.dto;


import com.nhsoft.module.report.annotation.ReportKey;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RequestOrderDetailSummaryDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3364988417199318396L;
	@ReportKey
	private Integer itemNum;
	@ReportKey
	private Integer branchNum;
	private String branchName;
	private BigDecimal requestOrderDetailQty = BigDecimal.ZERO;
	private BigDecimal requestOrderDetailOutQty = BigDecimal.ZERO; //配送数量+支配数量
	private BigDecimal requestOrderDetailAvailQty; //可配数量
	private List<String> requestOrderFids = new ArrayList<String>();
	
	public List<String> getRequestOrderFids() {
		return requestOrderFids;
	}
	
	public void setRequestOrderFids(List<String> requestOrderFids) {
		this.requestOrderFids = requestOrderFids;
	}
	
	public Integer getItemNum() {
		return itemNum;
	}
	public Integer getBranchNum() {
		return branchNum;
	}
	public BigDecimal getRequestOrderDetailQty() {
		return requestOrderDetailQty;
	}
	public BigDecimal getRequestOrderDetailOutQty() {
		return requestOrderDetailOutQty;
	}
	public BigDecimal getRequestOrderDetailAvailQty() {
		return requestOrderDetailAvailQty;
	}
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}
	public void setRequestOrderDetailQty(BigDecimal requestOrderDetailQty) {
		this.requestOrderDetailQty = requestOrderDetailQty;
	}
	public void setRequestOrderDetailOutQty(BigDecimal requestOrderDetailOutQty) {
		this.requestOrderDetailOutQty = requestOrderDetailOutQty;
	}
	public void setRequestOrderDetailAvailQty(BigDecimal requestOrderDetailAvailQty) {
		this.requestOrderDetailAvailQty = requestOrderDetailAvailQty;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public static RequestOrderDetailSummaryDTO get(List<RequestOrderDetailSummaryDTO> list, Integer itemNum, Integer branchNum){
		for(int i = 0;i < list.size();i++){
			RequestOrderDetailSummaryDTO dto = list.get(i);
			if(dto.getItemNum().equals(itemNum) && dto.getBranchNum().equals(branchNum)){
				return dto;
			}
		}
		return null;
	
	}
	
			
}
